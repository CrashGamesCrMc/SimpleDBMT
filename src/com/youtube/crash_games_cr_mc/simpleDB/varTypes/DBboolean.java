package com.youtube.crash_games_cr_mc.simpleDB.varTypes;

/**
 * 
 * @since SimpleDB 1.1.0
 * @version SimpleDB 1.3.0
 */

public class DBboolean implements DBvalueNumeric {

	private boolean value;

	public DBboolean() {

	}

	public DBboolean(boolean value) {
		this.value = value;
	}

	@Override
	public boolean Equals(DBCompareable value2) {
		if (value2 instanceof DBboolean) {
			if (((DBboolean) value2).getValue() == value) {
				return true;
			}
		} else {
			byte value = Convert();
			if (value2 instanceof DBbyte) {
				if (((DBbyte) value2).getValue() == value) {
					return true;
				}
			} else if (value2 instanceof DBdouble) {
				if (((DBdouble) value2).getValue() == value) {
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
				if (((((DBString) value2).getValue() == "true" || ((DBString) value2).getValue() == "1") && this.value)
						|| ((((DBString) value2).getValue() == "false" || ((DBString) value2).getValue() == "0")
								&& !this.value)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void Parse(String text) {
		value = Boolean.parseBoolean(text);
	}

	@Override
	public String Save() {
		return String.valueOf(value);
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String Display() {
		return String.valueOf(value);
	}

	public byte Convert() {
		if (value) {
			return 1;
		}
		return 0;
	}

	@Override
	public double getValueNumeric() {
		if (value) {
			return 1;
		}
		return 0;
	}

}
