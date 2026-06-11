package com.zzy.config_factory;

public class AliPay implements IPay {
    @Override
    public void pay(String orderId, double amount) {
        System.out.println("[支付宝] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
