package com.ruke.vrjassc.vrjassc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportPreProcessor {

    private static String getImport(String path) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(path)));
    }

    public static String process(String code) throws IOException {
        Matcher m = Pattern.compile("(//! *import *(.+)\n)").matcher(code);
        Set<String> imported = new HashSet<String>();
        String toImport = "";
        String result = code;

        while (m.find()) {
            toImport = getImport(m.group(2)) + "\n";

            if (!imported.contains(toImport)) {
                imported.add(toImport);
                result += toImport;
            }

            result = result.replaceAll(m.group(1), "");
        }

        return result;
    }

}
