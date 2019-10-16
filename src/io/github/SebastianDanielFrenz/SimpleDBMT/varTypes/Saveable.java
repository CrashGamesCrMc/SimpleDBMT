package io.github.SebastianDanielFrenz.SimpleDBMT.varTypes;

/**
 * 
 * @since SimpleDB 1.0.0
 *
 */

public interface Saveable {

	public void Parse(String text);

	public String Save();
}
