package com.branch.featureProcessor.smsLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class SuspiciousPhraseFeatureProcessor implements FeatureProcessor {

    private static final String MESSAGE_BODY = "message_body";
    private static final String SUSPICIOUS_PHRASE = "I'm not paying you back";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        //Suspicious phrases
        if (jsonObject.getString(MESSAGE_BODY).contains(SUSPICIOUS_PHRASE)) {
            features.setContainsSuspiciousPhrases(true);
        }
        features.setTotalMessageCount(features.getTotalMessageCount() + 1);
    }
}
