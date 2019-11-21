package io.github.SebastianDanielFrenz.SimpleDBMT.expandable;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.InterpreterIDMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBboolean;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBdouble;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBfloat;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBint;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.Saveable;

import io.github.SebastianDanielFrenz.SimpleDBMT.CrashedDBsep;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 2.2.0
 *
 */

public class DefaultValueManager extends ValueManager {

	private char IDsep = CrashedDBsep.sepValueID;

	@Override
	public DBvalue Interpret(String text) {
		DBvalue output;
		String[] parts = text.split("\\\\" + IDsep);
		char ID = parts[0].toCharArray()[0];
		String value = parts[1].replace((char) 0, '\\');

		if (ID == CrashedDBsep.ID_DBint) {
			output = new DBint();
			output.Parse(value);
		} else if (ID == CrashedDBsep.ID_DBfloat) {
			output = new DBfloat();
			output.Parse(value);
		} else if (ID == CrashedDBsep.ID_DBdouble) {
			output = new DBdouble();
			output.Parse(value);
		} else if (ID == CrashedDBsep.ID_DBString) {
			output = new DBString();
			output.Parse(value);
		} else if (ID == CrashedDBsep.ID_DBboolean) {
			output = new DBboolean();
			output.Parse(value);
		} else {
			output = new DBString("lol");
		}
		return output;
	}

	public char GetID(Saveable value) throws InterpreterIDMissingException {
		if (value instanceof DBint) {
			return CrashedDBsep.ID_DBint;
		} else if (value instanceof DBfloat) {
			return CrashedDBsep.ID_DBfloat;
		} else if (value instanceof DBdouble) {
			return CrashedDBsep.ID_DBdouble;
		} else if (value instanceof DBString) {
			return CrashedDBsep.ID_DBString;
		} else if (value instanceof DBboolean) {
			return CrashedDBsep.ID_DBboolean;
		} else {
			throw new InterpreterIDMissingException();
		}
	}

	public char getIDsep() {
		return IDsep;
	}

	public void setIDsep(char iDsep) {
		IDsep = iDsep;
	}

	@Override
	public String Save(DBvalue value) {
		String output = "";
		output += GetID(value);
		output += "\\" + IDsep;
		output += value.Save().replace("\\", "\\\\");
		return output;
	}

}
