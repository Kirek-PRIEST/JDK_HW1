package server;

public interface Repository {
    void loggingServer(String serverStatus);

    String getChatLog(String userName);
    void loggingChat(String massage, String userName);
}
