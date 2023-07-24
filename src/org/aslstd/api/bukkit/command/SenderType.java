package org.aslstd.api.bukkit.command;

/**
 * <p>SenderType class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public enum SenderType {
	ALL,
	CONSOLE_ONLY,
	PLAYER_ONLY,
	;

	/**
	 * <p>fromString.</p>
	 *
	 * @param type a {@link String} object
	 * @return a {@link org.aslstd.api.bukkit.command.SenderType} object
	 */
	public static SenderType of(String type) {
		switch(type.toUpperCase()) {
		case "CONSOLE_ONLY": return CONSOLE_ONLY;
		case "PLAYER_ONLY": return PLAYER_ONLY;
		default: return ALL;
		}
	}

}
