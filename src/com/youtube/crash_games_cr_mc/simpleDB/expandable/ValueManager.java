package com.youtube.crash_games_cr_mc.simpleDB.expandable;

import com.youtube.crash_games_cr_mc.simpleDB.error.InterpreterIDMissingException;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBvalue;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.Saveable;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public abstract class ValueManager {

	public abstract DBvalue Interpret(String text);

	public abstract String Save(Saveable text, char ID);

	public abstract String Save(DBvalue dBvalue) throws InterpreterIDMissingException;

}
