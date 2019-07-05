package com.youtube.crash_games_cr_mc.simpleDB;

import java.util.ArrayList;
import java.util.List;

import com.youtube.crash_games_cr_mc.simpleDB.varTypes.Saveable;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class CrashedDBsep {

	public static final String newValue = "\\0";
	public static final String newRow = "\\1";
	public static final String newTable = "\\2";
	public static final String newDataBase = "\\3";
	public static final String newFile = "\\4";
	public static final String sepInternalDataBase = "\\7";
	public static final String sepTables = "\\8";
	public static final String sepTableNames = "\\9";
	public static final String sepRow = "\\9";
	public static final String sepValue = "\\a";
	public static final String sepValueID = "\\b";
	public static final String sepTableHeadersFromData = "\\5";
	public static final String sepTableHeaders = "\\6";

	public static String regexFormat(String sep) {
		return sep.replace("\\", "\\\\");
	}

	public static final char ID_DBint = 0;
	public static final char ID_DBfloat = 1;
	public static final char ID_DBdouble = 2;
	public static final char ID_DBString = 3;
	public static final char ID_DBboolean = 4;
	public static final char ID_DBbyte = 5;
	public static final char ID_DBlong = 6;
	public static final char ID_DBshort = 7;
	public static final char ID_DBVersion = 8;

	public static List<DataBase> ToList(DataBase[] array) {
		List<DataBase> list = new ArrayList<DataBase>();
		for (DataBase obj : array) {
			list.add(obj);
		}
		return list;
	}

	public static List<Table> ToList(Table[] array) {
		List<Table> list = new ArrayList<Table>();
		for (Table obj : array) {
			list.add(obj);
		}
		return list;
	}

	public static List<Saveable> ToList(Saveable[] array) {
		List<Saveable> list = new ArrayList<Saveable>();
		for (Saveable obj : array) {
			list.add(obj);
		}
		return list;
	}
}