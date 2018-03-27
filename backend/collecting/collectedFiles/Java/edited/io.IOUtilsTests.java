

package org.elasticsearch.core.internal.io;

import org.apache.lucene.mockfile.FilterFileSystemProvider;
import org.apache.lucene.mockfile.FilterPath;
import org.elasticsearch.common.CheckedConsumer;
import org.elasticsearch.common.io.PathUtils;
import org.elasticsearch.test.ESTestCase;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class IOUtilsTests extends ESTestCase {

    public void testCloseArray() throws IOException {
        runTestClose(Function.identity(), IOUtils::close);
    }

    public void testCloseIterable() throws IOException {
        runTestClose((Function<Closeable[], List<Closeable>>) Arrays::asList, IOUtils::close);
    }

    private <T> void runTestClose(final Function<Closeable[], T> function, final CheckedConsumer<T, IOException> close) throws IOException {
        final int numberOfCloseables = randomIntBetween(0, 7);
        final Closeable[] closeables = new Closeable[numberOfCloseables];
        for (int i = 0; i < numberOfCloseables; i++) {
            closeables[i] = mock(Closeable.class);
        }
        close.accept(function.apply(closeables));
        for (int i = 0; i < numberOfCloseables; i++) {
            verify(closeables[i]).close();
            verifyNoMoreInteractions(closeables[i]);
        }
    }

    public void testCloseArrayWithIOExceptions() throws IOException {
        runTestCloseWithIOExceptions(Function.identity(), IOUtils::close);
    }

    public void testCloseIterableWithIOExceptions() throws IOException {
        runTestCloseWithIOExceptions((Function<Closeable[], List<Closeable>>) Arrays::asList, IOUtils::close);
    }

    private <T> void runTestCloseWithIOExceptions(
            final Function<Closeable[], T> function, final CheckedConsumer<T, IOException> close) throws IOException {
        final int numberOfCloseables = randomIntBetween(1, 8);
        final Closeable[] closeables = new Closeable[numberOfCloseables];
        final List<Integer> indexesThatThrow = new ArrayList<>(numberOfCloseables);
        for (int i = 0; i < numberOfCloseables - 1; i++) {
            final Closeable closeable = mock(Closeable.class);
            if (randomBoolean()) {
                indexesThatThrow.add(i);
                doThrow(new IOException(Integer.toString(i))).when(closeable).close();
            }
            closeables[i] = closeable;
        }

                final Closeable closeable = mock(Closeable.class);
        if (indexesThatThrow.isEmpty() || randomBoolean()) {
            indexesThatThrow.add(numberOfCloseables - 1);
            doThrow(new IOException(Integer.toString(numberOfCloseables - 1))).when(closeable).close();
        }
        closeables[numberOfCloseables - 1] = closeable;

        final IOException e = expectThrows(IOException.class, () -> close.accept(function.apply(closeables)));
        assertThat(e.getMessage(), equalTo(Integer.toString(indexesThatThrow.get(0))));
        assertThat(e.getSuppressed(), arrayWithSize(indexesThatThrow.size() - 1));
        for (int i = 1; i < indexesThatThrow.size(); i++) {
            assertNotNull(e.getSuppressed()[i - 1]);
            assertThat(e.getSuppressed()[i - 1].getMessage(), equalTo(Integer.toString(indexesThatThrow.get(i))));
        }
    }

    public void testDeleteFilesIgnoringExceptionsArray() throws IOException {
        runDeleteFilesIgnoringExceptionsTest(Function.identity(), IOUtils::deleteFilesIgnoringExceptions);
    }

    public void testDeleteFilesIgnoringExceptionsIterable() throws IOException {
        runDeleteFilesIgnoringExceptionsTest((Function<Path[], List<Path>>) Arrays::asList, IOUtils::deleteFilesIgnoringExceptions);
    }

    private <T> void runDeleteFilesIgnoringExceptionsTest(
            final Function<Path[], T> function, CheckedConsumer<T, IOException> deleteFilesIgnoringExceptions) throws IOException {
        final int numberOfFiles = randomIntBetween(0, 7);
        final Path[] files = new Path[numberOfFiles];
        for (int i = 0; i < numberOfFiles; i++) {
            if (randomBoolean()) {
                files[i] = createTempFile();
            } else {
                final Path temporary = createTempFile();
                files[i] = PathUtils.get(temporary.toString(), randomAlphaOfLength(8));
                Files.delete(temporary);
            }
        }
        deleteFilesIgnoringExceptions.accept(function.apply(files));
        for (int i = 0; i < numberOfFiles; i++) {
            assertFalse(files[i].toString(), Files.exists(files[i]));
        }
    }

    public void testRm() throws IOException {
        runTestRm(false);
    }

    public void testRmWithIOExceptions() throws IOException {
        runTestRm(true);
    }

    public void runTestRm(final boolean exception) throws IOException {
        final int numberOfLocations = randomIntBetween(0, 7);
        final Path[] locations = new Path[numberOfLocations];
        final List<Path> locationsThrowingException = new ArrayList<>(numberOfLocations);
        for (int i = 0; i < numberOfLocations; i++) {
            if (exception && randomBoolean()) {
                final Path location = createTempDir();
                final FileSystem fs =
                        new AccessDeniedWhileDeletingFileSystem(location.getFileSystem()).getFileSystem(URI.create("file:                final Path wrapped = new FilterPath(location, fs);
                locations[i] = wrapped.resolve(randomAlphaOfLength(8));
                Files.createDirectory(locations[i]);
                locationsThrowingException.add(locations[i]);
            } else {
                                locations[i] = createTempDir();
                Path location = locations[i];
                while (true) {
                    location = Files.createDirectory(location.resolve(randomAlphaOfLength(8)));
                    if (rarely() == false) {
                        Files.createTempFile(location, randomAlphaOfLength(8), null);
                        break;
                    }
                }
            }
        }

        if (locationsThrowingException.isEmpty()) {
            IOUtils.rm(locations);
        } else {
            final IOException e = expectThrows(IOException.class, () -> IOUtils.rm(locations));
            assertThat(e, hasToString(containsString("could not remove the following files (in the order of attempts):")));
            for (final Path locationThrowingException : locationsThrowingException) {
                assertThat(e, hasToString(containsString("access denied while trying to delete file [" + locationThrowingException + "]")));
            }
        }

        for (int i = 0; i < numberOfLocations; i++) {
            if (locationsThrowingException.contains(locations[i]) == false) {
                assertFalse(locations[i].toString(), Files.exists(locations[i]));
            }
        }
    }

    private static final class AccessDeniedWhileDeletingFileSystem extends FilterFileSystemProvider {

        
        AccessDeniedWhileDeletingFileSystem(final FileSystem delegate) {
            super("access_denied:        }

        @Override
        public void delete(final Path path) throws IOException {
            if (Files.exists(path)) {
                throw new AccessDeniedException("access denied while trying to delete file [" + path + "]");
            }
            super.delete(path);
        }

    }

    public void testFsyncDirectory() throws Exception {
        final Path path = createTempDir().toRealPath();
        final Path subPath = path.resolve(randomAlphaOfLength(8));
        Files.createDirectories(subPath);
        IOUtils.fsync(subPath, true);
            }

    public void testFsyncFile() throws IOException {
        final Path path = createTempDir().toRealPath();
        final Path subPath = path.resolve(randomAlphaOfLength(8));
        Files.createDirectories(subPath);
        final Path file = subPath.resolve(randomAlphaOfLength(8));
        try (OutputStream o = Files.newOutputStream(file)) {
            o.write("0\n".getBytes(StandardCharsets.US_ASCII));
        }
        IOUtils.fsync(file, false);
            }

}
