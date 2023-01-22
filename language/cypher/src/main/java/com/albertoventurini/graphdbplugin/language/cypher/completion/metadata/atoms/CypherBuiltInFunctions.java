/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherBuiltInFunctionElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.InvokableInformation;

import java.util.List;
import java.util.stream.Collectors;

public final class CypherBuiltInFunctions {

    public static final List<CypherBuiltInFunctionElement> FUNCTIONS = List.of(
            element("shortestPath", "(pattern :: PATH)", CypherSimpleType.PATH),
            element("allShortestPaths", "(pattern :: PATH)", CypherList.of(CypherSimpleType.PATH)),
            element("timestamp", "()", CypherSimpleType.INTEGER),
            element("coalesce", "(expression... :: ANY)", CypherSimpleType.ANY)
    );

    public static final List<String> FUNCTION_NAMES = FUNCTIONS.stream()
            .map(CypherBuiltInFunctionElement::getInvokable)
            .map(InvokableInformation::getName)
            .distinct()
            .collect(Collectors.toList());

    private static CypherBuiltInFunctionElement element(String name, String signature, CypherType returnType) {
        return new CypherBuiltInFunctionElement(
                new InvokableInformation(name, signature, returnType));
    }
}
