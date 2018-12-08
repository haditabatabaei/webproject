package hudson.util.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.Issue;

public class ZipArchiverTest {

    private static final Logger LOGGER = Logger.getLogger(ZipArchiverTest.class.getName());

    private File tmpDir;

    @Before
    public void setUp() {
        try {
                        tmpDir = File.createTempFile("temp", ".dir");
            tmpDir.delete();
            tmpDir.mkdir();
        } catch (IOException e) {
            fail("unable to create temp directory", e);
        }
    }

    @After
    public void tearDown() {
        deleteDir(tmpDir);
    }

    @Issue("JENKINS-9942")
    @Test
    public void backwardsSlashesOnWindows()  {
                File tmpFile = null;
        try {
            File baz = new File(new File(new File(tmpDir, "foo"), "bar"), "baz");
            baz.mkdirs();

            tmpFile = new File(baz, "Test.txt");
            tmpFile.createNewFile();
        } catch (IOException e) {
            fail("unable to prepare source directory for zipping", e);
        }

                File zipFile = null;

                ZipArchiver archiver = null;

        try {
            zipFile = File.createTempFile("test", ".zip");
            archiver = new ZipArchiver(Files.newOutputStream(zipFile.toPath()));

            archiver.visit(tmpFile, "foo\\bar\\baz\\Test.txt");
        } catch (Exception e) {
            fail("exception driving ZipArchiver", e);
        } finally {
            if (archiver != null) {
                try {
                    archiver.close();
                } catch (IOException e) {
                                    }
            }
        }

                        String zipEntryName = null;

        ZipFile zipFileVerify = null;
        try {
            zipFileVerify = new ZipFile(zipFile);

            zipEntryName = ((ZipEntry) zipFileVerify.entries().nextElement()).getName();
        } catch (Exception e) {
            fail("failure enumerating zip entries", e);
        } finally {
            if (zipFileVerify != null) {
                try {
                    zipFileVerify.close();
                } catch (IOException e) {
                                    }
            }
        }

        assertEquals("foo/bar/baz/Test.txt", zipEntryName);
    }

    
    private final void fail(final String msg, final Throwable cause) {
        LOGGER.log(Level.SEVERE, msg, cause);
        Assert.fail(msg);
    }

    
    private final void deleteDir(final File f) {
        for (File c : f.listFiles()) {
            if (c.isDirectory()) {
                deleteDir(c);
            } else {
                c.delete();
            }
        }

        f.delete();
    }
}