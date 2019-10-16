package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBase;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 1.3.1
 */

public abstract class TableQuery {

	public TableQuery(DataBase db) {
		this.db = db;
	}

	private String table;
	private DataBase db;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public abstract QueryResult Run(String tableName, String[] columnNames, SearchedValue[] searchedValues);

	/**
	 * @since SimpleDB 1.3.0
	 * @param tableName
	 * @param columnNames
	 * @param searchedValueConditions
	 * @param comparor
	 * @return
	 * @throws QueryComparorMissingException
	 */
	public abstract QueryResult Run(String tableName, String[] columnNames,
			SearchedValueCondition[] searchedValueConditions, Comparor comparor) throws QueryComparorMissingException;

	public DataBase getDb() {
		return db;
	}

	public void setDb(DataBase db) {
		this.db = db;
	}

	public abstract void Insert(String tableName, SearchedValue[] newValues);

	public abstract void Update(String tableName, SearchedValue[] conditions, SearchedValue[] newValues);

	/**
	 * @since SimpleDB 1.3.1
	 * @param tableName
	 * @param conditions
	 * @param newValues
	 * @param comparor
	 * @throws ComparorOperatorNotSupportedException
	 */
	public abstract void Update(String tableName, SearchedValueCondition[] conditions, SearchedValue[] newValues,
			Comparor comparor) throws ComparorOperatorNotSupportedException;

	public abstract void Delete(String tableName, SearchedValue[] conditions);

	/**
	 * @since SimpleDB 1.3.1
	 * @param tableName
	 * @param conditions
	 * @param comparor
	 * @throws ComparorOperatorNotSupportedException
	 */
	public abstract void Delete(String tableName, SearchedValueCondition[] conditions, Comparor comparor)
			throws ComparorOperatorNotSupportedException;

	public static ArrayList<DBvalue> SearchedValueArrayToRow(ArrayList<String> headers, SearchedValue[] array,
			int columnCount) {
		ArrayList<DBvalue> output = new ArrayList<DBvalue>();
		for (int i = 0; i < columnCount; i++) {
			output.add(new DBString("NULL"));
		}
		for (SearchedValue value : array) {
			output.set(headers.indexOf(value.getColumn()), value.getValue());
		}
		return output;
	}

	public abstract Table LeftJoin(ValueManager valueManager, String tableName1, String tableName2,
			String[] columnNames, ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

	public abstract Table RightJoin(ValueManager valueManager, String tableName1, String tableName2,
			String[] columnNames, ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

	public abstract Table InnerJoin(ValueManager valueManager, String tableName1, String tableName2,
			String[] columnNames, ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

	public abstract Table FullJoin(ValueManager valueManager, String tableName1, String tableName2,
			String[] columnNames, ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

}
