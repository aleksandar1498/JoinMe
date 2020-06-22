package com.enjoyit.services;

import org.springframework.http.HttpStatus;
public class ServiceResponse<T> {

    private HttpStatus responseCode;
    private MsgServiceResponse responseMessage;
    private T responseObject;

    public ServiceResponse() {}
    public ServiceResponse(final HttpStatus code, final MsgServiceResponse msg) {
        this.responseCode = code;
        this.responseMessage = msg;
    }
    public ServiceResponse(final HttpStatus code, final MsgServiceResponse msg, final T responseObject) {
        this.responseCode = code;
        this.responseMessage = msg;
        this.responseObject = responseObject;
    }
    public HttpStatus getResponseCode() {
        return responseCode;
    }
    public MsgServiceResponse getResponseMessage() {
        return responseMessage;
    }
    public T getResponseObject() {
        return responseObject;
    }
    public void setInternalServerErrorResponse() {
        this.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
        this.setResponseMessage(MsgServiceResponse.TRANSACTION_PROBLEM);
    }
    public void setResponseCode(final HttpStatus responseCode) {
        this.responseCode = responseCode;
    }
    public void setResponseMessage(final MsgServiceResponse responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setResponseObject(final T responseObject) {
        this.responseObject = responseObject;
    }

    public void setSuccessResponse() {
        this.setResponseCode(HttpStatus.OK);
        this.setResponseMessage(MsgServiceResponse.OK);
    }
}
