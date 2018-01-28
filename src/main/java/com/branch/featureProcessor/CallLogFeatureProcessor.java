package com.branch.featureProcessor;

import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class CallLogFeatureProcessor {

    //Input log keys
    private static final String COUNTRY = "country_iso";
    private static final String DATA_USAGE = "data_usage";
    private static final String FEATURES_VIDEO = "features_video";
    private static final String DATETIME = "datetime";

    public static void processCallLogFeatures(String callLog, UserFeatures features) {
        JSONArray callLogJson = new JSONArray(callLog);
        for(int i = 0; i < callLogJson.length(); i++) {
            JSONObject jsonObject = callLogJson.getJSONObject(i);
            if (jsonObject.has(COUNTRY)) {
                features.updateCountryMap(jsonObject.getString(COUNTRY));
            }
            if (jsonObject.has(FEATURES_VIDEO) && jsonObject.getBoolean(FEATURES_VIDEO)) {
                features.setCallWithVideoCount(features.getCallWithVideoCount() + 1);
            }
            if (jsonObject.has(DATA_USAGE)) {
                features.setTotalDataUsage(features.getTotalDataUsage() + jsonObject.getInt(DATA_USAGE));
            }
            Date date = new Date(Long.parseLong(jsonObject.getString(DATETIME)));
            //This could be applied to SMS messages as well
            features.setTotalMinuteCount(features.getTotalMinuteCount() + date.getHours() * 60 + date.getMinutes());
            features.setTotalCallCount(features.getTotalCallCount() + 1);
        }
    }
}
