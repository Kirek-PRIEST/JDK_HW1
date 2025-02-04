package server;

public interface ServerView {
    void setStatusText(String down);
    void startServer();
    void stopServer();
    void printToChat(String massage);
    void clearChat();
}

