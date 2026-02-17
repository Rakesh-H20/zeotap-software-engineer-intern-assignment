package sink;

public interface Sink {
    void send(String data) throws Exception;
    String name();
}
