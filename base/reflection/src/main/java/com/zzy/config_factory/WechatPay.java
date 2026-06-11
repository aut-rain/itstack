package com.zzy.config_factory;

public class WechatPay implements IPay {
    @Override
    public void pay(String orderId, double amount) {
        System.out.println("[微信] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
