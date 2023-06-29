package org.aslstd.api.openlib.expression.exception;

/**
 * <p>IllegalOperatorException class.</p>
 *
 * @author Snoop1CattZ69
 */
@SuppressWarnings("serial")
public class IllegalOperatorException extends ParsingException {

	/**
	 * <p>Constructor for IllegalOperatorException.</p>
	 *
	 * @param op a {@link String} object
	 */
	public IllegalOperatorException(final String op) {
		super("Illegal operation: " + op);
	}

	/**
	 * <p>Constructor for IllegalOperatorException.</p>
	 *
	 * @param op a {@link String} object
	 * @param pos a int
	 */
	public IllegalOperatorException(final String op, final int pos) {
		super("Illegal operation: " + op, pos);
	}

}
