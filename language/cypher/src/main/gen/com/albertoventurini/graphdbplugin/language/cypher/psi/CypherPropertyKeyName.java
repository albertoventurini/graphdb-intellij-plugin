// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherNamedElement;
import com.intellij.psi.PsiReference;

public interface CypherPropertyKeyName extends CypherNamedElement {

  @NotNull
  CypherSymbolicNameString getSymbolicNameString();

  String getName();

  CypherPropertyKeyName setName(String newName);

  PsiElement getNameIdentifier();

  @NotNull PsiReference[] getReferences();

}
