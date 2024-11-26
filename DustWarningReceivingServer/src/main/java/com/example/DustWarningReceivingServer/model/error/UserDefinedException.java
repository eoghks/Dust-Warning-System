package com.example.DustWarningReceivingServer.model.error;

public class UserDefinedException extends RuntimeException{
	private static final long serialVersionUID = -9003706418020361721L;

	public UserDefinedException(String message) {
		super(message);
	}
}
