package com.branch.featureProcessor.contactList;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class TimesContactedFeature implements FeatureProcessor {

    private static final String TIMES_CONTACTED = "times_contacted";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        //# of times contacted
        int timesContacted = jsonObject.getInt(TIMES_CONTACTED);
        if (timesContacted > 0) {
            features.setFrequentContactsCount(features.getFrequentContactsCount() + 1);
        }
    }
}
