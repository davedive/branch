package com.branch.featureProcessor.smsLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class ThreadCountFeatureProcessor implements FeatureProcessor {

    private static final String THREAD_ID = "thread_id";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        //Average thread length
        features.incrementThreadMap(jsonObject.getInt(THREAD_ID));
    }
}
