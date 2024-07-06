package com.dantn.weblaptop.constant;

public enum AccountStatus {
    DANG_LAM_VIEC("Đang Làm Việc"),
    DA_THOI_VIEC("Đã Thôi Việc"),
    ;
    private final String name;

    AccountStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static AccountStatus getAccountStatus(String name) {
        for (AccountStatus accountStatus : AccountStatus.values()) {
            if (accountStatus.getName().equals(name)) {
                return accountStatus;
            }
        }
        throw new IllegalArgumentException("No such account: " + name);
    }
}
