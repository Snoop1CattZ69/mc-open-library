package org.aslstd.api.openlib.expression.operation;

import static org.aslstd.api.openlib.expression.DoubleOperations.mod;

import org.aslstd.api.openlib.expression.AbstractBinaryOperation;
import org.aslstd.api.openlib.expression.CommonExpression;
import org.aslstd.api.openlib.expression.EnumBinaryOperation;
import org.aslstd.api.openlib.expression.exception.OverflowException;

/**
 * <p>Mod class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class Mod extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Mod.</p>
	 *
	 * @param left a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 */
	public Mod(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Mod);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return mod(x,y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 2;
	}
}
