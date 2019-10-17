package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.FullValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.Comparor;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DataBaseQuery;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DefaultComparor;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DefaultDataBaseQuery;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryResult;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.SearchedValueCondition;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBVersion;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

public class Test {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		DataBaseHandler dbh = new DataBaseHandler(new FullValueManager());

		// test of previous data

		try {
			dbh.addDataBase("main.db");
			dbh.getDataBase("main").getTable("users").ToQueryResult().DumpHTMLandFormat("main.db.html");
			dbh.unloadDataBase("main");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// text with new data

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

		try {
			dbh.saveDataBase("main", "main.db");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
