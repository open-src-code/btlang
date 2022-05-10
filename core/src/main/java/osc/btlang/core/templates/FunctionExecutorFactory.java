package osc.btlang.core.templates;

import osc.btlang.core.FunctionExecutor;
import osc.btlang.core.parser.FunctionLoader;

/**
 * Factory class which returns implementation of specific functions
 *
 */
public class FunctionExecutorFactory {

	private static FunctionLoader functionLoader = new FunctionLoader();
	/**
	 * Return a instance of executor capable of processing the passed function
	 * @param function
	 * @return
	 */
	public static FunctionExecutor getFunctionExecutor(String function) {
		return functionLoader.loadFunction(function);
	}
}
