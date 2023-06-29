package org.aslstd.api.openlib.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>EnumBinaryOperation class.</p>
 *
 * @author Snoop1CattZ69
 */
@SuppressWarnings("serial")
public enum EnumBinaryOperation {
	Add, Sub, Mult, Div, Mod, Pow, Log, Undefined;

	/** Constant <code>stringByOp</code> */
	public static Map<EnumBinaryOperation, String> stringByOp = new HashMap<>() {{
		put(Add,       "+");
		put(Sub,       "-");
		put(Mult,      "*");
		put(Div,       "/");
		put(Mod,       "%");
		put(Pow,       "^");
		put(Log,       "//");
		put(Undefined, "");
	}};

	/** Constant <code>opByString</code> */
	public static Map<String, EnumBinaryOperation> opByString = new HashMap<>() {{
		put("+",    Add);
		put("-",    Sub);
		put("*",    Mult);
		put("/",    Div);
		put("%",    Mod);
		put("^",    Pow);
		put("//",   Log);
		put("",     Undefined);
	}};

	/** Constant <code>levels</code> */
	public static List<Set<EnumBinaryOperation>> levels = Arrays.asList(
			new HashSet<>(Arrays.asList(EnumBinaryOperation.Add, EnumBinaryOperation.Sub)),
			new HashSet<>(Arrays.asList(EnumBinaryOperation.Mult, EnumBinaryOperation.Div, EnumBinaryOperation.Mod)),
			new HashSet<>(Arrays.asList(EnumBinaryOperation.Pow, EnumBinaryOperation.Log))
			);

	/**
	 * <p>Getter for the field <code>opByString</code>.</p>
	 *
	 * @param op a {@link String} object
	 * @return a {@link org.aslstd.api.openlib.expression.EnumBinaryOperation} object
	 */
	public static EnumBinaryOperation getOpByString(final String op) {
		return opByString.getOrDefault(op, Undefined);
	}

	/**
	 * <p>Getter for the field <code>stringByOp</code>.</p>
	 *
	 * @param op a {@link org.aslstd.api.openlib.expression.EnumBinaryOperation} object
	 * @return a {@link String} object
	 */
	public static String getStringByOp(final EnumBinaryOperation op) {
		return stringByOp.get(op);
	}
}
