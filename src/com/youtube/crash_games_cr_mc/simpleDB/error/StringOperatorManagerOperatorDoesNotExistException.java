package com.youtube.crash_games_cr_mc.simpleDB.error;

/**
 * 
 * @since SimpleDB 1.3.1
 *
 */
public class StringOperatorManagerOperatorDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msg;

	public StringOperatorManagerOperatorDoesNotExistException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}

}
