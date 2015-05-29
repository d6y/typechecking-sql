import doobie.imports._
import scalaz._, Scalaz._, scalaz.concurrent.Task

object DoobieExample extends App {

  final case class Message(content: String, id: Long = 0L)

  val testData = List(
    Message("Hello, HAL. Do you read me, HAL?"),
    Message("Affirmative, Dave. I read you."),
    Message("Open the pod bay doors, HAL."),
    Message("I'm sorry, Dave. I'm afraid I can't do that.")
  )

  def insert(ms: List[Message]) = {
    val sql = """ insert into "message"("content") values (?) """
    Update[String](sql).updateMany(ms.map(_.content))
  }

  val query: Query0[String] =
    sql""" select "content" from "message" """.query

  val program = for {
    _   <- insert(testData)
    msg <- query.list
  } yield msg

  val username = System.getProperty("user.name")
  val xa = DriverManagerTransactor[Task](
    "org.postgresql.Driver", "jdbc:postgresql:chat", username, ""
  )

  // Manually checking...
  // import xa.yolo._
  // query.check.run

  val task   = program.transact(xa)
  val result = task.run

  println(result)
}