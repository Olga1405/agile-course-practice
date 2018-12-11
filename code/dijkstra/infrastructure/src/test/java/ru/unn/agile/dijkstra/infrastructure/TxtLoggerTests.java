package ru.unn.agile.dijkstra.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.dijkstra.viewModel.ILogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TxtLoggerTests {

    private static final String FILENAMELOG = "./TxtLoggerTests.log";
    private static final String ONE_MESSAGE = "One log message";
    private static final String TWO_MESSAGE = "Two log message";
    private static final String FILE = "File ";
    private static final String NOT_FOUND = " wasn't found!";

    private ILogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = LoggerFactory.getLogger(FILENAMELOG);
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
        File f = new File(FILENAMELOG);

        assertTrue(f.exists());
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAMELOG));
        } catch (FileNotFoundException e) {
            fail(FILE + FILENAMELOG + NOT_FOUND);
        }
    }

    @Test
    public void canWriteOneLogMessage() {
        String message = ONE_MESSAGE;
        txtLogger.log(message);

        int logMessage = txtLogger.getLog().size();

        assertEquals(1, logMessage);
    }

    @Test
    public void canWriteSomeLogMessage() {
        String[] messages = {ONE_MESSAGE, TWO_MESSAGE};
        for (String message : messages) {
            txtLogger.log(message);
        }

        int logMessage = txtLogger.getLog().size();

        assertEquals(2, logMessage);
    }

    @Test
    public void canGetLogMessage() {
        String testMessage = ONE_MESSAGE;
        txtLogger.log(testMessage);
        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(".*" + ONE_MESSAGE + "$"));
    }

    @Test
    public void canCreateLoggerNameIsIncorrect() {
        TxtLogger emptyLogger = new TxtLogger("build/logger.log");

        assertEquals(0, emptyLogger.getLog().size());
    }

    @Test
    public void canWriteFileNameIsIncorrect() {
        TxtLogger emptyLogger = new TxtLogger("build/logger.log");
        emptyLogger.log(ONE_MESSAGE);

        assertEquals(1, emptyLogger.getLog().size());
    }

    @Test(expected = Test.None.class)
    public void noThrowForIncorrectPathToFileWhenCreateFileLogger() {
        txtLogger = new TxtLogger("build/logger.log");
    }

    @Test(expected = Test.None.class)
    public void noThrowForIncorrectPathToFileWhenLogByFileLogger() {
        txtLogger = new TxtLogger("build/logger.log");
        txtLogger.log(ONE_MESSAGE);
    }

    @Test(expected = Test.None.class)
    public void noThrowForIncorrectPathToFileWhenGetLogFromFileLogger() {
        txtLogger = new TxtLogger("build/logger.log");
        txtLogger.log(ONE_MESSAGE);
        txtLogger.getLog();
    }
}
