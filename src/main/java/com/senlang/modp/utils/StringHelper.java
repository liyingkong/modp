package com.senlang.modp.utils;

import java.util.Optional;

/**
 * @author Mc.D
 *
 */
public final class StringHelper {
	public final static String Empty = "";

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.equals("");
	}

	public static boolean isNullOrWhiteSpace(String s) {
		return s == null || s.trim().equals("");
	}

	private String str;

	public StringHelper(String s) {
		this.str = s;
	}

	public Optional<Integer> tryToInt() {
		try {
			return Optional.of(Integer.parseInt(str));
		} catch (Exception e) {
			return null;
		}
	}

	public int tryToInt(int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

}
