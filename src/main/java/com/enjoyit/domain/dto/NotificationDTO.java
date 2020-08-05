package com.enjoyit.domain.dto;

public class NotificationDTO {
    private String id;
    private String message;
    private UserDTO recipient;
    private Boolean isViewed;

    public NotificationDTO() {
    }



    public NotificationDTO( final String message, final UserDTO recipient) {
        this.message = message;
        this.recipient = recipient;
        this.isViewed = Boolean.FALSE;
    }



    public String getId() {
        return id;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
    public UserDTO getRecipient() {
        return recipient;
    }
    public void setRecipient(final UserDTO recipient) {
        this.recipient = recipient;
    }
    public Boolean getIsViewed() {
        return isViewed;
    }
    public void setIsViewed(final Boolean isViewed) {
        this.isViewed = isViewed;
    }


}
