package org.aslstd.api.openlib.expression;

/**
 * <p>ToMiniString interface.</p>
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)

 */
public interface ToMiniString {
	/**
	 * <p>toMiniString.</p>
	 *
	 * @return a {@link String} object
	 */
	default String toMiniString() {
		return toString();
	}
}
