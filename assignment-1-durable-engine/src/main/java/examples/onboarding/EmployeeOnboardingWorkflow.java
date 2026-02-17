package examples.onboarding;

import engine.DurableContext;
import engine.StepExecutor;

import java.util.concurrent.CompletableFuture;

public class EmployeeOnboardingWorkflow {

    public static void run(DurableContext ctx, StepExecutor step) {

        step.step("create-employee", () -> {
            System.out.println("Employee record created");
            return "EMP-001";
        });

        CompletableFuture<Void> laptop =
                CompletableFuture.runAsync(() ->
                        step.step("provision-laptop", () -> {
                            System.out.println("Laptop provisioned");
                            return "LAPTOP_OK";
                        })
                );

        CompletableFuture<Void> access =
                CompletableFuture.runAsync(() ->
                        step.step("provision-access", () -> {
                            System.out.println("Access granted");
                            return "ACCESS_OK";
                        })
                );

        CompletableFuture.allOf(laptop, access).join();

        step.step("send-email", () -> {
            System.out.println("Welcome email sent");
            return "EMAIL_OK";
        });
    }
}
