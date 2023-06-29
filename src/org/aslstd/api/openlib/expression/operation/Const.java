package org.aslstd.api.openlib.expression.operation;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.openlib.expression.CommonExpression;

/**
 * <p>Const class.</p>
 *
 * @author Snoop1CattZ69
 */
public class Const implements CommonExpression {
	private final Number num;

	/**
	 * <p>Constructor for Const.</p>
	 *
	 * @param num a double
	 */
	public Const(final double num) {
		this.num = num;
	}
	/**
	 * <p>Constructor for Const.</p>
	 *
	 * @param num a int
	 */
	public Const(final int num) {
		this.num = num;
	}
	/** {@inheritDoc} */
	@Override
	public double evaluate(double ...args) {
		return num.doubleValue();
	}

	/** {@inheritDoc} */
	@Override
	public List<String> getVariables() {
		return new ArrayList<>();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return num.toString();
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 99;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object b) {
		if (b == this) {
			return true;
		}
		if (b == null || b.getClass() != this.getClass()) {
			return false;
		}
		return num.equals(((Const) b).num);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return (num.intValue() << 16) * 31;
	}

}
