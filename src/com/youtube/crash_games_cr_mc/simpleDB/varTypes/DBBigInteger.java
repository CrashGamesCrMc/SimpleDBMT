package com.youtube.crash_games_cr_mc.simpleDB.varTypes;

import java.math.BigInteger;

public class DBBigInteger implements DBvalue {

	private BigInteger value;

	@Override
	public void Parse(String text) {
		value = new BigInteger(text);
	}

	@Override
	public String Save() {
		return value.toString();
	}

	@Override
	public boolean Equals(DBCompareable value2) {
		return ((DBvalue) value2).Display() == Display();
	}

	@Override
	public String Display() {
		return value.toString();
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

}
