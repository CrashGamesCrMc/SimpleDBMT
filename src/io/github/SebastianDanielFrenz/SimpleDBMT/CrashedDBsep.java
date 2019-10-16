package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.util.ArrayList;
import java.util.List;

import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.Saveable;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class CrashedDBsep {

	public static final char newValue = (char) 13;
	public static final char newRow = (char) 1;
	public static final char newTable = (char) 2;
	public static final char newDataBase = (char) 3;
	public static final char newFile = (char) 4;
	public static final char sepInternalDataBase = (char) 5;
	public static final char sepTables = (char) 6;
	public static final char sepTableNames = (char) 7;
	public static final char sepRow = (char) 8;
	public static final char sepValue = (char) 9;
	public static final char sepValueID = (char) 10;
	public static final char sepTableHeadersFromData = (char) 11;
	public static final char sepTableHeaders = (char) 12;

	public static final char ID_DBint = 9;
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