package com.example.flim.dto;

import java.util.Map;

public class SignResponse {
    private boolean success;
    private String message;
    private Map<String, String> data;
    private String token;

    // 생성자, getter, setter
    public SignResponse(boolean success, String message, Map<String, String> data, String token) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
