package org.aslstd.api.openlib.expression.parser;

import static org.aslstd.api.openlib.expression.EnumBinaryOperation.Undefined;
import static org.aslstd.api.openlib.expression.EnumBinaryOperation.getOpByString;
import static org.aslstd.api.openlib.expression.EnumBinaryOperation.getStringByOp;
import static org.aslstd.api.openlib.expression.EnumBinaryOperation.levels;

import org.aslstd.api.openlib.expression.CommonExpression;
import org.aslstd.api.openlib.expression.EnumBinaryOperation;
import org.aslstd.api.openlib.expression.Expression;
import org.aslstd.api.openlib.expression.exception.IllegalOperatorException;
import org.aslstd.api.openlib.expression.exception.MismatchArgumentException;
import org.aslstd.api.openlib.expression.exception.ParsingException;
import org.aslstd.api.openlib.expression.exception.UnexpectedSymbolException;
import org.aslstd.api.openlib.expression.operation.Add;
import org.aslstd.api.openlib.expression.operation.Const;
import org.aslstd.api.openlib.expression.operation.Divide;
import org.aslstd.api.openlib.expression.operation.Log;
import org.aslstd.api.openlib.expression.operation.Mod;
import org.aslstd.api.openlib.expression.operation.Multiply;
import org.aslstd.api.openlib.expression.operation.Negate;
import org.aslstd.api.openlib.expression.operation.Pow;
import org.aslstd.api.openlib.expression.operation.Subtract;
import org.aslstd.api.openlib.expression.operation.Variable;

/**
 * <p>ExpressionParser class.</p>
 *
 * @author Snoop1CattZ69 > Visit <a href="https://github.com/Snoop1CattZ69">Github</a>, <a href="https://www.spigotmc.org/resources/authors/115181/">Spigot</a>
 */
public class ExpressionParser extends BaseParser implements Parser {
	final static int MAX_LEVEL = levels.size() - 1;
	private EnumBinaryOperation operation = Undefined;

	/**
	 * <p>Constructor for ExpressionParser.</p>
	 *
	 * @param source a {@link org.aslstd.api.openlib.expression.parser.ExpressionSource} object
	 */
	public ExpressionParser(ExpressionSource source) {
		setSource(source);
	}

	/**
	 * <p>Constructor for ExpressionParser.</p>
	 */
	public ExpressionParser() { }

	/** {@inheritDoc} */
	@Override
	public Expression parse(String expression) throws ParsingException {
		return parse(new StringSource(expression));
	}

	private CommonExpression parse(ExpressionSource source) throws ParsingException {
		setSource(source);
		final CommonExpression result = parseExpression();
		if (ch != '\0') {
			throw new UnexpectedSymbolException("Expected: EOF, found: " + ch, pos);
		}
		return result;
	}

	private CommonExpression parseExpression() throws ParsingException {
		return parseLevel(0);
	}

	private CommonExpression nextLevel(int level) throws ParsingException {
		if (level == MAX_LEVEL) {
			return parseValue();
		} else {
			return parseLevel(level + 1);
		}
	}

	private boolean isThisLevelOp(int level) {
		operation = getOpByString("" + ch + secondCh);
		if (operation == Undefined) {
			operation = getOpByString("" + ch);
			return levels.get(level).contains(operation);
		}
		return levels.get(level).contains(operation);
	}

	private CommonExpression parseLevel(int level) throws ParsingException {
		skipWhitespace();
		CommonExpression result = nextLevel(level);
		skipWhitespace();
		while (isThisLevelOp(level)) {
			for (int i = 0; i < getStringByOp(operation).length(); i++)
				nextChar();
			result = createExpression(operation, result, nextLevel(level));
			skipWhitespace();
		}
		return result;
	}

	private CommonExpression parseValue() throws ParsingException {
		skipWhitespace();
		CommonExpression result;
		if (test('(')) {
			result = parseExpression();
			if (!test(')')) {
				throw new UnexpectedSymbolException("Excepted: ')', found: '" + ch + "'", pos);
			}
		} else if (test('-')) {
			if (between('0', '9')) {
				result = parseConst(true);
			} else {
				result = new Negate(parseValue());
			}
		} else if (between('0', '9')) {
			result = parseConst(false);
		} else if (test('%') || test(':') || test('_') || between('a', 'z') || between('A', 'Z')){
			result = parseVariable();
		} else {
			throw new MismatchArgumentException("Expected argument, found: " + ch, pos);
		}
		return result;
	}

	@SuppressWarnings("unused")
	private CommonExpression parseUnary(final String op) throws ParsingException {
		expect(op);
		if (!check('-') && !check('(') && !testWhiteSpace()) {
			throw new UnexpectedSymbolException("Expected: '-', '(',  or ' ', found: " + ch, pos);
		}
		return parseValue();
	}

	private CommonExpression parseVariable() {
		final StringBuilder name = new StringBuilder();
		while(check('%') || check(':') || check('_') || between('a', 'z') || between('A', 'Z')) {
			name.append(ch);
			nextChar();
		}
		return new Variable(name.toString(), nextVariableNum());
	}

	private CommonExpression parseConst(boolean isNegative) throws ParsingException {
		final StringBuilder sb = new StringBuilder();
		if (isNegative) {
			sb.append('-');
		}
		while(between('0', '9')) {
			sb.append(ch);
			nextChar();
		}
		if (test('.')) {
			sb.append('.');
		}
		while(between('0', '9')) {
			sb.append(ch);
			nextChar();
		}
		try {
			return new Const(Double.parseDouble(sb.toString()));
		} catch (final NumberFormatException e) {
			throw new MismatchArgumentException("Constant overflow: " + sb, pos);
		}
	}

	private void skipWhitespace() {
		while (testWhiteSpace()) {
			// skip
		}
	}

	private CommonExpression createExpression(EnumBinaryOperation op,
			CommonExpression left,
			CommonExpression right) throws ParsingException {
		switch (op) {
			case Add:
				return new Add(left, right);
			case Sub:
				return new Subtract(left, right);
			case Mult:
				return new Multiply(left, right);
			case Div:
				return new Divide(left, right);
			case Pow:
				return new Pow(left, right);
			case Log:
				return new Log(left, right);
			case Mod:
				return new Mod(left, right);
			default:
				throw new IllegalOperatorException(getStringByOp(op));
		}
	}
}
