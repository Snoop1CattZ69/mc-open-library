package org.aslstd.api.bukkit.value.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtil {

	public <T> List<T[]> split(int splitLength, T[] originData) {
		if (originData == null) return null;

		final List<T[]> splitData = new LinkedList<>();

		if (splitLength <= 0) return splitData;

		final int arraysAmount = (originData.length - 1) / splitLength + 1; // same as Math#ceil() but faster

		int fnIndex = 0; // always starts with this value

		for (int i = 0; i < arraysAmount; i++)
			splitData.add(Arrays.copyOfRange(originData, fnIndex, fnIndex +=
			Math.min(splitLength, originData.length - ((i) * splitLength) ) ) );

		return splitData;
	}

	public <T> List<T> getRandomized(int amount, BiPredicate<List<T>, ? super T> filter, List<T> origin) {
		if (origin == null || origin.isEmpty()) return new ArrayList<>();

		final List<T> copy = List.copyOf(origin);
		final List<T> result = new ArrayList<>();

		int i = 0;
		final Random rnd = new Random();
		while (i < amount && !copy.isEmpty()) {
			final T picked = copy.remove(rnd.nextInt(copy.size()-1));

			if (filter == null) {
				result.add(picked); i++; continue;
			}

			if (filter.test(result, picked)) {
				result.add(picked); i++; continue;
			}
		}

		return result;
	}

}
