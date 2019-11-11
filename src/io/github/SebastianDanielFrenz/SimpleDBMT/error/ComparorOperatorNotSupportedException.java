package io.github.SebastianDanielFrenz.SimpleDBMT.error;

/**
 * 
 * @since SimpleDB 1.3.0
 *
 */
@SuppressWarnings("serial")
public class ComparorOperatorNotSupportedException extends RuntimeException {

	private String message;

	public ComparorOperatorNotSupportedException(String msg) {
		message = msg;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
