package io.github.SebastianDanielFrenz.SimpleDBMT.error;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

@SuppressWarnings("serial")
public class InterpreterIDMissingException extends RuntimeException {

	public InterpreterIDMissingException() {
	}

	public InterpreterIDMissingException(String genericString) {
		super(genericString);
	}

}
