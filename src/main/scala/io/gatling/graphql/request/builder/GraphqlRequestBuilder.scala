package io.gatling.graphql.request.builder

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.graphql.ast._
import GraphqlTypeNameProvider.provideTypeName
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext
import io.gatling.graphql.ast.{GraphqlDocumentDef, GraphqlFragmentDef, GraphqlOperationDef, GraphqlOperationType}
import io.gatling.graphql.ast.GraphqlOperationType.QUERY
import io.gatling.http.request.builder.{CommonAttributes, HttpAttributes, HttpRequestBuilder}

final case class GraphqlRequestBuilder(
                                        commonAttributes: CommonAttributes,
                                        requestDef: GraphqlDocumentDef = GraphqlDocumentDef(),
                                        http: HttpAttributes = HttpAttributes.Empty,
                                      ) extends ActionBuilder {
  val httpRequestBuilder: HttpRequestBuilder = new HttpRequestBuilder(commonAttributes, http)

  def query: GraphqlOperationBuilder = GraphqlOperationBuilder(this, QUERY)

  def mutation: GraphqlOperationBuilder = GraphqlOperationBuilder(this, GraphqlOperationType.QUERY)

  //  def fragment[T](fragment: GraphqlFragmentBuilder => GraphqlFragmentDef)(implicit tnp: GraphqlTypeNameProvider[T]): GraphqlRequestBuilder =
  //    addFragment(fragment(GraphqlFragmentBuilder(this, provideTypeName[T])))

  def fragment[T](implicit tnp: GraphqlTypeNameProvider[T]): GraphqlFragmentBuilder = fragment(provideTypeName[T])

  def fragment(on: String): GraphqlFragmentBuilder = GraphqlFragmentBuilder(this, on)

  protected[graphql] def addOperation(op: GraphqlOperationDef): GraphqlRequestBuilder = this.modify(_.requestDef.operations)(_ :+ op)

  protected[graphql] def addFragment(fr: GraphqlFragmentDef): GraphqlRequestBuilder = this.modify(_.requestDef.fragments)(_ :+ fr)

  def header(key: String, value: Expression[String]): GraphqlRequestBuilder = {
    httpRequestBuilder.header(key, value)
    this
    //    this.modify(this.httpRequestBuilder).setTo(this.httpRequestBuilder.header(key, value))
  }

  override def build(ctx: ScenarioContext, next: Action): Action = {
    httpRequestBuilder
      //      .body()
      .build(ctx, next)
  }
}


object GraphqlRequestBuilder {

}
