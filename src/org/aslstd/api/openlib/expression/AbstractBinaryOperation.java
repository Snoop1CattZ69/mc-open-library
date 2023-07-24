package org.aslstd.api.openlib.expression;


import java.util.ArrayList;
import java.util.List;

import org.aslstd.api.openlib.expression.exception.DivideByZeroException;
import org.aslstd.api.openlib.expression.exception.OverflowException;

/**
 * <p>Abstract AbstractBinaryOperation class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public abstract class AbstractBinaryOperation implements CommonExpression {
	EnumBinaryOperation op;
	private final CommonExpression left, right;
	private final List<String> variables;
	/**
	 * <p>Constructor for AbstractBinaryOperation.</p>
	 *
	 * @param left a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 * @param op a {@link org.aslstd.api.openlib.expression.EnumBinaryOperation} object
	 */
	public AbstractBinaryOperation(final CommonExpression left, final CommonExpression right, final EnumBinaryOperation op) {
		this.left = left;
		this.right = right;
		this.op = op;
		variables = new ArrayList<>();
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
	}

	/** {@inheritDoc} */
	@Override
	public List<String> getVariables() {
		return new ArrayList<>(variables);
	}

	/**
	 * <p>doubleCalculate.</p>
	 *
	 * @param x a double
	 * @param y a double
	 * @return a double
	 * @throws java.lang.ArithmeticException if any.
	 */
	public abstract double doubleCalculate(double x, double y) throws ArithmeticException;

	/**
	 * <p>getOperation.</p>
	 *
	 * @return a {@link String} object
	 */
	public String getOperation() {
		return EnumBinaryOperation.getStringByOp(op);
	}

	/** {@inheritDoc} */
	@Override
	public abstract int getPriority();

	/**
	 * <p>isAssocOperation.</p>
	 *
	 * @return a boolean
	 */
	public boolean isAssocOperation() {
		return op == EnumBinaryOperation.Add || op == EnumBinaryOperation.Mult;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		//return toMiniString();
		return "(" + left.toString() + getOperation() + right.toString() + ")";
	}

	private boolean checkRight() {
		return right instanceof AbstractBinaryOperation
				&& getPriority() == right.getPriority()
				&& (!isAssocOperation() || !((AbstractBinaryOperation) right).isAssocOperation());
	}

	private static String getBrackets(final String s, final boolean isBrackets) {
		if (isBrackets) {
			return "(" + s + ")";
		} else {
			return s;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toMiniString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getBrackets(left.toMiniString(), getPriority() > left.getPriority()));
		sb.append(getOperation());
		sb.append(getBrackets(right.toMiniString(), getPriority() > right.getPriority() || checkRight()));
		return sb.toString();
	}

	/** {@inheritDoc} */
	@Override
	public double evaluate(double ...args) throws OverflowException, DivideByZeroException {
		return doubleCalculate(left.evaluate(args), right.evaluate(args));
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
		return left.equals(((AbstractBinaryOperation) b).left)
				&&  right.equals(((AbstractBinaryOperation) b).right)
				&&  op == ((AbstractBinaryOperation) b).op;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return left.hashCode() + 31 * (right.hashCode() +  31 * getOperation().hashCode());
	}
}
