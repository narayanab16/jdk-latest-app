package com.narayana.jdk21;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class StructureConMain {
    public static void main(String[] args) {
        StructureConMain ref = new StructureConMain();
        try {
            Object handle = ref.handle();
            System.out.println(" handle : " + handle);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    Object handle() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Supplier<String> user  = scope.fork(() -> findUser());
            Supplier<Integer> order = scope.fork(() -> fetchOrder());

            scope.join()            // Join both subtasks
                    .throwIfFailed();  // ... and propagate errors

            // Here, both subtasks have succeeded, so compose their results
            return new String(user.get() + " - " + order.get());
        }
    }
    String findUser() {
        return "Narayana";
    }
    Integer fetchOrder() {
        return 200;
    }
}
