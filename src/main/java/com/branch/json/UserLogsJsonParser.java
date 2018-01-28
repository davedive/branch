package com.branch.json;

import org.json.JSONArray;

import java.io.*;

public class UserLogsJsonParser {

    public static String readLog(String fileName) throws IOException {
        File f = new File(fileName);
        if (f.exists()) {
            String result = "";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
            return result;
        }
        return null;
    }

    public static void writeJson(String fileName, JSONArray json) {
        try {
            FileWriter file = new FileWriter(fileName);
            json.write(file);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
