package com.zzy.tutorials100.factory.simple;

import java.math.BigDecimal;

/**
 * 微信支付 —— 简单工厂模式中的"具体产品"角色
 *
 * @author zzy
 * @since 1.0.0
 */
public class WechatPay implements Payment {

    @Override
    public void pay(String orderId, BigDecimal amount) {
        // 实际场景中会调用微信支付 SDK 发起支付
        System.out.println("[微信支付] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
