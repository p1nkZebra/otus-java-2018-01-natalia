package ru.otus.hw5.logic;

import org.junit.Assert;
import ru.otus.hw5.annotation.After;
import ru.otus.hw5.annotation.Before;
import ru.otus.hw5.annotation.Test;

public class InvestmentTest {

    private Investment investment = new Investment();

    @Before
    public void setUp() {
        System.out.println("Start test");
    }


    @After
    public void tearDown() {
        System.out.println("End test\n");
    }


    @Test(priority = 9)
    public void test_noMoney() {
        System.out.println("run test_1 priority 9");
        int initMoneyValue = 0;


        int result = investment.doHighInvestment(initMoneyValue);


        Assert.assertEquals(0, result);

    }


    @Test(priority = 2)
    public void test_lowInvestment() {
        System.out.println("run test_2 priority 2");
        int initMoneyValue = 5_000_000;


        int result = investment.doLowInvestment(initMoneyValue);


        Assert.assertEquals(6_000_000, result);
    }

    @Test
    public void test_highInvestment() {
        System.out.println("run test_3 priority default");
        int initMoneyValue = 5_000_000;


        int result = investment.doHighInvestment(initMoneyValue);


        Assert.assertEquals(7_500_000, result);
    }
}
