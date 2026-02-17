package dispatcher;

import sink.Sink;
import transform.Transformer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FanOutDispatcher {

    private final ExecutorService executor;

    public FanOutDispatcher() {
        this.executor = Executors.newFixedThreadPool(4);
    }

    public void dispatch(
            String record,
            List<Sink> sinks,
            List<Transformer> transformers
    ) {
        for (int i = 0; i < sinks.size(); i++) {
            Sink sink = sinks.get(i);
            Transformer transformer = transformers.get(i);

            executor.submit(() -> {
                int retries = 0;
                while (retries < 3) {
                    try {
                        String transformed = transformer.transform(record);
                        sink.send(transformed);
                        break;
                    } catch (Exception e) {
                        retries++;
                    }
                }
            });
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
