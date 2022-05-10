package osc.btlang.core.exception;

/**
 *
 */
public class FunctionParseException extends RuntimeException{

	public FunctionParseException(String string) {
		super(string);
	}

	public FunctionParseException(String message, Exception innerException) {
        super(message, innerException);
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
