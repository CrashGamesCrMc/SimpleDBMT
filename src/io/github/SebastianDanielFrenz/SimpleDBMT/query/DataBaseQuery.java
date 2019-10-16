package io.github.SebastianDanielFrenz.SimpleDBMT.query;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 1.3.1
 */

import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;

public abstract class DataBaseQuery {

	public DataBaseQuery(DataBaseHandler dbh) {
		this.dbh = dbh;
	}

	private DataBaseHandler dbh;

	public DataBaseHandler getDbh() {
		return dbh;
	}

	public void setDbh(DataBaseHandler dbh) {
		this.dbh = dbh;
	}

	public TableQuery getTableQuery() {
		return tableQuery;
	}

	public void setTableQuery(TableQuery tableQuery) {
		this.tableQuery = tableQuery;
	}

	private String dataBase;
	private TableQuery tableQuery;

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public abstract QueryResult Run(String dataBaseName, String tableName, String[] columnNames,
			SearchedValue[] searchedValues);

	/**
	 * @since SimpleDB 1.3.1
	 * @param dataBaseName
	 * @param tableName
	 * @param columnNames
	 * @param searchedValueConditions
	 * @param comparor
	 * @return
	 * @throws QueryComparorMissingException
	 */
	public abstract QueryResult Run(String dataBaseName, String tableName, String[] columnNames,
			SearchedValueCondition[] searchedValueConditions, Comparor comparor) throws QueryComparorMissingException;

	public abstract void Insert(String dataBaseName, String tableName, SearchedValue[] newValues);

	public abstract void Update(String dataBaseName, String tableName, SearchedValue[] conditions,
			SearchedValue[] newValues);

	/**
	 * @since SimpleDB 1.3.1
	 * @param dataBaseName
	 * @param tableName
	 * @param conditions
	 * @param newValues
	 * @throws ComparorOperatorNotSupportedException
	 */
	public abstract void Update(String dataBaseName, String tableName, SearchedValueCondition[] conditions,
			SearchedValue[] newValues, Comparor comparor) throws ComparorOperatorNotSupportedException;

	public abstract void Delete(String dataBaseName, String tableName, SearchedValue[] conditions);

	/**
	 * @since SimpleDB 1.3.1
	 * @param dataBaseName
	 * @param tableName
	 * @param conditions
	 * @throws ComparorOperatorNotSupportedException
	 */
	public abstract void Delete(String dataBaseName, String tableName, SearchedValueCondition[] conditions,
			Comparor comparor) throws ComparorOperatorNotSupportedException;

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

	public abstract Table LeftJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

	public abstract Table RightJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

	public abstract Table InnerJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

	public abstract Table FullJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions);

}
