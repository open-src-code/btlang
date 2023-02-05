package osc.scala.lambda.japi;

import org.junit.Assert;
import org.junit.Test;
import osc.scala.core.LambdaFactory;
import osc.scala.exception.CompilationException;
import osc.scala.exception.LambdaExecutionException;
import scala.collection.mutable.ListBuffer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JScalaLambdaRunnerFromFileTest {

    public JScalaLambdaRunner lambdaRunner = LambdaFactory.getInstanceOf();


    @Test
    public void executeLambdaListBuffer() {
        String lambdaFunction = getSource("SimpleLambdaWithListBuffer.scala");
        ListBuffer list = lambdaRunner.executeLambda(lambdaFunction, ListBuffer.class,"Hi");
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("Hi",list.head());
    }
    @Test
    public void executeLambdaWordCountWithNoArgs() {
        String lambdaFunction = getSource("LambdaWordCountWithMap.scala");
        scala.collection.immutable.HashMap map = lambdaRunner.executeLambda(lambdaFunction, scala.collection.immutable.HashMap.class);
        Assert.assertTrue(map.size() == 55);
        Integer count = (Integer) map.get("function").get();
        Assert.assertEquals(Integer.valueOf(10),count);
    }
    @Test
    public void executeLambdaBoolean() {
        String lambdaFunction = getSource("SimpleLambdaWithBoolean.scala");
        Boolean ret = lambdaRunner.executeLambda(lambdaFunction, Boolean.class,"Hi");
        Assert.assertEquals(true, ret);
        ret = lambdaRunner.executeLambda(lambdaFunction, Boolean.class,"Hello");
        Assert.assertEquals(false, ret);
    }

    @Test
    public void executeLambdaWithVaryingArgsAndReturnInt() {
        String lambdaFunction = getSource("LambdaWithDiffArgsInt.scala");
        List<String> list = new ArrayList<>();
        list.add("Hi");
        Integer ret = lambdaRunner.executeLambda(lambdaFunction, Integer.class,"Hi",1,list);
        Assert.assertEquals(Integer.valueOf(1), ret);
        list.add("Hello");
        ret = lambdaRunner.executeLambda(lambdaFunction, Integer.class,"Hi",1,list);
        Assert.assertEquals(Integer.valueOf(0), ret);
    }
    @Test
    public void executeLambdaWithVaryingArgsAndReturnString() {
        String lambdaFunction = getSource("LambdaWithDiffArgsString.scala");
        List<String> list = new ArrayList<>();
        list.add("Hi");
        String ret = lambdaRunner.executeLambda(lambdaFunction, String.class,"Hi",1,list);
        Assert.assertEquals("Success", ret);
        list.add("Hello");
        ret = lambdaRunner.executeLambda(lambdaFunction, String.class,"Hi",1,list);
        Assert.assertEquals("Error", ret);
    }

    @Test
    public void executeLambdaWithVaryingArgsAndReturnStringCompileAndExecute() {
        String lambdaFunction = getSource("LambdaWithDiffArgsString.scala");
        List<String> list = new ArrayList<>();
        list.add("Hi");
        String key = "func";
        lambdaRunner.compileAndLoad(lambdaFunction,key);
        String ret = lambdaRunner.executeFromLoader(key, String.class,"Hi",1,list);
        Assert.assertEquals("Success", ret);
        list.add("Hello");
        ret = lambdaRunner.executeFromLoader(key, String.class,"Hi",1,list);
        Assert.assertEquals("Error", ret);

        // compile another func
        String lambdaFunction2 = getSource("LambdaWithDiffArgsInt.scala");
        String key2 = "func2";
        lambdaRunner.compileAndLoad(lambdaFunction2,key2);
        Integer ret2 = lambdaRunner.executeFromLoader(key2, Integer.class,"Hi",1,list);
        Assert.assertEquals(Integer.valueOf(0), ret2);
        List<String> list2 = new ArrayList<>();
        list2.add("Hi");
        ret2 = lambdaRunner.executeFromLoader(key2, Integer.class,"Hi",1,list2);
        Assert.assertEquals(Integer.valueOf(1), ret2);
    }

    @Test
    public void executeInvalidLambda() {
        String lambdaFunction = getSource("InvalidLambda.scala");
        Assert.assertThrows(CompilationException.class,() ->{
            lambdaRunner.compileAndLoad(lambdaFunction,"key");
        });
        Assert.assertThrows(CompilationException.class,() ->{
            lambdaRunner.executeLambda(lambdaFunction,Object.class);
        });

    }

    @Test
    public void executeLambdaWithRuntimeException() {
        String lambdaFunction = getSource("LambdaThrowsError.scala");
        lambdaRunner.compileAndLoad(lambdaFunction,"key");
        Assert.assertThrows(LambdaExecutionException.class,() ->{
            lambdaRunner.executeFromLoader("key",Object.class,"Hello");
        });
        Assert.assertThrows(LambdaExecutionException.class,() ->{
            lambdaRunner.executeLambda(lambdaFunction,Object.class,"Hello");
        });

    }
    private String getSource(String fileName) {
        String filePath = "src/test/resources";
        Path path = Paths.get(filePath+"/"+fileName);
        String source = null;
        Stream<String> lines = null;
        try {
            lines = Files.lines(path);
            source = lines.collect(Collectors.joining("\n"));
            lines.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return source;
    }


}