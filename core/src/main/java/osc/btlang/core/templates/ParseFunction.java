package osc.btlang.core.templates;

import java.util.List;

import osc.btlang.core.parser.FunctionMeta;

/**
 * Interface every function need to implement.
 *
 */
public interface ParseFunction {

	/**
	 * Parse and validate the function
	 * @param spaceNormalizedFunction
	 * @param hash
	 * @return
	 */
	public List<FunctionMeta> parseAndValidate(String spaceNormalizedFunction, String hash);
}
