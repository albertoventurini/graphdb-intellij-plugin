/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions.neo4j.bolt;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourcesComponent;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.DataSourceApi;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.AsyncProcessIcon;
import com.albertoventurini.graphdbplugin.database.neo4j.bolt.Neo4jBoltConfiguration;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.DataSourcesView;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.interactions.DataSourceDialog;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static com.albertoventurini.graphdbplugin.jetbrains.util.Validation.*;

public class Neo4jBoltDataSourceDialog extends DataSourceDialog {
    private final DataSourcesComponent dataSourcesComponent;
    private DataSourceApi dataSourceToEdit;

    private JPanel content;
    private JBTextField dataSourceNameField;
    private JComboBox<String> protocolComboBox;
    private JBTextField hostField;
    private JBTextField portField;
    private JBTextField userField;
    private JBTextField databaseField;
    private JBPasswordField passwordField;
    private JButton testConnectionButton;
    private JPanel loadingPanel;
    private AsyncProcessIcon loadingIcon;
    private JComboBox<String> authTypeComboBox;

    public Neo4jBoltDataSourceDialog(
            @NotNull final Project project,
            @NotNull final DataSourcesView dataSourcesView,
            @NotNull final DataSourceApi dataSourceToEdit) {
        this(project, dataSourcesView);
        this.dataSourceToEdit = dataSourceToEdit;
    }

    public Neo4jBoltDataSourceDialog(
            @NotNull final Project project,
            @NotNull final DataSourcesView dataSourcesView) {
        super(project, dataSourcesView);
        loadingPanel.setVisible(false);
        dataSourcesComponent = dataSourcesView.getComponent();
        testConnectionButton.addActionListener(e -> validationPopup());
        authTypeComboBox.addActionListener(this::handleAuthTypeChanged);
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (StringUtils.isBlank(dataSourceNameField.getText())) {
            return validation("Data source name must not be empty", dataSourceNameField);
        }
        if (StringUtils.isBlank(hostField.getText())) {
            return validation("Host must not be empty", hostField);
        }
        if (!StringUtils.isNumeric(portField.getText())) {
            return validation("Port must be numeric", portField);
        }

        final var data = extractData();

        if (dataSourcesComponent.getDataSourceContainer().isDataSourceExists(data.dataSourceName)) {
            if (!(dataSourceToEdit != null && dataSourceToEdit.getName().equals(data.dataSourceName))) {
                return validation(String.format("Data source [%s] already exists", data.dataSourceName), dataSourceNameField);
            }
        }

        return null;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        if (dataSourceToEdit != null) {
            Map<String, String> conf = dataSourceToEdit.getConfiguration();
            String protocol = conf.get(Neo4jBoltConfiguration.PROTOCOL);
            String host = conf.get(Neo4jBoltConfiguration.HOST);
            String port = conf.get(Neo4jBoltConfiguration.PORT);
            String authType = conf.get(Neo4jBoltConfiguration.AUTH_TYPE);
            String database = conf.get(Neo4jBoltConfiguration.DATABASE);
            String user = conf.get(Neo4jBoltConfiguration.USER);
            String password = conf.get(Neo4jBoltConfiguration.PASSWORD);

            dataSourceNameField.setText(dataSourceToEdit.getName());

            for (int i = 0; i < protocolComboBox.getItemCount(); i++) {
                if (protocolComboBox.getItemAt(i).equals(protocol)) {
                    protocolComboBox.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < authTypeComboBox.getItemCount(); i++) {
                if (authTypeComboBox.getItemAt(i).equals(authType)) {
                    authTypeComboBox.setSelectedIndex(i);
                    break;
                }
            }

            hostField.setText(host);
            portField.setText(port);
            databaseField.setText(database);
            userField.setText(user);
            passwordField.setText(password);
        }
        return content;
    }

    private void handleAuthTypeChanged(final ActionEvent e) {
        final boolean authFieldsEnabled =
                !authTypeComboBox.getItemAt(authTypeComboBox.getSelectedIndex()).equals("No auth");

        userField.setEnabled(authFieldsEnabled);
        passwordField.setEnabled(authFieldsEnabled);

        if (!authFieldsEnabled) {
            userField.setText("");
            passwordField.setText("");
        }
    }

    @Override
    public DataSourceApi constructDataSource() {
        final var data = extractData();

        Map<String, String> configuration = new HashMap<>();
        configuration.put(Neo4jBoltConfiguration.PROTOCOL, data.protocol);
        configuration.put(Neo4jBoltConfiguration.AUTH_TYPE, data.authType);
        configuration.put(Neo4jBoltConfiguration.HOST, data.host);
        configuration.put(Neo4jBoltConfiguration.PORT, data.port);
        configuration.put(Neo4jBoltConfiguration.USER, data.user);
        configuration.put(Neo4jBoltConfiguration.PASSWORD, data.password);
        configuration.put(Neo4jBoltConfiguration.DATABASE, data.database);

        return dataSourcesComponent.getDataSourceContainer().createDataSource(
                dataSourceToEdit,
                DataSourceType.NEO4J_BOLT,
                data.dataSourceName,
                configuration
        );
    }

    @Override
    protected void showLoading() {
        testConnectionButton.setEnabled(false);
        loadingIcon.resume();
        loadingPanel.setVisible(true);
    }

    @Override
    protected void hideLoading() {
        testConnectionButton.setEnabled(true);
        loadingIcon.suspend();
        loadingPanel.setVisible(false);
    }

    private Data extractData() {
        return new Data(
                dataSourceNameField.getText(),
                protocolComboBox.getItemAt(protocolComboBox.getSelectedIndex()),
                hostField.getText(),
                portField.getText(),
                authTypeComboBox.getItemAt(authTypeComboBox.getSelectedIndex()),
                databaseField.getText(),
                userField.getText(),
                String.valueOf(passwordField.getPassword())  // TODO: use password API?
        );
    }

    private void createUIComponents() {
        loadingIcon = new AsyncProcessIcon("validateConnectionIcon");
    }

    private static final class Data {

        private final String dataSourceName;
        private final String protocol;
        private final String host;
        private final String port;
        private final String authType;
        private final String database;
        private final String user;
        private final String password;

        public Data(String dataSourceName, String protocol, String host, String port, String authType, String database, String user, String password) {
            this.dataSourceName = dataSourceName;
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.authType = authType;
            this.database = database;
            this.user = user;
            this.password = password;
        }
    }
}
