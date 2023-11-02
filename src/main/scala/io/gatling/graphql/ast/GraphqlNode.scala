package io.gatling.graphql.ast

import io.gatling.core.session.Expression

sealed trait GraphqlNode

final case class GraphqlDocumentDef(
                                     operations: Seq[GraphqlOperationDef] = Seq.empty[GraphqlOperationDef],
                                     fragments: Seq[GraphqlFragmentDef] = Seq.empty[GraphqlFragmentDef],
                                   ) extends GraphqlNode

final case class GraphqlOperationDef(
                                      operationType: GraphqlOperationType,
                                      name: Option[Expression[Option[String]]] = None,
                                      variables: Seq[GraphqlVariableDef[_]] = Seq.empty[GraphqlVariableDef[_]],
                                      selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                    ) extends GraphqlNode

final case class GraphqlFragmentDef(
                                     name: Expression[String],
                                     onType: String,
                                     selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                   ) extends GraphqlNode


sealed trait GraphqlSelectionDef extends GraphqlNode

final case class GraphqlFieldDef(
                                  name: Expression[String],
                                  alias: Option[Expression[Option[String]]] = None,
                                  arguments: Seq[GraphqlArgumentDef[_]] = Seq.empty[GraphqlArgumentDef[_]],
                                  selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                ) extends GraphqlSelectionDef

final case class GraphqlInlineFragmentDef(
                                           onType: String,
                                           selections: Seq[GraphqlSelectionDef] = Seq.empty[GraphqlSelectionDef],
                                         ) extends GraphqlSelectionDef

final case class GraphqlFragmentSpreadDef(
                                           name: Expression[String],
                                         ) extends GraphqlSelectionDef


final case class GraphqlVariableDef[T](
                                        name: Expression[String],
                                        typeName: String,
                                        default: Option[Expression[Option[T]]] = None,
                                      ) extends GraphqlNode

final case class GraphqlArgumentDef[T](
                                        name: Expression[String],
                                        value: Expression[T],
                                      ) extends GraphqlNode
