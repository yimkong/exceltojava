package com.yimkong;

import com.yimkong.data.ColumnMeta;
import com.yimkong.data.TemplateMeta;
import com.yimkong.data.TypeEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * author yg
 * description
 * date 2019/11/6
 */
public class ExcelWriter {

    public void write(Collection<TemplateMeta> read, String packagePath, String out_path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter pw = null;
        File outFile = new File(out_path);
        if (!outFile.exists() || (outFile.exists() && !outFile.isDirectory())) {
            outFile.mkdirs();
        }
        try {
            for (TemplateMeta meta : read) {
                System.out.println("generator class: " + meta.name + ".java");
                File javaFile = new File(outFile.getPath() + "/" + meta.name + ".java");
//                if (javaFile.exists()) {
//                    System.err.println(javaFile.getName() + " is exists");
//                    return;
//                }
                pw = new PrintWriter(outFile.getPath() + "/" + meta.name + ".java", "utf-8");
                StringBuffer fieldSb = new StringBuffer();
                StringBuffer methodSb = new StringBuffer();
                Set<TypeEnum> typeSet = new HashSet<>();
                Set<String> objFields = new HashSet<>();
                for (ColumnMeta columnMeta : meta.columns) {
                    int index = columnMeta.name.indexOf(".");
                    if (index > 0) {
                        objFields.add(columnMeta.name.substring(0, index));
                        continue;
                    }
                    String name = columnMeta.name;
                    TypeEnum type = columnMeta.type;
                    String typeStr = type.getTypeStr();
                    if (type == TypeEnum.LIST)
                        typeStr += "<" + columnMeta.subType.get("").getObjTypeStr() + ">";
                    if (type == TypeEnum.MAP)
                        typeStr += "<" + columnMeta.subType.get("key").getObjTypeStr() + ","
                                + columnMeta.subType.get("value").getObjTypeStr() + ">";
                    if (type == TypeEnum.JSON)
                        typeStr = "JsonObject";
                    String desc = columnMeta.desc.replaceAll("\n|\r", " ");
                    fieldSb.append("\tprivate " + typeStr + " " + name + "; // " + desc + "\n");
                    methodSb.append("\n\tpublic " + typeStr + " get");
                    methodSb.append(Character.toUpperCase(name.charAt(0)) + name.substring(1) + "() {\n\t\treturn ");
                    methodSb.append(name + ";\n\t}\n");
                    typeSet.add(type);
                }
                for (String field : objFields) {
                    String type = Character.toUpperCase(field.charAt(0)) + field.substring(1);
                    fieldSb.append("\tprivate " + type + " " + field + ";\n");
                    methodSb.append("\n\tpublic " + type + " get");
                    methodSb.append(type + "() {\n\t\treturn ");
                    methodSb.append(field + ";\n\t}\n");
                }
                ColumnMeta pk = meta.columns.get(0);


                pw.println("package " + packagePath + ";\n");
                pw.println("import java.util.Map;");
                pw.println("import java.util.HashMap;");
                pw.println("import java.util.List;");
                if (typeSet.contains(TypeEnum.DATE))
                    pw.println("import java.util.Date;");
                pw.println();
                pw.println("import com.google.gson.JsonObject;");
                pw.println("import com.yaowan.game.common.template.LoadTemplate;");

                pw.println("\n//配置文件" + meta.fileName);
                pw.println("public class " + meta.name + "{\n");
                pw.print(fieldSb.toString());
                pw.print(methodSb.toString());
                pw.println("\n\tprivate static Map<" + pk.type.getObjTypeStr() + ", " + meta.name + "> cache = new HashMap<>();");
                pw.println("\n\tpublic static " + meta.name + " getConfig(" + pk.type.getTypeStr() + " " + pk.name + "){");
                pw.println("\t\treturn cache.get(" + pk.name + ");\n\t}\n");
                pw.println("\t@LoadTemplate\n\tpublic static void init(List<" + meta.name + "> list) {");
                pw.println("\t\tMap<" + pk.type.getObjTypeStr() + ", " + meta.name + "> m1 = new HashMap<>();");
                pw.println("\t\tfor (" + meta.name + " config : list) {");
                pw.println("\t\t\tm1.put(config." + pk.name + ", config);");
                pw.println("\t\t}");
                pw.println("\t\tcache = m1;");
                pw.println("\t}\n");
                pw.println("}\n");
            }
        } finally {
            if (pw != null)
                pw.close();
        }
    }
}
