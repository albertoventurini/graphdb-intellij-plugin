/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.ui.console;

import com.albertoventurini.graphdbplugin.database.api.query.GraphQueryResult;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.OpenTabEvent;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryPlanEvent;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.graph.GraphPanel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.log.LogPanel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.params.ParametersPanel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.plan.QueryPlanPanel;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.status.ExecutionStatusBarWidget;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.table.TablePanel;
import com.albertoventurini.graphdbplugin.platform.GraphConstants;
import com.albertoventurini.graphdbplugin.platform.GraphConstants.ToolWindow.Tabs;
import com.albertoventurini.graphdbplugin.visualization.services.LookAndFeelService;
import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.JBTabsPaneImpl;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.impl.JBTabsImpl;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoField.*;

public class GraphConsoleView implements Disposable {

    public static final String PROFILE_PLAN_TITLE = "Profile";
    public static final String EXPLAIN_PLAN_TITLE = "Explain";
    private boolean initialized;

    private ExecutionStatusBarWidget executionStatusBarWidget;
    @NotNull private JPanel consoleToolWindowContent;
    private JBTabsPaneImpl consoleTabsPane;
    @NotNull private JBTabsImpl consoleTabs;

    // Graph
    @NotNull private JPanel consoleToolbarPanel;
    @NotNull private JPanel graphCanvas;
    @NotNull private JBScrollPane entityDetailsScrollPane;
    @NotNull private Tree entityDetailsTree;

    // Table
    @NotNull private JBScrollPane tableScrollPane;
    @NotNull private JBTable tableExecuteResults;
    private JPanel entityDetailsScrollContent;
    @NotNull private JPanel logTab;
    @NotNull private JPanel graphTab;
    @NotNull private JPanel parametersTab;
    @NotNull private JBTabbedPane defaultTabContainer;
    private JBSplitter graphSplitter;

    private LookAndFeelService lookAndFeelService;

    private TablePanel tablePanel;
    private GraphPanel graphPanel;
    private LogPanel logPanel;
    private ParametersPanel parametersPanel;

    private static final DateTimeFormatter QUERY_PLAN_TIME_FORMAT = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();

    public GraphConsoleView() {
        initialized = false;

        tablePanel = new TablePanel();
        graphPanel = new GraphPanel();
        logPanel = new LogPanel();
        parametersPanel = new ParametersPanel();

        lookAndFeelService = ApplicationManager.getApplication().getService(LookAndFeelService.class);

        consoleToolWindowContent = new JPanel(new GridLayout());

        entityDetailsScrollPane = new JBScrollPane();
        logTab = new JPanel(new GridLayout());
        graphTab = new JPanel(new GridLayout());
        entityDetailsTree = new Tree();
        graphCanvas = new JPanel();


        defaultTabContainer = new JBTabbedPane();
        entityDetailsScrollContent = new JPanel();
        consoleToolbarPanel = new JPanel();

        // Wire up the Table components
        tableScrollPane = new JBScrollPane();
        tableExecuteResults = new JBTable();
        tableScrollPane.setViewportView(tableExecuteResults);

        parametersTab = new JPanel(new BorderLayout());
    }

    public void initToolWindow(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        if (!initialized) {
            updateLookAndFeel();
            //initializeWidgets(project);
            initializeUiComponents(project);

            // Hide standard tabs
            //defaultTabContainer.setVisible(false);

            // Tabs
            consoleTabs = new JBTabsImpl(project);
            consoleTabs.setFirstTabOffset(0);
            consoleTabs.addTab(new TabInfo(logTab)
                .setText(Tabs.LOG));
            consoleTabs.addTab(new TabInfo(graphTab)
                .setText(Tabs.GRAPH));
            consoleTabs.addTab(new TabInfo(tableScrollPane)
                .setText(Tabs.TABLE));
            consoleTabs.addTab(new TabInfo(parametersTab)
                .setText(Tabs.PARAMETERS));
            consoleTabs.setSelectionChangeHandler((info, requestFocus, doChangeSelection) -> {
                ActionCallback callback = doChangeSelection.run();
                graphPanel.resetPan();
                return callback;
            });

            project.getMessageBus().connect().subscribe(OpenTabEvent.OPEN_TAB_TOPIC, this::selectTab);

            AtomicInteger tabId = new AtomicInteger(0);
            project.getMessageBus().connect().subscribe(QueryPlanEvent.QUERY_PLAN_EVENT,
                    (QueryPlanEvent) (query, result) -> createNewQueryPlanTab(query, result, tabId.incrementAndGet()));

            // Actions
            final ActionGroup consoleActionGroup = (ActionGroup)
                    ActionManager.getInstance().getAction(GraphConstants.Actions.CONSOLE_ACTIONS);
            ActionToolbar consoleToolbar = ActionManager.getInstance()
                    .createActionToolbar(GraphConstants.ToolWindow.CONSOLE_TOOL_WINDOW, consoleActionGroup, false);
            consoleToolbarPanel.add(consoleToolbar.getComponent(), BorderLayout.CENTER);
            consoleToolbarPanel.setBorder(new CustomLineBorder(0, 0, 0, 1));
            consoleToolbarPanel.validate();

            ContentFactory contentFactory = ContentFactory.getInstance();
            Content content = contentFactory.createContent(consoleToolWindowContent, "", false);

            consoleToolWindowContent.add(consoleTabs);
            toolWindow.getContentManager().addContent(content);

            initialized = true;
        }
    }

    private void selectTab(String name) {
        for (TabInfo tab : consoleTabs.getTabs()) {
            if (name.equals(tab.getText())) {
                consoleTabs.select(tab, true);
                return;
            }
        }

        throw new IllegalArgumentException("No tab found with name: " + name);
    }

    private void createUIComponents() {
        graphCanvas = new JPanel(new GridLayout(0, 1));
        consoleTabsPane = new JBTabsPaneImpl(null, SwingConstants.TOP, this);
        consoleTabs = (JBTabsImpl) consoleTabsPane.getTabs();

        consoleTabs.addTabMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (UIUtil.isCloseClick(e, MouseEvent.MOUSE_RELEASED)) {
                    final TabInfo info = consoleTabs.findInfo(e);
                    if (info != null) {
                        String tabTitle = info.getText();
                        if (tabTitle.startsWith(PROFILE_PLAN_TITLE) || tabTitle.startsWith(EXPLAIN_PLAN_TITLE)) {
                            IdeEventQueue.getInstance().blockNextEvents(e);
                            consoleTabs.removeTab(info);
                        }
                    }
                }
            }
        });
    }

    private void updateLookAndFeel() {
        tableScrollPane.setBorder(JBUI.Borders.empty());
        entityDetailsScrollPane.setBorder(JBUI.Borders.empty());
        logTab.setBorder(JBUI.Borders.empty());
        graphTab.setBorder(JBUI.Borders.empty());
        parametersTab.setBorder(JBUI.Borders.empty());
    }

    private void initializeUiComponents(@NotNull final Project project) {
        graphPanel.initialize(this, project);
        tablePanel.initialize(this, project);
        logPanel.initialize(this, project);
        parametersPanel.initialize(this, project);
    }

//    private void initializeWidgets(Project project) {
//        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
//        executionStatusBarWidget = new ExecutionStatusBarWidget(project.getMessageBus());
//        statusBar.addWidget(executionStatusBarWidget, "before Position");
//    }

    private void createNewQueryPlanTab(String originalQuery,
                                       GraphQueryResult result, int tabId) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 3));

        QueryPlanPanel qpPanel = new QueryPlanPanel(originalQuery, result);
        qpPanel.initialize(panel);

        TabInfo tabInfo = new TabInfo(panel);
        DefaultActionGroup tabActions = new DefaultActionGroup(new QueryPlanPanel.CloseTab() {
            @Override
            public void actionPerformed(AnActionEvent e) {
                super.actionPerformed(e);
                consoleTabs.removeTab(tabInfo);
            }
        });
        tabInfo.setTabLabelActions(tabActions, ActionPlaces.EDITOR_TAB);

        String planType = result.isProfilePlan() ? PROFILE_PLAN_TITLE : EXPLAIN_PLAN_TITLE;
        consoleTabs.addTab(tabInfo.setText(String.format("%1s %2d - %3s", planType, tabId,
                LocalDateTime.now().format(QUERY_PLAN_TIME_FORMAT))));
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public LookAndFeelService getLookAndFeelService() {
        return lookAndFeelService;
    }

    @NotNull public JPanel getGraphCanvas() {
        return graphCanvas;
    }

    public Tree getEntityDetailsTree() {
        return entityDetailsTree;
    }

    public @NotNull JBTable getTableExecuteResults() {
        return tableExecuteResults;
    }

    public @NotNull JPanel getLogTab() {
        return logTab;
    }

    @Override
    public void dispose() {
    }

    public JPanel getParametersTab() {
        return parametersTab;
    }
}
