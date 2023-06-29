package org.aslstd.api.openlib.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.message.Texts;
import org.aslstd.api.openlib.exceptions.CatchableNullException;

import lombok.SneakyThrows;

public class Obj {

	@SneakyThrows
	public static void checkNull(Object... objects) {
		checkNull("Null catched", objects);
	}

	@SneakyThrows
	public static void checkNull(String message, Object... objects) {
		List<Object> obj = Stream.of(objects).filter(Objects::isNull).collect(Collectors.toList());
		if (!obj.isEmpty()) {
			for (Object o : obj)
				Texts.send(message + ": " + o.getClass().getName() + " is null");
			throw new CatchableNullException(message);
		}
	}

	@SneakyThrows
	public static <O> O nonNull(String message, O obj) {
		if (obj == null) throw new CatchableNullException(message);
		return obj;
	}

	@SneakyThrows
	public static <O> O nonNull(O obj) {
		return nonNull("Null catched", obj);
	}

	public static boolean classExist(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}

}
