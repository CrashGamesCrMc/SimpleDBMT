package com.youtube.crash_games_cr_mc.simpleDB.util;

/**
 * 
 * @since SimpleDB 1.3.0
 *
 */
public class MathUtil {

	public static double Lower(double x, double y) {
		if (x < y) {
			return x;
		}
		return y;
	}

	public static float Lower(float x, float y) {
		if (x < y) {
			return x;
		}
		return y;
	}

	public static int Lower(int x, int y) {
		if (x < y) {
			return x;
		}
		return y;
	}

}
