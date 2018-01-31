package com.branch.featureProcessor.smsLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class DayTypeFeatureProcessor implements FeatureProcessor {

    private static final String DATETIME = "datetime";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        Date date = new Date(jsonObject.getLong(DATETIME));
        //This could be applied to calls as well
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1 ||
                calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            features.setWeekendMessageCount(features.getWeekendMessageCount() + 1);
        } else {
            features.setWeekdayMessageCount(features.getWeekdayMessageCount() + 1);
        }
    }
}
