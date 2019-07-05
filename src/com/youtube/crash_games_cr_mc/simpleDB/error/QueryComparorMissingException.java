package com.youtube.crash_games_cr_mc.simpleDB.error;

@SuppressWarnings("serial")
public class QueryComparorMissingException extends Exception {

	private String msg;

	public QueryComparorMissingException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}

}
