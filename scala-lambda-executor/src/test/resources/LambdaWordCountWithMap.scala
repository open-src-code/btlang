// returns scala.collection.immutable.HashMap so do not assign to any val
() => {
  scala.io.Source.fromFile("src/test/resources/para.txt")
    .getLines
    .flatMap(_.split("\\W+"))
    .foldLeft(Map.empty[String, Int]){
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    }
}
