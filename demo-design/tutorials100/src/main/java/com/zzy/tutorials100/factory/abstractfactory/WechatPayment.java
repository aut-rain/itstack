package com.zzy.tutorials100.factory.abstractfactory;

import java.math.BigDecimal;

/**
 * 微信支付 —— 抽象工厂模式中"微信产品族"的支付产品
 *
 * @author zzy
 * @since 1.0.0
 */
public class WechatPayment implements Payment {

    @Override
    public void pay(String orderId, BigDecimal amount) {
        System.out.println("[微信支付] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
