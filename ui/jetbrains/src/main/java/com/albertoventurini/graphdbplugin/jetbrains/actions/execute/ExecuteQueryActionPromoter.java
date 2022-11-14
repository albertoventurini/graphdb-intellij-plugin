/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

import com.intellij.openapi.actionSystem.ActionPromoter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.albertoventurini.graphdbplugin.platform.SupportedLanguage.isSupported;

public class ExecuteQueryActionPromoter implements ActionPromoter {

    @Override
    public List<AnAction> promote(List<? extends AnAction> actions, DataContext context) {
        PsiFile psiFile = PlatformDataKeys.PSI_FILE.getData(context);
        if (psiFile != null) {
            String languageId = psiFile.getLanguage().getID();
            if (isSupported(languageId)) {
                return checkForExecuteQueryAction(actions);
            }
        }

        return Collections.emptyList();
    }

    private ArrayList<AnAction> checkForExecuteQueryAction(List<? extends AnAction> actions) {
        ArrayList<AnAction> selectedActions = new ArrayList<>();

        for (AnAction action : actions) {
            if (action instanceof ExecuteQueryAction) {
                selectedActions.add(action);
            }
        }
        return selectedActions;
    }
}
