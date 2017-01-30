package curri

/**
  * Created by assen on 29/01/2017.
  */
case class NotFoundException(val msg: String) extends RuntimeException(msg) {

}
