import java.util

(str: String) => {
  var result = str == "Hi"
  if (result)
    1
  else
    throw new RuntimeException("Not matching")
}