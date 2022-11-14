/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.base.BaseIntegrationTest;

import static java.util.Collections.singletonList;

public abstract class BaseFormattingTest extends BaseIntegrationTest {
    protected void doTest(String actual, String expected) {
        PsiFile file = myFixture.addFileToProject("test.cypher", actual);
        myFixture.configureFromExistingVirtualFile(file.getVirtualFile());

        WriteCommandAction.runWriteCommandAction(getProject(), () -> {
            PsiFile f = myFixture.getFile();
            CodeStyleManager.getInstance(getProject())
                    .reformatText(f, singletonList(f.getTextRange()));
        });

        myFixture.checkResult(expected);
    }
}
