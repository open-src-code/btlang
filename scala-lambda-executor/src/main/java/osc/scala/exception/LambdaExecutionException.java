package osc.scala.exception;

public class LambdaExecutionException extends RuntimeException{

    public LambdaExecutionException(String string) {
        super(string);
    }

    public LambdaExecutionException(String message, Exception innerException) {
        super(message, innerException);
    }
    public LambdaExecutionException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
