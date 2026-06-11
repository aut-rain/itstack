package com.zzy.tutorials100.factory.simple;

import java.math.BigDecimal;

/**
 * 支付接口 —— 简单工厂模式中的"抽象产品"角色
 * <p>
 * 所有具体的支付方式都必须实现此接口。
 * 客户端（如 PaymentStore）只依赖此接口，不依赖具体实现类。
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
