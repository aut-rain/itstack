package com.zzy.tutorials100.factory.method;

import java.math.BigDecimal;

/**
 * 支付商店（客户端）—— 工厂方法模式中的"客户端"角色
 * <p>
 * ✅ 对比简单工厂：
 * <ul>
 *   <li>简单工厂：客户端传入字符串 "alipay"，工厂内部 switch 决定创建什么</li>
 *   <li>工厂方法：客户端注入具体的工厂对象，由多态决定创建什么</li>
 * </ul>
 * 客户端不再依赖字符串类型，而是依赖工厂接口的抽象。
 *
 * @author zzy
 * @since 1.0.0
 */
public class PaymentStore {

    /**
     * 持有工厂接口的引用（构造器注入）
     */
    private final PaymentFactory factory;

    /**
     * 通过构造器注入工厂，实现控制反转
     *
     * @param factory 支付工厂
     */
    public PaymentStore(PaymentFactory factory) {
        this.factory = factory;
    }

    /**
     * 发起支付
     * <p>
     * 使用工厂方法创建支付对象，客户端完全不关心具体是哪种支付方式
     *
     * @param orderId 订单号
     * @param amount  支付金额
     */
    public void orderPayment(String orderId, BigDecimal amount) {
        Payment payment = factory.createPayment();
        payment.pay(orderId, amount);
    }
}
