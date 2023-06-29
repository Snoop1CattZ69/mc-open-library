package org.aslstd.api.openlib.expression.parser;

/**
 * <p>ExpressionSource interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface ExpressionSource {
	/**
	 * <p>hasNext.</p>
	 *
	 * @return a boolean
	 */
	boolean hasNext();
	/**
	 * <p>next.</p>
	 *
	 * @return a char
	 */
	char next();
	/**
	 * <p>error.</p>
	 *
	 * @param message a {@link String} object
	 * @return a {@link org.aslstd.api.openlib.expression.parser.ExpressionException} object
	 */
	ExpressionException error(final String message);
	/**
	 * <p>getPos.</p>
	 *
	 * @return a int
	 */
	int getPos();
	/**
	 * <p>nextVariableNum.</p>
	 *
	 * @return a int
	 */
	int nextVariableNum();
}
