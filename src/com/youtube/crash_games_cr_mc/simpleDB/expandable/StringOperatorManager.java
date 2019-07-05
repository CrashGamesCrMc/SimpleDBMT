package com.youtube.crash_games_cr_mc.simpleDB.expandable;

import com.youtube.crash_games_cr_mc.simpleDB.error.StringOperatorManagerOperatorDoesNotExistException;

/**
 * 
 * @since SimpleDB 1.3.1
 *
 */
public abstract class StringOperatorManager {

	public abstract int Interpret(String operator) throws StringOperatorManagerOperatorDoesNotExistException;

}
