package controllers

import controllers.Assets.Ok
import play.api._
import play.api.mvc._
import play.api.cache.Cache
import play.api.Play.current
import play.api.db._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("aaaaaaaaaaaaaaaaaa"))
  }

  def db = Action {
    var out = ""
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())")

      val rs = stmt.executeQuery("SELECT tick FROM ticks")

      while (rs.next) {
        out += "Read from DB: " + rs.getTimestamp("tick") + "\n"
      }
    } finally {
      conn.close()
    }
    Ok(out)
  }

  def test = Action { implicit request => {
      val name = request.body.asFormUrlEncoded.get("text")(0)
      Ok(views.html.index(s"hello, $name"))
    }
  }
}
