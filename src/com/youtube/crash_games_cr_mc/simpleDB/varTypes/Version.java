package com.youtube.crash_games_cr_mc.simpleDB.varTypes;

public class Version {

	private int[] numbers;

	public Version(int[] version) {
		numbers = version;
	}

	public Version(String version) {
		String[] split = version.split("\\.");
		numbers = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			numbers[i] = Integer.parseInt(split[i]);
		}
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < numbers.length - 1; i++) {
			out += String.valueOf(numbers[i]);
			out += ".";
		}
		if (numbers.length != 0) {
			out += String.valueOf(numbers[numbers.length - 1]);
		}
		return out;
	}

}
