package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.InterpreterIDMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringInterpreterBodyMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.StringValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 1.3.0
 */

public class SearchedValue {

	public SearchedValue(String column, DBvalue value) {
		this.column = column;
		this.value = value;
	}

	/**
	 * @since SimpleDB 1.2.0
	 */
	public SearchedValue() {
	}

	private String column;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public DBvalue getValue() {
		return value;
	}

	public void setValue(DBvalue value) {
		this.value = value;
	}

	private DBvalue value;

	/**
	 * This method turns a user entry for a <i>SearchedValue</i> like<br>
	 * <br>
	 * <b style="color:green">ID</b><b>,</b><b style=
	 * "color:blue">int</b><b>:</b><b style="color:red">1</b> into a
	 * <i>SearchedValue</i> with value = <b>new
	 * DB</b><b style="color:blue">int</b><b>(</b><b style=
	 * "color:red">1</b><b>)</b> and column =
	 * <b>"</b><b style="color:green">ID</b><b>"</b>
	 * 
	 * @since SimpleDB 1.2.0
	 * @version SimpleDB 1.2.3
	 */
	public void Parse(String text, StringValueManager stringValueManager) throws StringInterpreterBodyMissingException {

		String[] parts = text.split(",");
		column = parts[0];
		value = stringValueManager.Interpret(parts[1]);
	}

	/**
	 * @since SimpleDB 1.2.0
	 */
	public String Save(ValueManager valueManager) throws InterpreterIDMissingException {
		return column + "," + valueManager.Save(value);
	}

	public SearchedValueCondition toSearchedValueCondition() {
		return new SearchedValueCondition(column, Comparor.EQUALS, value);
	}

}
