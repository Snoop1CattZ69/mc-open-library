package org.aslstd.api.openlib.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>EnumUnaryOperation class.</p>
 *
 * @author Snoop1CattZ69
 */
@SuppressWarnings("serial")
public enum EnumUnaryOperation {
	UnMinus, Undefined;

	/** Constant <code>stringByOp</code> */
	public static Map<EnumUnaryOperation, String> stringByOp = new HashMap<EnumUnaryOperation, String>() {{
		put(UnMinus,   "-");
		put(Undefined, "");
	}};

	/** Constant <code>opByString</code> */
	public static Map<String, EnumUnaryOperation> opByString = new HashMap<String, EnumUnaryOperation>() {{
		put("-",     UnMinus);
		put("",      Undefined);
	}};

	/**
	 * <p>Getter for the field <code>opByString</code>.</p>
	 *
	 * @param op a {@link String} object
	 * @return a {@link org.aslstd.api.openlib.expression.EnumUnaryOperation} object
	 */
	public static EnumUnaryOperation getOpByString(final String op) {
		return opByString.getOrDefault(op, Undefined);
	}

	/**
	 * <p>Getter for the field <code>stringByOp</code>.</p>
	 *
	 * @param op a {@link org.aslstd.api.openlib.expression.EnumUnaryOperation} object
	 * @return a {@link String} object
	 */
	public static String getStringByOp(final EnumUnaryOperation op) {
		return stringByOp.get(op);
	}
}
