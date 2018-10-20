package org.springframework.boot.peach.message.enums;

public enum MessageVersion {
    Version10("20180909");

    MessageVersion(String fromDate) {
        this.fromDate = fromDate;
    }

    private String fromDate;

    public String getFromDate() {
        return fromDate;
    }
}
