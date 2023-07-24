package org.aslstd.api.bukkit.txt;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * <p>TXTReader class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
@RequiredArgsConstructor
public final class TXTReader {

	@NonNull File txtFile;

	/**
	 * <p>getLines.</p>
	 *
	 * @return a {@link List} object
	 */
	public List<String> getLines() {
		final List<String> result = new ArrayList<>();

		try (FileInputStream in = new FileInputStream(txtFile); Scanner scan = new Scanner(in);) {
			while (scan.hasNextLine()) {
				final String line = scan.nextLine();

				if (line == null || line.equalsIgnoreCase("") || line.matches("^\\s*$") || line.startsWith("/")) continue;

				result.add(line);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * <p>getCategorized.</p>
	 *
	 * @return a {@link java.util.Map} object
	 */
	public Map<String,List<String>> getCategorized() {
		final Map<String,List<String>> result = new HashMap<>();

		List<String> category = null;
		String currentCategory = null;
		for (final String line : getLines()) {
			if (line.startsWith("#")) {
				if (category != null && !category.isEmpty() && currentCategory != null) {
					result.put(currentCategory, category);
				}

				category = new ArrayList<>();
				currentCategory = line.replaceAll("#", "");
			} else {
				if (category != null)
					category.add(line);
			}
		}

		if (category != null && !category.isEmpty() && currentCategory != null) {
			result.put(currentCategory, category);
		}

		return result;
	}

}
