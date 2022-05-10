package osc.btlang.core.templates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import osc.btlang.core.FunctionExecutor;

public class ForEachIfThenFunctionExecutorTest extends TestCase {

	private static final String FUNCTION2 = "for each item in {$carBrandArray} if item value is equal to {#Tesla} or item value is equal to {#Volvo} then {#PriceDiscount} is {#20} and {#VehicleType} is {#Electric}";
	private static final String FUNCTION1 = "for each item in {$carBrandArray} if item value is equal to {#Ford} then {#PriceDiscount} is {#5} and {#VehicleType} is {#Hybrid}";
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ForEachIfThenFunctionExecutorTest( String testName )
    {
        super( testName );
    }
 
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ForEachIfThenFunctionExecutorTest.class );
    }
    
    public void testFunctionWithConstants() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(FUNCTION1);
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	String[] balArray = {"Ford","Tesla","Volvo"};
    	param.put("carBrandArray", balArray);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("5", map.get("PriceDiscount"));
    	assertEquals("Hybrid", map.get("VehicleType"));
    }
    
    public void testFunctionWithConstantsEmpty() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(FUNCTION1);
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	String[] balArray = {"Honda","Kia","Tata"};
    	param.put("carBrandArray", balArray);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should be empty",result.isEmpty());
    }
    
    public void testFunctionWithConstantsMultipleOr() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(FUNCTION2);
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	String[] balArray = {"Ford","Tesla","Volvo"};
    	param.put("carBrandArray", balArray);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("20", map.get("PriceDiscount"));
    	assertEquals("Electric", map.get("VehicleType"));
    }
    public void testFunctionWithConstantsMultipleAnd() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor("for each item in {$carBrandArray} if item value is equal to {#Tesla} and item value is equal to {#Volvo} then {#PriceDiscount} is {#20} and {#VehicleType} is {#Electric}");
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	String[] balArray = {"Ford","Tesla","Volvo"};
    	param.put("carBrandArray", balArray);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should be empty",result.isEmpty());
    }
}
