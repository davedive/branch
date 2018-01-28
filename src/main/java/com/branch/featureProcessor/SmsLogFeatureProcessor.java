package com.branch.featureProcessor;

import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class SmsLogFeatureProcessor {

    //Input log keys
    private static final String DATETIME = "datetime";
    private static final String THREAD_ID = "thread_id";
    private static final String MESSAGE_BODY = "message_body";
    private static final String SUSPICIOUS_PHRASE = "I'm not paying you back";

    public static void processSmsLogFeatures(String smsLog, UserFeatures features) {
        JSONArray smsLogJson = new JSONArray(smsLog);
        for(int i = 0; i < smsLogJson.length(); i++) {
            JSONObject jsonObject = smsLogJson.getJSONObject(i);
            Date date = new Date(jsonObject.getLong(DATETIME));
            //This could be applied to calls as well
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1 ||
                calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                features.setWeekendMessageCount(features.getWeekendMessageCount() + 1);
            } else {
                features.setWeekdayMessageCount(features.getWeekdayMessageCount() + 1);
            }
            //Average thread length
            features.incrementThreadMap(jsonObject.getInt(THREAD_ID));
            //Suspicious phrases
            if (jsonObject.getString(MESSAGE_BODY).contains(SUSPICIOUS_PHRASE)) {
                features.setContainsSuspiciousPhrases(true);
            }
            features.setTotalMessageCount(features.getTotalMessageCount() + 1);
        }
    }
}
