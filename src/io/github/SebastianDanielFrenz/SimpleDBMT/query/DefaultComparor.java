package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.util.MathUtil;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBVersion;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalueNumeric;

public class DefaultComparor extends Comparor {

	@Override
	public boolean Compare(DBvalue value, int operator, DBvalue value2) throws ComparorOperatorNotSupportedException {
		if (value instanceof DBvalueNumeric && value2 instanceof DBvalueNumeric) {
			DBvalueNumeric v = (DBvalueNumeric) value;
			DBvalueNumeric v2 = (DBvalueNumeric) value2;
			switch (operator) {
			case EQUALS:
				return v.getValueNumeric() == v2.getValueNumeric();
			case BIGGER:
				return v.getValueNumeric() > v2.getValueNumeric();
			case SMALLER:
				return v.getValueNumeric() < v2.getValueNumeric();
			case NOT_EQUALS:
				return v.getValueNumeric() != v2.getValueNumeric();
			case BIGGER_EQUALS:
				return v.getValueNumeric() >= v2.getValueNumeric();
			case SMALLER_EQUALS:
				return v.getValueNumeric() <= v2.getValueNumeric();
			default:
				throw new ComparorOperatorNotSupportedException(
						"Operation ID " + String.valueOf(operator) + " not supported!");
			}
		} else if (value instanceof DBVersion || value2 instanceof DBVersion) {
			DBVersion v;
			DBVersion v2;
			if (value instanceof DBVersion) {
				v = (DBVersion) value;
			} else {
				v = new DBVersion(value.Display());
			}
			if (value2 instanceof DBVersion) {
				v2 = (DBVersion) value2;
			} else {
				v2 = new DBVersion(value2.Display());
			}
			if (operator == EQUALS && v.getValue().getNumbers().length != v2.getValue().getNumbers().length) {
				return false;
			}
			for (int i = 0; i < MathUtil.Lower(v.getValue().getNumbers().length,
					v2.getValue().getNumbers().length); i++) {
				if (v.getValue().getNumbers()[i] != v2.getValue().getNumbers()[i]) {
					if (operator == EQUALS) {
						return false;
					} else if (operator == NOT_EQUALS) {
						return true;
					}
				}
				if (operator == BIGGER || operator == BIGGER_EQUALS) {
					if (v.getValue().getNumbers()[i] > v2.getValue().getNumbers()[i]) {
						return true;
					} else if (v.getValue().getNumbers()[i] < v2.getValue().getNumbers()[i]) {
						return false;
					}
				} else if (operator == SMALLER || operator == SMALLER_EQUALS) {
					System.out.println("SMALLER/SMALLER_EQUALS: " + v.getValue().getNumbers()[i] + " </<= "
							+ v2.getValue().getNumbers()[i]);
					if (v.getValue().getNumbers()[i] < v2.getValue().getNumbers()[i]) {
						return true;
					} else if (v.getValue().getNumbers()[i] > v2.getValue().getNumbers()[i]) {
						return false;
					}
				}

			}

			if (operator == EQUALS || operator == BIGGER_EQUALS || operator == SMALLER_EQUALS) {
				return true;
			} else if (operator == NOT_EQUALS) {
				return false;
			}
			System.out.println("how did you get here?");
			return false;

		} else {
			if (operator == EQUALS) {
				return value.Display().equals(value2.Display());
			} else {
				throw new ComparorOperatorNotSupportedException(
						"Operation ID " + String.valueOf(operator) + "not supported for string and string!");
			}
		}
	}

	@Override
	public boolean Compare(DBvalue value, DBvalue value2) {
		try {
			return Compare(value, Comparor.EQUALS, value2);
		} catch (ComparorOperatorNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
