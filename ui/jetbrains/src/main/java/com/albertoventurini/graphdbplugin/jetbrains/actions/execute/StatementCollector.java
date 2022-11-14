/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.jetbrains.actions.execute;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiErrorElement;
import com.intellij.util.messages.MessageBus;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.event.QueryParametersRetrievalErrorEvent;
import com.albertoventurini.graphdbplugin.jetbrains.ui.console.params.ParametersService;
import com.albertoventurini.graphdbplugin.language.cypher.psi.CypherTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatementCollector extends PsiElementVisitor {

    private final ParametersService parameterService;
    private final MessageBus messageBus;

    private boolean hasErrors;
    private List<String> queries = new ArrayList<>();
    private Map<String, Object> parameters = new HashMap<>();

    public StatementCollector(MessageBus messageBus, ParametersService parameterService) {
        this.parameterService = parameterService;
        this.messageBus = messageBus;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public List<String> getQueries() {
        return queries;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public void visitErrorElement(PsiErrorElement element) {
        fail();
    }

    @Override
    public void visitElement(PsiElement element) {
        if (hasErrors) {
            return;
        }

        if (element.getNode().getElementType() == CypherTypes.STATEMENT_ITEM) {
            queries.add(element.getText());
            try {
                parameters.putAll(parameterService.getParameters(element));
            } catch (Exception e) {
                fail();
                sendParametersRetrievalErrorEvent(e);
            }
        }
        element.acceptChildren(this);
    }

    private void sendParametersRetrievalErrorEvent(Exception exception) {
        QueryParametersRetrievalErrorEvent event = messageBus
            .syncPublisher(QueryParametersRetrievalErrorEvent.QUERY_PARAMETERS_RETRIEVAL_ERROR_EVENT_TOPIC);
        event.handleError(exception, null);
        exception.printStackTrace();
    }

    private void fail() {
        hasErrors = true;
        parameters.clear();
        queries.clear();
    }
}
