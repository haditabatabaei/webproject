

package com.google.common.io;

import static com.google.common.io.SourceSinkFactory.CharSinkFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import junit.framework.TestSuite;


@AndroidIncompatible public class CharSinkTester extends SourceSinkTester<CharSink, String, CharSinkFactory> {

  private static final ImmutableList<Method> testMethods = getTestMethods(CharSinkTester.class);

  static TestSuite tests(String name, CharSinkFactory factory) {
    TestSuite suite = new TestSuite(name);
    for (Entry<String, String> entry : TEST_STRINGS.entrySet()) {
      String desc = entry.getKey();
      TestSuite stringSuite = suiteForString(name, factory, entry.getValue(), desc);
      suite.addTest(stringSuite);
    }
    return suite;
  }

  static TestSuite suiteForString(
      String name, CharSinkFactory factory, String string, String desc) {
    TestSuite stringSuite = new TestSuite(name + " [" + desc + "]");
    for (final Method method : testMethods) {
      stringSuite.addTest(new CharSinkTester(factory, string, name, desc, method));
    }
    return stringSuite;
  }

  private final ImmutableList<String> lines;
  private final ImmutableList<String> expectedLines;

  private CharSink sink;

  public CharSinkTester(
      CharSinkFactory factory, String string, String suiteName, String caseDesc, Method method) {
    super(factory, string, suiteName, caseDesc, method);
    this.lines = getLines(string);
    this.expectedLines = getLines(expected);
  }

  @Override
  protected void setUp() throws Exception {
    this.sink = factory.createSink();
  }

  public void testOpenStream() throws IOException {
    Writer writer = sink.openStream();
    try {
      writer.write(data);
    } finally {
      writer.close();
    }

    assertContainsExpectedString();
  }

  public void testOpenBufferedStream() throws IOException {
    Writer writer = sink.openBufferedStream();
    try {
      writer.write(data);
    } finally {
      writer.close();
    }

    assertContainsExpectedString();
  }

  public void testWrite() throws IOException {
    sink.write(data);

    assertContainsExpectedString();
  }

  public void testWriteLines_systemDefaultSeparator() throws IOException {
    String separator = System.getProperty("line.separator");
    sink.writeLines(lines);

    assertContainsExpectedLines(separator);
  }

  public void testWriteLines_specificSeparator() throws IOException {
    String separator = "\r\n";
    sink.writeLines(lines, separator);

    assertContainsExpectedLines(separator);
  }

  private void assertContainsExpectedString() throws IOException {
    assertEquals(expected, factory.getSinkContents());
  }

  private void assertContainsExpectedLines(String separator) throws IOException {
    String expected = expectedLines.isEmpty() ? "" : Joiner.on(separator).join(expectedLines);
    if (!lines.isEmpty()) {
            expected += separator;
    }
    assertEquals(expected, factory.getSinkContents());
  }
}