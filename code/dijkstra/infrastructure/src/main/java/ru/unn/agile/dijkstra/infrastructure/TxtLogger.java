package ru.unn.agile.dijkstra.infrastructure;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import ru.unn.agile.dijkstra.viewModel.ILogger;

public class TxtLogger  implements ILogger {

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writerLog;
    private final String filename;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (IOException  e) {
            e.printStackTrace();
        }
        writerLog = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writerLog.write(now() + " > " + s);
            writerLog.newLine();
            writerLog.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader readerLog;
        ArrayList<String> log = new ArrayList<String>();
        try {
            readerLog = new BufferedReader(new FileReader(filename));
            String line = readerLog.readLine();

            while (line != null) {
                log.add(line);
                line = readerLog.readLine();
            }
        } catch (IOException  e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
