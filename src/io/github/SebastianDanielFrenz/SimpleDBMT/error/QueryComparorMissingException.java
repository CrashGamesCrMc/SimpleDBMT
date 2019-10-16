package io.github.SebastianDanielFrenz.SimpleDBMT.error;

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
