package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherElementFactory;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherLabelName;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherPropertyKeyName;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.ContributedReferenceHost;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.albertoventurini.graphdbplugin.language.cypher.psi.CypherPsiImplUtil.safelyReplaceSymbolicName;

public class CypherLabelNameImplMixin extends ASTWrapperPsiElement
        implements CypherNamedElement, ContributedReferenceHost {

    public CypherLabelNameImplMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return this;
    }

    @Override
    public String getName() {
        return this.getText();
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        CypherLabelName newCypherLabelName = CypherElementFactory
                .createLabelNameNode(this.getProject(), name);
        safelyReplaceSymbolicName(this, newCypherLabelName);
        return this;
    }

    @NotNull
    public PsiReference[] getReferences() {
        return PsiReferenceService.getService().getContributedReferences(this);
    }
}