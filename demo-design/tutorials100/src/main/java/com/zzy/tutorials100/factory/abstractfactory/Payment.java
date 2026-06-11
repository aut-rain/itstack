package com.zzy.tutorials100.factory.abstractfactory;

import java.math.BigDecimal;

/**
 * 支付接口 —— 抽象工厂模式中的"抽象产品A"角色
 * <p>
 * 与简单工厂/工厂方法中的 Payment 接口相同，
 * 但在抽象工厂中，Payment 只是产品族中的一个产品等级。
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
