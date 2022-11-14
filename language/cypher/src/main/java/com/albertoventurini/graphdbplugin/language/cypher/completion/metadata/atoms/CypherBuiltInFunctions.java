/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.lookup.LookupElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherBuiltInFunctionElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.InvokableInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CypherBuiltInFunctions {

    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_PREDICATE = Lists.newArrayList(
            element("all", "(variable IN list WHERE predicate :: ANY)", CypherSimpleType.BOOLEAN),
            element("any", "(variable IN list WHERE predicate :: ANY)", CypherSimpleType.BOOLEAN),
            element("none", "(variable in list WHERE predicate :: ANY)", CypherSimpleType.BOOLEAN),
            element("single", "(variable in list WHERE predicate :: ANY)", CypherSimpleType.BOOLEAN),
            element("exists", "(pattern :: ANY)", CypherSimpleType.BOOLEAN),
            element("exists", "(property :: ANY)", CypherSimpleType.BOOLEAN)
    );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_SHORTEST_PATH = Lists.newArrayList(
            element("shortestPath", "(pattern :: PATH)", CypherSimpleType.PATH),
            element("allShortestPaths", "(pattern :: PATH)", CypherList.of(CypherSimpleType.PATH))
    );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_SCALAR = Lists.newArrayList(
            element("size", "(list :: LIST OF ANY)", CypherSimpleType.INTEGER),
            element("size", "(pattern :: PATH)", CypherSimpleType.INTEGER),
            element("size", "(string :: STRING)", CypherSimpleType.INTEGER),
            element("length", "(path :: ANY)", CypherSimpleType.INTEGER),
            element("length", "(string :: STRING)", CypherSimpleType.INTEGER),
            element("type", "(relationship :: RELATIONSHIP)", CypherSimpleType.STRING),
            element("id", "(node :: NODE)", CypherSimpleType.INTEGER),
            element("id", "(relationship :: RELATIONSHIP)", CypherSimpleType.INTEGER),
            element("coalesce", "(expression... :: ANY)", CypherSimpleType.ANY),
            element("head", "(expression :: LIST OF ANY)", CypherSimpleType.ANY),
            element("last", "(expression :: LIST OF ANY)", CypherSimpleType.ANY),
            element("timestamp", "()", CypherSimpleType.INTEGER),
            element("startNode", "(relationship :: RELATIONSHIP)", CypherSimpleType.NODE),
            element("endNode", "(relationship :: RELATIONSHIP)", CypherSimpleType.NODE),
            element("properties", "(node :: NODE)", CypherSimpleType.MAP),
            element("properties", "(relationship :: RELATIONSHIP)", CypherSimpleType.MAP),
            element("toInt", "(expression :: STRING)", CypherSimpleType.INTEGER),
            element("toFloat", "(expression :: STRING)", CypherSimpleType.FLOAT)
    );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_LIST = Lists.newArrayList(
            element("nodes", "(path :: PATH)", CypherList.of(CypherSimpleType.NODE)),
            element("relationships", "(path :: PATH)", CypherList.of(CypherSimpleType.RELATIONSHIP)),
            element("labels", "(node :: NODE)", CypherList.of(CypherSimpleType.STRING)),
            element("keys", "(node :: NODE)", CypherList.of(CypherSimpleType.STRING)),
            element("keys", "(relationship :: RELATIONSHIP)", CypherList.of(CypherSimpleType.STRING)),
            element("extract", "(variable IN list | expression :: ANY)", CypherList.of(CypherSimpleType.ANY)),
            element("filter", "(variable IN list WHERE predicate :: ANY)", CypherList.of(CypherSimpleType.ANY)),
            element("tail", "(expression :: LIST OF ANY)", CypherList.of(CypherSimpleType.ANY)),
            element("range", "(start :: INTEGER, end :: INTEGER, step = 1 :: INTEGER)", CypherList.of(CypherSimpleType.INTEGER)),
                    element("reduce", "(accumulator = initial :: ANY, variable IN list | expression :: ANY)", CypherSimpleType.ANY)
            );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_MATH_NUMERIC = Lists.newArrayList(
            element("abs", "(expression :: NUMBER)", CypherSimpleType.INTEGER),
            element("ceil", "(expression :: NUMBER)", CypherSimpleType.INTEGER),
            element("floor", "(expression :: NUMBER)", CypherSimpleType.INTEGER),
            element("round", "(expression :: NUMBER)", CypherSimpleType.INTEGER),
            element("sign", "(expression :: NUMBER)", CypherSimpleType.INTEGER),
            element("rand", "()", CypherSimpleType.FLOAT)
    );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_MATH_LOGARITHMIC = Lists.newArrayList(
            element("log", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("log10", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("exp", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("e", "()", CypherSimpleType.FLOAT),
            element("sqrt", "(expression :: NUMBER)", CypherSimpleType.FLOAT)
    );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_MATH_TRIGONOMETRIC = Lists.newArrayList(
            element("sin", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("cos", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("tan", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("cot", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("asin", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("acos", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("atan", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("atan2", "(expression :: NUMBER, expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("pi", "()", CypherSimpleType.FLOAT),
            element("degrees", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("radians", "(expression :: NUMBER)", CypherSimpleType.FLOAT),
            element("haversin", "(expression :: NUMBER)", CypherSimpleType.FLOAT)
    );
    private static final List<CypherBuiltInFunctionElement> FUNCTIONS_STRING = Lists.newArrayList(
            element("replace", "(original :: STRING, search :: STRING, replace :: STRING)", CypherSimpleType.STRING),
            element("substring", "(original :: STRING, start :: INTEGER)", CypherSimpleType.STRING),
            element("substring", "(original :: STRING, start :: INTEGER, length = length(original) :: INTEGER)", CypherSimpleType.STRING),
            element("left", "(original :: STRING, length :: INTEGER)", CypherSimpleType.STRING),
            element("right", "(original :: STRING, length :: INTEGER)", CypherSimpleType.STRING),
            element("ltrim", "(original :: STRING)", CypherSimpleType.STRING),
            element("rtrim", "(original :: STRING)", CypherSimpleType.STRING),
            element("trim", "(original :: STRING)", CypherSimpleType.STRING),
            element("lower", "(original :: STRING)", CypherSimpleType.STRING),
            element("upper", "(original :: STRING)", CypherSimpleType.STRING),
            element("split", "(original :: STRING, splitPattern :: STRING)", CypherList.of(CypherSimpleType.STRING)),
            element("reverse", "(original :: STRING)", CypherSimpleType.STRING),
            element("toString", "(expression :: STRING)", CypherSimpleType.STRING)
    );

    public static final List<CypherBuiltInFunctionElement> FUNCTIONS = new ArrayList<CypherBuiltInFunctionElement>() {{
        addAll(FUNCTIONS_PREDICATE);
        addAll(FUNCTIONS_SHORTEST_PATH);
        addAll(FUNCTIONS_SCALAR);
        addAll(FUNCTIONS_LIST);
        addAll(FUNCTIONS_MATH_NUMERIC);
        addAll(FUNCTIONS_MATH_LOGARITHMIC);
        addAll(FUNCTIONS_MATH_TRIGONOMETRIC);
        addAll(FUNCTIONS_STRING);
    }};

    public static final List<LookupElement> FUNCTION_LOOKUP_ELEMENTS = FUNCTIONS.stream()
            .map(CypherBuiltInFunctionElement::getLookupElement)
            .collect(Collectors.toList());

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
