package com.branch.userFeatures;

import com.branch.UserLoanStatus;

public class UserFeatures {
    private int userId;
    private UserLoanStatus status;

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
}
