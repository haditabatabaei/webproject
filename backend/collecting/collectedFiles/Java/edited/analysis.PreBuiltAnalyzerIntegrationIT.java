

package org.elasticsearch.indices.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.Version;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.test.InternalSettingsPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.test.VersionUtils.randomVersion;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ESIntegTestCase.ClusterScope(scope = ESIntegTestCase.Scope.SUITE)
public class PreBuiltAnalyzerIntegrationIT extends ESIntegTestCase {
    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return Arrays.asList(InternalSettingsPlugin.class);
    }

    public void testThatPreBuiltAnalyzersAreNotClosedOnIndexClose() throws Exception {
        Map<PreBuiltAnalyzers, List<Version>> loadedAnalyzers = new HashMap<>();
        List<String> indexNames = new ArrayList<>();
        final int numIndices = scaledRandomIntBetween(2, 4);
        for (int i = 0; i < numIndices; i++) {
            String indexName = randomAlphaOfLength(10).toLowerCase(Locale.ROOT);
            indexNames.add(indexName);

            int randomInt = randomInt(PreBuiltAnalyzers.values().length-1);
            PreBuiltAnalyzers preBuiltAnalyzer = PreBuiltAnalyzers.values()[randomInt];
            String name = preBuiltAnalyzer.name().toLowerCase(Locale.ROOT);

            Version randomVersion = randomVersion(random());
            if (!loadedAnalyzers.containsKey(preBuiltAnalyzer)) {
                 loadedAnalyzers.put(preBuiltAnalyzer, new ArrayList<Version>());
            }
            loadedAnalyzers.get(preBuiltAnalyzer).add(randomVersion);

            final XContentBuilder mapping = jsonBuilder().startObject()
                .startObject("type")
                    .startObject("properties")
                        .startObject("foo")
                            .field("type", "text")
                            .field("analyzer", name)
                        .endObject()
                    .endObject()
                .endObject()
                .endObject();

            Settings versionSettings = settings(randomVersion).build();
            client().admin().indices().prepareCreate(indexName).addMapping("type", mapping).setSettings(versionSettings).get();
        }

        ensureGreen();

        final int numDocs = randomIntBetween(10, 100);
                for (int i = 0; i < numDocs; i++) {
            String randomIndex = indexNames.get(randomInt(indexNames.size()-1));
            String randomId = randomInt() + "";

            Map<String, Object> data = new HashMap<>();
            data.put("foo", randomAlphaOfLength(scaledRandomIntBetween(5, 50)));

            index(randomIndex, "type", randomId, data);
        }

        refresh();

                int amountOfIndicesToClose = randomInt(numIndices-1);
        for (int i = 0; i < amountOfIndicesToClose; i++) {
            String indexName = indexNames.get(i);
            client().admin().indices().prepareClose(indexName).execute().actionGet();
        }

        ensureGreen();

                assertThatAnalyzersHaveBeenLoaded(loadedAnalyzers);

                assertLuceneAnalyzersAreNotClosed(loadedAnalyzers);
    }

    private void assertThatAnalyzersHaveBeenLoaded(Map<PreBuiltAnalyzers, List<Version>> expectedLoadedAnalyzers) {
        for (Map.Entry<PreBuiltAnalyzers, List<Version>> entry : expectedLoadedAnalyzers.entrySet()) {
            for (Version version : entry.getValue()) {
                                assertThat(entry.getKey().getCache().get(version), is(notNullValue()));
            }
        }
    }

        private void assertLuceneAnalyzersAreNotClosed(Map<PreBuiltAnalyzers, List<Version>> loadedAnalyzers) throws IOException {
        for (Map.Entry<PreBuiltAnalyzers, List<Version>> preBuiltAnalyzerEntry : loadedAnalyzers.entrySet()) {
            for (Version version : preBuiltAnalyzerEntry.getValue()) {
                Analyzer analyzer = preBuiltAnalyzerEntry.getKey().getCache().get(version);
                try (TokenStream stream = analyzer.tokenStream("foo", "bar")) {
                    stream.reset();
                    while (stream.incrementToken()) {
                    }
                    stream.end();
                }
            }
        }
    }
}
