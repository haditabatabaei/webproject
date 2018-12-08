

package org.elasticsearch.index;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.test.hamcrest.ElasticsearchAssertions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.containsString;

public class IndexRequestBuilderIT extends ESIntegTestCase {
    public void testSetSource() throws InterruptedException, ExecutionException {
        createIndex("test");
        Map<String, Object> map = new HashMap<>();
        map.put("test_field", "foobar");
        IndexRequestBuilder[] builders = new IndexRequestBuilder[] {
                client().prepareIndex("test", "test").setSource((Object)"test_field", (Object)"foobar"),
                client().prepareIndex("test", "test").setSource("{\"test_field\" : \"foobar\"}", XContentType.JSON),
                client().prepareIndex("test", "test").setSource(new BytesArray("{\"test_field\" : \"foobar\"}"), XContentType.JSON),
                client().prepareIndex("test", "test").setSource(new BytesArray("{\"test_field\" : \"foobar\"}"), XContentType.JSON),
                client().prepareIndex("test", "test")
                    .setSource(BytesReference.toBytes(new BytesArray("{\"test_field\" : \"foobar\"}")), XContentType.JSON),
                client().prepareIndex("test", "test").setSource(map)
        };
        indexRandom(true, builders);
        SearchResponse searchResponse = client().prepareSearch("test").setQuery(QueryBuilders.termQuery("test_field", "foobar")).get();
        ElasticsearchAssertions.assertHitCount(searchResponse, builders.length);
    }

    public void testOddNumberOfSourceObjects() {
        try {
            client().prepareIndex("test", "test").setSource("test_field", "foobar", new Object());
            fail ("Expected IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("The number of object passed must be even but was [3]"));
        }
    }
}