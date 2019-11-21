package io.github.SebastianDanielFrenz.SimpleDBMT.registry;

import io.github.SebastianDanielFrenz.SimpleDBMT.CrashedDBsep;
import io.github.SebastianDanielFrenz.SimpleDBMT.expandable.ValueManager;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBvalue;

/**
 * 
 * @since SimpleDBMT 2.2.0
 *
 */
public class RegistryValueManager extends ValueManager {

	private TypeRegistry typeRegistry;

	public TypeRegistry getTypeRegistry() {
		return typeRegistry;
	}

	public void setTypeRegistry(TypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
	}

	public RegistryValueManager(TypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
	}

	private char IDsep = CrashedDBsep.sepValueID;

	@Override
	public DBvalue Interpret(String text) {
		DBvalue output;
		String[] parts = text.split("\\\\" + IDsep);
		int ID = Integer.parseInt(parts[0]);
		String value = parts[1].replace((char) 0, '\\');

		Class<? extends DBvalue> type = typeRegistry.getType(ID);

		try {
			output = type.newInstance();
			output.Parse(value);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not construct an instance of " + type.getCanonicalName());
		}
		return output;
	}

	public int GetID(DBvalue value) {
		return typeRegistry.getID(value.getClass());
	}

	public char getIDsep() {
		return IDsep;
	}

	public void setIDsep(char iDsep) {
		IDsep = iDsep;
	}

	@Override
	public String Save(DBvalue value) {
		String output = "";
		output += GetID(value);
		output += "\\" + IDsep;
		output += value.Save().replace("\\", "\\\\");
		return output;
	}

}
