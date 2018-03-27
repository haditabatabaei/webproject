

package org.elasticsearch.common.io;

import org.apache.lucene.util.BytesRef;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.test.ESTestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.elasticsearch.common.io.Streams.copy;
import static org.elasticsearch.common.io.Streams.copyToString;
import static org.hamcrest.Matchers.equalTo;


public class StreamsTests extends ESTestCase {
    public void testCopyFromInputStream() throws IOException {
        byte[] content = "content".getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        ByteArrayOutputStream out = new ByteArrayOutputStream(content.length);
        long count = copy(in, out);

        assertThat(count, equalTo((long) content.length));
        assertThat(Arrays.equals(content, out.toByteArray()), equalTo(true));
    }

    public void testCopyFromByteArray() throws IOException {
        byte[] content = "content".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream out = new ByteArrayOutputStream(content.length);
        copy(content, out);
        assertThat(Arrays.equals(content, out.toByteArray()), equalTo(true));
    }

    public void testCopyFromReader() throws IOException {
        String content = "content";
        StringReader in = new StringReader(content);
        StringWriter out = new StringWriter();
        int count = copy(in, out);
        assertThat(content.length(), equalTo(count));
        assertThat(out.toString(), equalTo(content));
    }

    public void testCopyFromString() throws IOException {
        String content = "content";
        StringWriter out = new StringWriter();
        copy(content, out);
        assertThat(out.toString(), equalTo(content));
    }

    public void testCopyToString() throws IOException {
        String content = "content";
        StringReader in = new StringReader(content);
        String result = copyToString(in);
        assertThat(result, equalTo(content));
    }

    public void testBytesStreamInput() throws IOException {
        byte stuff[] = new byte[] { 0, 1, 2, 3 };
        BytesRef stuffRef = new BytesRef(stuff, 2, 2);
        BytesArray stuffArray = new BytesArray(stuffRef);
        StreamInput input = stuffArray.streamInput();
        assertEquals(2, input.read());
        assertEquals(3, input.read());
        assertEquals(-1, input.read());
        input.close();
    }
}
