package io.github.SebastianDanielFrenz.SimpleDBMT.query;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class QueryJoinCondition {

	public QueryJoinCondition(String columnName1, String columnName2) {
		this.columnName1 = columnName1;
		this.columnName2 = columnName2;
	}

	private String columnName1;
	private String columnName2;

	public String getColumnName1() {
		return columnName1;
	}

	public void setColumnName1(String columnName1) {
		this.columnName1 = columnName1;
	}

	public String getColumnName2() {
		return columnName2;
	}

	public void setColumnName2(String columnName2) {
		this.columnName2 = columnName2;
	}

}
