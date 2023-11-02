package io.gatling.graphql

object Predef extends GraphqlDsl {
  import io.gatling.graphql.request.builder.Graphql._
  import io.gatling.graphql.request.builder.GraphqlTypeNameProvider._
}
