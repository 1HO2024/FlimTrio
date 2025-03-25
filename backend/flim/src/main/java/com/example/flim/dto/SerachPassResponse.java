package com.example.flim.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerachPassResponse {
    private boolean success;
    private String message;
    private String tempPassword;

    
    public SerachPassResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SerachPassResponse(boolean success, String message, String tempPassword) {
    	this.success = success;
        this.message = message;
        this.tempPassword = tempPassword; 
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
}
