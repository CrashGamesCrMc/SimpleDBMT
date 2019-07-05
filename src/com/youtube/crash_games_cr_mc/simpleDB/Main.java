package com.youtube.crash_games_cr_mc.simpleDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.youtube.crash_games_cr_mc.simpleDB.error.CommandDoesNotExistException;
import com.youtube.crash_games_cr_mc.simpleDB.error.ComparorOperatorNotSupportedException;
import com.youtube.crash_games_cr_mc.simpleDB.error.QueryComparorMissingException;
import com.youtube.crash_games_cr_mc.simpleDB.error.StringInterpreterBodyMissingException;
import com.youtube.crash_games_cr_mc.simpleDB.error.StringOperatorManagerOperatorDoesNotExistException;
import com.youtube.crash_games_cr_mc.simpleDB.expandable.DefaultStringOperatorManager;
import com.youtube.crash_games_cr_mc.simpleDB.expandable.DefaultStringValueManager;
import com.youtube.crash_games_cr_mc.simpleDB.expandable.FullValueManager;
import com.youtube.crash_games_cr_mc.simpleDB.query.Comparor;
import com.youtube.crash_games_cr_mc.simpleDB.query.DefaultComparor;
import com.youtube.crash_games_cr_mc.simpleDB.query.QueryResult;
import com.youtube.crash_games_cr_mc.simpleDB.query.SimpleStringDataBaseQuery;

public class Main {

	public static void main(String[] args) {
		DataBaseHandler dbh = new DataBaseHandler(new FullValueManager());
		SimpleStringDataBaseQuery dataBaseQuery = new SimpleStringDataBaseQuery(dbh, new DefaultStringValueManager(),
				new DefaultStringOperatorManager());

		Scanner scanner = new Scanner(System.in);
		String cmd = "";
		String[] parts;

		Comparor comparor = new DefaultComparor();

		while (cmd != "exit") {
			try {
				cmd = scanner.nextLine();
				parts = cmd.split("\\/");
				if (cmd.startsWith("get")) {
					QueryResult result;
					try {
						result = dataBaseQuery.GetQuery(parts[0], comparor);
						result.DumpHTMLandFormat(parts[1]);
					} catch (StringInterpreterBodyMissingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (QueryComparorMissingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (StringOperatorManagerOperatorDoesNotExistException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						dataBaseQuery.ChangeQuery(cmd, comparor);
					} catch (CommandDoesNotExistException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (StringInterpreterBodyMissingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ComparorOperatorNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (StringOperatorManagerOperatorDoesNotExistException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {

			}

			scanner.close();
		}

	}
}
