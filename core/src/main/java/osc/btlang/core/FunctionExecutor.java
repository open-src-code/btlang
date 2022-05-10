package osc.btlang.core;

import java.util.List;
import java.util.Map;

/**
 * Interface used by all functions.
 *
 */
public interface FunctionExecutor {

	/**
	 * Execute's the function associated and returns matching result
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> executeFunction(Map<String, Object> param);
}
