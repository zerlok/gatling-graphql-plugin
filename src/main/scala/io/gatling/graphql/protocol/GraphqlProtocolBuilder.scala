package io.gatling.graphql.protocol

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.core.config.GatlingConfiguration
import io.gatling.http.protocol.{HttpProtocol, HttpProtocolBuilder}
import io.gatling.javaapi.core.ProtocolBuilder

object GraphqlProtocolBuilder {
  implicit def toGraphqlProtocol(builder: GraphqlProtocolBuilder): GraphqlProtocol = builder.build

  def apply(configuration: GatlingConfiguration): GraphqlProtocolBuilder =
    GraphqlProtocolBuilder(HttpProtocolBuilder(configuration))
}

final case class GraphqlProtocolBuilder(
                                         protocol: HttpProtocol,
                                       ) extends ProtocolBuilder {
  def baseUrl(url: String): GraphqlProtocolBuilder = baseUrls(List(url))

  def baseUrls(urls: String*): GraphqlProtocolBuilder = baseUrls(urls.toList)

  def baseUrls(urls: List[String]): GraphqlProtocolBuilder = this.modify(_.protocol.baseUrls).setTo(urls)

  def warmUp(url: String): GraphqlProtocolBuilder = this.modify(_.protocol.warmUpUrl).setTo(Some(url))

  def disableWarmUp: GraphqlProtocolBuilder = this.modify(_.protocol.warmUpUrl).setTo(None)

  def build: GraphqlProtocol = GraphqlProtocol(protocol)
}
