// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.psi.*;

public class CypherSetItemImpl extends ASTWrapperPsiElement implements CypherSetItem {

  public CypherSetItemImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CypherVisitor visitor) {
    visitor.visitSetItem(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CypherVisitor) accept((CypherVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CypherExpression getExpression() {
    return findChildByClass(CypherExpression.class);
  }

  @Override
  @Nullable
  public CypherNodeLabels getNodeLabels() {
    return findChildByClass(CypherNodeLabels.class);
  }

  @Override
  @Nullable
  public CypherPropertyExpression getPropertyExpression() {
    return findChildByClass(CypherPropertyExpression.class);
  }

  @Override
  @Nullable
  public CypherVariable getVariable() {
    return findChildByClass(CypherVariable.class);
  }

}
