/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.test.integration.neo4j.tests.cypher.util;

import com.albertoventurini.graphdbplugin.jetbrains.util.NameUtil;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import com.albertoventurini.graphdbplugin.test.integration.neo4j.util.base.BaseIntegrationTest;

import java.util.Set;

public abstract class BaseInspectionTest extends BaseIntegrationTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        myFixture.enableInspections(provideInspectionClasses());
    }

    protected abstract Set<Class<? extends LocalInspectionTool>> provideInspectionClasses();

    protected void addFileAndCheck(String filePath, String fileContent) {
        PsiFile psiFile = myFixture.addFileToProject(filePath, fileContent);
        configureAndCheck(psiFile.getVirtualFile());
    }

    protected void addDataSourceFileAndCheck(String fileContent) {
        String fileName = NameUtil.createDataSourceFileName(dataSource().neo4j52());
        addFileAndCheck(fileName, fileContent);
    }

    protected void deleteFile() {
        ApplicationManager.getApplication().runWriteAction(() -> myFixture.getFile().delete());
    }

    protected void addUnavailableDataSourceFileAndCheck(String fileContent) {
        String fileName = NameUtil.createDataSourceFileName(dataSource().unavailable());
        addFileAndCheck(fileName, fileContent);
    }

    private void configureAndCheck(VirtualFile virtualFile) {
        myFixture.configureFromExistingVirtualFile(virtualFile);
        myFixture.checkHighlighting();
    }
}
