package osc.btlang.core.exception;

/**
 *
 */
public class FunctionExecutorNotFoundException extends RuntimeException{

	public FunctionExecutorNotFoundException(String string) {
		super(string);
	}

	public FunctionExecutorNotFoundException(String message, Exception innerException) {
        super(message, innerException);
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
