package com.branch.featureProcessor;

import com.branch.userFeatures.UserFeatures;
import org.json.JSONArray;
import org.json.JSONObject;

public class ContactListFeatureProcessor {

    //Input log keys
    private static final String LAST_TIME_CONTACTED = "last_time_contacted";
    private static final String TIMES_CONTACTED = "times_contacted";
    private static final String PHONE_NUMBERS = "phone_numbers";
    private static final String PHONE_NUMBER = "phone_number";
    private static final int MIN_LAST_TIME = 1262306551;
    private static final int MAX_LAST_TIME = 1514767351;
    private static final int OLD_CONTACT_THRESHOLD  = 1451608951;
    private static final String BLACKLISTED_NUMBER = "+254345558748";

    public static void processContactListFeatures(String contactList, UserFeatures features) {
        JSONArray contactListJson = new JSONArray(contactList);
        features.setContactListSize(features.getContactListSize() + contactListJson.length());
        for(int i = 0; i < contactListJson.length(); i++) {
            JSONObject jsonObject = contactListJson.getJSONObject(i);
            //Last time contacted
            int lastTimeContacted = jsonObject.getInt(LAST_TIME_CONTACTED);
            if (lastTimeContacted < MAX_LAST_TIME && lastTimeContacted > MIN_LAST_TIME) {
                if (lastTimeContacted < OLD_CONTACT_THRESHOLD) {
                    features.setOldContactsCount(features.getOldContactsCount() + 1);
                }
                features.setTotalContactCountWithinRange(features.getTotalContactCountWithinRange() + 1);
            }
            //# of times contacted
            int timesContacted = jsonObject.getInt(TIMES_CONTACTED);
            if (timesContacted > 0) {
                features.setFrequentContactsCount(features.getFrequentContactsCount() + 1);
            }
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
}
