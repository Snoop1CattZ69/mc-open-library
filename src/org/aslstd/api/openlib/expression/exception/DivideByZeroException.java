package org.aslstd.api.openlib.expression.exception;

/**
 * <p>DivideByZeroException class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
@SuppressWarnings("serial")
public class DivideByZeroException extends ArithmeticException {
	/**
	 * <p>Constructor for DivideByZeroException.</p>
	 */
	public DivideByZeroException() {
		super("Division by zero");
	}
}
