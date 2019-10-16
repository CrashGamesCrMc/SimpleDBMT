package io.github.SebastianDanielFrenz.SimpleDBMT.expandable;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringInterpreterBodyMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

/**
 * A <i>StringValueReader</i> convert a <i>String</i> to a <i>DBvalue</i>.<br>
 * It can be used to convert a console input like<br>
 * <br>
 * <b>int:0</b> to be converted to <b>new DBint(0)</b><br>
 * <br>
 * This should not be used for files as it produces a lot of overhead, but is understandable to the user.
 * @since SimpleDB 1.2.3
 *
 */
public abstract class StringValueManager {
	
	public abstract DBvalue Interpret(String text) throws StringInterpreterBodyMissingException;

}
