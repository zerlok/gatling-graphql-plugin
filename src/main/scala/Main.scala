
import io.gatling.commons.validation.Success
import io.gatling.graphql.render.GraphqlDefaultRenderer.render
import io.gatling.graphql.request.builder.Graphql

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")

    val doc = new Graphql(_ => Success("asdf"))
      .query
      .name(_ => Success(Some("Hey")))
      .variable[String](_ => Success("foo"))
      .select {
        _.field(_ => Success("myField")).argument(_ => Success("myArg"), _ => Success("x"))
          .field(_ => Success("myField2")).select {
            _.field(_ => Success("xyz"))
          }
      }.build.requestDef

    println(render(doc))
  }
}