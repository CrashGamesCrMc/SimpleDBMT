package com.youtube.crash_games_cr_mc.simpleDB.error;

/**
 * 
 * @since SimpleDB 1.3.0
 *
 */
@SuppressWarnings("serial")
public class ComparorOperatorNotSupportedException extends Exception {

	private String message;

	public ComparorOperatorNotSupportedException(String msg) {
		message = msg;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
