package app;

import dispatcher.FanOutDispatcher;
import sink.*;
import transform.*;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        FanOutDispatcher dispatcher = new FanOutDispatcher();

        List<Sink> sinks = List.of(
                new RestSink(),
                new GrpcSink(),
                new QueueSink(),
                new DbSink()
        );

        List<Transformer> transformers = List.of(
                new JsonTransformer(),   // REST
                new JsonTransformer(),   // gRPC
                new XmlTransformer(),    // Queue
                record -> record         // DB (no transform)
        );

        try (BufferedReader reader =
             Files.newBufferedReader(Path.of("sample-data/input.txt"))) {

    String line;
    while ((line = reader.readLine()) != null) {
        dispatcher.dispatch(line, sinks, transformers);
    }
}

        dispatcher.shutdown();
    }
}
