package wiki.lostark.app;

public class QueueViewModel {
    private String server;
    private String queue;
    private String state;

    public QueueViewModel(String server, String queue, String state) {
        this.server = server;
        this.queue = queue;
        this.state = state;
    }
}