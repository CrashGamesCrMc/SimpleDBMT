package com.youtube.crash_games_cr_mc.simpleDB.expandable;

import com.youtube.crash_games_cr_mc.simpleDB.error.StringInterpreterBodyMissingException;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBString;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBdouble;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBfloat;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBint;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBvalue;

/**
 * The readable equivalent to <i>DefaultValueManager</i>.
 * @since SimpleDB 1.2.3
 *
 */

public class DefaultStringValueManager extends StringValueManager {

	@Override
	public DBvalue Interpret(String text) throws StringInterpreterBodyMissingException {
		/**
		 * The <u>text</u> should be in the format<br><br>
		 * <b>int:0</b> and will be converted to <b>new DBint(0)</b><br>
		 */
		String[] parts = text.split(":");
		switch (parts[0]) {
		case "int":
			return new DBint(Integer.parseInt(parts[1]));
		case "float":
			return new DBfloat(Float.parseFloat(parts[1]));
		case "double":
			return new DBdouble(Double.parseDouble(parts[1]));
		case "str":
			return new DBString(parts[1]);
		case "String":
			return new DBString(parts[1]);
		default:
			throw new StringInterpreterBodyMissingException(text);
		}
	}

}
