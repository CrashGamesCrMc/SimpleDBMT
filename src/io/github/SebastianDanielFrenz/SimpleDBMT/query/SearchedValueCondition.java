package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringInterpreterBodyMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringOperatorManagerOperatorDoesNotExistException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.StringOperatorManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.StringValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

/**
 * 
 * @since SimpleDB 1.3.0
 *
 */
public class SearchedValueCondition {

	private String column1;
	private int operator;
	private Object column2;
	private boolean internal = false;

	public SearchedValueCondition(String column1, int operator, String column2) {
		this.column1 = column1;
		this.operator = operator;
		this.column2 = column2;
	}

	public SearchedValueCondition(String column1, int operator, DBvalue value) {
		this.column1 = column1;
		this.operator = operator;
		column2 = value;
		internal = true;
	}

	public SearchedValueCondition() {
	}

	public void Parse(String source, StringValueManager stringValueManager, StringOperatorManager stringOperatorManager)
			throws StringOperatorManagerOperatorDoesNotExistException {
		if (!source.contains(" ")) {
			source = source.replace('_', ' ');
		}
		String[] split = source.split(" ");
		column1 = split[0];
		operator = stringOperatorManager.Interpret(split[1]);
		try {
			column2 = stringValueManager.Interpret(split[2]);
			internal = true;
		} catch (StringInterpreterBodyMissingException e) {
			column2 = split[2];
		}
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public Object getColumn2() {
		return column2;
	}

	public void setColumn2(Object column2) {
		this.column2 = column2;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

}
