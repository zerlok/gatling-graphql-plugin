package io.gatling.graphql.request.builder

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.graphql.ast._
import GraphqlTypeNameProvider.provideTypeName
import io.gatling.core.session.Expression
import io.gatling.graphql.ast.{GraphqlFragmentSpreadDef, GraphqlInlineFragmentDef, GraphqlSelectionDef}


final case class GraphqlSelectionBuilder(
                                          selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                        ) {
  def field(name: Expression[String]): GraphqlFieldBuilder = GraphqlFieldBuilder(this, name)

  def inlineFragmentSpread[T](selection: GraphqlSelectionBuilder => Seq[GraphqlSelectionDef])(implicit tnp: GraphqlTypeNameProvider[T]): GraphqlSelectionBuilder = inlineFragmentSpread(provideTypeName[T])(selection)

  def inlineFragmentSpread(on: String)(selection: GraphqlSelectionBuilder => Seq[GraphqlSelectionDef]): GraphqlSelectionBuilder =
    this.addSelections(GraphqlInlineFragmentDef(on, selection(GraphqlSelectionBuilder())))

  def fragmentSpread(name: Expression[String]): GraphqlSelectionBuilder = this.addSelections(GraphqlFragmentSpreadDef(name))

  protected[graphql] def addSelections(s: GraphqlSelectionDef*): GraphqlSelectionBuilder = this.modify(_.selections)(_ ++ s)

  def build: Seq[GraphqlSelectionDef] = selections
}


object GraphqlSelectionBuilder {
  implicit def toRequestBuilder(builder: GraphqlSelectionBuilder): Seq[GraphqlSelectionDef] = builder.build
}