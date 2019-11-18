package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.util.ArrayList;
import java.util.List;

import io.github.SebastianDanielFrenz.SimpleDBMT.error.InterpreterIDMissingException;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.query.QueryResult;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.Saveable;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 2.0.0
 */

public class Table implements Saveable {

	private int columnCount;
	private int rowCount;

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	private ValueManager valueManager;

	public Table(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	private List<ArrayList<DBvalue>> values = new ArrayList<ArrayList<DBvalue>>();
	private List<String> headers = new ArrayList<String>();

	public List<ArrayList<DBvalue>> getValues() {
		return values;
	}

	public void setValues(List<ArrayList<DBvalue>> values) {
		this.values = values;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
		columnCount = headers.size();
	}

	@Override
	public void Parse(String text) {
		String[] parts = text.split("\\\\" + CrashedDBsep.sepTableHeadersFromData);
		String[] rawHeaders = parts[0].split("\\\\" + CrashedDBsep.sepTableHeaders);

		for (String header : rawHeaders) {
			addColumn(header.replace((char) 0, '\\'));
		}

		String[] rows = parts[1].split("\\\\" + CrashedDBsep.sepRow);
		String[] _values;
		ArrayList<DBvalue> row;

		for (String _row : rows) {
			row = new ArrayList<DBvalue>();
			_values = _row.split("\\\\" + CrashedDBsep.sepValue);
			for (String value : _values) {
				row.add(valueManager.Interpret(value));
			}
			this.addRow(row);
		}
	}

	@Override
	public String Save() {
		String output = "";
		// headers
		for (int i = 0; i < headers.size() - 1; i++) {
			output += headers.get(i);
			output += "\\" + CrashedDBsep.sepTableHeaders;
		}
		if (headers.size() != 0) {
			output += headers.get(headers.size() - 1);
		}
		output += "\\" + CrashedDBsep.sepTableHeadersFromData;
		// data
		for (int x = 0; x < rowCount - 1; x++) {
			for (int y = 0; y < columnCount - 1; y++) {
				try {
					output += valueManager.Save(values.get(x).get(y));
				} catch (InterpreterIDMissingException e) {
					e.printStackTrace();
					return "TABLE_ERROR";
				}
				output += "\\" + CrashedDBsep.sepValue;
			}
			if (columnCount != 0) {
				try {
					output += valueManager.Save(values.get(x).get(columnCount - 1));
				} catch (InterpreterIDMissingException e) {
					e.printStackTrace();
					return "TABLE_ERROR";
				}
			}
			output += "\\" + CrashedDBsep.sepRow;
		}

		// inner repeat

		if (rowCount != 0) {
			for (int y = 0; y < columnCount - 1; y++) {
				try {
					output += valueManager.Save(values.get(rowCount - 1).get(y));
				} catch (InterpreterIDMissingException e) {
					e.printStackTrace();
					return "TABLE_ERROR";
				}
				output += "\\" + CrashedDBsep.sepValue;
			}
			if (columnCount != 0) {
				try {
					output += valueManager.Save(values.get(rowCount - 1).get(columnCount - 1));
				} catch (InterpreterIDMissingException e) {
					e.printStackTrace();
					return "TABLE_ERROR";
				}
			}
		}
		return output;
	}

	public ValueManager getValueManager() {
		return valueManager;
	}

	public void setValueManager(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	public void addRow(ArrayList<DBvalue> row) {
		values.add(row);
		rowCount++;
	}

	public void addColumn(String name) {
		for (ArrayList<DBvalue> row : values) {
			row.add(new DBString(CrashedDBstock.VALUE_NULL));
		}
		headers.add(name);
		columnCount++;
	}

	public ArrayList<DBvalue> getRow(int index) {
		return values.get(index);
	}

	public DBvalue getValue(String column, int row) {
		return getRow(row).get(headers.indexOf(column));
	}

	public QueryResult ToQueryResult() {
		QueryResult output = new QueryResult();
		output.headers = (ArrayList<String>) headers;
		output.rows = (ArrayList<ArrayList<DBvalue>>) values;
		return output;
	}

	public boolean isEmpty() {
		return rowCount == 0;
	}

}
