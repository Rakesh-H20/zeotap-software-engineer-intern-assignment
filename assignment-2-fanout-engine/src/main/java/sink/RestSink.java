package sink;

public class RestSink implements Sink {

    @Override
    public void send(String data) {
        System.out.println("REST -> " + data);
    }

    @Override
    public String name() {
        return "REST";
    }
}
