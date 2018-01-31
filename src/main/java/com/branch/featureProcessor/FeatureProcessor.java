package com.branch.featureProcessor;

import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public interface FeatureProcessor {

    // Extract relevant fields from jsonObject, then process them and update features
    void processFeature(JSONObject jsonObject, UserFeatures features);
}
