package com.branch;

import com.branch.csv.UserStatusCsvReader;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.branch.featureProcessor.CallLogFeatureProcessor.processCallLogFeatures;
import static com.branch.featureProcessor.ContactListFeatureProcessor.processContactListFeatures;
import static com.branch.featureProcessor.SmsLogFeatureProcessor.processSmsLogFeatures;
import static com.branch.json.UserLogsJsonParser.readLog;
import static com.branch.json.UserLogsJsonParser.writeJson;

public class App {

    //Directory navigation
    private static final String USER_LOGS_DIR = "user_logs";
    private static final String USER_DIR_PREFIX = "/user-";
    private static final String DEVICE_DIR_PREFIX = "/device-";
    private static final String USER_STATUS_FILENAME = USER_LOGS_DIR + "/user_status.csv";
    private static final String USER_DEVICE_DIR = USER_LOGS_DIR + USER_DIR_PREFIX + "%d" + DEVICE_DIR_PREFIX + "%d";
    private static final String CALL_LOG_FILENAME = "/collated_call_log.txt";
    private static final String CONTACT_LIST_FILENAME = "/collated_contact_list.txt";
    private static final String SMS_LOG_FILENAME = "/collated_sms_log.txt";
    private static final String OUTPUT_FILENAME = "userFeatures.json";


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
            Map<String, Integer> countryMap = new HashMap<>();

            //For each device directory
            while (f.exists()) {
                String callLog = null;
                String contactList = null;
                String smsLog = null;

                // Read in all logs
                try {
                    callLog = readLog(curDir + CALL_LOG_FILENAME);
                    contactList = readLog(curDir + CONTACT_LIST_FILENAME);
                    smsLog = readLog(curDir + SMS_LOG_FILENAME);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException();
                }

                //Process each log into features
                if (contactList != null) {
                    processContactListFeatures(contactList, features);
                }
                if (callLog != null) {
                    processCallLogFeatures(callLog, features);
                }
                if (smsLog != null) {
                    processSmsLogFeatures(smsLog, features);
                }

                deviceId++;
                curDir = String.format(USER_DEVICE_DIR, userId, deviceId);
                f = new File(curDir);
            }

            // Create user entry with features
            features.setUserId(userId);
            features.setStatus(statusMap.get(userId));
            JSONObject featuresJson = features.serializeToJson();
            outputJson.put(featuresJson);

            userId++;
            deviceId = 1;
            curDir = String.format(USER_DEVICE_DIR, userId, deviceId);
            f = new File(curDir);
        }
        writeJson(OUTPUT_FILENAME, outputJson);
    }
}
