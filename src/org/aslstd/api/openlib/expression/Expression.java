package org.aslstd.api.openlib.expression;

import java.util.List;

import org.aslstd.api.openlib.expression.exception.DivideByZeroException;
import org.aslstd.api.openlib.expression.exception.OverflowException;

/**
 * <p>Expression interface.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public interface Expression extends ToMiniString {
	/**
	 * <p>evaluate.</p>
	 *
	 * @param args a double
	 * @return a double
	 * @throws org.aslstd.api.openlib.expression.exception.OverflowException if any.
	 * @throws org.aslstd.api.openlib.expression.exception.DivideByZeroException if any.
	 * @throws org.aslstd.api.openlib.expression.exception.ParsingException if any.
	 */
	double evaluate(double ...args) throws OverflowException, DivideByZeroException;
	/**
	 * <p>getVariables.</p>
	 *
	 * @return a {@link List} object
	 */
	List<String> getVariables();

}
