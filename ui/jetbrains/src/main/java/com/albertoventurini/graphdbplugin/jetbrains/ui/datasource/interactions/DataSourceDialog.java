/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions;

import com.albertoventurini.graphdbplugin.database.api.GraphDatabaseApi;
import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.albertoventurini.graphdbplugin.jetbrains.database.DatabaseManagerService;
import com.albertoventurini.graphdbplugin.jetbrains.services.ExecutorService;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.DataSourcesView;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static org.codehaus.plexus.util.ExceptionUtils.getCause;

public abstract class DataSourceDialog extends DialogWrapper {
    public static final int THICKNESS = 10;
    public static final int HEIGHT = 150;

    protected DataSourceDialog(@NotNull final Project project, DataSourcesView dataSourcesView) {
        super(project);
        Disposer.register(project, myDisposable);
        init();
    }

    public abstract DataSourceApi constructDataSource();

    protected abstract void showLoading();

    protected abstract void hideLoading();

    public boolean go() {
        init();
        return showAndGet();
    }

    public void validationPopup() {
        JPanel popupPanel = new JPanel(new BorderLayout());
        popupPanel.setBorder(JBUI.Borders.empty(THICKNESS));

        ValidationInfo validationInfo = doValidate();
        if (validationInfo != null) {
            JLabel connectionFailed = new JLabel("Connection failed: " + validationInfo.message,
                    JLabel.LEFT);
            popupPanel.add(connectionFailed, BorderLayout.CENTER);
            createPopup(popupPanel, getContentPanel());
        } else {
            validateConnection(popupPanel, getContentPanel());
        }
    }

    private void createPopup(JPanel popupPanel, JComponent contentPanel) {
        if (contentPanel.isShowing()) {
            JBPopupFactory.getInstance()
                    .createComponentPopupBuilder(popupPanel, getPreferredFocusedComponent())
                    .setCancelButton(new IconButton("Close", AllIcons.Actions.Close))
                    .setTitle("Test Connection")
                    .setResizable(true)
                    .setMovable(true)
                    .setCancelButton(new IconButton("Close", AllIcons.Actions.Close, AllIcons.Actions.CloseHovered))
                    .createPopup()
                    .showInCenterOf(contentPanel);
        }
    }

    private void validateConnection(
            JPanel popupPanel,
            JComponent contentPanel) {
        final var executorService = ApplicationManager.getApplication().getService(ExecutorService.class);
        showLoading();
        executorService.runInBackground(
                this::executeOkQuery,
                (status) -> connectionSuccessful(popupPanel, contentPanel),
                (exception) -> connectionFailed(exception, popupPanel, contentPanel),
                ModalityState.current()
        );
    }

    // TODO: this needs to be moved in the DataSourceDialog implementation.
    // Right now, it assumes that the data source will understand a query such as "RETURN 'ok'"
    // which might not be true for data sources different than neo4j.
    private String executeOkQuery() {
        DataSourceApi dataSource = constructDataSource();
        DatabaseManagerService databaseManager =
                ApplicationManager.getApplication().getService(DatabaseManagerService.class);
        GraphDatabaseApi db = databaseManager.getDatabaseFor(dataSource);
        GraphQueryResult result = db.execute("RETURN 'ok'");

        Object value = result.getRows().get(0).getValue(result.getColumns().get(0));

        if (value.equals("ok")) {
            return "ok";
        } else {
            throw new RuntimeException("Unexpected test query output: " + value);
        }
    }

    private void connectionSuccessful(
            JPanel popupPanel,
            JComponent contentPanel) {
        hideLoading();
        JLabel connectionSuccessful = new JLabel("Connection successful!", JLabel.LEFT);
        popupPanel.add(connectionSuccessful, BorderLayout.CENTER);

        createPopup(popupPanel, contentPanel);
    }

    private void connectionFailed(
            Exception exception,
            JPanel popupPanel,
            JComponent contentPanel) {
        hideLoading();

        JLabel connectionFailed = new JLabel("Connection failed: " +
                exception.getMessage(), JLabel.LEFT);

        JTextArea exceptionCauses = new JTextArea();
        exceptionCauses.setLineWrap(false);
        exceptionCauses.append(Optional.ofNullable(getCause(exception)).map(Throwable::toString).orElse(""));

        JBScrollPane scrollPane = new JBScrollPane(exceptionCauses);
        scrollPane.setPreferredSize(new Dimension(-1, HEIGHT));
        popupPanel.add(connectionFailed, BorderLayout.NORTH);
        popupPanel.add(scrollPane, BorderLayout.CENTER);

        createPopup(popupPanel, contentPanel);
    }
}
