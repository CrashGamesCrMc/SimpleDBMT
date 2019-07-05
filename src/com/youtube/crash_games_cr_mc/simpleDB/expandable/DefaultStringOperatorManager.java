package com.youtube.crash_games_cr_mc.simpleDB.expandable;

import com.youtube.crash_games_cr_mc.simpleDB.error.StringOperatorManagerOperatorDoesNotExistException;
import com.youtube.crash_games_cr_mc.simpleDB.query.Comparor;

/**
 * This class can interpret ==, !=, <, >, <= and >=
 * 
 * @since SimpleDB 1.3.1
 *
 */
public class DefaultStringOperatorManager extends StringOperatorManager {

	@Override
	public int Interpret(String operator) throws StringOperatorManagerOperatorDoesNotExistException {
		switch (operator) {
		case "=":
			return Comparor.EQUALS;
		case "==":
			return Comparor.EQUALS;
		case "<":
			return Comparor.SMALLER;
		case ">":
			return Comparor.BIGGER;
		case "<=":
			return Comparor.SMALLER_EQUALS;
		case ">=":
			return Comparor.BIGGER_EQUALS;
		case "!=":
			return Comparor.NOT_EQUALS;
		default:
			throw new StringOperatorManagerOperatorDoesNotExistException("\"" + operator + "\"" + "does not exist!");
		}
	}

}
