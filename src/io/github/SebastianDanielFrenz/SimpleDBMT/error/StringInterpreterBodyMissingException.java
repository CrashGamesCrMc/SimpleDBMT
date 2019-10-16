package io.github.SebastianDanielFrenz.SimpleDBMT.error;

/**
 * Used when a <i>StringValueManager</i> cannot find the value body<br>
 * int:<b style="color:red">0</b> is readable<br>
 * int:  has no body -> Error
 * 
 * @since SimpleDB 1.2.3
 *
 */
@SuppressWarnings("serial")
public class StringInterpreterBodyMissingException extends Exception {

	private String message;

	public StringInterpreterBodyMissingException(String msg) {
		message = msg;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
