package io.github.SebastianDanielFrenz.SimpleDBMT.expandable;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.InterpreterIDMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.Saveable;

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
