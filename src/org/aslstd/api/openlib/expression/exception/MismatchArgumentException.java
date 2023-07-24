package org.aslstd.api.openlib.expression.exception;

/**
 * <p>MismatchArgumentException class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@SuppressWarnings("serial")
public class MismatchArgumentException extends ParsingException {
	/**
	 * <p>Constructor for MismatchArgumentException.</p>
	 *
	 * @param message a {@link String} object
	 */
	public MismatchArgumentException(final String message) {
		super(message);
	}
	/**
	 * <p>Constructor for MismatchArgumentException.</p>
	 *
	 * @param message a {@link String} object
	 * @param pos a int
	 */
	public MismatchArgumentException(final String message, final int pos) {
		super(message, pos);
	}
}
