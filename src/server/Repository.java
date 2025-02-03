package server;

public interface Repository {
    void loggingServer(String serverStatus);

    String getChatLog();
    void loggingChat(String massage, String userName);
}
