package com.youtube.crash_games_cr_mc.simpleDB;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.youtube.crash_games_cr_mc.simpleDB.error.QueryComparorMissingException;
import com.youtube.crash_games_cr_mc.simpleDB.expandable.FullValueManager;
import com.youtube.crash_games_cr_mc.simpleDB.query.Comparor;
import com.youtube.crash_games_cr_mc.simpleDB.query.DataBaseQuery;
import com.youtube.crash_games_cr_mc.simpleDB.query.DefaultComparor;
import com.youtube.crash_games_cr_mc.simpleDB.query.DefaultDataBaseQuery;
import com.youtube.crash_games_cr_mc.simpleDB.query.QueryResult;
import com.youtube.crash_games_cr_mc.simpleDB.query.SearchedValueCondition;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBVersion;
import com.youtube.crash_games_cr_mc.simpleDB.varTypes.DBvalue;

public class Test {

	public static void main(String[] args) {

		DataBaseHandler dbh = new DataBaseHandler(new FullValueManager());
		dbh.createDataBase("main");
		DataBase main = dbh.getDataBase("main");
		main.createTable("users");
		Table users = main.getTable("users");
		users.addColumn("ID");
		users.addColumn("ID2");
		ArrayList<DBvalue> row = new ArrayList<DBvalue>();
		row.add(new DBVersion("1.0.0.0"));
		row.add(new DBVersion("1.0.0.0"));
		users.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBVersion("1.0.0.1"));
		row.add(new DBVersion("1.0.0.0"));
		users.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBVersion("1.0.0.0"));
		row.add(new DBVersion("1.0.0.1"));
		users.addRow(row);

		DataBaseQuery query = new DefaultDataBaseQuery(dbh);
		Comparor comparor = new DefaultComparor();
		try {
			QueryResult result = query.Run("main", "users", new String[] { "ID", "ID2" },
					new SearchedValueCondition[] { new SearchedValueCondition("ID", Comparor.SMALLER_EQUALS, "ID2") },
					comparor);

			try {
				result.DumpHTMLandFormat("test.html");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (QueryComparorMissingException e) {
			e.printStackTrace();
		}
	}

}
