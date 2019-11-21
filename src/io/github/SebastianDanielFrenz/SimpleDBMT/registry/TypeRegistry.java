package io.github.SebastianDanielFrenz.SimpleDBMT.registry;

import java.util.ArrayList;

import io.github.SebastianDanielFrenz.SimpleDBMT.Table;
import io.github.SebastianDanielFrenz.SimpleDBMT.error.TypeNotRegisteredException;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBint;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

/**
 * 
 * @since SimpleDBMT 2.2.0
 *
 */
public class TypeRegistry {

	private ArrayList<Class<? extends DBvalue>> types = new ArrayList<Class<? extends DBvalue>>();
	private ArrayList<Integer> IDs = new ArrayList<Integer>();

	public boolean contains(int ID) {
		return IDs.contains(ID);
	}

	public boolean contains(Class<? extends DBvalue> clazz) {
		return types.contains(clazz);
	}

	public int getID(Class<? extends DBvalue> clazz) throws TypeNotRegisteredException {
		try {
			return IDs.get(types.indexOf(clazz));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new TypeNotRegisteredException(clazz.getName());
		}
	}

	public Class<? extends DBvalue> getType(int ID) {
		return types.get(IDs.indexOf(ID));
	}

	public void register(int ID, Class<? extends DBvalue> clazz) {
		IDs.add(ID);
		types.add(clazz);
	}

	private int findFirstUnusedID() {
		int i;
		for (i = 0; IDs.contains(i); i++) {
		}
		return i;
	}

	public boolean register(Class<? extends DBvalue> clazz) {
		if (types.contains(clazz)) {
			return false;
		} else {
			types.add(clazz);
			IDs.add(findFirstUnusedID());
			return true;
		}

	}

	public Table toTable() {
		Table table = new Table(new TypeRegistryTableValueManager());
		table.addColumn("ID");
		table.addColumn("type");

		ArrayList<DBvalue> row;

		for (int i = 0; i < IDs.size(); i++) {
			row = new ArrayList<DBvalue>();

			row.add(new DBint(IDs.get(i)));
			row.add(new DBString(types.get(i).getCanonicalName()));
			table.addRow(row);
		}
		return table;
	}

	@SuppressWarnings("unchecked")
	public void fromTable(Table table) {
		for (ArrayList<DBvalue> row : table.getValues()) {
			try {
				register(((DBint) row.get(0)).getValue(),
						(Class<? extends DBvalue>) Class.forName(((DBString) row.get(1)).getValue()));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
