package com.branch.userFeatures;

import com.branch.UserLoanStatus;

import java.util.HashMap;
import java.util.Map;

public class UserFeatures {
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
}
