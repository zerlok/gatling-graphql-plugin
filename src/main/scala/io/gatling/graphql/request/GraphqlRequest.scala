package io.gatling.graphql.request

import io.gatling.graphql.ast.GraphqlDocumentDef

final case class GraphqlRequest(
                                 doc: GraphqlDocumentDef,
                                 variables: Seq[(String, _)] = Seq.empty[(String, _)],
                               )
