/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CypherMetadataProviderServiceImpl implements CypherMetadataProviderService {

    private final Map<String, CypherMetadataContainer> sourceData;

    public CypherMetadataProviderServiceImpl() {
        sourceData = new HashMap<>();
    }

    @Override
    public void wipeAll() {
        sourceData.clear();
    }

    @Override
    public void wipeContainer(String sourceId) {
        sourceData.remove(sourceId);
    }

    @Override
    public CypherMetadataContainer getContainer(String sourceId) {
        if (!sourceData.containsKey(sourceId)) {
            sourceData.put(sourceId, new CypherMetadataContainer());
        }
        return sourceData.get(sourceId);
    }

    @Override
    public List<CypherLabelElement> getLabels() {
        return sourceData.values().stream()
                .flatMap((container) -> container.getLabels().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<CypherRelationshipTypeElement> getRelationshipTypes() {
        return sourceData.values().stream()
                .flatMap((container) -> container.getRelationshipTypes().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<CypherPropertyKeyElement> getPropertyKeys() {
        return sourceData.values().stream()
                .flatMap((container) -> container.getPropertyKeys().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<CypherProcedureElement> getProcedures() {
        return sourceData.values().stream()
                .flatMap((container) -> container.getProcedures().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<CypherFunctionElement> getFunctions() {
        return sourceData.values().stream()
                .flatMap(container -> container.getFunctions().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CypherLabelElement> findLabel(String labelName) {
        return sourceData.values().stream()
                .flatMap((container) -> container.getLabels().stream())
                .filter((labelElement) -> labelElement.getName().equals(labelName))
                .findFirst();
    }

    @Override
    public Optional<CypherRelationshipTypeElement> findRelationshipType(String relationshipTypeName) {
        return sourceData.values().stream()
                .flatMap((container) -> container.getRelationshipTypes().stream())
                .filter((relationshipTypeElement) -> relationshipTypeElement.getName().equals(relationshipTypeName))
                .findFirst();
    }

    @Override
    public Optional<CypherPropertyKeyElement> findPropertyKey(String propertyKeyName) {
        return sourceData.values().stream()
                .flatMap((container) -> container.getPropertyKeys().stream())
                .filter((propertyKeyElement) -> propertyKeyElement.getName().equals(propertyKeyName))
                .findFirst();
    }

    @Override
    public Optional<CypherProcedureElement> findProcedure(String name) {
        return sourceData.values().stream()
                .flatMap((container) -> container.getProcedures().stream())
                .filter((procedureElement) -> procedureElement.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<CypherFunctionElement> findFunctions(String name) {
        return sourceData.values().stream()
                .flatMap(container -> container.getFunctions().stream())
                .filter(functionElement -> functionElement.getName().equals(name))
                .collect(Collectors.toList());
    }
}
