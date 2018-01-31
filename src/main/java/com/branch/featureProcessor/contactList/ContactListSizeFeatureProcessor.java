package com.branch.featureProcessor.contactList;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class ContactListSizeFeatureProcessor implements FeatureProcessor {

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        features.setContactListSize(features.getContactListSize() + 1);
    }
}
