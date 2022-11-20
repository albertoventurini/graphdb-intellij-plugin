package com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.metadata;

import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.DataSourceType;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourceMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.metadata.DataSourcesComponentMetadata;
import com.albertoventurini.graphdbplugin.jetbrains.component.datasource.state.impl.DataSourceV1;
import com.albertoventurini.graphdbplugin.jetbrains.ui.datasource.tree.RootTreeNodeModel;
import com.intellij.testFramework.ServiceContainerUtil;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class DataSourceMetadataUpdateServiceTest extends LightJavaCodeInsightFixtureTestCase {

    private DataSourceMetadataUpdateService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        final var metadataComponent = mock(DataSourcesComponentMetadata.class);
        final var treeUpdaters = mock(DataSourceTreeUpdaters.class);

        ServiceContainerUtil.replaceService(
                getProject(), DataSourcesComponentMetadata.class, metadataComponent, getTestRootDisposable());
        ServiceContainerUtil.replaceService(
                getProject(), DataSourceTreeUpdaters.class, treeUpdaters, getTestRootDisposable());

        final var dataSourceMetadata = mock(DataSourceMetadata.class);
        when(metadataComponent.getMetadata(any()))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(dataSourceMetadata)));

        final var treeUpdater = mock(DataSourceTreeUpdater.class);
        when(treeUpdaters.get(DataSourceType.NEO4J_BOLT))
                .thenReturn(Optional.of(treeUpdater));

        service = getProject().getService(DataSourceMetadataUpdateService.class);
    }

    @Test
    public void updateDataSourceMetadataUi_withKnownDataSource_returnsTrue() throws Exception {
        final var root = new PatchedDefaultMutableTreeNode(RootTreeNodeModel.ROOT_NAME);
        final var dataSourceApi =
                new DataSourceV1("uuid", "local", DataSourceType.NEO4J_BOLT, new HashMap<>());

        final var future = service.updateDataSourceMetadataUi(root, dataSourceApi);

        final var result = future.get();

        assertTrue(result);
    }

    @Test
    public void updateDataSourceMetadataUi_withUnknownDataSource_returnsTrue() throws Exception {
        final var root = new PatchedDefaultMutableTreeNode(RootTreeNodeModel.ROOT_NAME);
        final var dataSourceApi =
                new DataSourceV1("uuid", "local", DataSourceType.UNKNOWN, new HashMap<>());

        final var future = service.updateDataSourceMetadataUi(root, dataSourceApi);

        final var result = future.get();

        assertFalse(result);
    }

}
