package osc.btlang.core.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import osc.btlang.core.exception.FunctionExecutionException;
import osc.btlang.core.parser.FunctionMeta;
import osc.btlang.core.util.RegexHelper;

/**
 * Function executor for simple if then logic
 * Template : for each item in {$arraryOrListKey} if item value is equal to {#param2} and/or {$param3} is greater than {#param4} then {#key1} is {#val1} and {#key2} is {#val2} and ...
 * Supported operator 'is greater than' and 'is less than' and 'is equal to' and 'is not equal to'
 * variable starts with $ and constants start with # multiple functions are separated by #,#
 * Multiple conditions can be separated by either 'and' or 'or'. Combination is not supported
 *
 */
class ForEachIfThenFunctionExecutor extends SimpleIfThenFunctionExecutor{

	private static final String ARRAY_ITEM_VALUE = "{$btlang.item.value}";
	private static final String ARRAY_ITEM_VALUE_KEY = "btlang.item.value";

	public ForEachIfThenFunctionExecutor(String spaceNormalizedFunction, String hash) {
		super(spaceNormalizedFunction,hash);
		for(FunctionMeta eachFunctionMeta: metaList) {
			/*
			 * 
			 * replace meta data
			    for each item in {$carBrandArray} if item value is equal to {#Tesla} then {#PriceDiscount} is {#20} and {#VehicleType} is {#Electric}
				with
				if {$btlang.item.value} is equal to {#Tesla} then {#PriceDiscount} is {#20} and {#VehicleType} is {#Electric}
				where blpe.item.value will be for each value
			 */
			eachFunctionMeta.setForEachArrayName(eachFunctionMeta.getFunctionArgs().get(0));
			String ifThenFunction = RegexHelper.getAfterIfFromForEach(eachFunctionMeta.getFunction()).get(0);
			ifThenFunction = ifThenFunction.replace("item value", ARRAY_ITEM_VALUE); // this will get replaced by actual value during runtime
			FunctionMeta ifFunctionMeta = parseAndValidate(ifThenFunction, "temp").get(0);
			eachFunctionMeta.getConditionList().clear();
			eachFunctionMeta.getConditionList().addAll(ifFunctionMeta.getConditionList());
			eachFunctionMeta.getFunctionArgs().clear();
			eachFunctionMeta.getFunctionArgs().addAll(ifFunctionMeta.getFunctionArgs());
		}
	}

	public List<Map<String, Object>> executeFunction(Map<String, Object> param) {
		if (param == null) {
			throw new FunctionExecutionException("param is null");
		}
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String,Object>>();
		for(FunctionMeta eachFunctionMeta: metaList) {
			List<Map<String, Object>> result = executeFunction(eachFunctionMeta, param);
			if (!result.isEmpty()) {
				resultMapList.addAll(result);
			}
		}
		return resultMapList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeFunction(FunctionMeta eachFunctionMeta, Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		// first parm is the item key
		Object item = getValue(eachFunctionMeta.getForEachArrayName(),param);
		if (null == item) {
			throw new FunctionExecutionException("No item present in the param map for the key : "+getValue(eachFunctionMeta.getForEachArrayName(),param));
		}
		
		List<Object> itemList = new ArrayList<Object>();
		if (item.getClass().isArray()) {
			Object[] array = (Object[]) item;
			for (int i = 0; i < array.length; i++) {
				itemList.add(array[i]);
			}
		} else if (item instanceof List) {
			itemList = (List<Object>) item;
		}
		Map<String, Object> updatedParam = new HashMap<String, Object>(); // we don't want to modify the original param map
		updatedParam.putAll(param);
		for(int i=0; i<itemList.size();i++) {
			updatedParam.put(ARRAY_ITEM_VALUE_KEY, itemList.get(i));
			result.addAll(super.executeFunction(updatedParam));
		}
		return result;
	}

}
