package io.gatling.graphql.protocol

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.Protocol
import io.gatling.http.protocol._

object GraphqlProtocol {
  def apply(configuration: GatlingConfiguration): GraphqlProtocol =
    GraphqlProtocol(HttpProtocol(configuration))
}

final case class GraphqlProtocol(http: HttpProtocol) extends Protocol {

}
