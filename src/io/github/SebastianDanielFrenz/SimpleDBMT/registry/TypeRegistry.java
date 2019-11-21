package io.github.SebastianDanielFrenz.SimpleDBMT.registry;

import java.util.ArrayList;

public class TypeRegistry {

	private ArrayList<Class<?>> types;
	private ArrayList<Integer> IDs;

	public boolean contains(int ID) {
		return IDs.contains(ID);
	}

	public boolean contains(Class<?> clazz) {
		return types.contains(clazz);
	}

	public int getID(Class<?> clazz) throws TypeNotPresentException {
		try {
			return IDs.get(types.indexOf(clazz));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new TypeNotPresentException(arg0, arg1)
		}
	}

}
