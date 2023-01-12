/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata;

import com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.elements.*;

import java.util.List;
import java.util.Optional;

public interface CypherMetadataProviderService {

    void wipeAll();

    void wipeContainer(String sourceId);

    CypherMetadataContainer getContainer(String sourceId);

    List<CypherLabelElement> getLabels();

    List<CypherRelationshipTypeElement> getRelationshipTypes();

    List<CypherPropertyKeyElement> getPropertyKeys();

    List<CypherProcedureElement> getProcedures();

    List<CypherFunctionElement> getFunctions();

    Optional<CypherLabelElement> findLabel(String labelName);

    Optional<CypherRelationshipTypeElement> findRelationshipType(String relationshipTypeName);

    Optional<CypherPropertyKeyElement> findPropertyKey(String propertyKeyName);

    Optional<CypherProcedureElement> findProcedure(String fullName);

    List<CypherFunctionElement> findFunctions(String fullName);
}
