package io.github.SebastianDanielFrenz.SimpleDBMT.registry;

import io.github.SebastianDanielFrenz.SimpleDBMT.CrashedDBsep;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.InterpreterIDMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBint;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.Saveable;

/**
 * 
 * @since SimpleDBMT 2.2.0
 *
 */
public class TypeRegistryTableValueManager extends ValueManager {

	private char IDsep = CrashedDBsep.sepValueID;
	private char ID_CLASS = 1;
	private char ID_ID = 0;

	@Override
	public DBvalue Interpret(String text) {
		DBvalue output;
		String[] parts = text.split("\\\\" + IDsep);
		int ID = Integer.parseInt(parts[0]);
		String value = parts[1].replace((char) 0, '\\');

		if (ID == ID_ID) {
			output = new DBint();
			output.Parse(value);
		} else if (ID == ID_CLASS) {
			output = new DBString();
			output.Parse(value);
		} else {
			throw new TableRegistryException("Invalid ID: " + ID);
		}
		return output;
	}

	public char GetID(Saveable value) throws InterpreterIDMissingException {
		if (value instanceof DBint) {
			return ID_ID;
		} else if (value instanceof DBString) {
			return ID_CLASS;
		} else {
			throw new InterpreterIDMissingException(value.getClass().toGenericString());
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
