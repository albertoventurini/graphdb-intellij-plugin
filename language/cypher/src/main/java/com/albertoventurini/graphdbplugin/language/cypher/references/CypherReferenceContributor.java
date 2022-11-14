/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.references;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherLabelName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherPropertyKeyName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherRelTypeName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherVariable;
import com.albertoventurini.graphdbplugin.language.cypher.references.impl.CypherLabelNameReference;
import com.albertoventurini.graphdbplugin.language.cypher.references.impl.CypherPropertyKeyNameReference;
import com.albertoventurini.graphdbplugin.language.cypher.references.impl.CypherRelTypeNameReference;
import com.albertoventurini.graphdbplugin.language.cypher.references.impl.CypherVariableReference;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.albertoventurini.graphdbplugin.language.cypher.CypherLanguage;
import org.jetbrains.annotations.NotNull;

import static com.albertoventurini.graphdbplugin.language.cypher.util.PsiUtil.rangeFrom;

/**
 * Contribute references for specified PSI elements.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherReferenceContributor extends PsiReferenceContributor {

    public static final PsiReference[] NO_REFERENCES = new PsiReference[0];

    public CypherReferenceContributor() {
    }

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        register(registrar,
                PlatformPatterns.psiElement().withLanguage(CypherLanguage.INSTANCE),
                CypherReferenceContributionPriority.VARIABLE,
                (element, context) ->
                        element instanceof CypherVariable ? single(new CypherVariableReference(element, rangeFrom(element))) : null);
        register(registrar,
                PlatformPatterns.psiElement().withLanguage(CypherLanguage.INSTANCE),
                CypherReferenceContributionPriority.LABEL_NAME,
                (element, context) ->
                        element instanceof CypherLabelName ? single(new CypherLabelNameReference(element, rangeFrom(element))) : null);
        register(registrar,
                PlatformPatterns.psiElement().withLanguage(CypherLanguage.INSTANCE),
                CypherReferenceContributionPriority.REL_TYPE_NAME,
                (element, context) ->
                        element instanceof CypherRelTypeName ? single(new CypherRelTypeNameReference(element, rangeFrom(element))) : null);
        register(registrar,
                PlatformPatterns.psiElement().withLanguage(CypherLanguage.INSTANCE),
                CypherReferenceContributionPriority.PROPERTY_KEY_NAME,
                (element, context) ->
                        element instanceof CypherPropertyKeyName ? single(new CypherPropertyKeyNameReference(element, rangeFrom(element))) : null);
    }

    private void register(PsiReferenceRegistrar registrar,
                          @NotNull ElementPattern<PsiElement> pattern,
                          CypherReferenceContributionPriority contributionPriority,
                          ReferenceFactory referenceFactory) {
        registrar.registerReferenceProvider(
                pattern,
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        PsiReference[] psiReferences = referenceFactory.run(element, context);
                        return psiReferences != null ? psiReferences : NO_REFERENCES;
                    }
                },
                contributionPriority.getPriority());
    }

    private PsiReference[] single(PsiReference reference) {
        return new PsiReference[]{reference};
    }

    private interface ReferenceFactory {
        PsiReference[] run(@NotNull PsiElement element, @NotNull ProcessingContext context);
    }
}
