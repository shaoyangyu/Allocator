package Allocator{
  class Circle(_index:Int,_size:Int){
    var index:Int = _index;
    var size:Int = _size;
    override def toString()={
      s"type:${this.getClass.getSimpleName},Index: $index, Size: $size"
    }
  }
  class Hole(index:Int,size:Int) extends Circle(index,size){
  }
  class Ball(index:Int,size:Int) extends Circle(index,size){
  }
  object allocator{
    def reducer(r:Object,_ball:Object)={
      val array=r.asInstanceOf[Array[Any]]
      val balls_not_be_assigned=array(0).asInstanceOf[Array[Any]]
      val holes_not_be_assigned=array(1).asInstanceOf[Array[Any]]
      val holes_rest=array(2).asInstanceOf[Array[Any]]
      val assigned_pair=array(3).asInstanceOf[Map[Any,Any]]
      val ball=_ball.asInstanceOf[Ball]
      val first_hole=holes_rest.zipWithIndex.find{case (hole,index)=>hole.asInstanceOf[Hole].size>=ball.size}
      if(!first_hole.isEmpty){
        val hole= first_hole.get._1
        val index=first_hole.get._2
         Array(
            balls_not_be_assigned,
            holes_not_be_assigned++holes_rest.slice(0,index),
            holes_rest.slice(index+1,holes_rest.length),
            assigned_pair++Map(hole -> ball)
          )
      }else{
         Array(
           balls_not_be_assigned:+ball,
           holes_not_be_assigned++holes_rest,
           Array(),
           assigned_pair)
      }

    }

    def allocate(holes:Array[Hole],balls:Array[Ball]):Array[Any]={
      val sorted_holes=holes.sortBy(hole=>hole.size)
      val sorted_balls=balls.sortBy(ball=>ball.size)
      val init_val=Array(Array(), Array(), sorted_holes, Map() );
      val result=sorted_balls.fold(init_val)(reducer)
      return result.asInstanceOf[Array[Any]]
    }

  }

}
