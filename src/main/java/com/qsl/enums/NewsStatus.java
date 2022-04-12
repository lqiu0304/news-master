package com.qsl.enums;


public enum NewsStatus {
    DRAFT(0, "草稿"),
    DRAFT1(4, "草稿"),
    TO_AUDIT(2, "待审核"),
    NO_PASS(3, "审核不通过"),
    PUBLISH(1, "已发布");

    private Integer value;

    private String message;

    NewsStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
