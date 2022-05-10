package osc.btlang.core.templates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import osc.btlang.core.exception.FunctionExecutionException;
import osc.btlang.core.parser.FunctionMeta;
import osc.btlang.core.util.RegexHelper;

/**
 * Function executor for simple if then logic
 * Template : if {$param1} is equal to {#param2} and/or {$param3} is greater than {#param4} then {#key1} is {#val1} and {#key2} is {#val2} and ...
 * Supported operator 'is greater than' and 'is less than' and 'is equal to' and 'is not equal to'
 * variable starts with $ and constants start with # multiple functions are separated by #,#
 * Multiple conditions can be separated by either 'and' or 'or'. Combination is not supported
 *
 */
class SimpleIfThenFunctionExecutor extends AbstractFunctionExecutor{

	
	public SimpleIfThenFunctionExecutor(String spaceNormalizedFunction, String hash) {
		super(spaceNormalizedFunction,hash);
	}

	@Override
	protected Map<String, Object> runFunction(FunctionMeta functionMeta,Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		if (evaluateConditions(functionMeta, param)) {
			for (int i = 2; i<functionMeta.getFunctionArgs().size();i++) { // index 0 and 1 contain conditional so use index 2 onwards
				result.put(getValue(functionMeta.getFunctionArgs().get(i),param).toString(), getValue(functionMeta.getFunctionArgs().get(++i),param));
			}
		}
		return result;
	}

	protected List<String> parseFunction(String eachFunction) {
		List<String> matches = RegexHelper.getBetweenCurlyBrackets(eachFunction);
		if (matches.isEmpty() || matches.size() < 4) {
			throw new FunctionExecutionException("function is invalid.Should be of format eg: 'if {$Balance_Code} is equal to {#OPBD} then {#MT_FIELD} is {#61} and {#COMPONENT_NAME} is {#Amount}'");
		}
		return matches;
	}

}
