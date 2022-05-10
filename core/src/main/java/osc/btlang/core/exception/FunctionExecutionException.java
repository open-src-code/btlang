package osc.btlang.core.exception;

/**
 *
 */
public class FunctionExecutionException extends RuntimeException{

	public FunctionExecutionException(String string) {
		super(string);
	}

	public FunctionExecutionException(String message, Exception innerException) {
        super(message, innerException);
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
