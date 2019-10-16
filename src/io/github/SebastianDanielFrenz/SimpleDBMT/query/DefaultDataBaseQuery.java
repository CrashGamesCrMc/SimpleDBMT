package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;

import io.github.SebastianDanielFrenz.SimpleDBMT.DataBaseHandler;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;

public class DefaultDataBaseQuery extends DataBaseQuery {

	public DefaultDataBaseQuery(DataBaseHandler dbh) {
		super(dbh);
	}

	@Override
	public QueryResult Run(String dataBaseName, String tableName, String[] columnNames,
			SearchedValue[] searchedValues) {
		return new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Run(tableName, columnNames, searchedValues);
	}

	@Override
	public QueryResult Run(String dataBaseName, String tableName, String[] columnNames,
			SearchedValueCondition[] searchedValueConditions, Comparor comparor) throws QueryComparorMissingException {
		return new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Run(tableName, columnNames,
				searchedValueConditions, comparor);
	}

	@Override
	public void Insert(String dataBaseName, String tableName, SearchedValue[] newValues) {
		new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Insert(tableName, newValues);
	}

	@Override
	public void Update(String dataBaseName, String tableName, SearchedValue[] conditions, SearchedValue[] newValues) {
		new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Update(tableName, conditions, newValues);
	}

	@Override
	public void Update(String dataBaseName, String tableName, SearchedValueCondition[] conditions,
			SearchedValue[] newValues, Comparor comparor) throws ComparorOperatorNotSupportedException {
		new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Update(tableName, conditions, newValues, comparor);
	}

	@Override
	public void Delete(String dataBaseName, String tableName, SearchedValue[] conditions) {
		new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Delete(tableName, conditions);
	}

	@Override
	public void Delete(String dataBaseName, String tableName, SearchedValueCondition[] conditions, Comparor comparor)
			throws ComparorOperatorNotSupportedException {
		new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).Delete(tableName, conditions, comparor);
	}

	@Override
	public Table LeftJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		return new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).LeftJoin(getDbh().getValueManager(),
				tableName1, tableName2, columnNames, columnOrigins, conditions);
	}

	@Override
	public Table RightJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		return new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).RightJoin(getDbh().getValueManager(),
				tableName1, tableName2, columnNames, columnOrigins, conditions);
	}

	@Override
	public Table InnerJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		return new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).InnerJoin(getDbh().getValueManager(),
				tableName1, tableName2, columnNames, columnOrigins, conditions);
	}

	@Override
	public Table FullJoin(String dataBaseName, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		return new DefaultTableQuery(getDbh().getDataBase(dataBaseName)).FullJoin(getDbh().getValueManager(),
				tableName1, tableName2, columnNames, columnOrigins, conditions);
	}

}