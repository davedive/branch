package com.branch;

import com.branch.csv.UserStatusCsvReader;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.branch.json.UserLogsJsonParser.readLog;
import static com.branch.json.UserLogsJsonParser.writeJson;

public class App {

    private static final String USER_LOGS_DIR = "user_logs";
    private static final String USER_DIR_PREFIX = "/user-";
    private static final String DEVICE_DIR_PREFIX = "/device-";
    private static final String USER_STATUS_FILENAME = USER_LOGS_DIR + "/user_status.csv";
    private static final String USER_DEVICE_DIR = USER_LOGS_DIR + USER_DIR_PREFIX + "%d" + DEVICE_DIR_PREFIX + "%d";
    private static final String CALL_LOG_FILENAME = "/collated_call_log.txt";
    private static final String CONTACT_LIST_FILENAME = "/collated_contact_list.txt";
    private static final String SMS_LOG_FILENAME = "/collated_sms_log.txt";

    private static final String OUTPUT_FILENAME = "userFeatures.json";
    private static final String USER_ID = "userId";
    private static final String USER_STATUS = "userStatus";


    public static void main(String[] args) {

        //Parse user status CSV
        UserStatusCsvReader reader = new UserStatusCsvReader(USER_STATUS_FILENAME);
        Map<Integer, UserLoanStatus> statusMap = reader.parseUserStatusCsv();

        int userId = 1;
        int deviceId = 1;
        String curDir = String.format(USER_DEVICE_DIR, userId, deviceId);
        File f = new File(curDir);
        JSONArray outputJson = new JSONArray();

        //Iterate for each user directory
        while (f.exists()) {
            UserFeatures features = new UserFeatures();
            while (f.exists()) {
                // Read in call log
                // Collect stats and store useful info in memory
                try {
                    String fileName = curDir + CALL_LOG_FILENAME;
                    f = new File(fileName);
                    if (f.exists()) {
                        String callLogJson = readLog(fileName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

//            TODO - add relevant data to stats, and add features for this user to result
//            String jsonData = result;
//            JSONArray jarr = new JSONArray(jsonData);
//            System.out.println("Name: " + jobj.getString("name"));
//            for(int i = 0; i < jarr.length(); i++) {
//                System.out.println("Keyword: " + jarr.getString(i));
//            }

                // Read in contact list
                // Collect stats and store useful info in memory

                // Read in sms log
                // Collect stats and store useful info in memory

                deviceId++;
                curDir = String.format(USER_DEVICE_DIR, userId, deviceId);
                f = new File(curDir);
            }

            // Create user entry with features
            features.setUserId(userId);
            features.setStatus(statusMap.get(userId));
            JSONObject featuresJson = mapFeaturesToJson(features);
            outputJson.put(featuresJson);

            userId++;
            deviceId = 1;
            curDir = String.format(USER_DEVICE_DIR, userId, deviceId);
            f = new File(curDir);
        }
        writeJson(OUTPUT_FILENAME, outputJson);
    }

    public static JSONObject mapFeaturesToJson(UserFeatures features) {
        JSONObject userFeatures = new JSONObject();
        userFeatures.put(USER_ID, features.getUserId());
        userFeatures.put(USER_STATUS, features.getStatus());
        return userFeatures;
    }
}
