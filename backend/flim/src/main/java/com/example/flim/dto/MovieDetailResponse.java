package com.example.flim.dto;

public class MovieDetailResponse {
	private boolean success;
    private String message;
	public MovieDetailResponse(boolean success, String message) {
		this.setSuccess(success);
	    this.setMessage(message);
			
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
