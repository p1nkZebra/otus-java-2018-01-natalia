package ru.otus.logic;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {

    private static final int ARRAY_SIZE = 20_000_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        System.out.println("Starting the loop");
        while (true) {

            System.out.println("\n\nCheck size for \'empty reference\'\n");
            checkSize(() -> null);

            System.out.println("\n\nCheck size for empty String from String pool\n");
            checkSize(() -> new String(""));

            System.out.println("\n\nCheck size for empty String\n");
            checkSize(() -> new String(new char[0]));

            System.out.println("\n\nCheck size for empty Object\n");
            checkSize(() -> new Object());

            System.out.println("\n\nCheck size for instance of EmptyClass\n");
            checkSize(() -> new EmptyClass());

            System.out.println("\n\nCheck size for instance of PrimitivesClass\n");
            checkSize(() -> new PrimitivesClass());

            System.out.println("\n\nCheck size for instance of ReferencesClass\n");
            checkSize(() -> new ReferencesClass());



            System.out.println("\n\nCheck size for container int[0]\n");
            checkSize(() -> new int[0]);

            System.out.println("\n\nCheck size for container int[1]\n");
            checkSize(() -> new int[1]);

            System.out.println("\n\nCheck size for container int[10]\n");
            checkSize(() -> new int[10]);

            System.out.println("\n\nCheck size for container int[50]\n");
            checkSize(() -> new int[50]);

            System.out.println("\n\nCheck size for container int[100]\n");
            checkSize(() -> new int[100]);

            System.out.println("\n\nCheck size for container int[500]\n");
            checkSize(() -> new int[500]);

            System.out.println("\n\nCheck size for container int[1000]\n");
            checkSize(() -> new int[1000]);

        }
    }

    private static void checkSize(Supplier<Object> supplier) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Object[] array = new Object[ARRAY_SIZE];

        runGarbageCollector();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = supplier.get();
        }

        runGarbageCollector();
        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory for element: " + (usedMemoryAfterArrayFilling - initUsedMemory) / ARRAY_SIZE);


        //any use for array
        Object[] array2 = new Object[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array2[i] = array[i];
        }
        Thread.sleep(1000);
    }

    private static void runGarbageCollector() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        System.out.println("Garbage collector is running...");
    }

    private static class PrimitivesClass {
        private long id = 0;
    }

    private static class ReferencesClass {
        private long id = 0;
        private EmptyClass ref = new EmptyClass();
    }

    private static class EmptyClass {
    }
}
