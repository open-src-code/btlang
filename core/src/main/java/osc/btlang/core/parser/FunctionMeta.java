package osc.btlang.core.parser;

import java.util.List;

/**
 * Class contains meta data related to a function
 *
 */
public class FunctionMeta {

	private final String checksum;
	private final String function;
	private final List<String> conditionList;
	private final boolean isOr;
	/**
	 * key which contains for each array
	 */
	private String forEachArrayName;
	
	/**
	 * static params betweeon the {} which contains variables $ or constants #  
	 */
	private List<String> functionArgs;
	public FunctionMeta(String checksum, String function, List<String> conditionList, boolean isOr,
			List<String> functionArgs) {
		super();
		this.checksum = checksum;
		this.function = function;
		this.conditionList = conditionList;
		this.isOr = isOr;
		this.functionArgs = functionArgs;
	}
	public List<String> getFunctionArgs() {
		return functionArgs;
	}
	public void setFunctionArgs(List<String> functionArgs) {
		this.functionArgs = functionArgs;
	}
	public String getChecksum() {
		return checksum;
	}
	public String getFunction() {
		return function;
	}
	public List<String> getConditionList() {
		return conditionList;
	}
	public boolean isOr() {
		return isOr;
	}
	public String getForEachArrayName() {
		return forEachArrayName;
	}
	public void setForEachArrayName(String forEachArrayName) {
		this.forEachArrayName = forEachArrayName;
	}
	
}
