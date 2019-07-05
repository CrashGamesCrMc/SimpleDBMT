package com.youtube.crash_games_cr_mc.simpleDB.error;

/**
 * 
 * @since SimpleDB 1.2.0
 *
 */
@SuppressWarnings("serial")
public class CommandDoesNotExistException extends Exception {

	private String message;

	public CommandDoesNotExistException(String msg) {
		message = msg;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
