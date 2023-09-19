package com.narayana.jdk21;

import java.util.concurrent.Executors;

public class VirtualThreads {

    public static void main(String[] args) {
        // Virtual thread 1
        Thread.Builder.OfVirtual ofVirtual = Thread.ofVirtual();
        ofVirtual.name("virtual thread 1").start(() -> System.out.println("virtual thread"));
        // Virtual thread 2
        Thread.startVirtualThread(() -> {
            System.out.println("inside virtual thread 2");
        });
        // with executors
        int _10_000 = 10000;
        try(var exeService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < _10_000; i++) {
                exeService.submit(() -> {
                    try{
                        System.out.println("Entering into task - make external call");
                        Thread.sleep(100); // wait for some time to get response
                        System.out.println(" received response ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }


    }
}
