package com.example.flim.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ProfileResponse {
    private boolean success;
    private String message;
	private Map<String, String> Data;
	
	public ProfileResponse(boolean success, String message, Map<String, String> userData) {
		this.success = success;
        this.message = message;
        this.Data = userData; 
	}

	public ProfileResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
    
    
}
