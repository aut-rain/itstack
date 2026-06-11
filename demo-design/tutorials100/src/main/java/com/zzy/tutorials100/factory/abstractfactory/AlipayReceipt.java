package com.zzy.tutorials100.factory.abstractfactory;

import java.math.BigDecimal;

/**
 * 支付宝回执 —— 抽象工厂模式中"支付宝产品族"的回执产品
 * <p>
 * 与 AlipayPayment 属于同一产品族，保证支付和回执的格式一致。
 *
 * @author zzy
 * @since 1.0.0
 */
public class AlipayReceipt implements Receipt {

    @Override
    public void generate(String orderId, BigDecimal amount) {
        System.out.println("[支付宝回执] 订单 " + orderId + " 已生成电子回执，金额: " + amount);
    }
}
