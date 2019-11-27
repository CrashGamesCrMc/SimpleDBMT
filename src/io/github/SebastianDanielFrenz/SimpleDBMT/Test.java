package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.FullValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.ColumnOrigin;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.Comparor;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DataBaseQuery;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DefaultComparor;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DefaultDataBaseQuery;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryJoinCondition;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryResult;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.SearchedValue;
import io.github.SebastianDanielFrenz.SimpleDBMT.registry.RegistryValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBBigInteger;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBVersion;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

@SuppressWarnings("unused")
public class Test {

	@SuppressWarnings({ "deprecation" })
	public static void main(String[] args) {

		//DataBaseHandler dbh = new DataBaseHandler(new FullValueManager());
		DataBaseHandler dbh = new DataBaseHandler(new RegistryValueManager(CrashedDBstock.getDefaultTypeRegistry()));

		// test of previous data

		
		try {
			dbh.addDataBase("main.db");
			dbh.getDataBase("main").getTable("users").ToQueryResult().DumpHTMLandFormat("main.db.html");
			dbh.unloadDataBase("main");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 

		// text with new data

		dbh.createDataBase("main");
		DataBase main = dbh.getDataBase("main");
		
		// type registry
		
		main.addTable("__SimpleDBMT_typereg",
				((RegistryValueManager) main.getValueManager()).getTypeRegistry().toTable());

		
		main.createTable("users"); Table users = main.getTable("users");
		 users.addColumn("ID"); users.addColumn("ID2"); ArrayList<DBvalue> row
		 = new ArrayList<DBvalue>(); row.add(new DBVersion("1.0.0.0"));
		 row.add(new DBVersion("1.0.0.0")); users.addRow(row);
		  
		 row = new ArrayList<DBvalue>(); row.add(new DBVersion("1.0.0.1"));
		 row.add(new DBVersion("1.0.0.0")); users.addRow(row);
		 
		 row = new ArrayList<DBvalue>(); row.add(new DBVersion("1.0.0.0"));
		 row.add(new DBVersion("1.0.0.1")); users.addRow(row);
		 
		 DataBaseQuery query = new DefaultDataBaseQuery(dbh);
		 QueryResult result = query.Run("main", "users", new String[]{"ID","ID2"},new SearchedValue[]{});

		/*main.createTable("players");
		main.createTable("playerclasses");
		Table players = main.getTable("players");
		players.addColumn("UUID");
		players.addColumn("class");
		Table playerclasses = main.getTable("playerclasses");
		playerclasses.addColumn("UUID");
		playerclasses.addColumn("class");
		playerclasses.addColumn("xp");
		ArrayList<DBvalue> row = new ArrayList<DBvalue>();

		row.add(new DBString("UUID1"));
		row.add(new DBString("priest"));
		players.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBString("UUID2"));
		row.add(new DBString("assasin"));
		players.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBString("UUID1"));
		row.add(new DBString("assasin"));
		row.add(new DBBigInteger(new BigInteger("10000")));
		playerclasses.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBString("UUID2"));
		row.add(new DBString("priest"));
		row.add(new DBBigInteger(new BigInteger("90")));
		playerclasses.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBString("UUID1"));
		row.add(new DBString("priest"));
		row.add(new DBBigInteger(new BigInteger("143")));
		playerclasses.addRow(row);

		row = new ArrayList<DBvalue>();
		row.add(new DBString("UUID2"));
		row.add(new DBString("assasin"));
		row.add(new DBBigInteger(new BigInteger("23456")));
		playerclasses.addRow(row);

		DataBaseQuery query = new DefaultDataBaseQuery(dbh);
		Comparor comparor = new DefaultComparor();
		QueryResult result = query
				.InnerJoin("main", "players", "playerclasses", new String[] { "xp" },
						new ColumnOrigin[] { new ColumnOrigin(2, "xp") }, new QueryJoinCondition[] {
								new QueryJoinCondition("UUID", "UUID"), new QueryJoinCondition("class", "class") })
				.ToQueryResult();*/

		
		try {
			result.DumpHTMLandFormat("test.html");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			dbh.saveDataBase("main", "main.db");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
