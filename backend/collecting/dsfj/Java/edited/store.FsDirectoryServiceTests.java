
package org.elasticsearch.index.store;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FileSwitchDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.SleepingLockWrapper;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.IndexModule;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.shard.ShardPath;
import org.elasticsearch.test.ESTestCase;
import org.elasticsearch.test.IndexSettingsModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FsDirectoryServiceTests extends ESTestCase {

    public void testPreload() throws IOException {
        doTestPreload();
        doTestPreload("nvd", "dvd", "tim");
        doTestPreload("*");
    }

    private void doTestPreload(String...preload) throws IOException {
        Settings build = Settings.builder()
                .put(IndexModule.INDEX_STORE_TYPE_SETTING.getKey(), "mmapfs")
                .putList(IndexModule.INDEX_STORE_PRE_LOAD_SETTING.getKey(), preload)
                .build();
        IndexSettings settings = IndexSettingsModule.newIndexSettings("foo", build);
        IndexStore store = new IndexStore(settings);
        Path tempDir = createTempDir().resolve(settings.getUUID()).resolve("0");
        Files.createDirectories(tempDir);
        ShardPath path = new ShardPath(false, tempDir, tempDir, new ShardId(settings.getIndex(), 0));
        FsDirectoryService fsDirectoryService = new FsDirectoryService(settings, store, path);
        Directory directory = fsDirectoryService.newDirectory();
        assertFalse(directory instanceof SleepingLockWrapper);
        if (preload.length == 0) {
            assertTrue(directory.toString(), directory instanceof MMapDirectory);
            assertFalse(((MMapDirectory) directory).getPreload());
        } else if (Arrays.asList(preload).contains("*")) {
            assertTrue(directory.toString(), directory instanceof MMapDirectory);
            assertTrue(((MMapDirectory) directory).getPreload());
        } else {
            assertTrue(directory.toString(), directory instanceof FileSwitchDirectory);
            FileSwitchDirectory fsd = (FileSwitchDirectory) directory;
            assertTrue(fsd.getPrimaryDir() instanceof MMapDirectory);
            assertTrue(((MMapDirectory) fsd.getPrimaryDir()).getPreload());
            assertTrue(fsd.getSecondaryDir() instanceof MMapDirectory);
            assertFalse(((MMapDirectory) fsd.getSecondaryDir()).getPreload());
        }
    }
}