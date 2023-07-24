package org.aslstd.api.openlib.expression.exception;

/**
 * <p>IllegalVariableNameException class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@SuppressWarnings("serial")
public class IllegalVariableNameException extends ParsingException {
	/**
	 * <p>Constructor for IllegalVariableNameException.</p>
	 *
	 * @param name a {@link String} object
	 */
	public IllegalVariableNameException(final String name) {
		super("Invalid variable name: " + name);
	}
	/**
	 * <p>Constructor for IllegalVariableNameException.</p>
	 *
	 * @param name a {@link String} object
	 * @param pos a int
	 */
	public IllegalVariableNameException(final String name, final int pos) {
		super("Invalid variable name: " + name, pos);
	}
}
