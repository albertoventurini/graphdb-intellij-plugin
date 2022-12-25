/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;


import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherFunctionArguments;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherFunctionInvocation;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherProcedureInvocation;
import com.albertoventurini.graphdbplugin.language.cypher.references.types.CypherTyped;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataProviderService;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherBuiltInFunctions;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherBuiltInFunctionElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherProcedureElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherFunctionElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.InvokableInformation;

import java.util.*;

import static com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherSimpleType.ANY;
import static java.util.stream.Collectors.toList;

public interface CypherInvocation extends PsiElement, CypherTyped {

    default List<? extends PsiElement> arguments() {
        if (this instanceof CypherFunctionInvocation) {
            return Optional.ofNullable(((CypherFunctionInvocation) this).getFunctionArguments())
                    .map(CypherFunctionArguments::getExpressionList)
                    .orElse(Collections.emptyList());
        }
        if (this instanceof CypherProcedureInvocation) {
            return ((CypherProcedureInvocation) this).getProcedureArguments()
                    .getExpressionList();
        } else {
            return Collections.emptyList();
        }
    }

    default PsiElement argumentsToken() {
        if (this instanceof CypherFunctionInvocation) {
            return PsiTreeUtil.getChildOfType(this, CypherArgumentList.class);
        }
        if (this instanceof CypherProcedureInvocation) {
            return PsiTreeUtil.getChildOfType(this, CypherArgumentList.class);
        } else {
            return this;
        }

    }

    default List<InvokableInformation> resolve() {
        CypherMetadataProviderService svc = getProject().getService(CypherMetadataProviderService.class);
        final List<InvokableInformation> matchedInvocations = new ArrayList<>();

        if (this instanceof CypherProcedureInvocation) {
            svc.findProcedure(getFullName())
                .map(CypherProcedureElement::getInvokableInformation)
                .ifPresent(matchedInvocations::add);
            return matchedInvocations;
        }

        String name = getFullName();

        matchedInvocations.addAll(CypherBuiltInFunctions.FUNCTIONS.stream()
                .map(CypherBuiltInFunctionElement::getInvokable)
                .filter(invokable -> Objects.equals(invokable.getName(), name))
                .collect(toList()));

        if (matchedInvocations.isEmpty()) {
            svc.findFunction(getFullName())
                .map(CypherFunctionElement::getInvokableInformation)
                .ifPresent(matchedInvocations::add);
        }
        return matchedInvocations;
    }

    @Override
    default CypherType getType() {
        return resolve().stream()
            .findFirst()  // TODO: potential could be many return types for similar method
            .map(InvokableInformation::getReturnType)
            .orElse(ANY);
    }

    String getFullName();

}
