package io.gatling.graphql

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.session._
import io.gatling.graphql.protocol.GraphqlProtocolBuilder
import io.gatling.graphql.request.builder.Graphql

trait GraphqlDsl {
  def graphql(implicit configuration: GatlingConfiguration): GraphqlProtocolBuilder = GraphqlProtocolBuilder(configuration)

  def graphql(requestName: Expression[String]): Graphql = new Graphql(requestName)
}
