/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import com.albertoventurini.graphdbplugin.language.cypher.references.CypherNamedElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Description
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherPsiImplUtil {

    /**
     * Safely replaces symbolic name node with new node.
     * It's possible that nothing happens if newElement is invalid. No error thrown in such case.
     * <p>
     * TODO: handle cases with invalid newElement (e.g. cyrilic name is set)
     */
    public static void safelyReplaceSymbolicName(CypherNamedElement element,
                                                  CypherNamedElement newElement) {
        ASTNode oldNameNode = element.getNode().findChildByType(CypherTypes.SYMBOLIC_NAME_STRING);
        if (oldNameNode != null) {
            if (newElement != null) {
                ASTNode newNameNode = newElement
                        .getNode()
                        .findChildByType(CypherTypes.SYMBOLIC_NAME_STRING);
                if (newNameNode != null) {
                    element.getNode().replaceChild(oldNameNode, newNameNode);
                }
            }
        }
    }
}
