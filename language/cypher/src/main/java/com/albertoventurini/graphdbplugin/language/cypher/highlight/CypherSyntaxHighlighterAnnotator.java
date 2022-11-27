/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.highlight;

import com.albertoventurini.graphdbplugin.language.cypher.psi.*;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class CypherSyntaxHighlighterAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CypherVariable) {
            setHighlighting(holder, CypherSyntaxColors.VARIABLE);
        } else if (element instanceof CypherFunctionInvocationBody || element instanceof CypherProcedureInvocationBody) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherLabelName) {
            setHighlighting(holder, CypherSyntaxColors.LABEL);
        } else if (element instanceof CypherUnaryOperator) {
            setHighlighting(holder, CypherSyntaxColors.NUMBER);
        } else if (element instanceof CypherRelTypeName) {
            setHighlighting(holder, CypherSyntaxColors.RELATIONSHIP_TYPE);
        } else if (element instanceof CypherParameter) {
            setHighlighting(holder, CypherSyntaxColors.PARAMETER);
        } else if (element instanceof CypherShortestPathFunctionInvocation) {
            CypherShortestPathFunctionInvocation invocation = (CypherShortestPathFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherAllShortestPathsFunctionInvocation) {
            CypherAllShortestPathsFunctionInvocation invocation = (CypherAllShortestPathsFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherFilterFunctionInvocation) {
            CypherFilterFunctionInvocation invocation = (CypherFilterFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherExtractFunctionInvocation) {
            CypherExtractFunctionInvocation invocation = (CypherExtractFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherReduceFunctionInvocation) {
            CypherReduceFunctionInvocation invocation = (CypherReduceFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherAllFunctionInvocation) {
            CypherAllFunctionInvocation invocation = (CypherAllFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherAnyFunctionInvocation) {
            CypherAnyFunctionInvocation invocation = (CypherAnyFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherNoneFunctionInvocation) {
            CypherNoneFunctionInvocation invocation = (CypherNoneFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherSingleFunctionInvocation) {
            CypherSingleFunctionInvocation invocation = (CypherSingleFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherExistsFunctionInvocation) {
            CypherExistsFunctionInvocation invocation = (CypherExistsFunctionInvocation) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherNodeLookup) {
            CypherNodeLookup nodeLookup = (CypherNodeLookup) element;
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherRelationshipLookup) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        }
    }

    private static void setHighlighting(
            @NotNull final AnnotationHolder holder,
            @NotNull final TextAttributesKey key) {

        holder.newAnnotation(HighlightSeverity.INFORMATION, "")
                .enforcedTextAttributes(TextAttributes.ERASE_MARKER)
                .create();

        String description = ApplicationManager.getApplication().isUnitTestMode() ? key.getExternalName() : "";

        holder.newAnnotation(HighlightSeverity.INFORMATION, description)
                .textAttributes(key)
                .create();
    }
}
