package org.aslstd.api.openlib.expression.exception;

/**
 * <p>UnexpectedSymbolException class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@SuppressWarnings("serial")
public class UnexpectedSymbolException extends ParsingException {
	/**
	 * <p>Constructor for UnexpectedSymbolException.</p>
	 *
	 * @param message a {@link String} object
	 */
	public UnexpectedSymbolException(final String message) {
		super(message);
	}
	/**
	 * <p>Constructor for UnexpectedSymbolException.</p>
	 *
	 * @param message a {@link String} object
	 * @param pos a int
	 */
	public UnexpectedSymbolException(final String message, final int pos) {
		super(message, pos);
	}
}
