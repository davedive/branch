package com.branch;

import com.branch.csv.UserStatusCsvReader;
import com.branch.featureProcessor.FeatureProcessor;
import com.branch.featureProcessor.callLog.AverageTimeOfDayFeatureProcessor;
import com.branch.featureProcessor.callLog.DataUsageFeatureProcessor;
import com.branch.featureProcessor.callLog.TopCountryFeatureProcessor;
import com.branch.featureProcessor.callLog.VideoFeatureProcessor;
import com.branch.featureProcessor.contactList.BlackListFeatureProcessor;
import com.branch.featureProcessor.contactList.ContactListSizeFeatureProcessor;
import com.branch.featureProcessor.contactList.OldContactFeatureProcessor;
import com.branch.featureProcessor.contactList.TimesContactedFeature;
import com.branch.featureProcessor.smsLog.DayTypeFeatureProcessor;
import com.branch.featureProcessor.smsLog.SuspiciousPhraseFeatureProcessor;
import com.branch.featureProcessor.smsLog.ThreadCountFeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static Map<String, List<FeatureProcessor>> FEATURE_MAP = new HashMap<>();

    static {
        FEATURE_MAP.put(CONTACT_LIST_FILENAME, Arrays.asList(new OldContactFeatureProcessor(),
                                                             new TimesContactedFeature(),
                                                             new ContactListSizeFeatureProcessor(),
                                                             new BlackListFeatureProcessor()));
        FEATURE_MAP.put(CALL_LOG_FILENAME, Arrays.asList(new AverageTimeOfDayFeatureProcessor(),
                                                         new DataUsageFeatureProcessor(),
                                                         new TopCountryFeatureProcessor(),
                                                         new VideoFeatureProcessor()));
        FEATURE_MAP.put(SMS_LOG_FILENAME, Arrays.asList(new DayTypeFeatureProcessor(),
                                                        new SuspiciousPhraseFeatureProcessor(),
                                                        new ThreadCountFeatureProcessor()));
    }

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

            //For each device directory
            while (f.exists()) {
                // Read in all logs
                try {
                    for (String fileName : FEATURE_MAP.keySet()) {
                        String log = readLog(curDir + fileName);
                        if (log != null) {
                            processFeatures(log, fileName, features);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException();
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

    private static void processFeatures(String log, String fileName, UserFeatures features) {
        JSONArray jsonArray = new JSONArray(log);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (FeatureProcessor processor : FEATURE_MAP.get(fileName)) {
                processor.processFeature(jsonObject, features);
            }
        }
    }
}
