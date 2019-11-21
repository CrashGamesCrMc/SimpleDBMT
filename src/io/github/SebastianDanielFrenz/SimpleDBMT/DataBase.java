package io.github.SebastianDanielFrenz.SimpleDBMT;

import java.util.ArrayList;
import java.util.List;

import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.registry.RegistryValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.registry.TypeRegistryTableValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.Saveable;

/**
 * 
 * @since SimpleDB 1.0.0
 * @version SimpleDB 2.2.0
 *
 */

public class DataBase implements Saveable {

	/**
	 * If you can, use DataBase(RegistryValueManager valueManager) instead. This
	 * constructor will not comply with newer features.<br>
	 * Using a type registry is advised.
	 * 
	 * @param valueManager
	 */
	@Deprecated
	public DataBase(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	/**
	 * 
	 * @param reg
	 * @since SimpleDBMT 2.2.0
	 */
	public DataBase(RegistryValueManager valueManager) {
		this.valueManager = valueManager;
	}

	private ValueManager valueManager;

	public ValueManager getValueManager() {
		return valueManager;
	}

	public void setValueManager(ValueManager valueManager) {
		this.valueManager = valueManager;
	}

	private String name;
	private List<String> tablenames = new ArrayList<String>();
	private List<Table> tables = new ArrayList<Table>();

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<String> getTablenames() {
		return tablenames;
	}

	public void setTablenames(List<String> tablenames) {
		this.tablenames = tablenames;
	}

	/**
	 * @version SimpleDBMT 2.2.0
	 */
	@Override
	public void Parse(String text) {
		text = text.replace("\\\\", new String(new char[] { 0 }));

		String[] parts = text.split("\\\\" + CrashedDBsep.sepInternalDataBase);
		String name = parts[0].replace((char) 0, '\\');
		String rawTableNames = parts[1];
		String rawTables = parts[2];
		this.name = name;
		Table table;

		String[] rawTableDataStrings = rawTables.split("\\\\" + CrashedDBsep.sepTables);
		boolean hasRegistry = valueManager instanceof RegistryValueManager;

		if (hasRegistry) {
			table = new Table(new TypeRegistryTableValueManager());
			table.Parse(rawTableDataStrings[0]);
			((RegistryValueManager) valueManager).getTypeRegistry().fromTable(table);
		}

		for (int i = hasRegistry ? 1 : 0; i < rawTableDataStrings.length; i++) {
			table = new Table(valueManager);
			table.Parse(rawTableDataStrings[i]);
			tables.add(table);
		}
		for (String tableName : rawTableNames.split("\\\\" + CrashedDBsep.sepTableNames)) {
			tablenames.add(tableName.replace((char) 0, '\\'));
		}
	}

	/**
	 * @version SimpleDBMT 2.2.0
	 */
	@Override
	public String Save() {
		if (valueManager instanceof RegistryValueManager) {
			addTable("__sys_typereg", ((RegistryValueManager) valueManager).getTypeRegistry().toTable());
		}

		String output = "";
		output += name;
		output += "\\" + CrashedDBsep.sepInternalDataBase;

		// rawTableNames

		for (int i = 0; i < tablenames.size() - 1; i++) {
			output += tablenames.get(i);
			output += "\\" + CrashedDBsep.sepTableNames;
		}
		if (tablenames.size() != 0) {
			output += tablenames.get(tablenames.size() - 1);
		}
		output += "\\" + CrashedDBsep.sepInternalDataBase;
		// rawTables

		for (int i = 0; i < tables.size() - 1; i++) {
			output += tables.get(i).Save();
			output += "\\" + CrashedDBsep.sepTables;
		}
		if (tables.size() != 0) {
			output += tables.get(tables.size() - 1).Save();
		}
		return output;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void createTable(String name) {
		tablenames.add(name);
		tables.add(new Table(valueManager));
	}

	public void deleteTable(String name) {
		int index = tablenames.indexOf(name);
		tablenames.remove(index);
		tables.remove(index);
	}

	public Table getTable(String name) {
		return tables.get(tablenames.indexOf(name));
	}

	/**
	 * Adds the <u>table</u> to the list in the <i>DataBase</i>.
	 * 
	 * @since SimpleDB 1.2.4
	 * @param name
	 * @param table
	 */
	public void addTable(String name, Table table) {
		tablenames.add(name);
		tables.add(table);
	}

}
