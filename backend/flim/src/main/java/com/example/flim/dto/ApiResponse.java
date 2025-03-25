package com.example.flim.dto;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private boolean success;
    private String message;
    private String tempPassword;
	private UserDetails Data;
    
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, String tempPassword) {
    	this.success = success;
        this.message = message;
        this.tempPassword = tempPassword; 
	}

	public ApiResponse(boolean success , String message,  UserDetails Data) {
		this.success = success;
        this.message = message;
        this.Data = Data; 
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
