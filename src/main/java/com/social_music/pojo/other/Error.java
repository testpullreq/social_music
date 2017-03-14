package com.social_music.pojo.other;

import java.util.List;

public class Error {

    private String message;

    private List<String> errors;

    private int code;

    public Error(String message, List<String> errors, int code) {
        this.message = message;
        this.errors = errors;
        this.code = code;
    }

    public Error(String message, int code) {
        this(message, null, code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
