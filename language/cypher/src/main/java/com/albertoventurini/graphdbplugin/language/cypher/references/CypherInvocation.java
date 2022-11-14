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
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.CypherMetadataProviderService;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherBuiltInFunctions;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms.CypherType;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherBuiltInFunctionElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherProcedureElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.CypherUserFunctionElement;
import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.InvokableInformation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
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
        CypherMetadataProviderService svc = ServiceManager.getService(
                getProject(),
                CypherMetadataProviderService.class);
        final List<InvokableInformation> matchedInvocations = newArrayList();

        if (this instanceof CypherProcedureInvocation) {
            svc.findProcedure(getFullName())
                .map(CypherProcedureElement::getInvokableInformation)
                .ifPresent(matchedInvocations::add);
            return matchedInvocations;
        }

        matchedInvocations.addAll(CypherBuiltInFunctions.FUNCTIONS.stream()
            .filter(func -> Objects.equals(func.getInvokable().getName(), getFullName()))
            .map(CypherBuiltInFunctionElement::getInvokable)
            .filter(Objects::nonNull)
            .collect(toList()));
        if (matchedInvocations.isEmpty()) {
            svc.findUserFunction(getFullName())
                .map(CypherUserFunctionElement::getInvokableInformation)
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
