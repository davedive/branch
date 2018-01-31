package com.branch.featureProcessor.callLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class VideoFeatureProcessor implements FeatureProcessor {

    private static final String FEATURES_VIDEO = "features_video";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        if (jsonObject.has(FEATURES_VIDEO) && jsonObject.getBoolean(FEATURES_VIDEO)) {
            features.setCallWithVideoCount(features.getCallWithVideoCount() + 1);
        }
    }
}
