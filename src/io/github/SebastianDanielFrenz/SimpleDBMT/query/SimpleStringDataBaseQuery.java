package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import java.io.IOException;
import java.util.List;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.CommandDoesNotExistException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringInterpreterBodyMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.StringOperatorManagerOperatorDoesNotExistException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.StringOperatorManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.StringValueManager;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;

/**
 * 
 * @since SimpleDB 1.2.0
 * @version SimpleDB 1.3.1
 */

public class SimpleStringDataBaseQuery extends DefaultDataBaseQuery {

	private StringValueManager stringValueManager;
	private StringOperatorManager stringOperatorManager;

	public SimpleStringDataBaseQuery(DataBaseHandler dbh, StringValueManager stringValueManager,
			StringOperatorManager stringOperatorManager) {
		super(dbh);
		this.stringValueManager = stringValueManager;
		this.stringOperatorManager = stringOperatorManager;
	}

	/**
	 * Query schematic:<br>
	 * get |database| |table| |columns| |conditions|<br>
	 * <br>
	 * Some example queries:
	 * <ul>
	 * <li><b>get main users * *</b> (gets the entire main.users table)</li>
	 * <li><b>get main users user ID,int:1</b> (gets the user in main.users who
	 * has ID = 1)</li>
	 * <li><b>get main users ID,user,permission,darkTheme
	 * permission,str:default;darkTheme,bool:true</b> (gets every users' ID,
	 * user name, permission level and dark theme setting in main.users who has
	 * permission "default" and dark theme enabled)
	 * </ul>
	 * 
	 * @version SimpleDB 1.3.1
	 * @throws QueryComparorMissingException
	 * @throws StringOperatorManagerOperatorDoesNotExistException
	 */
	public QueryResult GetQuery(String cmd, Comparor comparor) throws StringInterpreterBodyMissingException,
			QueryComparorMissingException, StringOperatorManagerOperatorDoesNotExistException {

		String[] args = cmd.split(" ");

		// args[0] must be get
		// args[1] data base name
		// args[2] table name
		// args[3] column names (, separated)
		// args[4] searched values (; separated)

		String[] columnNames;

		if (args[3].equals("*")) {
			List<String> headers = getDbh().getDataBase(args[1]).getTable(args[2]).getHeaders();
			columnNames = new String[headers.size()];
			int i = 0;
			for (String columnName : headers) {
				columnNames[i] = columnName;
				i++;
			}
		} else {
			columnNames = args[3].split(",");
		}

		SearchedValueCondition[] searchedValues;
		if (args[4].equals("*")) {
			searchedValues = new SearchedValueCondition[0];
		} else {
			String[] rawSearchedValues = args[4].split(";");
			searchedValues = new SearchedValueCondition[rawSearchedValues.length];
			int i = 0;
			for (String condition : rawSearchedValues) {
				searchedValues[i] = new SearchedValueCondition();
				searchedValues[i].Parse(condition, stringValueManager, stringOperatorManager);
				i++;
			}
		}

		return Run(args[1], args[2], columnNames, searchedValues, comparor);
	}

	/**
	 * @version SimpleDB 1.3.1
	 * @param cmd
	 * @param comparor
	 * @return
	 * @throws StringInterpreterBodyMissingException
	 * @throws QueryComparorMissingException
	 * @throws StringOperatorManagerOperatorDoesNotExistException
	 */
	public String GetQueryString(String cmd, Comparor comparor) throws StringInterpreterBodyMissingException,
			QueryComparorMissingException, StringOperatorManagerOperatorDoesNotExistException {
		return GetQuery(cmd, comparor).ToTable("test", getDbh().getValueManager()).Save();

	}

	/**
	 * Query schematic:<br>
	 * <b>update |database| |table| |conditions| |new values|<br>
	 * insert |database| |table| |new values|<br>
	 * delete |database| |table| |conditions|<br>
	 * create database |database|<br>
	 * create table |database| |table|<br>
	 * create column |database| |table| |column|<br>
	 * load |database file path|<br>
	 * unload |database|<br>
	 * save |database| |file path|<br>
	 * </b> <br>
	 * Some example queries:
	 * <ul>
	 * <li><b>update main users user,str:admin darkTheme,bool:true</b> (update
	 * the admin's dark theme setting to true)</li>
	 * <li><b>insert main users user,str:root;darkTheme,bool:false</b> (add a
	 * new user "root" who has dark theme disabled)</li>
	 * <li><b>delete main users user,str:admin</b> (remove the user
	 * "admin")</li>
	 * <li><b>create database main</b> (creates the database "main")</li>
	 * <li><b>create table main users</b> (creates the table "users" in database
	 * "main")</li>
	 * <li><b>create column main users ID</b> (creates a new column in the table
	 * "users" in database "main")</li>
	 * <li><b>load main.db</b> (loads a database from the file "main.db")</li>
	 * <li><b>unload main</b> (unloads the database main;
	 * <b style="font-size: 25">THIS DOES NOT SAVE IT</b>)</li>
	 * <li><b>save main main.db</b> (saves the database "main" to
	 * "main.db")</li>
	 * </ul>
	 * 
	 * @param cmd
	 * @throws CommandDoesNotExistException
	 * @throws IOException
	 * @throws StringInterpreterBodyMissingException
	 * 
	 * @version SimpleDB 1.3.1
	 * @throws ComparorOperatorNotSupportedException
	 * @throws StringOperatorManagerOperatorDoesNotExistException
	 */
	public void ChangeQuery(String cmd, Comparor comparor)
			throws CommandDoesNotExistException, IOException, StringInterpreterBodyMissingException,
			ComparorOperatorNotSupportedException, StringOperatorManagerOperatorDoesNotExistException {

		String[] args = cmd.split(" ");
		if (args[0].equalsIgnoreCase("update")) {
			String[] rawConditions = args[3].split(";");
			String[] rawNewValues = args[4].split(";");
			SearchedValueCondition[] conditions = new SearchedValueCondition[rawConditions.length];
			SearchedValue[] newValues = new SearchedValue[rawNewValues.length];
			int i = 0;
			for (String condition : rawConditions) {
				conditions[i] = new SearchedValueCondition();
				conditions[i].Parse(condition, stringValueManager, stringOperatorManager);
				i++;
			}
			i = 0;
			for (String newValue : rawNewValues) {
				newValues[i] = new SearchedValue();
				newValues[i].Parse(newValue, stringValueManager);
				i++;
			}

			Update(args[1], args[2], conditions, newValues, comparor);
		} else if (args[0].equalsIgnoreCase("insert")) {
			String[] rawNewValues = args[3].split(";");
			SearchedValue[] newValues = new SearchedValue[rawNewValues.length];
			int i = 0;
			for (String newValue : rawNewValues) {
				newValues[i] = new SearchedValue();
				newValues[i].Parse(newValue, stringValueManager);
				i++;
			}
			Insert(args[1], args[2], newValues);
		} else if (args[0].equalsIgnoreCase("delete")) {
			String[] rawConditions = args[3].split(";");
			SearchedValueCondition[] conditions = new SearchedValueCondition[rawConditions.length];
			int i = 0;
			for (String condition : rawConditions) {
				conditions[i] = new SearchedValueCondition();
				conditions[i].Parse(condition, stringValueManager, stringOperatorManager);
				i++;
			}
			Delete(args[1], args[2], conditions, comparor);
		} else if (args[0].equalsIgnoreCase("create")) {
			if (args[1].equalsIgnoreCase("database")) {
				getDbh().createDataBase(args[2], args[2] + ".db");
			} else if (args[1].equalsIgnoreCase("table")) {
				getDbh().getDataBase(args[2]).createTable(args[3]);
			} else if (args[1].equalsIgnoreCase("column")) {
				getDbh().getDataBase(args[2]).getTable(args[3]).addColumn(args[4]);
			} else {
				throw new CommandDoesNotExistException(cmd);
			}
		} else if (args[0].equalsIgnoreCase("load")) {
			getDbh().addDataBase(args[1]);
		} else if (args[0].equalsIgnoreCase("unload")) {
			getDbh().unloadDataBase(args[1]);
		} else if (args[0].equalsIgnoreCase("save")) {
			getDbh().saveDataBase(args[1], args[2]);
		} else {
			throw new CommandDoesNotExistException(cmd);
		}
	}

	public StringOperatorManager getStringOperatorManager() {
		return stringOperatorManager;
	}

	public void setStringOperatorManager(StringOperatorManager stringOperatorManager) {
		this.stringOperatorManager = stringOperatorManager;
	}

}
