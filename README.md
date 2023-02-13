# btlang

## Scala Lambda function execution through java API
```
<dependency>
			<groupId>io.github.open-src-code</groupId>
			<artifactId>scala-lamba-executor</artifactId>
			<version>1.0</version>
</dependency>
```
Purpose of this library is to execute scala lambda functions from java.

Sample Lambda source :
``` scala
() => {
  scala.io.Source.fromFile("src/test/resources/para.txt")
    .getLines
    .flatMap(_.split("\\W+"))
    .foldLeft(Map.empty[String, Int]){
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    }
}
```

Sample para text:
```
This is an example of word count with some repeating words.
The number of words repeating should be grouped using scala word count lambda function.
Introduction to Scala Anonymous Function
A function that does not contain any name or a function without a name is called an anonymous function in scala. This anonymous function is also called a function literal.
Inside the anonymous function, we can write our logic and define our function. We can create a very lightweight function also if we can create an inline function.
```

API : https://github.com/open-src-code/btlang/blob/main/scala-lambda-executor/src/main/java/osc/scala/lambda/japi/JScalaLambdaRunner.java
``` java
String lambdaFunction = getSource("LambdaWordCountWithMap.scala"); // Lambda source
scala.collection.immutable.HashMap map = lambdaRunner.executeLambda(lambdaFunction, scala.collection.immutable.HashMap.class);
Assert.assertTrue(map.size() == 55);
```

Note: The maximum arguments in lambda functions supported by this library currently is 10.
Additional examples -> https://github.com/open-src-code/btlang/blob/main/scala-lambda-executor/src/test/java/osc/scala/lambda/japi/JScalaLambdaRunnerFromFileTest.java


## Rule engine with simple language
``` 
<dependency>
			<groupId>io.github.open-src-code</groupId>
			<artifactId>btlang-core</artifactId>
			<version>1.0</version>
</dependency>
```
Purpose of this module is to take rules as simple english language and process it.
```
Eg: if {$Vehicle_type} is equal to {#Electric} and {$Car_brand} is equal to {#Tesla} then {#Discount_percentage} is {#25} and {#Emission} is {#zero}
```
Now during runtime the api expects a map with key Vehicle_type and Car_brand. Anything stating with '$' represents variable and '#'represents constants.
To execute the above rule pass the map as below

``` java
Map<String, Object> param = new HashMap<String, Object>();
    	param.put("Vehicle_type", "Electric");
    	param.put("Car_brand", "Tesla");
FunctionExecutor re = FunctionExecutorFactory.getFunctionExecutor(function);
List<Map<String, Object>> result = re.executeFunction(param);
Since the value passed macthes the rule you can see ->
Map<String, Object> map = result.get(0);
assertEquals("25", map.get("Discount_percentage"));
assertEquals("zero", map.get("Emission"));
```

@ForEachIfThenFunctionExecutorTest has example for list of rules.



