package osc.btlang.core.templates;

import org.apache.commons.lang3.StringUtils;

import osc.btlang.core.FunctionExecutor;
import osc.btlang.core.exception.FunctionExecutorNotFoundException;

/**
 * Creates instance of the function executor for parsing and executing the function
 *
 */
public class CreateInstance {

	public static FunctionExecutor getInstance(String spaceNormalizedFunction, String hash) {
		if (StringUtils.startsWith(spaceNormalizedFunction, "for each")) {
			return new ForEachIfThenFunctionExecutor(spaceNormalizedFunction, hash);
		} else if (StringUtils.startsWith(spaceNormalizedFunction, "if")) {
			return new SimpleIfThenFunctionExecutor(spaceNormalizedFunction,hash);
		} else {
			throw new FunctionExecutorNotFoundException("Function executor not found for the passed function -> " + spaceNormalizedFunction);
		}
	}
	
}
