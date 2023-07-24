package org.aslstd.api.openlib.expression.operation;

import static org.aslstd.api.openlib.expression.DoubleOperations.negate;

import org.aslstd.api.openlib.expression.AbstractUnaryOperation;
import org.aslstd.api.openlib.expression.CommonExpression;
import org.aslstd.api.openlib.expression.EnumUnaryOperation;
import org.aslstd.api.openlib.expression.exception.OverflowException;

/**
 * <p>Negate class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class Negate extends AbstractUnaryOperation {
	/**
	 * <p>Constructor for Negate.</p>
	 *
	 * @param expr a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 */
	public Negate(CommonExpression expr) {
		super(expr, EnumUnaryOperation.UnMinus);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x) throws OverflowException {
		return negate(x);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 4;
	}
}
