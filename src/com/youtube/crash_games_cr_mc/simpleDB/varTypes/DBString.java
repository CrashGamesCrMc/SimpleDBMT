package com.youtube.crash_games_cr_mc.simpleDB.varTypes;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class DBString implements DBvalue {
	private String value;

	public DBString(String value) {
		this.value = value;

	}

	public DBString() {
		// TODO Auto-generated constructor stub
	}

	public void Parse(String text) {
		value = text;

	}

	public String Save() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean Equals(DBCompareable value2) {
		if (value2 instanceof DBString) {
			if (((DBString) value2).getValue().equals(value)) {
				return true;
			}
		} else {
			try {
				long value = Long.parseLong(this.value);
				if (value2 instanceof DBbyte) {
					if (((DBboolean) value2).Convert() == value) {
						return true;
					}
				} else if (value2 instanceof DBbyte) {
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
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	@Override
	public String Display() {
		return String.valueOf(value);
	}

}
