package osc.btlang.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for regex functions
 *
 */
public class RegexHelper {

	private static Pattern CURLY_PATTERN = Pattern.compile("\\{(.*?)\\}");
	private static Pattern IF_THEN_PATTERN = Pattern.compile("if(.*?)then");
	private static Pattern IF_FROM_FOR_EACH_PATTERN = Pattern.compile("if(.*)");

	private static Pattern CONDITION_OPERATOR_PATTERN = Pattern.compile("\\}(.*?)\\{");
	
	
	public static List<String> getBetweenCurlyBrackets(String string) {
		Matcher m = CURLY_PATTERN.matcher(string);
		return findMatchGroup1(m);
	}
	
	public static List<String> getBetweenIfThen(String string) {
		Matcher m = IF_THEN_PATTERN.matcher(string);
		return findMatchGroup1(m);
	}
	
	public static List<String> getAfterIfFromForEach(String string) {
		Matcher m = IF_FROM_FOR_EACH_PATTERN.matcher(string);
		return findMatchGroup0(m);
	}

	private static List<String> findMatchGroup1(Matcher m) {
		List<String> matches = new ArrayList<String>();
        while (m.find()) {
        	matches.add((m.group(1).trim()));
        }
        return matches;
	}
	
	/**
	 * Includes the start/end patterns
	 * @param m
	 * @return
	 */
	private static List<String> findMatchGroup0(Matcher m) {
		List<String> matches = new ArrayList<String>();
        while (m.find()) {
        	matches.add((m.group().trim()));
        }
        return matches;
	}
	
	public static List<String> getConditionalOperator(String string) {
		Matcher m = CONDITION_OPERATOR_PATTERN.matcher(string);
		return findMatchGroup1(m);
	}
}
