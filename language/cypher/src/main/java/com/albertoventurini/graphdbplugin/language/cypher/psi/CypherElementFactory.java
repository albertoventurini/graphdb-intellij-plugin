/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.psi;

import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFile;
import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFileType;
import com.albertoventurini.graphdbplugin.language.cypher.util.TraverseUtil;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.tree.IElementType;

/**
 * TODO: Description
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherElementFactory {

    public static CypherVariable createIdentifierNode(Project project, String name) {
        String pattern = String.format("MATCH (%s) RETURN *;", name);
        return createAndGetElement(project, pattern, CypherTypes.VARIABLE);
    }

    public static CypherLabelName createLabelNameNode(Project project, String name) {
        String pattern = String.format("MATCH (n:%s) RETURN *;", name);
        return createAndGetElement(project, pattern, CypherTypes.LABEL_NAME);
    }

    public static CypherRelTypeName createRelTypeNameNode(Project project, String name) {
        String pattern = String.format("MATCH (n)-[:%s]-() RETURN *;", name);
        return createAndGetElement(project, pattern, CypherTypes.REL_TYPE_NAME);
    }

    public static CypherPropertyKeyName createPropertyKeyNameNode(Project project, String name) {
        String pattern = String.format("MATCH (n) WHERE n.%s = 1 RETURN n;", name);
        return createAndGetElement(project, pattern, CypherTypes.PROPERTY_KEY_NAME);
    }

    @SuppressWarnings("unchecked")
    private static <T extends PsiElement> T createAndGetElement(Project project, String pattern,
                                                                IElementType elementType) {
        final CypherFile file = createFile(project, pattern);
        ASTNode astNode = TraverseUtil.findFirstDeepChildByType(file.getNode(), elementType);
        if (astNode != null) {
            return (T) astNode.getPsi();
        } else {
            return null;
        }
    }

    private static CypherFile createFile(Project project, String text) {
        String name = "dummy.cypher";
        return (CypherFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, CypherFileType.INSTANCE, text);
    }
}
