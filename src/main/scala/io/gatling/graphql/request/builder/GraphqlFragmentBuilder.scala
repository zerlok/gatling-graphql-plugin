package io.gatling.graphql.request.builder

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.graphql.ast._
import io.gatling.core.session.Expression
import io.gatling.graphql.ast.{GraphqlFragmentDef, GraphqlSelectionDef}

final case class GraphqlFragmentBuilder(
                                         private val root: GraphqlRequestBuilder,
                                         private val onType: String,
                                         private val name: Option[Expression[String]] = None,
                                         private val selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                       ) {
  def name(value: Expression[String]): GraphqlFragmentBuilder = this.modify(_.name).setTo(Some(value))

  def select(selection: GraphqlSelectionBuilder => Seq[GraphqlSelectionDef]): GraphqlFragmentBuilder =
    this.modify(_.selections)(_ ++ selection(GraphqlSelectionBuilder()))

  def build: GraphqlRequestBuilder = {
    require(name.nonEmpty)
    require(selections.nonEmpty)
    root.addFragment(GraphqlFragmentDef(
      name = name.get,
      onType = onType,
      selections = selections,
    ))
  }
}

object GraphqlFragmentBuilder {
  implicit def toRequestBuilder(builder: GraphqlFragmentBuilder): GraphqlRequestBuilder = builder.build
}
