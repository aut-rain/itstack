package com.zzy.tutorials100.factory.abstractfactory;

import java.math.BigDecimal;

/**
 * 支付宝支付 —— 抽象工厂模式中"支付宝产品族"的支付产品
 *
 * @author zzy
 * @since 1.0.0
 */
public class AlipayPayment implements Payment {

    @Override
    public void pay(String orderId, BigDecimal amount) {
        System.out.println("[支付宝] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
