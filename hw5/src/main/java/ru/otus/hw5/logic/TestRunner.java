package ru.otus.hw5.logic;

import com.google.common.reflect.ClassPath;
import org.reflections.Reflections;
import ru.otus.hw5.annotation.After;
import ru.otus.hw5.annotation.Before;
import ru.otus.hw5.annotation.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    private static List<Method>  before = new ArrayList<>();
    private static List<TestMethod> tests = new ArrayList<>();
    private static List<Method> after  = new ArrayList<>();

    private TestRunner() {

    }


    public static void start(String clazzName) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        start(Class.forName(clazzName));
    }

    public static void start(Package pack) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, IOException {
        List<Class<? extends Object>> allClasses = getClassesFromPackage(pack);

        for (Class<?> clazz : allClasses) {
            System.out.println("==== " + clazz.getName() + " =====");
            start(Class.forName(clazz.getName()));
        }
    }

    private static Optional<Class<?>> getClass(String path) {
        try {
            return Optional.ofNullable(Class.forName(path));
        }
        catch(ClassNotFoundException ignored) {
            return Optional.empty();
        }
    }

    private static List<Class<? extends Object>> getClassesFromPackage(Package pack) throws IOException {
        ClassPath classPath = ClassPath.from(TestRunner.class.getClassLoader());
        Set<ClassPath.ClassInfo> classInfo = classPath.getTopLevelClassesRecursive(pack.getName());
        Iterator<ClassPath.ClassInfo> iterator = classInfo.iterator();

        List<Class<?>> classes = new ArrayList<>();
        while(iterator.hasNext())
        {
            ClassPath.ClassInfo ci = iterator.next();
            Optional<Class<?>> classOptional = getClass(ci.getName());
            classOptional.ifPresent(classes::add);
        }

        return classes;
    }

    public static void start(Class clazz) throws InvocationTargetException, IllegalAccessException {

        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Before annotationBefore = method.getAnnotation(Before.class);
            Test annotationTest = method.getAnnotation(Test.class);
            After annotationAfter = method.getAnnotation(After.class);

            if (annotationBefore != null) {
                before.add(method);
            }
            if (annotationTest != null) {
                tests.add(new TestMethod(method, annotationTest.priority()));
            }
            if (annotationAfter != null) {
                after.add(method);
            }
        }

//        tests.sort(Comparator.comparingInt(TestMethod::getPriority));

        Collections.sort(tests, new Comparator<TestMethod>() {
            @Override
            public int compare(TestMethod o1, TestMethod o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        if (before.size() > 1){
            throw new RuntimeException("More than one @Before annotated methods");
        }

        if (after.size() > 1) {
            throw new RuntimeException("More than one @After annotated methods");
        }

        if ( tests.size() > 0) {
            for (TestMethod tm : tests ) {
                before.get(0).invoke(o);
                tm.getMethod().invoke(o);
                after.get(0).invoke(o);
            }
        }

        clearInnerStaticLists();

    }

    private static void clearInnerStaticLists() {
        before.clear();
        tests.clear();
        after.clear();
    }

    private static class TestMethod {
        private Method method;
        private int priority;

        public TestMethod(Method method, int priority) {
            this.method = method;
            this.priority = priority;
        }

        public Method getMethod() {
            return method;
        }

        public int getPriority() {
            return priority;
        }

    }
}
