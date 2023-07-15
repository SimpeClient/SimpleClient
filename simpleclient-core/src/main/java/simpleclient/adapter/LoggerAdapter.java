package simpleclient.adapter;

public abstract class LoggerAdapter {
    public abstract void info(String message, Object... args);
    public abstract void error(String message, Object... args);
    public abstract void debug(String message, Object... args);
}