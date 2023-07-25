package simpleclient.network;

public class SimpleBackend {
    public static final BackendClient CLIENT = new BackendClient();
    public static final String URL = "localhost";
    public static final int PORT = 6969;

    public static void handleBackCommand(String command) {
    }

    public static void main(String[] args) {
        CLIENT.connect("test");
    }
}