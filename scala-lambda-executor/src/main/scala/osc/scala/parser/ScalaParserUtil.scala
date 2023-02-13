package osc.scala.parser


import scala.io.Source
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox


object ScalaParserUtil {

  def compileFromFile(sourcePath: String) : FunctionHolder = {
    val fileContents = Source.fromFile(sourcePath)
    val src = fileContents.getLines.mkString("\n")
    val toolbox = currentMirror.mkToolBox()
    val tree = toolbox.parse(src)
    fileContents.close()
    val compileCode = toolbox.eval(tree)
      val scalaFunctionHolder: FunctionHolder = new FunctionHolder(compileCode,"someHash")
    return scalaFunctionHolder;
  }
  def compile(source : String, hash: String) : FunctionHolder = {
    val toolbox = currentMirror.mkToolBox()
    val tree = toolbox.parse(source)
    val compileCode = toolbox.eval(tree)
    val scalaFunctionHolder: FunctionHolder = new FunctionHolder(compileCode,hash)
    return scalaFunctionHolder;
  }

  // No param Lambda function
  def executeFunction(func: ()=>(Any), returnType: Any ) : Any = {
    func().asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any)=>(Any), param:Any, returnType: Any ) : Any = {
    func(param).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any)=>(Any), param1:Any,param2:Any, returnType: Any ) : Any = {
    func(param1,param2).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any, returnType: Any ) : Any = {
    func(param1,param2,param3).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any, returnType: Any ) : Any = {
    func(param1,param2,param3,param4).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any,param5:Any, returnType: Any ) : Any = {
    func(param1,param2,param3,param4,param5).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any,param5:Any,param6:Any, returnType: Any ) : Any = {
    func(param1,param2,param3,param4,param5,param6).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any,param5:Any,param6:Any,param7:Any, returnType: Any ) : Any = {
    func(param1,param2,param3,param4,param5,param6,param7).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any,Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any,param5:Any,param6:Any,param7:Any,param8:Any,returnType: Any ) : Any = {
    func(param1,param2,param3,param4,param5,param6,param7,param8).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any,Any,Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any,param5:Any,param6:Any,param7:Any,param8:Any,param9:Any,returnType: Any ) : Any = {
    func(param1,param2,param3,param4,param5,param6,param7,param8,param9).asInstanceOf[returnType.type]
  }
  def executeFunction(func: (Any,Any,Any,Any,Any,Any,Any,Any,Any,Any)=>(Any), param1:Any,param2:Any,param3:Any,param4:Any,param5:Any,param6:Any,param7:Any,param8:Any,param9:Any,param10:Any,returnType: Any ) : Any = {
    func(param1,param2,param3,param4,param5,param6,param7,param8,param9,param10).asInstanceOf[returnType.type]
  }
}
