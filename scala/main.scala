import Allocator._
import scala.io.Source

val filename = "testdata.txt"
val lines=io.Source.fromFile(filename).getLines.toArray

var  holes_size:Array[Int]=Array()
var  balls_size:Array[Int]=Array()

if(lines.isEmpty){
  holes_size=Array(1,10,20,11,20)
  balls_size=Array(5,2,5,9)
}else{
   holes_size=lines(0).split(",").map(size=>size.toInt)
   balls_size=lines(1).split(",").map(size=>size.toInt)
}

try{
    var holes=holes_size.zipWithIndex.map{case (size,index)=>new Hole(index+1,size)}
    var balls=balls_size.zipWithIndex.map{case (size,index)=>new Ball(index+1,size)}

    val array=allocator.allocate(holes,balls)
    val balls_not_be_assigned=array(0).asInstanceOf[Array[Any]]
    val holes_not_be_assigned=array(1).asInstanceOf[Array[Any]]
    val assigned_pair=array(3).asInstanceOf[Map[Any,Any]]
    println("[Input]Balls Are:")
    pp(balls)
    println("---------------------")
    println("[Input]Holes Are:")
    pp(holes)
    println("---------------------")
    println("[Output]Not-assigned-Balls:")
    pp(balls_not_be_assigned)
    println("---------------------")
    println("[Output]Not-assigned-Holes:")
    pp(holes_not_be_assigned)
    println("---------------------")
    println("[Output]Assigned pair:")
    println(assigned_pair.mkString("\n"))
}catch{
    case ex: Exception => println(ex)
}

def pp(c:Object){
  val t=c.asInstanceOf[Array[Any]]
  if(t.isEmpty){
    println("NONE")
  }else{
    println(t.deep.mkString("\n"))
  }
}
println("Done")
