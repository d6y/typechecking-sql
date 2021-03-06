
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

import slick.backend.{DatabaseConfig, StaticDatabaseConfig}

@StaticDatabaseConfig("file:src/main/resources/application.conf#tsql")
object PlainSqlExample extends App {

  import slick.driver.H2Driver.api._

  final case class Message(content: String, id: Long = 0L)

  val testData = Seq(
    Message("Hello, HAL. Do you read me, HAL?"),
    Message("Affirmative, Dave. I read you."),
    Message("Open the pod bay doors, HAL."),
    Message("I'm sorry, Dave. I'm afraid I can't do that.")
  )

  final class MessageTable(tag: Tag) extends Table[Message](tag, "message") {
    def id      = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def content = column[String]("content")
    def * = (content, id) <> (Message.tupled, Message.unapply)
  }

  lazy val messages = TableQuery[MessageTable]

  val query: DBIO[Seq[String]] =
    tsql""" select "content" from "message" """

  val prog = for {
    _   <- messages.schema.create
    _   <- messages ++= testData
    msg <- query
  } yield msg

  val db = Database.forConfig("chat")
  val future = db.run(prog).map { _ foreach println }
  Await.result(future, 2 seconds)
  db.close
}