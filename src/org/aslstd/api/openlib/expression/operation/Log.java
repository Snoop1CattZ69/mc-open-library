package org.aslstd.api.openlib.expression.operation;

import static org.aslstd.api.openlib.expression.DoubleOperations.log;

import org.aslstd.api.openlib.expression.AbstractBinaryOperation;
import org.aslstd.api.openlib.expression.CommonExpression;
import org.aslstd.api.openlib.expression.EnumBinaryOperation;

/**
 * <p>Log class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class Log extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Log.</p>
	 *
	 * @param left a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 */
	public Log(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Log);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws ArithmeticException {
		return log(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 3;
	}
}
