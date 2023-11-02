package io.gatling.graphql.ast

final case class GraphqlId(value: String)

object GraphqlId {
  implicit def id2string(x: GraphqlId): String = x.value

  implicit def int2id(x: Int): GraphqlId = GraphqlId(x)

  implicit def string2id(x: String): GraphqlId = GraphqlId(x)

  def apply(x: Int): GraphqlId = GraphqlId(x.toString)
}
