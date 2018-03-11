package ru.otus.logic;

import java.lang.management.ManagementFactory;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {

    private static final int ARRAY_SIZE = 20_000_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        System.out.println("Starting the loop");
        while (true) {
            checkSizeOfReference();

            checkSizeOfEmptyStringFromStringPool();

            checkSizeOfEmptyString();

            checkSizeOfEmptyObject();

            checkSizeOfClassEmptyClass();

            checkSizeOfClassPrimitivesClass();

            checkSizeOfClassReferencesClass();
        }
    }

    private static void checkSizeOfReference() throws InterruptedException {
        System.out.println( "\n\nCheck size for reference\n" );

        runGarbageCollector();

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one reference: " + (usedMemoryAfter - initUsedMemory)/ARRAY_SIZE);
    }
    
    private static void checkSizeOfEmptyStringFromStringPool() throws InterruptedException {
        System.out.println( "\n\nCheck size for empty String from String pool\n" );

        runGarbageCollector();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new String(""); //String pool
        }

        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one empty String from String pool: " + (usedMemoryAfterArrayFilling - initUsedMemory)/ARRAY_SIZE);

        Thread.sleep(1000); //wait for 1 sec
    }

    private static void checkSizeOfEmptyString() throws InterruptedException {
        System.out.println( "\n\nCheck size for empty String\n" );

        runGarbageCollector();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new String(new char[0]); //without String pool
        }

        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one empty String: " + (usedMemoryAfterArrayFilling - initUsedMemory)/ARRAY_SIZE);

        Thread.sleep(1000); //wait for 1 sec
    }

    private static void checkSizeOfEmptyObject() throws InterruptedException {
        System.out.println( "\n\nCheck size for empty Object\n" );

        runGarbageCollector();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new Object();
        }

        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one empty Object: " + (usedMemoryAfterArrayFilling - initUsedMemory)/ARRAY_SIZE);

        Thread.sleep(1000); //wait for 1 sec
    }

    private static void checkSizeOfClassEmptyClass() throws InterruptedException {
        System.out.println( "\n\nCheck size for instance of EmptyClass\n" );

        runGarbageCollector();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new EmptyClass();
        }

        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one EmptyClass instance: " + (usedMemoryAfterArrayFilling - initUsedMemory)/ARRAY_SIZE);

        Thread.sleep(1000); //wait for 1 sec
    }

        private static void checkSizeOfClassPrimitivesClass() throws InterruptedException {
        System.out.println( "\n\nCheck size for instance of PrimitivesClass\n" );

        runGarbageCollector();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new PrimitivesClass();
        }

        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one PrimitivesClass instance: " + (usedMemoryAfterArrayFilling - initUsedMemory)/ARRAY_SIZE);

        Thread.sleep(1000); //wait for 1 sec
    }

        private static void checkSizeOfClassReferencesClass() throws InterruptedException {
        System.out.println( "\n\nCheck size for instance of ReferencesClass\n" );

        runGarbageCollector();

        Object[] array = new Object[ARRAY_SIZE];
        System.out.println( "Create array of references");

        Runtime runtime = Runtime.getRuntime();
        long initUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("New array of size: " + array.length + " created");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new ReferencesClass();
        }

        long usedMemoryAfterArrayFilling = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory for one ReferencesClass instance: " + (usedMemoryAfterArrayFilling - initUsedMemory)/ARRAY_SIZE);

        Thread.sleep(1000); //wait for 1 sec
    }

    private static void runGarbageCollector() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        System.out.println( "Garbage collector is running..." );
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
