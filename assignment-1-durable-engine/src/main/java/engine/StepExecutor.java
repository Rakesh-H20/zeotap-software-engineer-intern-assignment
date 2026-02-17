package engine;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.Callable;

public class StepExecutor {

    private final DurableContext context;
    private final StepRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public StepExecutor(DurableContext context, StepRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    public <T> T step(String stepId, Callable<T> fn) {

        String stepKey = stepId + "_" + context.nextSequence();

        try {
            StepRecord existing = repository.find(context.getWorkflowId(), stepKey);
            if (existing != null && "SUCCESS".equals(existing.status)) {
                return (T) mapper.readValue(existing.output, Object.class);
            }

            repository.insertInProgress(context.getWorkflowId(), stepKey);

            T result = fn.call();

            repository.markSuccess(
                    context.getWorkflowId(),
                    stepKey,
                    mapper.writeValueAsString(result)
            );

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

