package com.branch.featureProcessor.callLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class TopCountryFeatureProcessor implements FeatureProcessor {

    private static final String COUNTRY = "country_iso";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        if (jsonObject.has(COUNTRY)) {
            features.updateCountryMap(jsonObject.getString(COUNTRY));
        }
    }
}
