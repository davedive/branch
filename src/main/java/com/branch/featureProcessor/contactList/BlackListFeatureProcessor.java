package com.branch.featureProcessor.contactList;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

public class BlackListFeatureProcessor implements FeatureProcessor {

    private static final String PHONE_NUMBERS = "phone_numbers";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String BLACKLISTED_NUMBER = "+254345558748";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        //Blacklist check
        if (!features.isIncludesBlacklistedContact() && jsonObject.has(PHONE_NUMBERS)) {
            JSONArray phoneNumbers = jsonObject.getJSONArray(PHONE_NUMBERS);
            for (int j = 0; j < phoneNumbers.length(); j++) {
                if (phoneNumbers.getJSONObject(j).getString(PHONE_NUMBER).equals(BLACKLISTED_NUMBER)) {
                    features.setIncludesBlacklistedContact(true);
                }
            }
        }
    }
}
