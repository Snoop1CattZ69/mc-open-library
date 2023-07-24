package org.aslstd.api.openlib.expression.exception;

/**
 * <p>DivideByZeroException class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
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
