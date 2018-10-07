/**
 * 
 */
package com.senlang.modp.utils;

/**
 * @author Mc.D
 *
 */
public final class Convert<T> {
	T value;

	private Convert(T value) {
		this.value = value;
	}

	public static <T> Convert<T> of(T value) {
		return new Convert<>(value);
	}

	public Convert<Boolean> tryToBool() {
		if (this.value == null) {
			return Convert.of(null);
		}
		String str = this.value.toString();
		return Convert.of("1".equalsIgnoreCase(str) || "是".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str));
	}

	public Convert<Long> tryToLong() {
		if (this.value == null) {
			return Convert.of(null);
		}
		String str = this.value.toString();
		try {
			return Convert.of(Long.parseLong(str));
		} catch (Exception e) {
			return Convert.of(null);
		}
	}

	public Convert<Integer> tryToInt() {
		if (this.value == null) {
			return Convert.of(null);
		}
		String str = this.value.toString();
		try {
			return Convert.of(Integer.parseInt(str));
		} catch (Exception e) {
			return Convert.of(null);
		}
	}

	public Convert<Float> tryToFloat() {
		if (this.value == null) {
			return Convert.of(null);
		}
		String str = this.value.toString();
		try {
			return Convert.of(Float.parseFloat(str));
		} catch (Exception e) {
			return Convert.of(null);
		}
	}

	public Convert<Double> tryToDouble() {
		if (this.value == null) {
			return Convert.of(null);
		}
		String str = this.value.toString();
		try {
			return Convert.of(Double.parseDouble(str));
		} catch (Exception e) {
			return Convert.of(null);
		}
	}
	
	public String toString(){
		return this.value==null?null:this.value.toString();
	}

	public T getValueOrDefault(T value) {
		return this.value == null ? value : this.value;
	}

}
