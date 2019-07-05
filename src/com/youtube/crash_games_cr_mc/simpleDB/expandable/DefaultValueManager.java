package com.youtube.crash_games_cr_mc.simpleDB.expandable;

import com.youtube.crash_games_cr_mc.simpleDB.CrashedDBsep;
import com.youtube.crash_games_cr_mc.simpleDB.error.InterpreterIDMissingException;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBString;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBboolean;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBdouble;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBfloat;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBint;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBvalue;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.Saveable;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class DefaultValueManager extends ValueManager {

	private String IDsep = CrashedDBsep.sepValueID;
	private String regexIDsep = CrashedDBsep.regexFormat(CrashedDBsep.sepValueID);

	@Override
	public DBvalue Interpret(String text) {
		DBvalue output;
		String[] parts = text.split(regexIDsep);
		char ID = parts[0].toCharArray()[0];
		String value = parts[1];
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

	@Override
	public String Save(Saveable text, char ID) {
		String output = "";
		output += ID;
		output += IDsep;
		output += text.Save();
		return output;
	}

	public String getIDsep() {
		return IDsep;
	}

	public void setIDsep(String iDsep) {
		IDsep = iDsep;
	}

	@Override
	public String Save(DBvalue dBvalue) throws InterpreterIDMissingException {
		return Save(dBvalue, GetID(dBvalue));
	}

}
