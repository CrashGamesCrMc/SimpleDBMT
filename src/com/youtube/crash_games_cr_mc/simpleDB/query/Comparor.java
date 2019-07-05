package com.youtube.crash_games_cr_mc.simpleDB.query;

import com.youtube.crash_games_cr_mc.simpleDB.error.ComparorOperatorNotSupportedException;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBvalue;

public abstract class Comparor {

	public static final int EQUALS = 0;
	public static final int BIGGER = 1;
	public static final int SMALLER = 2;
	public static final int NOT_EQUALS = 3;
	public static final int BIGGER_EQUALS = 4;
	public static final int SMALLER_EQUALS = 5;

	public abstract boolean Compare(DBvalue value, int operator, DBvalue value2)
			throws ComparorOperatorNotSupportedException;
	public abstract boolean Compare(DBvalue value, DBvalue value2);

}
