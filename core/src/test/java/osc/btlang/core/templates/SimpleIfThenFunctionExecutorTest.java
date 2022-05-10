package osc.btlang.core.templates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import osc.btlang.core.FunctionExecutor;
import osc.btlang.core.util.Constants;

public class SimpleIfThenFunctionExecutorTest extends TestCase {

	private static final String FUNCTION2 = "if {$Vehicle_type} is equal to {#true} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
	private static final String FUNCTION1 = "if {$Vehicle_type} is equal to {#Electric} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";


	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimpleIfThenFunctionExecutorTest( String testName )
    {
        super( testName );
    }
 
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimpleIfThenFunctionExecutorTest.class );
    }
    
    public void testFunctionEmpty() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(FUNCTION1);
    	assertNotNull(re);
    	List<Map<String, Object>> result = re.executeFunction(new HashMap<String, Object>());
    	assertTrue(result.isEmpty());
    }
    
    public void testFunctionWithConstants() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(FUNCTION1);
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithConstantsBoolean() {
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(FUNCTION2);
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", true);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConditions() {
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$Car_brand} is equal to {#Tesla} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	assertNotNull(re);
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("Car_brand", "Tesla");
    	
		List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleOrConditions() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("Car_brand", "Tesla");
    	param.put("zero", "188938338.673838");
    	String function = "if {$Car_brand} is equal to {#Volvo} or {$Car_brand} is equal to {#Tesla} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    public void testFunctionWithMultipleConditionsNumber() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("price", "25000");
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$price} is greater than {#24999} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);

    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConditionsNumberLess() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("price", "188938338.673838");
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$price} is less than {#188938338.773837} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConditionsNumberLessInteger() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("price", 188);
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$price} is less than {#1189} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);

    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConditionsAll() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("price", 188);
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$Vehicle_type} is not equal to {#OPB1D} and {$price} is less than {#1189} and {$price} is greater than {#18} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testMultipleFunctionsWithMultipleConditionsAll() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("Car_brand", "Tesla");
    	param.put("price", 188);
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$Vehicle_type} is not equal to {#Ford} and {$price} is less than {#1189} and {$price} is greater than {#18} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}"
		+ Constants.FUNCTION_END
		+ "if {$Car_brand} is equal to {#Tesla} and {$Vehicle_type} is not equal to {#Ford} and {$price} is less than {#118229} and {$price} is greater than {#1} then {#Discount_percentage} is {#62} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    	map = result.get(1);
    	assertEquals("62", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConditionsNumberEquals() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("price", "188938338.773838");
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$price} is equal to {#188938338.773838} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConditionsFalse() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("Car_brand", "Tesla");
    	String function = "if {$Vehicle_type} is equal to {#Electric} and {$Car_brand} is equal to {#Ford} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should be empty",result.isEmpty());
    }
    
    public void testFunctionWithNull() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	//param.put("Vehicle_type", "Electric");
    	String function = "if {$Vehicle_type} is equal to {#null} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testFunctionWithMultipleConstants() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	String function = "if {$Vehicle_type} is equal to {#Electric} then {#Discount_percentage} is {#25} and {#Emission} is {#zero} and {#FLOW} is {#Car}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    	assertEquals("Car", map.get("FLOW"));
    }
    
    public void testFunctionVariables() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "CLBD");
    	param.put("Vehicle_type_val", "CLBD");
    	param.put("Emission", "Emission");
    	param.put("DIS", 15);
    	String function = "if {$Vehicle_type} is equal to {$Vehicle_type_val} then {#Discount_percentage} is {$DIS} and {$Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals(15, map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testMultipleFunctions() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	String function = "if {$Vehicle_type} is equal to {#Electric} then {#Discount_percentage} is {#25} and {#Emission} is {#zero} " +Constants.FUNCTION_END + " if {$Vehicle_type} is equal to {#Hybrid} then {#Discount_percentage} is {#62} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
    
    public void testMultipleFunctionsAndResults() {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("Brand", "Ford");
    	String function = FUNCTION1
    			+ Constants.FUNCTION_END
    			+ " if {$Brand} is equal to {#Ford} then {#Discount_percentage} is {#62} and {#Emission} is {#zero}";
    	FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
    	List<Map<String, Object>> result = re.executeFunction(param);
    	assertTrue("Result should not be empty",!result.isEmpty());
    	Map<String, Object> map = result.get(0);
    	assertEquals("25", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    	
    	map = result.get(1);
    	assertEquals("62", map.get("Discount_percentage"));
    	assertEquals("zero", map.get("Emission"));
    }
   
    
    public void testInvalidFunction() {
    	boolean error = false;
    	try {
    		FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor("if {$Vehicle_type} {#Emission} is {#zero}");
    		re.executeFunction(new HashMap<String, Object>());
		} catch (Exception e) {
			error = true;
		}
    	
    	assertTrue("Should throw exception",error);
    }
}
