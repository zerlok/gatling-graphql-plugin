package io.gatling.graphql.request.builder

import io.gatling.graphql.ast.GraphqlId

trait GraphqlTypeNameProvider[T] {
  def provideTypeName: String
}

object GraphqlTypeNameProvider {

  implicit val booleanTypeNameProvider: GraphqlTypeNameProvider[Boolean] = new GraphqlTypeNameProvider[Boolean] {
    override def provideTypeName: String = "Boolean"
  }

  implicit val intTypeNameProvider: GraphqlTypeNameProvider[Int] = new GraphqlTypeNameProvider[Int] {
    override def provideTypeName: String = "Int"
  }

  implicit val floatTypeNameProvider: GraphqlTypeNameProvider[Float] = new GraphqlTypeNameProvider[Float] {
    override def provideTypeName: String = "Float"
  }

  implicit val stringTypeNameProvider: GraphqlTypeNameProvider[String] = new GraphqlTypeNameProvider[String] {
    override def provideTypeName: String = "String"
  }

  implicit val idTypeNameProvider: GraphqlTypeNameProvider[GraphqlId] = new GraphqlTypeNameProvider[GraphqlId] {
    override def provideTypeName: String = "ID"
  }

  implicit class GraphqlOptionTypeNameProvider[T](x: Class[Option[T]])(implicit inner: GraphqlTypeNameProvider[T]) extends GraphqlTypeNameProvider[Option[T]] {
    override def provideTypeName: String = inner.provideTypeName
  }

  implicit class GraphqlSeqTypeNameProvider[T](x: Class[Seq[T]])(implicit inner: GraphqlTypeNameProvider[T]) extends GraphqlTypeNameProvider[Seq[T]] {
    override def provideTypeName: String = s"[${inner.provideTypeName}]"
  }

  def provideTypeName[T](implicit tnp: GraphqlTypeNameProvider[T]): String = tnp match {
    case _: GraphqlOptionTypeNameProvider[T] => tnp.provideTypeName
    case _ => s"${tnp.provideTypeName}!"
  }
}
