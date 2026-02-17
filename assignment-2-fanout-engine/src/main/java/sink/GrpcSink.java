package sink;

public class GrpcSink implements Sink {

    @Override
    public void send(String data) {
        System.out.println("gRPC -> " + data);
    }

    @Override
    public String name() {
        return "gRPC";
    }
}
