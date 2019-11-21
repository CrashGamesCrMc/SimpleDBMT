package io.github.SebastianDanielFrenz.SimpleDBMT;

import io.github.SebastianDanielFrenz.SimpleDBMT.registry.TypeRegistry;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBBigInteger;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBString;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBVersion;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBboolean;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBbyte;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBdouble;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBfloat;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBint;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBlong;
import io.github.SebastianDanielFrenz.SimpleDBMT.varTypes.DBshort;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */
public class CrashedDBstock {

	public static final String VALUE_MISSING = "missing";
	public static final String VALUE_NULL = "null";

	public static TypeRegistry getDefaultTypeRegistry() {
		// Setup the registry table

		TypeRegistry typeRegistry = new TypeRegistry();

		typeRegistry.register(DBBigInteger.class);
		typeRegistry.register(DBboolean.class);
		typeRegistry.register(DBbyte.class);
		typeRegistry.register(DBdouble.class);
		typeRegistry.register(DBfloat.class);
		typeRegistry.register(DBint.class);
		typeRegistry.register(DBlong.class);
		typeRegistry.register(DBshort.class);
		typeRegistry.register(DBString.class);
		typeRegistry.register(DBVersion.class);

		return typeRegistry;
	}

}
