package com.dream.study01.proxy;

public class Cash implements Payment{

    @Override
    public void pay(int amount){
        System.out.println(amount + "현금 결제");
    }
}
