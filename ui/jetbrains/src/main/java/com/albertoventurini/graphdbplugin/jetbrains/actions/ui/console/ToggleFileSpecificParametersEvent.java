/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.ui.console;

import com.intellij.util.messages.Topic;

public interface ToggleFileSpecificParametersEvent {

    Topic<ToggleFileSpecificParametersEvent> TOGGLE_FILE_SPECIFIC_PARAMETERS_EVENT_TOPIC =
            Topic.create("GraphDatabaseConsole.ToggleFileSpecificParametersTopic",
                    ToggleFileSpecificParametersEvent.class);

    void toggle(boolean setToUseFileSpecificParams);

}
