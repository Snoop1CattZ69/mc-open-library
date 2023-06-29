package org.aslstd.api.openlib.expression;

/**
 * <p>Abstract DoubleOperations class.</p>
 *
 * @author Snoop1CattZ69
 */
public abstract class DoubleOperations {
	/**
	 * <p>negate.</p>
	 *
	 * @param x a double
	 * @return a double
	 */
	public static double negate(double x) {
		return -x;
	}

	/**
	 * <p>abs.</p>
	 *
	 * @param x a double
	 * @return a double
	 */
	public static double abs(double x) {
		return Math.abs(x);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double add(double x, double y) {
		return x + y;
	}

	/**
	 * <p>subtract.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double subtract(double x, double y) {
		return x - y;
	}

	/**
	 * <p>multiply.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double multiply(double x, double y) {
		return x * y;
	}

	/**
	 * <p>divide.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double divide(double x, double y) {
		return x / y;
	}

	/**
	 * <p>mod.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double mod(double x, double y) {
		return x % y;
	}
	/**
	 * <p>pow.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double pow(double x, double y) {
		return Math.pow(x, y);
	}

	/**
	 * <p>log.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 */
	public static double log(double x, double y) {
		return Math.log(y) / Math.log(x);
	}

}
