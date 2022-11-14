/**
 * Copied and adapted from plugin
 * <a href="https://github.com/neueda/jetbrains-plugin-graph-database-support">Graph Database Support</a>
 * by Neueda Technologies, Ltd.
 * Modified by Alberto Venturini, 2022
 */
package com.albertoventurini.graphdbplugin.language.cypher.completion.metadata.atoms;

import com.google.common.base.Enums;
import org.jetbrains.annotations.NotNull;

public interface CypherType {

    static CypherType parse(@NotNull String type) {
        if (type.startsWith("LIST")) {
            if (!type.contains("OF")) {
                return CypherList.of(CypherSimpleType.ANY);
            }
            String param = type.split("OF")[1]
                    .replaceAll("\\?", "")
                    .trim();

            return CypherList.of(Enums.getIfPresent(CypherSimpleType.class, param).or(CypherSimpleType.ANY));
        }

        return Enums.getIfPresent(CypherSimpleType.class, type.replaceAll("\\?", "").trim())
                .or(CypherSimpleType.ANY);
    }

    default boolean isAssignableTo(CypherType t) {
        if (this == CypherSimpleType.NULL || t == CypherSimpleType.ANY || this == CypherSimpleType.ANY || this.equals(t)) {
            return true;
        }

        if ((t == CypherSimpleType.NUMBER || t == CypherSimpleType.FLOAT) && (this == CypherSimpleType.INTEGER || this == CypherSimpleType.FLOAT)) {
            return true;
        }

        return this instanceof CypherList && t instanceof CypherList;
    }

}
