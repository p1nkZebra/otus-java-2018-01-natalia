package ru.otus.hw5.logic;

public class Investment {

    public int doLowInvestment(int initMoneyValue) {
        return initMoneyValue + initMoneyValue/100*20;
    }

    public int doHighInvestment(int initMoneyValue) {
        return initMoneyValue + initMoneyValue/100*50;
    }


}
