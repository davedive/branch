package com.branch.featureProcessor;

import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.attribute.UserPrincipalNotFoundException;
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
            processCountryFeature(jsonObject, features);
            processVideoFeature(jsonObject, features);
            processDataUsageFeature(jsonObject, features);
            processAverageTimeFeature(jsonObject, features);
        }
    }

    private static void processCountryFeature(JSONObject jsonObject, UserFeatures features) {
        if (jsonObject.has(COUNTRY)) {
            features.updateCountryMap(jsonObject.getString(COUNTRY));
        }
    }

    private static void processVideoFeature(JSONObject jsonObject, UserFeatures features) {
        if (jsonObject.has(FEATURES_VIDEO) && jsonObject.getBoolean(FEATURES_VIDEO)) {
            features.setCallWithVideoCount(features.getCallWithVideoCount() + 1);
        }
    }

    private static void processDataUsageFeature(JSONObject jsonObject, UserFeatures features) {
        if (jsonObject.has(DATA_USAGE)) {
            features.setTotalDataUsage(features.getTotalDataUsage() + jsonObject.getInt(DATA_USAGE));
        }
    }

    private static void processAverageTimeFeature(JSONObject jsonObject, UserFeatures features) {
        Date date = new Date(Long.parseLong(jsonObject.getString(DATETIME)));
        //This could be applied to SMS messages as well
        features.setTotalMinuteCount(features.getTotalMinuteCount() + date.getHours() * 60 + date.getMinutes());
        features.setTotalCallCount(features.getTotalCallCount() + 1);
    }
}
