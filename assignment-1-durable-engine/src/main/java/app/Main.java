package app;

import engine.*;
import examples.onboarding.EmployeeOnboardingWorkflow;

public class Main {

    public static void main(String[] args) throws Exception {

        String workflowId = "employee-onboarding-1";

        DurableContext context = new DurableContext(workflowId);
        StepRepository repository = new StepRepository();

        // Zombie step cleanup
        repository.cleanupZombieSteps();

        StepExecutor executor = new StepExecutor(context, repository);

        EmployeeOnboardingWorkflow.run(context, executor);

        if (args.length > 0 && args[0].equals("crash")) {
            System.out.println("Simulating crash...");
            System.exit(1);
        }
    }
}
