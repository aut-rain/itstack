package com.zzy.tutorials100.factory.method;

import java.math.BigDecimal;

/**
 * 支付接口 —— 工厂方法模式中的"抽象产品"角色
 *
 * @author zzy
 * @since 1.0.0
 */
public interface Payment {

    /**
     * 执行支付
     *
     * @param orderId 订单号
     * @param amount  支付金额
     */
    void pay(String orderId, BigDecimal amount);
}
