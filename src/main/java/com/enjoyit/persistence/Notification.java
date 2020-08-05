package com.enjoyit.persistence;

public interface Notification {
    String getMessage();
    User getRecipient();
    Boolean getIsViewed();
    void setIsViewed(Boolean isViewed);
}
