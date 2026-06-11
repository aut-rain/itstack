package com.zzy.tutorials100.factory.abstractfactory;

import java.math.BigDecimal;

/**
 * 微信回执 —— 抽象工厂模式中"微信产品族"的回执产品
 * <p>
 * 与 WechatPayment 属于同一产品族，保证支付和回执的格式一致。
 *
 * @author zzy
 * @since 1.0.0
 */
public class WechatReceipt implements Receipt {

    @Override
    public void generate(String orderId, BigDecimal amount) {
        System.out.println("[微信回执] 订单 " + orderId + " 已生成电子回执，金额: " + amount);
    }
}
