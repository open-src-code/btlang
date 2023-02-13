package osc.scala.lambda.japi;

/**
 * Java API to compile and execute Scala Lambda function from the source.
 * Currently, supports execution of Lambda functions with upto 10 parameters/args and a return value
 */
public interface JScalaLambdaRunner {

    /**
     * Use this method to compile and execute at once.This method compiles the function every time so for repeat function try using @compileAndLoad & @executeFromLoader
     *
     * @param scalaSource   - scala lambda source code to be executed
     * @param returnType    - return type from the function
     * @param functionParam - Parameter(s) expected by the function to be executed. The number of args should match the Lambda args
     * @param <T>           - Return type expected from the function
     * @return - result from the function casted to returnType class
     */
    <T> T executeLambda(String scalaSource, Class<T> returnType, Object... functionParam);

    /**
     * Compiles the given anonymous scala function from the file path and caches it. Use this method to load once and @executeFromLoader
     *
     * @param scalaSource      - scala lambda source code to be compiled and loaded
     * @param uniqueIdentifier - an identifier which is uniquely used to load this and reference while execution
     */
    void compileAndLoad(String scalaSource, String uniqueIdentifier);

    /**
     * @param uniqueIdentifier - The unique identifier used while compiling & loading the source
     * @param returnType       - return type from the function
     * @param functionParam    - Parameter(s) expected by the function to be executed. The number of args should match the Lambda args
     * @param <T>              - Return type expected from the function
     * @return - result from the function casted to returnType class
     */
    <T> T executeFromLoader(String uniqueIdentifier, Class<T> returnType, Object... functionParam);

    /**
     * Unloads Lambda function associated with the uniqueIdentifier from memory
     *
     * @param uniqueIdentifier - used during @compileAndLoad
     * @return - true if matching function was unloaded
     */
    Boolean unload(String uniqueIdentifier);

    /**
     * Unloads all Lambda functions from memory
     * @return - true if successful
     */
    Boolean unloadAll();


}
