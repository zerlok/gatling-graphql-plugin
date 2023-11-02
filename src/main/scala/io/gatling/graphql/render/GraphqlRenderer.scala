package io.gatling.graphql.render

import io.gatling.graphql.ast._
import io.gatling.core.session.Expression
import io.gatling.graphql.ast.GraphqlNode

trait GraphqlRenderer {
  def render(node: GraphqlNode): Expression[String]
}
