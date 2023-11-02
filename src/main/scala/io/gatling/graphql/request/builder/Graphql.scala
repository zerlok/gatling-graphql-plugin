package io.gatling.graphql.request.builder

import io.gatling.commons.validation.Success
import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.request.builder.CommonAttributes
import io.netty.handler.codec.http.HttpMethod

final class Graphql(requestName: Expression[String]) {

  def request: GraphqlRequestBuilder = request("/")

  def request(url: Expression[String]): GraphqlRequestBuilder =
    new GraphqlRequestBuilder(CommonAttributes(requestName, HttpMethod.POST, Left(url)))


  def query: GraphqlOperationBuilder = request.query

  def mutation: GraphqlOperationBuilder = request.mutation
}

object Graphql {
  implicit def toExpression[T](x: T): Expression[T] = _ => Success(x)
}
