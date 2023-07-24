package org.aslstd.api.openlib.expression.exception;

/**
 * <p>ParsingException class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
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
