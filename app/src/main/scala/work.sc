import java.util

val a =  Array("a", "bb")

a.map(_.length)

a.find( _ == "ad")

type Environment = String => Int



abstract class Tree
case class Sum(l: Tree, r: Tree) extends Tree
case class Var(n: String) extends Tree
case class Const(v: Int) extends Tree

def eval(t: Tree, env: Environment): Int = t match {
  case Sum(l, r) => eval(l, env) + eval(r, env)
  case Var(n) => env(n)
  case Const(v) => v
}

eval(Sum(Const(3), Var("xd")), {case "x" => 4 case _ => 0})


