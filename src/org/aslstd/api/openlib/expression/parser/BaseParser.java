package org.aslstd.api.openlib.expression.parser;

import org.aslstd.api.openlib.expression.exception.ParsingException;
import org.aslstd.api.openlib.expression.exception.UnexpectedSymbolException;

/**
 * <p>BaseParser class.</p>
 *
 * @author Snoop1CattZ69 (https://github.com/Snoop1CattZ69)
 */
public class BaseParser {
	private ExpressionSource source;
	protected char ch;
	protected char secondCh;
	protected int pos = 0;
	/**
	 * <p>Constructor for BaseParser.</p>
	 */
	protected BaseParser() {
	}

	/**
	 * <p>Constructor for BaseParser.</p>
	 *
	 * @param source a {@link org.aslstd.api.openlib.expression.parser.ExpressionSource} object
	 */
	protected BaseParser(final ExpressionSource source) {
		setSource(source);
	}

	/**
	 * <p>nextVariableNum.</p>
	 *
	 * @return a int
	 */
	public int nextVariableNum() {
		return source.nextVariableNum();
	}

	/**
	 * <p>Setter for the field <code>source</code>.</p>
	 *
	 * @param source a {@link org.aslstd.api.openlib.expression.parser.ExpressionSource} object
	 */
	protected void setSource(final ExpressionSource source) {
		this.source = source;
		pos = 0;
		nextChar();
		nextChar();
	}

	/**
	 * <p>hasNext.</p>
	 *
	 * @return a boolean
	 */
	protected boolean hasNext() {
		return source.hasNext();
	}

	/**
	 * <p>nextChar.</p>
	 */
	protected void nextChar() {
		ch = secondCh;
		if (hasNext()) {
			secondCh = source.next();
			pos++;
		} else {
			secondCh = '\0';
		}
	}

	/**
	 * <p>test.</p>
	 *
	 * @param expected a char
	 * @return a boolean
	 */
	protected boolean test(char expected) {
		if (ch == expected) {
			nextChar();
			return true;
		}
		return false;
	}

	/**
	 * <p>test.</p>
	 *
	 * @param value a {@link String} object
	 * @return a boolean
	 */
	protected boolean test(final String value) {
		for (final char c : value.toCharArray()) {
			if (!test(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>testWhiteSpace.</p>
	 *
	 * @return a boolean
	 */
	protected boolean testWhiteSpace() {
		if (isWhitespace(ch)) {
			nextChar();
			return true;
		}
		return false;
	}

	/**
	 * <p>check.</p>
	 *
	 * @param expected a char
	 * @return a boolean
	 */
	protected boolean check(char expected) {
		if (ch == expected) {
			return true;
		}
		return false;
	}

	/**
	 * <p>expect.</p>
	 *
	 * @param c a char
	 * @throws org.aslstd.api.openlib.expression.exception.ParsingException if any.
	 */
	protected void expect(final char c) throws ParsingException {
		if (ch != c) {
			throw new UnexpectedSymbolException("Expected: '" + c + "', found: '" + ch + "'", pos);
		}
		nextChar();
	}

	/**
	 * <p>expect.</p>
	 *
	 * @param value a {@link String} object
	 * @throws org.aslstd.api.openlib.expression.exception.ParsingException if any.
	 */
	protected void expect(final String value) throws ParsingException {
		for (final char c : value.toCharArray()) {
			expect(c);
		}
	}

	/**
	 * <p>between.</p>
	 *
	 * @param from a char
	 * @param to a char
	 * @return a boolean
	 */
	protected boolean between(final char from, final char to) {
		return from <= ch && ch <= to;
	}

	/**
	 * <p>isWhitespace.</p>
	 *
	 * @param c a char
	 * @return a boolean
	 */
	protected boolean isWhitespace(char c) {
		return c == ' ' || c == '\n' || c == '\t' || c == '\r';
	}
}
