package osc.scala.core;

import osc.scala.lambda.japi.JScalaLambdaRunner;

public class LambdaFactory {

    private static final JScalaLambdaRunner jslr = new JScalaLambdaRunnerImpl();

    private LambdaFactory() {
    }

    /**
     * @return - new or cached instance of JScalaLambdaRunner
     */
    public static JScalaLambdaRunner getInstanceOf() {
        return jslr;
    }
}
