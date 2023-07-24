package org.aslstd.api.openlib.expression.operation;

import static org.aslstd.api.openlib.expression.DoubleOperations.pow;

import org.aslstd.api.openlib.expression.AbstractBinaryOperation;
import org.aslstd.api.openlib.expression.CommonExpression;
import org.aslstd.api.openlib.expression.EnumBinaryOperation;
import org.aslstd.api.openlib.expression.exception.OverflowException;

/**
 * <p>Pow class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class Pow extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Pow.</p>
	 *
	 * @param left a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 */
	public Pow(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Pow);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException, ArithmeticException {
		return pow(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 3;
	}
}
