package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.CommandDoesNotExistException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringInterpreterBodyMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringOperatorManagerOperatorDoesNotExistException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.DefaultStringOperatorManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.DefaultStringValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.FullValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.Comparor;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.DefaultComparor;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryResult;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.SimpleStringDataBaseQuery;

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
						if (parts.length > 1) {
							result.DumpHTMLandFormat(parts[1]);
						}
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
				e.printStackTrace();
			}

		}

		scanner.close();

	}
}
