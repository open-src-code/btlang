package osc.btlang.core.util;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RegexHelperTest extends TestCase {

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RegexHelperTest( String testName )
    {
        super( testName );
    }
 
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RegexHelperTest.class );
    }
    
    public void testgetBetweenCurlyBrackets() {
    	List<String> matches = RegexHelper.getBetweenCurlyBrackets("if {$pollution} is equal to {#HIGH} then {#VEHICLE_TYPE} is {#Electric} and {#VEHICLE_BRAND} is {#Tesla}");
    	assertNotNull(matches);
    	assertEquals(6, matches.size());
    }
    
    public void testgetBetweenCurlyBracketsValues() {
    	List<String> matches = RegexHelper.getBetweenCurlyBrackets("if {$pollution} is equal to {#HIGH} then {#VEHICLE_TYPE} is {#Electric} and {#VEHICLE_BRAND} is {#Tesla}");
    	assertNotNull(matches);
    	assertEquals(6, matches.size());
    	assertEquals("$pollution", matches.get(0));
    	assertEquals("#HIGH", matches.get(1));
    	assertEquals("#VEHICLE_TYPE", matches.get(2));
    	assertEquals("#Electric", matches.get(3));
    	assertEquals("#VEHICLE_BRAND", matches.get(4));
    	assertEquals("#Tesla", matches.get(5));
    }
    
    public void testgetBetweenCurlyBracketsNoMatch() {
    	List<String> matches = RegexHelper.getBetweenCurlyBrackets("No match in { this");
    	assertNotNull(matches);
    	assertEquals(0, matches.size());
    }
    
    public void testgetBetweenIfThen() {
    	List<String> matches = RegexHelper.getBetweenIfThen("if {$pollution} is equal to {#HIGH} then {#VEHICLE_TYPE} is {#Electric} and {#VEHICLE_BRAND} is {#Tesla}");
    	assertNotNull(matches);
    	assertEquals(1, matches.size());
    	assertEquals("{$pollution} is equal to {#HIGH}", matches.get(0));
    }
    
    public void testgetAfterIfFromForEach() {
    	List<String> matches = RegexHelper.getAfterIfFromForEach("for each item in {$balArray} if item value is equal to {#OPDB} then {#VEHICLE_TYPE} is {#Electric} and {#VEHICLE_BRAND} is {#Tesla}");
    	assertNotNull(matches);
    	assertEquals(1, matches.size());
    	assertEquals("if item value is equal to {#OPDB} then {#VEHICLE_TYPE} is {#Electric} and {#VEHICLE_BRAND} is {#Tesla}", matches.get(0));
    }
    
    public void testEqualToOperator() {
    	List<String> matches = RegexHelper.getConditionalOperator("{$pollution} is equal to {#HIGH}");
    	assertNotNull(matches);
    	assertEquals(1, matches.size());
    	assertEquals("is equal to", matches.get(0));
    }
}
