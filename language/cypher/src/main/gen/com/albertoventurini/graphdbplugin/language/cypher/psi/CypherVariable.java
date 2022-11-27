// This is a generated file. Not intended for manual editing.
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.albertoventurini.graphdbplugin.language.cypher.references.CypherVariableElement;
import com.intellij.psi.PsiReference;

public interface CypherVariable extends CypherVariableElement {

  @NotNull
  CypherSymbolicNameString getSymbolicNameString();

  String getName();

  CypherVariable setName(String newName);

  PsiElement getNameIdentifier();

  @NotNull PsiReference[] getReferences();

}
