/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.inspection;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.database.DatabaseManagerService;
import com.albertoventurini.graphdbplugin.jetbrains.util.NameUtil;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.albertoventurini.graphdbplugin.database.api.GraphDatabaseApi;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;
import com.albertoventurini.graphdbplugin.platform.GraphConstants;
import org.jetbrains.annotations.NotNull;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.Objects;
import java.util.Optional;

public class CypherExplainWarningInspection extends LocalInspectionTool {

    private DatabaseManagerService service;

    public CypherExplainWarningInspection() {
        this.service = ServiceManager.getService(DatabaseManagerService.class);
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly,
                                          @NotNull LocalInspectionToolSession session) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                checkStatement(element, holder);
            }
        };
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                checkStatement(element, holder);
            }
        };
    }

    private void checkStatement(@NotNull PsiElement statement, @NotNull ProblemsHolder problemsHolder) {
        if (statement.getNode().getElementType() == CypherTypes.SINGLE_QUERY) {
            Optional.of(statement.getContainingFile().getName())
                    .filter(s -> s.startsWith(GraphConstants.BOUND_DATA_SOURCE_PREFIX))
                    .map(this::safeExtractDataSourceUUID)
                    .flatMap(uuid -> statement.getProject()
                            .getService(DataSourcesComponent.class)
                            .getDataSourceContainer()
                            .findDataSource(uuid))
                    .map(service::getDatabaseFor)
                    .map(api -> this.executeExplainQuery(api, statement.getText()))
                    .filter(Objects::nonNull)
                    .map(GraphQueryResult::getNotifications)
                    .filter(list -> !list.isEmpty())
                    .ifPresent(notifications -> notifications.forEach(notification -> {
                        PsiElement elementAt = Optional.ofNullable(notification.getPositionOffset())
                                .filter(position -> position > 0)
                                .map(statement::findElementAt)
                                .orElse(statement);

                        problemsHolder.registerProblem(elementAt, notification.getTitle());
                    }));
        }
    }

    private GraphQueryResult executeExplainQuery(GraphDatabaseApi api, String query) {
        try {
            return api.execute("EXPLAIN " + query);
        } catch (Neo4jException ex) {
            return null;
        }
    }

    private String safeExtractDataSourceUUID(String fileName) {
        try {
            return NameUtil.extractDataSourceUUID(fileName);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
