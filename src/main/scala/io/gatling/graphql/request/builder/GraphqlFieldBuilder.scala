package io.gatling.graphql.request.builder

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.graphql.ast._
import io.gatling.core.session.Expression
import io.gatling.graphql.ast.{GraphqlArgumentDef, GraphqlFieldDef, GraphqlSelectionDef}

final case class GraphqlFieldBuilder(
                                      root: GraphqlSelectionBuilder,
                                      name: Expression[String],
                                      alias: Option[Expression[Option[String]]] = None,
                                      arguments: Seq[GraphqlArgumentDef[_]] = Seq.empty[GraphqlArgumentDef[_]],
                                      selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                    ) {
  def alias(value: Expression[Option[String]]): GraphqlFieldBuilder = this.modify(_.alias).setTo(Some(value))

  def argument[T](name: Expression[String], value: Expression[T]): GraphqlFieldBuilder = this.modify(_.arguments)(_ :+ GraphqlArgumentDef(name, value))

  def select(selection: GraphqlSelectionBuilder => Seq[GraphqlSelectionDef]): GraphqlFieldBuilder =
    this.modify(_.selections)(_ ++ selection(GraphqlSelectionBuilder()))

  def build: GraphqlSelectionBuilder =
    root.addSelections(GraphqlFieldDef(
      name = name,
      alias = alias,
      arguments = arguments,
      selections = selections,
    ))
}


object GraphqlFieldBuilder {
  implicit def toSelectionBuilder(builder: GraphqlFieldBuilder): GraphqlSelectionBuilder = builder.build

  implicit def toSelections(builder: GraphqlFieldBuilder): Seq[GraphqlSelectionDef] = builder.build
}
