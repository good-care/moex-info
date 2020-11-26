package com.mokhovav.goodcare_moex_info.exceptions;

import org.springframework.stereotype.Component;

@Component
public class GoodCareException extends Exception {
    private ErrorResponse response;

    public GoodCareException() {
        response = new ErrorResponse();
    }

    public GoodCareException(String message) {
        super(message);
        response = new ErrorResponse(null, null, null, message);
    }

    public GoodCareException(Object place, String message) {
        super(message);
        response = new ErrorResponse(null, null, place.getClass().getName(), message);
    }

    public GoodCareException(String code, String type, String message) {
        super(message);
        response = new ErrorResponse(code, type, null, message);
    }

    public GoodCareException(String code, String type, Object place, String message) {
        super(message);
        response = new ErrorResponse(code, type, place.getClass().getCanonicalName(), message);
    }

    public ErrorResponse getResponse() {
        return response;
    }

}
