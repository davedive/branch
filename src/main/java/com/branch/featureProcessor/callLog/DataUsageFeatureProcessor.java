package com.branch.featureProcessor.callLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class DataUsageFeatureProcessor implements FeatureProcessor {

    private static final String DATA_USAGE = "data_usage";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        if (jsonObject.has(DATA_USAGE)) {
            features.setTotalDataUsage(features.getTotalDataUsage() + jsonObject.getInt(DATA_USAGE));
        }
    }
}
