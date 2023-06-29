package org.aslstd.api.openlib.exceptions;


public class CatchableNullException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7775802362439589748L;


	public CatchableNullException(String message) {
		super(message, new NullPointerException());
	}

}
