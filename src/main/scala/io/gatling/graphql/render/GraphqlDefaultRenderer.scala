package io.gatling.graphql.render

import io.gatling.graphql.ast._
import io.gatling.commons.validation.Failure
import io.gatling.core.session.Expression
import io.gatling.graphql.ast.{GraphqlDocumentDef, GraphqlFieldDef, GraphqlFragmentDef, GraphqlFragmentSpreadDef, GraphqlInlineFragmentDef, GraphqlNode, GraphqlOperationDef, GraphqlOperationType, GraphqlSelectionDef, GraphqlVariableDef}

object GraphqlDefaultRenderer extends GraphqlRenderer {

  private def renderOpType(op: GraphqlOperationType): String = op match {
    case GraphqlOperationType.QUERY => "query"
    case GraphqlOperationType.MUTATION => "mutation"
    case GraphqlOperationType.SUBSCRIPTION => "subscription"
  }

  private def renderVar[T](`var`: GraphqlVariableDef[T]): Expression[String] =
    session => {
      for {
        name <- `var`.name(session)
        defaultValue <- `var`.default.map(_(session))
      } yield s"$name: ${`var`.typeName} = $defaultValue"
    }
    `var`.default match {
      case Some(default) =>
      case _ => session => `var`.name(session).map(name => s"$name: ${`var`.typeName}")
    }

  private def renderOp(op: GraphqlOperationDef): Expression[String] = {
//    session => s"${renderOpType(op.operationType)} ${op.name} (${op.variables.map(renderVar(_)).mkString(", ")})"
    session => {
      for {
        name <- op.name.map(_(session))
        `var` <- op.variables.map(renderVar(_))
      } `var`
    }
  }

  private def renderDoc(doc: GraphqlDocumentDef): Expression[String] = (doc.operations.map(renderOp) ++ doc.fragments.map(renderFrag)).mkString("\n")

  private def renderFrag(frag: GraphqlFragmentDef): Expression[String] = s"fragment ${frag.name} on ${frag.onType} {${frag.selections.map(renderSel)}}"

  private def renderSel(sel: GraphqlSelectionDef): String = sel match {
    case GraphqlFieldDef(name, alias, arguments, selections) => alias.map()
    case GraphqlInlineFragmentDef(onType, selections) => ???
    case GraphqlFragmentSpreadDef(name) => s"... ${}"
  }

  override def render(node: GraphqlNode): Expression[String] = node match {
    case doc: GraphqlDocumentDef => s => renderDoc(doc)
    case x => _ => Failure(s"invalid root node for rendering: $x")
  }
}
