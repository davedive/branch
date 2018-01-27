package com.branch.json;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserLogsJsonParser {

    public static String readLog(String fileName) throws IOException {
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
