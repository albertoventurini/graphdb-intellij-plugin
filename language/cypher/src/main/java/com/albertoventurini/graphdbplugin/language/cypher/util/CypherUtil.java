/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.util;

import com.albertoventurini.graphdbplugin.language.cypher.references.CypherNamedElement;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.IElementType;
import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFile;
import com.albertoventurini.graphdbplugin.language.cypher.file.CypherFileType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utility method for finding PSI elements.
 *
 * @author dmitry@vrublevsky.me
 */
public class CypherUtil {

    private static final Filter INCLUDE_ALL = (element) -> true;

    public static <T extends CypherNamedElement> List<T> findAll(Project project, IElementType elementType) {
        return findAllAndFilter(project, elementType, INCLUDE_ALL);
    }

    public static <T extends CypherNamedElement> List<T> findAll(PsiFile file, IElementType elementType) {
        return findAllAndFilter(file, elementType, INCLUDE_ALL);
    }

    public static <T extends CypherNamedElement> List<T> findAllByName(Project project, IElementType elementType, String name) {
        return findAllAndFilter(project, elementType, (element) -> name.equals(element.getName()));
    }

    public static <T extends CypherNamedElement> List<T> findAllByName(PsiFile file, IElementType elementType, String name) {
        return findAllAndFilter(file, elementType, (element) -> name.equals(element.getName()));
    }

    public static <T extends CypherNamedElement> List<T> findAllByName(PsiElement psiElement, IElementType elementType, String name) {
        return findAllAndFilter(psiElement, elementType, (element) -> name.equals(element.getName()));
    }

    public static <T extends CypherNamedElement> List<T> findAllAndFilter(Project project, IElementType elementType, Filter filter) {
        final Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(CypherFileType.INSTANCE, GlobalSearchScope.allScope(project));

        List<T> result = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            CypherFile cypherFile = (CypherFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cypherFile != null) {
                List<T> found = findAllAndFilter(cypherFile, elementType, filter);
                result.addAll(found);
            }
        }

        return result;
    }

    public static <T extends CypherNamedElement> List<T> findAllAndFilter(PsiFile file, IElementType elementType, Filter filter) {
        List<T> candidates = TraverseUtil.collectPsiElementsByType(file, elementType);
        return filterSameElementsFromCandidates(candidates, filter);
    }

    public static <T extends CypherNamedElement> List<T> findAllAndFilter(PsiElement psiElement, IElementType elementType, Filter filter) {
        List<T> candidates = TraverseUtil.collectPsiElementsByType(psiElement, elementType);
        return filterSameElementsFromCandidates(candidates, filter);
    }

    public interface Filter {
        boolean filter(CypherNamedElement element);
    }

    private static <T extends CypherNamedElement> List<T> filterSameElementsFromCandidates(List<T> candidates, Filter filter) {
        List<T> sameElements = null;
        for (T candidate : candidates) {
            if (filter.filter(candidate)) {
                if (sameElements == null) {
                    sameElements = new ArrayList<>();
                }
                sameElements.add(candidate);
            }
        }

        return sameElements != null ? sameElements : Collections.emptyList();
    }
}
