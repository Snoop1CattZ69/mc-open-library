package org.aslstd.api.bukkit.utils;

import org.aslstd.api.bukkit.message.Texts;
import org.bukkit.OfflinePlayer;

import lombok.Getter;

/**
 * <p>ServerVersion class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public final class ServerVersion {

	/** Constant <code>VER_1_5_2=1502</code> */
	/** Constant <code>VER_1_6_2=1602</code> */
	/** Constant <code>VER_1_6_4=1604</code> */
	/** Constant <code>VER_1_7_2=1702</code> */
	/** Constant <code>VER_1_7_5=1705</code> */
	/** Constant <code>VER_1_7_8=1708</code> */
	/** Constant <code>VER_1_7_9=1709</code> */
	/** Constant <code>VER_1_8_0=1800</code> */
	/** Constant <code>VER_1_8_3=1803</code> */
	/** Constant <code>VER_1_8_8=1808</code> */
	/** Constant <code>VER_1_9_0=1900</code> */
	/** Constant <code>VER_1_10_2=11002</code> */
	/** Constant <code>VER_1_11_0=11100</code> */
	/** Constant <code>VER_1_11_1=11101</code> */
	/** Constant <code>VER_1_11_2=11102</code> */
	/** Constant <code>VER_1_12_2=11202</code> */
	/** Constant <code>VER_1_13=11300</code> */
	/** Constant <code>VER_1_14=11400</code> */
	/** Constant <code>VER_1_14_4=11404</code> */
	/** Constant <code>VER_1_15=11500</code> */
	/** Constant <code>VER_1_15_2=11502</code> */
	/** Constant <code>VER_1_16=11600</code> */
	/** Constant <code>VER_1_16_4=11604</code> */
	/** Constant <code>VER_1_17=11700</code> */
	/** Constant <code>VER_1_18=11800</code> */
	public static final int
	/////////////////////// VERSION CONSTANS
	LEGACY		 = 0,	 //
	VER_1_16	 = 11600,//
	VER_1_16_4	 = 11604,//
	VER_1_17	 = 11700,//
	VER_1_18	 = 11800,//
	VER_1_19	 = 11900;//
	///////////////////////


	@Getter private static int		VERSION		= -1;
	@Getter private static String	TYPE		= "UNKNOWN";

	/**
	 * <p>init.</p>
	 *
	 * @param version a {@link String} object
	 * @param serverType a {@link String} object
	 */
	public static void init(String version, String serverType) {
		if (serverType != null) ServerVersion.TYPE = serverType;

		try {
			final int i = version.indexOf("-");
			if (i <= 0) return;
			final String pre = version.substring(0, i);
			final String[] pieces = pre.split("\\.");
			ServerVersion.VERSION = Integer.parseInt(pieces[0]) * 10000 + Integer.parseInt(pieces[1]) * 100;
			if (pieces.length > 2) ServerVersion.VERSION += Integer.parseInt(pieces[2]);
		} catch (final Exception e) {
			if (ServerVersion.VERSION == -1) try {
				OfflinePlayer.class.getDeclaredMethod("getUniqueId");
				ServerVersion.VERSION = 99999;
			} catch (final Exception ex) { ServerVersion.VERSION = ServerVersion.LEGACY; }
		}

		Texts.fine("&aServer version: &5'" + ServerVersion.TYPE + "-" + ServerVersion.VERSION + "'");
	}

	/**
	 * <p>isVersionAtLeast.</p>
	 *
	 * @param version a int
	 * @return a boolean
	 */
	public static boolean isVersionAtLeast(int version) { return ServerVersion.VERSION < version; }
	/**
	 * <p>isVersionAtMost.</p>
	 *
	 * @param version a int
	 * @return a boolean
	 */
	public static boolean isVersionAtMost(int version) { return ServerVersion.VERSION >= version; }

}
