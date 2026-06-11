package com.zzy.tutorials100.factory.simple;

import java.math.BigDecimal;

/**
 * 支付商店（客户端）—— 简单工厂模式中的"客户端"角色
 * <p>
 * 客户端只需要知道工厂和产品接口，不需要知道具体创建了哪个产品类。
 * 但客户端仍然需要传入类型字符串（如 "alipay"），与工厂耦合。
 *
 * @author zzy
 * @since 1.0.0
 */
public class PaymentStore {

    /**
     * 发起支付
     *
     * @param orderId 订单号
     * @param amount  支付金额
     * @param payType 支付类型
     */
    public void orderPayment(String orderId, BigDecimal amount, String payType) {
        // 通过工厂获取支付对象，客户端不直接 new 具体类
        Payment payment = SimplePaymentFactory.create(payType);
        payment.pay(orderId, amount);
    }
}
