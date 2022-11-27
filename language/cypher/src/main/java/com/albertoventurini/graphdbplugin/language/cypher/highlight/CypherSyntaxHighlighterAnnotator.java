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
    public void annotate(
            @NotNull final PsiElement element,
            @NotNull final AnnotationHolder holder) {
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
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherAllShortestPathsFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherFilterFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherExtractFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherReduceFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherAllFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherAnyFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherNoneFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherSingleFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherExistsFunctionInvocation) {
            setHighlighting(holder, CypherSyntaxColors.FUNCTION);
        } else if (element instanceof CypherNodeLookup) {
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
