package com.mokhovav.goodcare_moex_info.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {
    private Error error;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String type, String place, String message) {
         error = new Error();
         error.setCode(code);
         error.setType(type);
         error.setPlace(place);
         error.setMessage(message);
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    private class Error {
        private String code;
        private String type;
        private String place;
        private String message;

        public Error() {
        }

        public Error(String code, String type, String message, String place) {
            this.code = code;
            this.type = type;
            this.place = place;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }
    }
}
