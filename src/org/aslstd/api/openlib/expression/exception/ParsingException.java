package org.aslstd.api.openlib.expression.exception;

/**
 * <p>ParsingException class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
@SuppressWarnings("serial")
public class ParsingException extends Exception {
	/**
	 * <p>Constructor for ParsingException.</p>
	 *
	 * @param message a {@link String} object
	 */
	public ParsingException(final String message) {
		super(message);
	}
	/**
	 * <p>Constructor for ParsingException.</p>
	 *
	 * @param message a {@link String} object
	 * @param pos a int
	 */
	public ParsingException(final String message, final int pos) {
		super("pos: " + pos + ", " + message);
	}
}
