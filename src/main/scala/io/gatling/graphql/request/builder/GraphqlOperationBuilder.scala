package io.gatling.graphql.request.builder

import com.softwaremill.quicklens.ModifyPimp
import io.gatling.graphql.ast._
import GraphqlTypeNameProvider.provideTypeName
import io.gatling.core.session.Expression
import io.gatling.graphql.ast.{GraphqlOperationDef, GraphqlOperationType, GraphqlSelectionDef, GraphqlVariableDef}


final case class GraphqlOperationBuilder(
                                          private val root: GraphqlRequestBuilder,
                                          private val operationType: GraphqlOperationType,
                                          private val name: Option[Expression[Option[String]]] = None,
                                          private val variables: Seq[GraphqlVariableDef[_]] = Seq.empty[GraphqlVariableDef[_]],
                                          private val selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                        ) {

  def name(value: Expression[Option[String]]): GraphqlOperationBuilder = this.modify(_.name).setTo(Some(value))

  def variable[T](name: Expression[String])(implicit tnp: GraphqlTypeNameProvider[T]): GraphqlOperationBuilder = variable(name, None)

  def variable[T](name: Expression[String], default: Option[Expression[Option[T]]])(implicit typeNameProvider: GraphqlTypeNameProvider[T]): GraphqlOperationBuilder =
    this.modify(_.variables)(_.appended(GraphqlVariableDef(name, provideTypeName[T], default)))

  def select(selection: GraphqlSelectionBuilder => Seq[GraphqlSelectionDef]): GraphqlOperationBuilder =
    this.modify(_.selections)(_ ++ selection(GraphqlSelectionBuilder()))

  def build: GraphqlRequestBuilder = root.addOperation(GraphqlOperationDef(
    operationType = operationType,
    name = name,
    variables = variables,
    selections = selections,
  ))
}

object GraphqlOperationBuilder {
  implicit def toRequestBuilder(builder: GraphqlOperationBuilder): GraphqlRequestBuilder = builder.build
}