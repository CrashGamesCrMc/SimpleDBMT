package io.github.SebastianDanielFrenz.SimpleDBMT.query;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class ColumnOrigin {

	public ColumnOrigin(int tableID, String columnName) {
		this.tableID = tableID;
		this.columnName = columnName;
	}

	private int tableID;
	private String columnName;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

}
