package io.github.SebastianDanielFrenz.SimpleDBMT.query;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

import io.github.SebastianDanielFrenz.SimpleDBMT.Table;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class QueryResult {

	public ArrayList<ArrayList<DBvalue>> rows = new ArrayList<ArrayList<DBvalue>>();
	public ArrayList<String> headers = new ArrayList<String>();

	public String ToHTML() {
		String output = "<table><tr>";
		for (String header : headers) {
			output += "<th>";
			output += header;
			output += "</th>";
		}
		output += "</tr>";
		for (ArrayList<DBvalue> row : rows) {
			output += "<tr>";
			for (DBvalue value : row) {
				output += "<td>";
				output += value.Save();
				output += "</td>";
			}
			output += "</tr>";
		}
		output += "</table>";
		return output;
	}

	public String ToFormattedHTML() {
		return "<style>table, td, th, tr {border: 3px solid black}</style>" + ToHTML();
	}

	public void DumpHTML(String path) throws FileNotFoundException {
		File file = new File(path);
		PrintWriter pw = new PrintWriter(file);
		pw.append(ToHTML());
		pw.close();
	}

	public void DumpHTMLandFormat(String path) throws FileNotFoundException {
		File file = new File(path);
		PrintWriter pw = new PrintWriter(file);
		pw.append(ToFormattedHTML());
		pw.close();
	}

	public Table ToTable(String tableName, ValueManager valueManager) {
		Table output = new Table(valueManager);
		output.setHeaders(headers);
		for (ArrayList<DBvalue> row : rows) {
			output.addRow(row);
		}
		return output;
	}

	public boolean isEmpty() {
		return rows.size() == 0;
	}

}
