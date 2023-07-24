package org.aslstd.api.openlib.expression;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Abstract AbstractUnaryOperation class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public abstract class AbstractUnaryOperation implements CommonExpression {
	private final EnumUnaryOperation op;
	private final CommonExpression expr;
	private final List<String> variables;

	/**
	 * <p>Constructor for AbstractUnaryOperation.</p>
	 *
	 * @param expr a {@link org.aslstd.api.openlib.expression.CommonExpression} object
	 * @param op a {@link org.aslstd.api.openlib.expression.EnumUnaryOperation} object
	 */
	public AbstractUnaryOperation(final CommonExpression expr, final EnumUnaryOperation op) {
		this.expr = expr;
		this.op = op;
		variables = expr.getVariables();
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
	 * @return a double
	 * @throws java.lang.ArithmeticException if any.
	 */
	public abstract double doubleCalculate(double x) throws ArithmeticException;

	/**
	 * <p>getOperation.</p>
	 *
	 * @return a {@link String} object
	 */
	public String getOperation() {
		return EnumUnaryOperation.getStringByOp(op);
	}

	/** {@inheritDoc} */
	@Override
	public abstract int getPriority();

	/** {@inheritDoc} */
	@Override
	public String toString() {
		//return toMiniString();
		return "(" + getOperation() + expr.toString() + ")";
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
		sb.append(getOperation());
		sb.append(getBrackets(expr.toMiniString(), getPriority() > expr.getPriority()));
		return sb.toString();
	}

	/** {@inheritDoc} */
	@Override
	public double evaluate(double...args) throws ArithmeticException {
		return doubleCalculate(expr.evaluate(args));
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
		return expr.equals(((AbstractUnaryOperation) b).expr)
				&&  op == ((AbstractUnaryOperation) b).op;

	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return expr.hashCode() + 31 * getOperation().hashCode();
	}
}
