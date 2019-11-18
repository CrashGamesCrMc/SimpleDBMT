package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.ComparorOperatorNotSupportedException;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.QueryComparorMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

import io.github.SebastianDanielFrenz.SimpleDBMT.CrashedDBstock;
import io.github.SebastianDanielFrenz.SimpleDBMT.DataBase;
import io.github.SebastianDanielFrenz.SimpleDBMT.Table;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 1.3.1
 */

public class DefaultTableQuery extends TableQuery {

	public DefaultTableQuery(DataBase db) {
		super(db);
	}

	@Override
	public QueryResult Run(String tableName, String[] columnNames, SearchedValue[] searchedValues) {
		QueryResult result = new QueryResult();
		ArrayList<DBvalue> resultRow;
		Table table = getDb().getTable(tableName);
		for (String columnName : columnNames) {
			result.headers.add(columnName);
		}
		for (ArrayList<DBvalue> row : table.getValues()) {
			if (meetsConditions(row, (ArrayList<String>) table.getHeaders(), searchedValues)) {
				resultRow = new ArrayList<DBvalue>();
				for (String columnName : columnNames) {
					resultRow.add(row.get(table.getHeaders().indexOf(columnName)));
				}
				result.rows.add(resultRow);
			}
		}
		return result;
	}

	@Override
	public QueryResult Run(String tableName, String[] columnNames, SearchedValueCondition[] searchedValueConditions,
			Comparor comparor) throws QueryComparorMissingException {
		QueryResult result = new QueryResult();
		ArrayList<DBvalue> resultRow;
		Table table = getDb().getTable(tableName);
		for (String columnName : columnNames) {
			result.headers.add(columnName);
		}
		for (ArrayList<DBvalue> row : table.getValues()) {
			if (meetsConditions(row, (ArrayList<String>) table.getHeaders(), searchedValueConditions, comparor)) {
				resultRow = new ArrayList<DBvalue>();
				for (String columnName : columnNames) {
					resultRow.add(row.get(table.getHeaders().indexOf(columnName)));
				}
				result.rows.add(resultRow);
			}
		}
		return result;
	}

	private boolean meetsConditions(ArrayList<DBvalue> row, ArrayList<String> headers,
			SearchedValueCondition[] searchedValueConditions, Comparor comparor) {
		for (SearchedValueCondition searchedValueCondition : searchedValueConditions) {
			try {
				if (!searchedValueCondition.isInternal()) {
					if (!comparor.Compare(row.get(headers.indexOf(searchedValueCondition.getColumn1())),
							searchedValueCondition.getOperator(),
							row.get(headers.indexOf(searchedValueCondition.getColumn2())))) {
						return false;
					}
				} else {
					if (!comparor.Compare(row.get(headers.indexOf(searchedValueCondition.getColumn1())),
							searchedValueCondition.getOperator(), (DBvalue) searchedValueCondition.getColumn2())) {
						return false;
					}
				}
			} catch (ComparorOperatorNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private boolean meetsConditions(ArrayList<DBvalue> row, ArrayList<String> headers, SearchedValue[] searchedValues) {
		for (SearchedValue searchedValue : searchedValues) {
			if (!row.get(headers.indexOf(searchedValue.getColumn())).Equals(searchedValue.getValue())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void Insert(String tableName, SearchedValue[] newValues) {
		ArrayList<DBvalue> row = new ArrayList<DBvalue>();
		Table table = getDb().getTable(tableName);
		for (int i = 0; i < table.getColumnCount(); i++) {
			row.add(new DBString("NULL"));
		}
		for (SearchedValue newValue : newValues) {
			row.set(table.getHeaders().indexOf(newValue.getColumn()), newValue.getValue());
		}
		getDb().getTable(tableName).addRow(row);
	}

	@Override
	public void Update(String tableName, SearchedValue[] conditions, SearchedValue[] newValues) {
		Table table = getDb().getTable(tableName);
		boolean possible;
		for (ArrayList<DBvalue> row : table.getValues()) {
			possible = true;
			for (SearchedValue condition : conditions) {
				if (!row.get(table.getHeaders().indexOf(condition.getColumn())).Equals(condition.getValue())) {
					possible = false;
					break;
				}
			}
			if (possible) {
				for (SearchedValue newValue : newValues) {
					row.set(table.getHeaders().indexOf(newValue.getColumn()), newValue.getValue());
				}
			}
		}
	}

	@Override
	public void Update(String tableName, SearchedValueCondition[] conditions, SearchedValue[] newValues,
			Comparor comparor) throws ComparorOperatorNotSupportedException {
		Table table = getDb().getTable(tableName);
		boolean possible;
		for (ArrayList<DBvalue> row : table.getValues()) {
			possible = true;
			for (SearchedValueCondition condition : conditions) {
				if (condition.isInternal()) {
					if (!comparor.Compare(row.get(table.getHeaders().indexOf(condition.getColumn1())),
							condition.getOperator(), (DBvalue) condition.getColumn2())) {
						possible = false;
						break;
					}
				} else {
					if (!comparor.Compare(row.get(table.getHeaders().indexOf(condition.getColumn1())),
							condition.getOperator(), row.get(table.getHeaders().indexOf(condition.getColumn2())))) {
						possible = false;
						break;
					}
				}
			}
			if (possible) {
				for (SearchedValue newValue : newValues) {
					row.set(table.getHeaders().indexOf(newValue.getColumn()), newValue.getValue());
				}
			}
		}
	}

	@Override
	public void Delete(String tableName, SearchedValue[] conditions) {
		Table table = getDb().getTable(tableName);
		boolean possible;
		int index = 0;
		ArrayList<DBvalue> row;
		int removed = 0;
		while (index < table.getValues().size() - removed) {
			row = table.getValues().get(index);
			possible = true;
			for (SearchedValue condition : conditions) {
				if (!row.get(table.getHeaders().indexOf(condition.getColumn())).Equals(condition.getValue())) {
					possible = false;
					break;
				}
			}
			if (possible) {
				table.getValues().remove(index);
				removed++;
			} else {
				index++;
			}
		}
	}

	@Override
	public void Delete(String tableName, SearchedValueCondition[] conditions, Comparor comparor)
			throws ComparorOperatorNotSupportedException {
		Table table = getDb().getTable(tableName);
		boolean possible;
		int index = 0;
		ArrayList<DBvalue> row;
		int removed = 0;
		while (index < table.getValues().size() - removed) {
			row = table.getValues().get(index);
			possible = true;
			for (SearchedValueCondition condition : conditions) {
				if (condition.isInternal()) {
					if (!comparor.Compare(row.get(table.getHeaders().indexOf(condition.getColumn1())),
							condition.getOperator(), (DBvalue) condition.getColumn2())) {
						possible = false;
						break;
					}
				} else {
					if (!comparor.Compare(row.get(table.getHeaders().indexOf(condition.getColumn1())),
							condition.getOperator(), row.get(table.getHeaders().indexOf(condition.getColumn2())))) {
						possible = false;
						break;
					}
				}
			}
			if (possible) {
				table.getValues().remove(index);
				removed++;
			} else {
				index++;
			}
		}
	}

	@Override
	public Table LeftJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		// left join stuff - start
		boolean[] usedIndexes = new boolean[table1.getRowCount()];
		int row1Counter = 0;
		// left join stuff - end
		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// left join stuff - start
					usedIndexes[row1Counter] = true;
					// left join stuff - end
					newRow = new ArrayList<DBvalue>();
					for (ColumnOrigin columnOrigin : columnOrigins) {
						if (columnOrigin.getTableID() == 1) {
							newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
						} else if (columnOrigin.getTableID() == 2) {
							newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
						}
					}
					table.addRow(newRow);
				}
			}
			row1Counter++;
		}
		// left join stuff - start
		int index = 0;
		for (boolean usedIndex : usedIndexes) {
			if (!usedIndex) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 1) {
						newRow.add(table1.getRow(index).get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 2) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// left join stuff - end
		return table;
	}

	@Override
	public Table RightJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		// right join stuff - start
		boolean[] usedIndexesRight = new boolean[table2.getRowCount()];
		int row2Counter;
		// right join stuff - end

		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			row2Counter = 0;
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// right join stuff - start
					usedIndexesRight[row2Counter] = true;
					// right join stuff - end
					newRow = new ArrayList<DBvalue>();
					for (ColumnOrigin columnOrigin : columnOrigins) {
						if (columnOrigin.getTableID() == 1) {
							newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
						} else if (columnOrigin.getTableID() == 2) {
							newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
						}
					}
					table.addRow(newRow);
				}
				row2Counter++;
			}
		}
		// right join stuff - start
		int index = 0;
		for (boolean usedIndexRight : usedIndexesRight) {
			if (!usedIndexRight) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 2) {
						newRow.add(table2.getRow(index).get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 1) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// right join stuff - end
		return table;
	}

	@Override
	public Table InnerJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					newRow = new ArrayList<DBvalue>();
					for (ColumnOrigin columnOrigin : columnOrigins) {
						if (columnOrigin.getTableID() == 1) {
							newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
						} else if (columnOrigin.getTableID() == 2) {
							newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
						}
					}
					table.addRow(newRow);
				}
			}
		}
		return table;
	}

	@Override
	public Table FullJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions) {
		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		// left join stuff - start
		boolean[] usedIndexes = new boolean[table1.getRowCount()];
		int row1Counter = 0;
		// left join stuff - end
		// right join stuff - start
		boolean[] usedIndexesRight = new boolean[table2.getRowCount()];
		int row2Counter;
		// right join stuff - end

		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			row2Counter = 0;
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// left join stuff - start
					usedIndexes[row1Counter] = true;
					// left join stuff - end
					// right join stuff - start
					usedIndexesRight[row2Counter] = true;
					// right join stuff - end
					newRow = new ArrayList<DBvalue>();
					for (ColumnOrigin columnOrigin : columnOrigins) {
						if (columnOrigin.getTableID() == 1) {
							newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
						} else if (columnOrigin.getTableID() == 2) {
							newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
						}
					}
					table.addRow(newRow);
				}
				row2Counter++;
			}
			row1Counter++;
		}
		// left join stuff - start
		int index = 0;
		for (boolean usedIndex : usedIndexes) {
			if (!usedIndex) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 1) {
						newRow.add(table1.getRow(index).get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 2) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// left join stuff - end
		// right join stuff - start
		index = 0;
		for (boolean usedIndexRight : usedIndexesRight) {
			if (!usedIndexRight) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 2) {
						newRow.add(table2.getRow(index).get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 1) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// right join stuff - end
		return table;
	}

	@Override
	public Table LeftJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions, SearchedValue[] conditions2,
			int[] conditions2Origins) {

		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		// left join stuff - start
		boolean[] usedIndexes = new boolean[table1.getRowCount()];
		int row1Counter = 0;
		// left join stuff - end
		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// 2nd condition
					for (int i = 0; i < conditions2.length; i++) {
						if (conditions2Origins[i] == 1) {
							if (!row1.get(table1.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						} else {
							if (!row2.get(table2.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						}
					}
					if (possible) {
						// 2nd condition end

						// left join stuff - start
						usedIndexes[row1Counter] = true;
						// left join stuff - end
						newRow = new ArrayList<DBvalue>();
						for (ColumnOrigin columnOrigin : columnOrigins) {
							if (columnOrigin.getTableID() == 1) {
								newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
							} else if (columnOrigin.getTableID() == 2) {
								newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
							}
						}
						table.addRow(newRow);
					}
				}
			}
			row1Counter++;
		}
		// left join stuff - start
		int index = 0;
		for (boolean usedIndex : usedIndexes) {
			if (!usedIndex) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 1) {
						newRow.add(table1.getRow(index).get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 2) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// left join stuff - end
		return table;
	}

	@Override
	public Table RightJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions, SearchedValue[] conditions2,
			int[] conditions2Origins) {

		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		// right join stuff - start
		boolean[] usedIndexesRight = new boolean[table2.getRowCount()];
		int row2Counter;
		// right join stuff - end

		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			row2Counter = 0;
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// 2nd condition
					for (int i = 0; i < conditions2.length; i++) {
						if (conditions2Origins[i] == 1) {
							if (!row1.get(table1.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						} else {
							if (!row2.get(table2.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						}
					}
					if (possible) {
						// 2nd condition end
						// right join stuff - start
						usedIndexesRight[row2Counter] = true;
						// right join stuff - end
						newRow = new ArrayList<DBvalue>();
						for (ColumnOrigin columnOrigin : columnOrigins) {
							if (columnOrigin.getTableID() == 1) {
								newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
							} else if (columnOrigin.getTableID() == 2) {
								newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
							}
						}
						table.addRow(newRow);
					}
				}
				row2Counter++;
			}
		}
		// right join stuff - start
		int index = 0;
		for (boolean usedIndexRight : usedIndexesRight) {
			if (!usedIndexRight) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 2) {
						newRow.add(table2.getRow(index).get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 1) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// right join stuff - end
		return table;
	}

	@Override
	public Table InnerJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions, SearchedValue[] conditions2,
			int[] conditions2Origins) {

		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// 2nd condition
					for (int i = 0; i < conditions2.length; i++) {
						if (conditions2Origins[i] == 1) {
							if (!row1.get(table1.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						} else {
							if (!row2.get(table2.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						}
					}
					if (possible) {
						// 2nd condition end
						newRow = new ArrayList<DBvalue>();
						for (ColumnOrigin columnOrigin : columnOrigins) {
							if (columnOrigin.getTableID() == 1) {
								newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
							} else if (columnOrigin.getTableID() == 2) {
								newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
							}
						}
						table.addRow(newRow);
					}
				}
			}
		}
		return table;
	}

	@Override
	public Table FullJoin(ValueManager valueManager, String tableName1, String tableName2, String[] columnNames,
			ColumnOrigin[] columnOrigins, QueryJoinCondition[] conditions, SearchedValue[] conditions2,
			int[] conditions2Origins) {

		Table table = new Table(valueManager);
		Table table1 = getDb().getTable(tableName1);
		Table table2 = getDb().getTable(tableName2);
		for (String columnName : columnNames) {
			table.addColumn(columnName);
		}
		boolean possible;
		ArrayList<DBvalue> newRow;
		// left join stuff - start
		boolean[] usedIndexes = new boolean[table1.getRowCount()];
		int row1Counter = 0;
		// left join stuff - end
		// right join stuff - start
		boolean[] usedIndexesRight = new boolean[table2.getRowCount()];
		int row2Counter;
		// right join stuff - end

		for (ArrayList<DBvalue> row1 : table1.getValues()) {
			row2Counter = 0;
			for (ArrayList<DBvalue> row2 : table2.getValues()) {
				possible = true;
				for (QueryJoinCondition condition : conditions) {
					if (!row1.get(table1.getHeaders().indexOf(condition.getColumnName1()))
							.Equals(row2.get(table2.getHeaders().indexOf(condition.getColumnName2())))) {
						possible = false;
						break;
					}
				}
				if (possible) {
					// 2nd condition
					for (int i = 0; i < conditions2.length; i++) {
						if (conditions2Origins[i] == 1) {
							if (!row1.get(table1.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						} else {
							if (!row2.get(table2.getHeaders().indexOf(conditions2[i].getColumn()))
									.Equals(conditions2[i].getValue())) {
								possible = false;
								break;
							}
						}
					}
					if (possible) {
						// 2nd condition end
						// left join stuff - start
						usedIndexes[row1Counter] = true;
						// left join stuff - end
						// right join stuff - start
						usedIndexesRight[row2Counter] = true;
						// right join stuff - end
						newRow = new ArrayList<DBvalue>();
						for (ColumnOrigin columnOrigin : columnOrigins) {
							if (columnOrigin.getTableID() == 1) {
								newRow.add(row1.get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
							} else if (columnOrigin.getTableID() == 2) {
								newRow.add(row2.get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
							}
						}
						table.addRow(newRow);
					}
				}
				row2Counter++;
			}
			row1Counter++;
		}
		// left join stuff - start
		int index = 0;
		for (boolean usedIndex : usedIndexes) {
			if (!usedIndex) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 1) {
						newRow.add(table1.getRow(index).get(table1.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 2) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// left join stuff - end
		// right join stuff - start
		index = 0;
		for (boolean usedIndexRight : usedIndexesRight) {
			if (!usedIndexRight) {
				newRow = new ArrayList<DBvalue>();
				for (ColumnOrigin columnOrigin : columnOrigins) {
					if (columnOrigin.getTableID() == 2) {
						newRow.add(table2.getRow(index).get(table2.getHeaders().indexOf(columnOrigin.getColumnName())));
					} else if (columnOrigin.getTableID() == 1) {
						newRow.add(new DBString(CrashedDBstock.VALUE_MISSING));
					}
				}
				table.addRow(newRow);
			}
			index++;
		}
		// right join stuff - end
		return table;
	}

}
