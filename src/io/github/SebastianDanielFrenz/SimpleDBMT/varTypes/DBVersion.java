package io.github.SebastianDanielFrenz.SimpleDBMT.varTypes;

/**
 * 
 * @s
 *
 */
public class DBVersion implements DBvalue {

	private Version value;

	public DBVersion(Version version) {
		value = version;
	}

	public DBVersion() {
		value = new Version(new int[] { 0 });
	}

	public DBVersion(String display) {
		value = new Version(display);
	}

	public Version getValue() {
		return value;
	}

	public void setValue(Version value) {
		this.value = value;
	}

	@Override
	public boolean Equals(DBCompareable value2) {
		if (value2 instanceof DBString) {
			if (((DBString) value2).getValue().equals(value.toString())) {
				return true;
			}
		} else if (value2 instanceof DBVersion) {
			if (((DBVersion) value2).getValue().getNumbers() == value.getNumbers()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void Parse(String text) {
		value = new Version(text);
	}

	@Override
	public String Save() {
		return value.toString();
	}

	@Override
	public String Display() {
		return value.toString();
	}

}
