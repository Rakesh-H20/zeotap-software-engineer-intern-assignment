package engine;

import java.util.concurrent.atomic.AtomicInteger;

public class DurableContext {

    private final String workflowId;
    private final AtomicInteger sequence = new AtomicInteger(0);

    public DurableContext(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public int nextSequence() {
        return sequence.incrementAndGet();
    }
}
