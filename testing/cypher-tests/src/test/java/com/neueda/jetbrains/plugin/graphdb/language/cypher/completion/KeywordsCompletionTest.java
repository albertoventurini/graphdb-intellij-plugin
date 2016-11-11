package com.neueda.jetbrains.plugin.graphdb.language.cypher.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.neueda.jetbrains.plugin.graphdb.language.cypher.lang.CypherAtoms;
import com.neueda.jetbrains.plugin.graphdb.language.cypher.util.BaseCompletionTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KeywordsCompletionTest extends BaseCompletionTest {

    public KeywordsCompletionTest() {
        super("keywords");
    }

    public void testBuiltIn() throws Exception {
        myFixture.configureByFiles("Keywords.cyp");
        myFixture.complete(CompletionType.BASIC);
        List<String> strings = myFixture.getLookupElementStrings();
        assertThat(strings).containsAll(CypherAtoms.KEYWORDS);
    }
}