import java.util

(str: String, num: Int, list: util.ArrayList[Any]) => {
  var result = str == "Hi" && num == 1 && list.size == 1
  if (result)
    1
  else
    0
}