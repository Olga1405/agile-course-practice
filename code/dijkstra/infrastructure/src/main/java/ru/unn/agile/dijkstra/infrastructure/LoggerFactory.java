package ru.unn.agile.dijkstra.infrastructure;

import ru.unn.agile.dijkstra.viewModel.ILogger;

public final class LoggerFactory {
    private static ILogger logger;

    private LoggerFactory() { }

    public static ILogger getLogger(final String path) {

        if (logger == null) {
            return new TxtLogger(path);
        }
        return logger;
    }
}
