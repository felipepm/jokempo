package com.btg.jokempo.exception;

public class NegocioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3330850203775825907L;

	/**
	 * @param errorMessage
	 */
	public NegocioException(String errorMessage) {
        super(errorMessage);
    }

}
