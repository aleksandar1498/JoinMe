package com.enjoyit.enums;

public enum MsgServiceResponse {
    NO_USER_WITH_USERNAME("No such user in system."),
    USER_EMAIL_ALREADY_EXIST("Email already registered."),
    USER_USERNAME_ALREADY_EXIST("Username already exists."),
    NEW_PASSWORD_IS_THE_SAME("New password is the same as old one"),
    NEW_PASSWORD_MISMATCHED("Password mismatched"),
    AUTHENTICATION_FAILED("Incorrect username or password"),
    ACCOUNT_IS_LOCKED("Your account is locked, contact the administrator"),
    ACCOUNT_IS_DISABLED("Your account is locked, contact the administrator"),
    FORBIDDEN_ACTION("The action is forbidden for current user"),
    TRANSACTION_PROBLEM("Transaction is failed."),
    EMAIL_SENDING_PROBLEM("Sending email failed."),
    UKNOWN_PROBLEM("Uknown problem"),
    OK("Well done"),
    ERROR("Error"),
    NO_EVENT_WITH_ID_FOUND("An event with this Id does not exist");
    private final String msg;
    MsgServiceResponse(final String msg) {
        this.msg = msg;
    }
    @Override
    public String toString() {
        return msg;
    }
}
