package com.youtube.crash_games_cr_mc.simpleDB.varTypes;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 1.3.0
 */

public class DBdouble implements DBvalueNumeric {

	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DBdouble(double value) {
		this.value = value;
	}

	public DBdouble() {
	}

	@Override
	public void Parse(String text) {
		value = Double.parseDouble(text);
	}

	@Override
	public String Save() {
		return String.valueOf(value);
	}

	@Override
	public boolean Equals(DBCompareable value2) {
		if (value2 instanceof DBdouble) {
			if (((DBdouble) value2).getValue() == value) {
				return true;
			}
		} else {
			if (value2 instanceof DBbyte) {
				if (((DBboolean) value2).Convert() == value) {
					return true;
				}
			} else if (value2 instanceof DBbyte) {
				if (((DBbyte) value2).getValue() == value) {
					return true;
				}
			} else if (value2 instanceof DBfloat) {
				if (((DBfloat) value2).getValue() == value) {
					return true;
				}
			} else if (value2 instanceof DBint) {
				if (((DBint) value2).getValue() == value) {
					return true;
				}
			} else if (value2 instanceof DBlong) {
				if (((DBlong) value2).getValue() == value) {
					return true;
				}
			} else if (value2 instanceof DBshort) {
				if (((DBshort) value2).getValue() == value) {
					return true;
				}
			} else if (value2 instanceof DBString) {
				if (((DBString) value2).getValue() == Display()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String Display() {
		return String.valueOf(value);
	}

	@Override
	public double getValueNumeric() {
		return value;
	}

}
