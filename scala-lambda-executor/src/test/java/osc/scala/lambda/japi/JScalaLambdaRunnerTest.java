package osc.scala.lambda.japi;

import org.junit.Assert;
import org.junit.Test;
import osc.scala.core.LambdaFactory;
import osc.scala.exception.CompilationException;
import osc.scala.exception.LambdaExecutionException;

public class JScalaLambdaRunnerTest {

    public JScalaLambdaRunner lambdaRunner = LambdaFactory.getInstanceOf();


    @Test
    public void executeLambda() {
        String lambdaFunction = "(n: Int) => 2 * n";
        Integer res = lambdaRunner.executeLambda(lambdaFunction,Integer.class,22);
        Assert.assertEquals(Integer.valueOf(44), res);
    }
    @Test
    public void executeLambdaWith2Args() {
        String lambdaFunction = "(a: Int,b: Int) => a + b";
        Integer res = lambdaRunner.executeLambda(lambdaFunction,Integer.class,10,10);
        Assert.assertEquals(Integer.valueOf(20), res);
    }
    @Test
    public void executeLambdaWith3Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int) => a + b + c";
        Integer res = lambdaRunner.executeLambda(lambdaFunction,Integer.class,10,10,10);
        Assert.assertEquals(Integer.valueOf(30), res);
    }
    @Test
    public void executeLambdaWith4Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int) => {" +
                "a + b + c + d" +
                "}" ;
        Integer res = lambdaRunner.executeLambda(lambdaFunction,Integer.class,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(40), res);
    }
    @Test
    public void executeLambdaWith5Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int) => {" +
                "a + b + c + d + e" +
                "}" ;
        Integer res = lambdaRunner.executeLambda(lambdaFunction,Integer.class,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(50), res);
    }

    @Test
    public void executeLambdaWith6Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int) => {" +
                "a + b + c + d + e + f" +
                "}" ;
        String uniqueIdentifier = "6ArgsFunction";
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        Integer res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(60), res);
    }
    @Test
    public void executeLambdaWith7Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int) => {" +
                "a + b + c + d + e + f + g" +
                "}" ;
        String uniqueIdentifier = "";
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        Integer res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(70), res);
    }

    @Test
    public void executeLambdaWith8Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int, h: Int) => {" +
                "a + b + c + d + e + f + g + h" +
                "}" ;
        String uniqueIdentifier = "8ArgsFunction";
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        Integer res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(80), res);
    }
    @Test
    public void executeLambdaWith9Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int, h: Int, i: Int) => {" +
                "a + b + c + d + e + f + g + h + i" +
                "}" ;
        String uniqueIdentifier = "9ArgsFunction";
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        Integer res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(90), res);
    }
    @Test
    public void executeLambdaWith10Args() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int, h: Int, i: Int, j: Int) => {" +
                "a + b + c + d + e + f + g + h + i + j" +
                "}" ;
        Integer res = lambdaRunner.executeLambda(lambdaFunction,Integer.class,10,10,10,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(100), res);
    }

    @Test
    public void executeLambdaWithInvalidArgs() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int, h: Int, i: Int, j: Int) => {" +
                "a + b + c + d + e + f + g + h + i + j" +
                "}" ;
        Assert.assertThrows(LambdaExecutionException.class,() ->{
            lambdaRunner.executeLambda(lambdaFunction,Integer.class,10,10,10,10,10,10,10,10,10);
        });
    }

    @Test
    public void unloadAllTest() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int) => {" +
                "a + b + c + d + e + f + g" +
                "}" ;
        String uniqueIdentifier = "Key";
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        Integer res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(70), res);
        lambdaRunner.unloadAll();
        Assert.assertThrows(CompilationException.class,() ->{
            lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10);
        });

    }
    @Test
    public void unloadTest() {
        String lambdaFunction = "(a: Int,b: Int, c: Int, d: Int, e: Int, f : Int, g: Int) => {" +
                "a + b + c + d + e + f + g" +
                "}" ;
        String uniqueIdentifier = "Key";
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        Integer res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(70), res);
        Assert.assertTrue(lambdaRunner.unload(uniqueIdentifier));
        // should return false after unloading
        Assert.assertTrue(!lambdaRunner.unload(uniqueIdentifier));
        //load again
        lambdaRunner.compileAndLoad(lambdaFunction, uniqueIdentifier);
        res = lambdaRunner.executeFromLoader(uniqueIdentifier,Integer.class,10,10,10,10,10,10,10);
        Assert.assertEquals(Integer.valueOf(70), res);
    }

}