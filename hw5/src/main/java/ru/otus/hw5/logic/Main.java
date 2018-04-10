package ru.otus.hw5.logic;


import ru.otus.hw5.test.InvestmentTest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        TestRunner.start(ru.otus.hw5.test.InvestmentTest.class);

        TestRunner.start(InvestmentTest.class);

        try {
            TestRunner.start(InvestmentTest.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            TestRunner.start(InvestmentTest.class.getPackage());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


    }
}
