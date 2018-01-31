package com.branch.featureProcessor.contactList;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

public class OldContactFeatureProcessor implements FeatureProcessor {

    private static final String LAST_TIME_CONTACTED = "last_time_contacted";
    private static final int MIN_LAST_TIME = 1262306551;
    private static final int MAX_LAST_TIME = 1514767351;
    private static final int OLD_CONTACT_THRESHOLD  = 1451608951;

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        //Last time contacted
        int lastTimeContacted = jsonObject.getInt(LAST_TIME_CONTACTED);
        if (lastTimeContacted < MAX_LAST_TIME && lastTimeContacted > MIN_LAST_TIME) {
            if (lastTimeContacted < OLD_CONTACT_THRESHOLD) {
                features.setOldContactsCount(features.getOldContactsCount() + 1);
            }
            features.setTotalContactCountWithinRange(features.getTotalContactCountWithinRange() + 1);
        }
    }
}
