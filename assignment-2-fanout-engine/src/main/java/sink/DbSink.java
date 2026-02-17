package sink;

public class DbSink implements Sink {

    @Override
    public void send(String data) {
        System.out.println("DB UPSERT -> " + data);
    }

    @Override
    public String name() {
        return "DB";
    }
}
