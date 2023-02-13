package osc.btlang.core.templates;

import osc.btlang.core.FunctionExecutor;
import osc.btlang.core.exception.FunctionExecutionException;
import osc.btlang.core.parser.FunctionMeta;
import osc.btlang.core.util.Constants;
import osc.btlang.core.util.RegexHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Function executor base code
 * Supported operator 'is greater than' and 'is less than' and 'is equal to' and 'is not equal to'
 * variable starts with $ and constants start with # multiple functions are separated by #,#
 * Multiple conditions can be separated by either 'and' or 'or'. Combination is not supported
 *
 */
abstract class AbstractFunctionExecutor implements FunctionExecutor,ParseFunction{

	protected final String function;
	protected final List<FunctionMeta> metaList;
	
	public AbstractFunctionExecutor(String spaceNormalizedFunction, String hash) {
		super();
		this.function = spaceNormalizedFunction;
		this.metaList = parseAndValidate(spaceNormalizedFunction, hash);
	}
	
	public List<FunctionMeta> parseAndValidate(String spaceNormalizedFunction, String hash) {
		List<String> functions = Arrays.asList(spaceNormalizedFunction.split(Constants.FUNCTION_END));
		List<FunctionMeta> functionMetaList = new ArrayList<FunctionMeta>();
		for(String eachFunction : functions) {
			List<String> functionArgs = parseFunction(eachFunction);
			String ifThen = getAallIfThenConditions(eachFunction);
			String[] conditions = null;
			boolean isOR = false;
			if (ifThen.contains(Constants.OR)) { // we support either all 'or' or all 'and'. Combination will be supported in future
				isOR = true;
				conditions = ifThen.split(Constants.OR); // each condition is separated by a or
			}
			else {
				conditions = ifThen.split(Constants.AND); // each condition is separated by a and
			}
			functionMetaList.add(new FunctionMeta(hash, eachFunction, convertToList(conditions), isOR, functionArgs));
		}
		return functionMetaList;
	}

	// doing this way to support list.clear
	private List<String> convertToList(String[] conditions) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < conditions.length; i++) {
			list.add(conditions[i]);
		}
		return list;
	}
	
	protected abstract List<String> parseFunction(String eachFunction);

	public List<Map<String, Object>> executeFunction(Map<String, Object> param) {
		if (param == null) {
			throw new FunctionExecutionException("param is null");
		}
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String,Object>>();
		for(FunctionMeta eachFunctionMeta: metaList) {
			Map<String, Object> result = runFunction(eachFunctionMeta, param);
			if (!result.isEmpty()) {
				resultMapList.add(result);
			}
		}
		return resultMapList;
	}

	protected abstract Map<String, Object> runFunction(FunctionMeta functionMeta,Map<String, Object> param) ;

	protected boolean evaluateConditions(FunctionMeta functionMeta, Map<String, Object> param) {
		boolean isOR = functionMeta.isOr();
		boolean result = false;
		int index1 = 0;
		int index2 = 1; 
		for (String eachCondition : functionMeta.getConditionList()) {
			String operator = RegexHelper.getConditionalOperator(eachCondition).get(0);
			result = evaluateCondition(functionMeta.getFunctionArgs(), param, result, index1, index2, operator);
			if (isOR && result) { // this may have many 'or' conditions so on first true we stop more comparison and return
				return true;
			}
			if (!result && !isOR) { // this may have many 'and' conditions so on first false we stop more comparison and return
				return false;
			}
			// for each condition get the matching params and move to next set. Eg : if {$v1} is equal to {#v2} and {$v3} is equal to {#v4} and... .In this use case v1,v2 are first set v3,v4 are next 
			index1=index1+2;
			index2=index2+2; // next set
		}
		return result;
	}

	/**
	 * Gets all conditions between a if * then
	 * @param function
	 * @return
	 */
	protected String getAallIfThenConditions(String function) {
		return RegexHelper.getBetweenIfThen(function).get(0);
	}

	protected boolean evaluateCondition(List<String> matches, Map<String, Object> param, boolean result, int index1,
			int index2, String operator) {
		Object value1 = getValue(matches.get(index1),param);
		Object value2 = getValue(matches.get(index2),param);
		switch (operator) {
		case Constants.IS_EQUAL_TO:
			result = value1.equals(value2);
			break;
		case Constants.IS_NOT_EQUAL_TO:
			result = !value1.equals(value2);
			break;
		case Constants.IS_GREATER_THAN:
			result = Double.valueOf(value1.toString()) > Double.valueOf(value2.toString());
			break;
		case Constants.IS_LESS_THAN:
			result = Double.valueOf(value1.toString()) < Double.valueOf(value2.toString());
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * Returns the value from the param map or evaluates functions and returns the value if it is a sub function
	 * @param val
	 * @param paramMap
	 * @return
	 */
	protected Object getValue(String val, Map<String, Object> paramMap) {
		Object ret = val;
		if (val.startsWith(Constants.CONSTANT)) {
			ret = val.substring(1);
		}
		if (val.startsWith(Constants.VARIABLE)) {
			ret = paramMap.get(val.substring(1));
		}
		if (ret == null) {
			ret = Constants.NULL;
		}
		if (ret instanceof Boolean) {
			ret = Boolean.valueOf((boolean) ret).toString();
		}
		return ret;
	}
	
}
