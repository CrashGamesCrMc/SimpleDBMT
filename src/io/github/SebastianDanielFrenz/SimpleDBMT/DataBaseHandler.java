package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public class DataBaseHandler {

	private ValueManager valueManager;

	private List<DataBase> DBs = new ArrayList<DataBase>();
	private List<String> DBnames = new ArrayList<String>();

	public List<String> getDBnames() {
		return DBnames;
	}

	public void setDBnames(List<String> dBnames) {
		DBnames = dBnames;
	}

	public List<DataBase> getDBs() {
		return DBs;
	}

	public void setDBs(List<DataBase> dBs) {
		DBs = dBs;
	}

	public DataBaseHandler(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	public ValueManager getValueManager() {
		return valueManager;
	}

	public void setValueManager(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	public void addDataBase(String path) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(path)));
		DataBase db = new DataBase(valueManager);
		db.Parse(content);
		DBs.add(db);
		DBnames.add(db.getName());
	}

	public void saveDataBase(String dataBase, String path) throws FileNotFoundException {
		File file = new File(path);
		PrintWriter pw = new PrintWriter(file);
		pw.write(DBs.get(DBnames.indexOf(dataBase)).Save());
		pw.close();
	}

	public void unloadDataBase(String dataBase) {
		int index = DBnames.indexOf(dataBase);
		DBnames.remove(index);
		DBs.remove(index);
	}

	public void createDataBase(String name) {
		DBnames.add(name);
		DataBase db = new DataBase(valueManager);
		db.setName(name);
		DBs.add(db);
	}

	public DataBase getDataBase(String name) {
		return DBs.get(DBnames.indexOf(name));
	}

	public void setDataBase(String name, DataBase db) {
		DBs.set(DBnames.indexOf(name), db);
	}

}
