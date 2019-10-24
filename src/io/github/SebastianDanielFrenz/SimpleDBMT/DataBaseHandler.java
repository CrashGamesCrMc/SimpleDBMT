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
 * @version SimpleDBMT 2.0.0
 */

public class DataBaseHandler {

	private ValueManager valueManager;
	private String dir;

	private List<DataBase> DBs = new ArrayList<DataBase>();
	private List<String> DBnames = new ArrayList<String>();
	private List<String> DBpaths = new ArrayList<String>();

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

	/**
	 * 
	 * @param valueManager
	 * @param dir
	 * 
	 * @since SimpleDBMT 2.0.0
	 */
	public DataBaseHandler(ValueManager valueManager, String dir) {
		this.valueManager = valueManager;
		this.dir = dir;
	}

	/**
	 * This constructor should only be used for compatibility. It
	 */
	@Deprecated
	public DataBaseHandler(ValueManager valueManager) {
		this.valueManager = valueManager;
		dir = ".";
	}

	public ValueManager getValueManager() {
		return valueManager;
	}

	public void setValueManager(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	/**
	 * 
	 * @param path
	 *            (a | at the beginning indicates a full path [since 2.0.0])
	 * @throws IOException
	 * 
	 * @version SimpleDBMT 2.0.0
	 */
	public void addDataBase(String path) throws IOException {
		String content;

		if (path.startsWith("|")) {
			content = new String(Files.readAllBytes(Paths.get(path.substring(1))));
			DBpaths.add(path.substring(1));
		} else {
			content = new String(Files.readAllBytes(Paths.get(dir + "/" + path)));
			DBpaths.add(path);
		}

		DataBase db = new DataBase(valueManager);
		db.Parse(content);
		DBs.add(db);
		DBnames.add(db.getName());
	}

	public void saveDataBase(String dataBase, String path) throws FileNotFoundException {
		File file;

		if (path.startsWith("|")) {
			file = new File(path.substring(1));
		} else {
			file = new File(dir + "/" + path);
		}
		PrintWriter pw = new PrintWriter(file);
		pw.write(DBs.get(DBnames.indexOf(dataBase)).Save());
		pw.close();
	}

	public void unloadDataBase(String dataBase) {
		int index = DBnames.indexOf(dataBase);
		DBnames.remove(index);
		DBs.remove(index);
	}

	/**
	 * 
	 * @param name
	 * @param path
	 * 
	 * @since SimpleDBMT 2.0.0
	 */
	public void createDataBase(String name, String path) {
		DBnames.add(name);
		DataBase db = new DataBase(valueManager);
		db.setName(name);
		DBs.add(db);
		DBpaths.add(path);
	}

	/**
	 * 
	 * @param name
	 * 
	 * @version SimpleDBMT 2.0.0
	 * @deprecated
	 */
	public void createDataBase(String name) {
		DBnames.add(name);
		DataBase db = new DataBase(valueManager);
		db.setName(name);
		DBs.add(db);
		DBpaths.add(name + ".db");
	}

	public DataBase getDataBase(String name) {
		return DBs.get(DBnames.indexOf(name));
	}

	public void setDataBase(String name, DataBase db) {
		DBs.set(DBnames.indexOf(name), db);
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @since SimpleDBMT 2.0.0
	 */
	public void saveDBs() {
		for (int i = 0; i < DBs.size(); i++) {
			try {
				saveDataBase(DBnames.get(i), DBpaths.get(i));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
