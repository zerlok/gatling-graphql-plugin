package io.gatling.graphql.ast

sealed abstract class GraphqlOperationType(val label: String)

object GraphqlOperationType {
  final case object QUERY extends GraphqlOperationType("query")

  final case object MUTATION extends GraphqlOperationType("mutation")

  final case object SUBSCRIPTION extends GraphqlOperationType("subscription")
}

