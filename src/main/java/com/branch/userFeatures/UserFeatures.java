package com.branch.userFeatures;

import com.branch.UserLoanStatus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserFeatures {

    private static final String USER_ID = "user_id";
    private static final String USER_STATUS = "user_status";
    private static final String CONTACT_LIST_SIZE = "contact_list_size";
    private static final String OLD_CONTACT_PERCENTAGE = "old_contact_percentage";
    private static final String FREQUENT_CONTACT_PERCENTAGE = "frequent_contact_percentage";
    private static final String MOST_COMMON_COUNTRY = "most_common_country";
    private static final String VIDEO_CALL_PERCENTAGE = "video_call_percentage";
    private static final String TOTAL_DATA_USAGE = "total_data_usage";
    private static final String AVERAGE_TIME = "average_time_of_day_in_minutes";
    private static final String COMMON_DAY_TYPE = "most_common_day_type";
    private static final String INCLUDES_BLACKLISTED_NUMBER = "includes_blacklisted_number";
    private static final String MESSAGES_PER_THREAD = "average_messages_per_thread";
    private static final String SUSPICIOUS_PHRASES = "contains_suspicious_phrases";
    
    private int userId;
    private UserLoanStatus status;
    private int contactListSize;
    private double totalContactCountWithinRange;
    private double oldContactsCount;
    private double frequentContactsCount;
    private Map<String, Integer> countryMap = new HashMap<>();
    private String mostCommonCountry;
    private int totalDataUsage;
    private double totalCallCount;
    private double callWithVideoCount;
    private int totalMinuteCount;
    private int weekdayMessageCount;
    private int weekendMessageCount;
    private int totalMessageCount;
    private boolean includesBlacklistedContact;
    private Map<Integer, Integer> threadMap = new HashMap<>();
    private boolean containsSuspiciousPhrases;

    public enum DayType {
        WEEKDAY,
        WEEKEND
    }

    public UserFeatures() {
        this.contactListSize = 0;
        this.totalContactCountWithinRange = 0.0;
        this.oldContactsCount = 0.0;
        this.frequentContactsCount = 0.0;
        this.mostCommonCountry = null;
        this.totalDataUsage = 0;
        this.totalCallCount = 0.0;
        this.callWithVideoCount = 0.0;
        this.totalMinuteCount = 0;
        this.weekdayMessageCount = 0;
        this.weekendMessageCount = 0;
        this.totalMessageCount = 0;
        this.includesBlacklistedContact = false;
        this.containsSuspiciousPhrases = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserLoanStatus getStatus() {
        return status;
    }

    public void setStatus(UserLoanStatus status) {
        this.status = status;
    }

    public int getContactListSize() {
        return contactListSize;
    }

    public void setContactListSize(int contactListSize) {
        this.contactListSize = contactListSize;
    }

    public double getTotalContactCountWithinRange() {
        return totalContactCountWithinRange;
    }

    public void setTotalContactCountWithinRange(double totalContactCountWithinRange) {
        this.totalContactCountWithinRange = totalContactCountWithinRange;
    }

    public double getOldContactsCount() {
        return oldContactsCount;
    }

    public void setOldContactsCount(double oldContactsCount) {
        this.oldContactsCount = oldContactsCount;
    }

    public double getFrequentContactsCount() {
        return frequentContactsCount;
    }

    public void setFrequentContactsCount(double frequentContactsCount) {
        this.frequentContactsCount = frequentContactsCount;
    }

    public double getOldContactPercentage() {
        return Math.round(this.oldContactsCount/totalContactCountWithinRange*100.00)/100.00;
    }

    public double getFrequentContactPercentage() {
        return Math.round(frequentContactsCount/contactListSize*100.00)/100.00;
    }

    public double getVideoCallPercentage() {
        return Math.round(callWithVideoCount/totalCallCount*100.00)/100.00;
    }

    public double getAverageTimeOfDayInMinutes() {
        return Math.round(totalMinuteCount/totalCallCount*100.00)/100.00;
    }

    public void updateCountryMap(String country) {
        countryMap.put(country, countryMap.getOrDefault(country, 0) + 1);
        if (mostCommonCountry == null ||
            countryMap.get(country) > countryMap.get(mostCommonCountry)) {
            mostCommonCountry = country;
        }
    }

    public void incrementThreadMap(int threadId) {
        threadMap.put(threadId, threadMap.getOrDefault(threadId, 0) + 1);
    }

    public String getMostCommonCountry() {
        return mostCommonCountry;
    }

    public int getTotalDataUsage() {
        return totalDataUsage;
    }

    public void setTotalDataUsage(int totalDataUsage) {
        this.totalDataUsage = totalDataUsage;
    }

    public double getTotalCallCount() {
        return totalCallCount;
    }

    public void setTotalCallCount(double totalCallCount) {
        this.totalCallCount = totalCallCount;
    }

    public double getCallWithVideoCount() {
        return callWithVideoCount;
    }

    public void setCallWithVideoCount(double callWithVideoCount) {
        this.callWithVideoCount = callWithVideoCount;
    }

    public int getTotalMinuteCount() {
        return totalMinuteCount;
    }

    public void setTotalMinuteCount(int totalMinuteCount) {
        this.totalMinuteCount = totalMinuteCount;
    }

    public int getWeekdayMessageCount() {
        return weekdayMessageCount;
    }

    public void setWeekdayMessageCount(int weekdayMessageCount) {
        this.weekdayMessageCount = weekdayMessageCount;
    }

    public int getWeekendMessageCount() {
        return weekendMessageCount;
    }

    public void setWeekendMessageCount(int weekendMessageCount) {
        this.weekendMessageCount = weekendMessageCount;
    }

    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    public void setTotalMessageCount(int totalMessageCount) {
        this.totalMessageCount = totalMessageCount;
    }

    public DayType getMostCommonDayTypeForMessages() {
        if (weekendMessageCount > weekdayMessageCount) {
            return DayType.WEEKEND;
        }
        return DayType.WEEKDAY;
    }

    public boolean isIncludesBlacklistedContact() {
        return includesBlacklistedContact;
    }

    public void setIncludesBlacklistedContact(boolean includesBlacklistedContact) {
        this.includesBlacklistedContact = includesBlacklistedContact;
    }

    public int getAverageThreadLength() {
        int totalMessageCount = 0;
        for (Integer messageTotal : threadMap.values()) {
            totalMessageCount += messageTotal;
        }
        return totalMessageCount/threadMap.keySet().size();
    }

    public boolean isContainsSuspiciousPhrases() {
        return containsSuspiciousPhrases;
    }

    public void setContainsSuspiciousPhrases(boolean containsSuspiciousPhrases) {
        this.containsSuspiciousPhrases = containsSuspiciousPhrases;
    }

    public JSONObject serializeToJson() {
        JSONObject userFeatures = new JSONObject();
        userFeatures.put(USER_ID, this.getUserId());
        userFeatures.put(USER_STATUS, this.getStatus());
        userFeatures.put(CONTACT_LIST_SIZE, this.getContactListSize());
        userFeatures.put(OLD_CONTACT_PERCENTAGE, this.getOldContactPercentage());
        userFeatures.put(FREQUENT_CONTACT_PERCENTAGE, this.getFrequentContactPercentage());
        userFeatures.put(MOST_COMMON_COUNTRY, this.getMostCommonCountry());
        userFeatures.put(VIDEO_CALL_PERCENTAGE, this.getVideoCallPercentage());
        userFeatures.put(TOTAL_DATA_USAGE, this.getTotalDataUsage());
        userFeatures.put(AVERAGE_TIME, this.getAverageTimeOfDayInMinutes());
        userFeatures.put(COMMON_DAY_TYPE, this.getMostCommonDayTypeForMessages());
        userFeatures.put(INCLUDES_BLACKLISTED_NUMBER, this.isIncludesBlacklistedContact());
        userFeatures.put(MESSAGES_PER_THREAD, this.getAverageThreadLength());
        userFeatures.put(SUSPICIOUS_PHRASES, this.isContainsSuspiciousPhrases());
        return userFeatures;
    }
}
