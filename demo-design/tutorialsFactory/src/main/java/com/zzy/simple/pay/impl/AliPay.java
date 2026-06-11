package com.zzy.simple.pay.impl;

import com.zzy.simple.pay.Ipay;

public class AliPay implements Ipay {
    @Override
    public void pay(String orderId, double amount) {
        System.out.println("[支付宝] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
