package sink;

public class QueueSink implements Sink {

    @Override
    public void send(String data) {
        System.out.println("QUEUE -> " + data);
    }

    @Override
    public String name() {
        return "QUEUE";
    }
}
