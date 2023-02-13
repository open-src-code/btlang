package osc.scala.core;


import osc.scala.exception.CompilationException;
import osc.scala.exception.LambdaExecutionException;
import osc.scala.lambda.japi.JScalaLambdaRunner;
import osc.scala.parser.FunctionHolder;
import osc.scala.parser.ScalaParserUtil;
import scala.*;

import java.lang.Boolean;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


class JScalaLambdaRunnerImpl implements JScalaLambdaRunner {
    private MessageDigest digest;
    private HashMap<String, FunctionHolder> hashToCompiledCode = new HashMap<>();
    private HashMap<String, String> uniqueIdentifierToHash = new HashMap<>(); // Hold link between unique identifier and hash

    public JScalaLambdaRunnerImpl() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new CompilationException("Error occurred while creating SHA-256 digest.", e);
        }
    }

    /**
     * Unloads Lambda function associated with the uniqueIdentifier from memory
     *
     * @param uniqueIdentifier - used during @compileAndLoad
     */
    @Override
    public synchronized Boolean unload(String uniqueIdentifier) {
        Boolean result = false;
        String hash = uniqueIdentifierToHash.get(uniqueIdentifier);
        if (null != hash) {
            hashToCompiledCode.remove(hash);
            uniqueIdentifierToHash.remove(uniqueIdentifier);
            result = true;
        }
        return result;
    }

    /**
     * Unloads all Lambda functions from memory
     */
    @Override
    public synchronized Boolean unloadAll() {
        uniqueIdentifierToHash.clear();
        hashToCompiledCode.clear();
        return true;
    }

    /**
     * Compiles the given anonymous scala function from the file path and caches it. Use this method to load once and @executeFromLoader
     *
     * @param scalaSource      - scala lambda source code to be compiled and loaded
     * @param uniqueIdentifier - an identifier which is uniquely used to load this and reference while execution
     */
    @Override
    public synchronized void compileAndLoad(String scalaSource, String uniqueIdentifier) {
        String hash = getHash(scalaSource);
        FunctionHolder scalaFunctionHolder = hashToCompiledCode.get(hash);
        if (scalaFunctionHolder != null) {
            uniqueIdentifierToHash.put(uniqueIdentifier, hash);
            return; // lets return without compiling again
        }
        scalaFunctionHolder = getCompiledFunction(scalaSource, hash);
        uniqueIdentifierToHash.put(uniqueIdentifier, hash);
        hashToCompiledCode.put(hash, scalaFunctionHolder);
    }

    /**
     * @param uniqueIdentifier - The unique identifier used while compiling & loading the source
     * @param returnType       - return type from the function
     * @param functionParam    - Parameter(s) expected by the function to be executed. The number of args should match the Lambda args
     * @param <T>              - Return type expected from the function
     * @return - result from the function casted to returnType class
     */
    @Override
    public <T> T executeFromLoader(String uniqueIdentifier, Class<T> returnType, Object... functionParam) {
        String hash = uniqueIdentifierToHash.get(uniqueIdentifier);
        if (null == hash) {
            throw new CompilationException("Could not find a compiled class associated with " + uniqueIdentifier);
        }
        int length = functionParam.length;
        Object compiledFunction = hashToCompiledCode.get(hash).compiledLambdaFunction();
        return executeFunction(compiledFunction, returnType, functionParam);
    }

    /**
     * Use this method to compile and execute at once.This method compiles the function every time so for repeat function try using @compileAndLoad & @executeFromLoader
     *
     * @param scalaSource   - scala lambda source code to be executed
     * @param returnType    - return type from the function
     * @param functionParam - Parameter(s) expected by the function to be executed. The number of args should match the Lambda args
     * @param <T>           - Return type expected from the function
     * @return - result from the function casted to returnType class
     */
    @Override
    public <T> T executeLambda(String scalaSource, Class<T> returnType, Object... functionParam) {
        FunctionHolder scalaFunctionHolder = getCompiledFunction(scalaSource,"NA");
        int length = functionParam.length;
        return executeFunction(scalaFunctionHolder.compiledLambdaFunction(), returnType, functionParam);
    }

    private FunctionHolder getCompiledFunction(String scalaSource, String hash) {
        try{
            return ScalaParserUtil.compile(scalaSource, hash);
        } catch (Throwable e) {
            throw new CompilationException("Passed lambda function cannot be compiled :: \n " + scalaSource , e);
        }
    }

    /**
     * @param compiledFunction - The compiled function
     * @param returnType       - return type from the function
     * @param functionParam    - Parameter(s) expected by the function to be executed. The number of args should match the Lambda args
     * @param <T>              - Return type expected from the function
     * @return - result from the function casted to returnType class
     */
    private <T> T executeFunction(Object compiledFunction, Class<T> returnType, Object... functionParam) {
        int length = functionParam.length;
        try {
            switch (length) {
                case 0:
                    return (T) ScalaParserUtil.executeFunction((Function0<Object>) compiledFunction, returnType);
                case 1:
                    return (T) ScalaParserUtil.executeFunction((Function1<Object, Object>) compiledFunction, functionParam[0], returnType);
                case 2:
                    return (T) ScalaParserUtil.executeFunction((Function2<Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], returnType);
                case 3:
                    return (T) ScalaParserUtil.executeFunction((Function3<Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], returnType);
                case 4:
                    return (T) ScalaParserUtil.executeFunction((Function4<Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], returnType);
                case 5:
                    return (T) ScalaParserUtil.executeFunction((Function5<Object, Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], functionParam[4], returnType);
                case 6:
                    return (T) ScalaParserUtil.executeFunction((Function6<Object, Object, Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], functionParam[4], functionParam[5], returnType);
                case 7:
                    return (T) ScalaParserUtil.executeFunction((Function7<Object, Object, Object, Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], functionParam[4], functionParam[5], functionParam[6], returnType);
                case 8:
                    return (T) ScalaParserUtil.executeFunction((Function8<Object, Object, Object, Object, Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], functionParam[4], functionParam[5], functionParam[6], functionParam[7], returnType);
                case 9:
                    return (T) ScalaParserUtil.executeFunction((Function9<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], functionParam[4], functionParam[5], functionParam[6], functionParam[7], functionParam[8], returnType);
                case 10:
                    return (T) ScalaParserUtil.executeFunction((Function10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>) compiledFunction,
                            functionParam[0], functionParam[1], functionParam[2], functionParam[3], functionParam[4], functionParam[5], functionParam[6], functionParam[7], functionParam[8], functionParam[9], returnType);
            }
        } catch (Throwable t) {
            throw new LambdaExecutionException("Exception occurred while executing the lambda function.",t);
        }
        throw new LambdaExecutionException("Maximum number of params/arguments supported during execution is 10. Try to use a map for extra arguments");

    }

    /**
     * Generates SHA-265 hash for the given string
     *
     * @param string -
     * @return -
     */
    private String getHash(String string) {
        byte[] encodedhash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
