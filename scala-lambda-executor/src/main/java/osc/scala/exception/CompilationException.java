package osc.scala.exception;

public class CompilationException extends RuntimeException{

    public CompilationException(String string) {
        super(string);
    }

    public CompilationException(String message, Exception innerException) {
        super(message, innerException);
    }
    public CompilationException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
