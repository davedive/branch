package com.branch.featureProcessor.callLog;

import com.branch.featureProcessor.FeatureProcessor;
import com.branch.userFeatures.UserFeatures;
import org.json.JSONObject;

import java.util.Date;

public class AverageTimeOfDayFeatureProcessor implements FeatureProcessor {

    private static final String DATETIME = "datetime";

    @Override
    public void processFeature(JSONObject jsonObject, UserFeatures features) {
        Date date = new Date(Long.parseLong(jsonObject.getString(DATETIME)));
        //This could be applied to SMS messages as well
        features.setTotalMinuteCount(features.getTotalMinuteCount() + date.getHours() * 60 + date.getMinutes());
        features.setTotalCallCount(features.getTotalCallCount() + 1);
    }
}
