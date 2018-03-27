
package com.alibaba.dubbo.common.io;

import org.junit.Test;

import java.io.InputStream;
import java.io.PushbackInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StreamUtilsTest {

    @Test
    public void testMarkSupportedInputStream() throws Exception {
        InputStream is = StreamUtilsTest.class.getResourceAsStream("/StreamUtilsTest.txt");
        assertEquals(10, is.available());

        is = new PushbackInputStream(is);
        assertEquals(10, is.available());
        assertFalse(is.markSupported());

        is = StreamUtils.markSupportedInputStream(is);
        assertEquals(10, is.available());

        is.mark(0);
        assertEquals((int) '0', is.read());
        assertEquals((int) '1', is.read());

        is.reset();
        assertEquals((int) '0', is.read());
        assertEquals((int) '1', is.read());
        assertEquals((int) '2', is.read());

        is.mark(0);
        assertEquals((int) '3', is.read());
        assertEquals((int) '4', is.read());
        assertEquals((int) '5', is.read());

        is.reset();
        assertEquals((int) '3', is.read());
        assertEquals((int) '4', is.read());

        is.mark(0);
        assertEquals((int) '5', is.read());
        assertEquals((int) '6', is.read());

        is.reset();
        assertEquals((int) '5', is.read());
        assertEquals((int) '6', is.read());
        assertEquals((int) '7', is.read());
        assertEquals((int) '8', is.read());
        assertEquals((int) '9', is.read());
        assertEquals(-1, is.read());
        assertEquals(-1, is.read());

        is.mark(0);
        assertEquals(-1, is.read());
        assertEquals(-1, is.read());

        is.reset();
        assertEquals(-1, is.read());
        assertEquals(-1, is.read());
    }
}