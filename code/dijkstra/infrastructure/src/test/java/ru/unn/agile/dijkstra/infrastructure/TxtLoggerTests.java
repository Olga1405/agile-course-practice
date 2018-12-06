package ru.unn.agile.dijkstra.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TxtLoggerTests {
    private static final String TXT = "./TxtLoggerTests.log";

    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(TXT);
    }

    @After
    public void tearDown() {
        txtLogger = null;
    }

    @Test
    public void isLoggerFileCreated() {
        assertNotNull(txtLogger);
    }

    @Test
    public void isLogFileExist() {
        File f = new File(TXT);

        assertTrue(f.exists());
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(TXT));
        } catch (FileNotFoundException e) {
            fail("File " + TXT + " wasn't found!");
        }
    }

    @Test
    public void canWriteOneLogMessage() {
        String message = "One log message";
        txtLogger.log(message);

        int logMessage = txtLogger.getLog().size();

        assertEquals(1, logMessage);
    }

    @Test
    public void canWriteSomeLogMessage() {
        String[] messages = {"One", "Two"};
        for (String message : messages) {
            txtLogger.log(message);
        }

        int logMessage = txtLogger.getLog().size();

        assertEquals(2, logMessage);
    }

    @Test
    public void canCreateLoggerNameIsIncorrect() {
        TxtLogger emptyLogger = new TxtLogger("build/log.log");

        assertEquals(0, emptyLogger.getLog().size());
    }

    @Test
    public void canWriteFileNameIsIncorrect() {
        TxtLogger emptyLogger = new TxtLogger("build/log.log");
        emptyLogger.log("Message");

        assertEquals(1, emptyLogger.getLog().size());
    }

    @Test
    public void canReadFileNameIsIncorrect() {
        TxtLogger emptyLogger = new TxtLogger("build/log.log");
        List<String> log = emptyLogger.getLog();

        assertEquals(0, log.size());
    }
}
