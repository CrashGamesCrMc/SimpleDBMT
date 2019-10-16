package io.github.SebastianDanielFrenz.SimpleDBMT.expandable;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringOperatorManagerOperatorDoesNotExistException;

/**
 * 
 * @since SimpleDB 1.3.1
 *
 */
public abstract class StringOperatorManager {

	public abstract int Interpret(String operator) throws StringOperatorManagerOperatorDoesNotExistException;

}
