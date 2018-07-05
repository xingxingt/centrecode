trait TraitTest {

  def moves(move: String): String
  def step(num: Int): Unit = {
    println(s"move ${num}")
  }
}

class Ttest1 extends TraitTest {
  override def moves(move: String): String = move + "like a cat"
}

class Ttest2 extends TraitTest {
  override def moves(move: String): String = move + "like a mouse"
}

object RunTest {
  def main(args: Array[String]): Unit = {
    var ttest1 = new Ttest1
    var ttest2 = new Ttest2
    println(ttest1.step(3))
  }

}
