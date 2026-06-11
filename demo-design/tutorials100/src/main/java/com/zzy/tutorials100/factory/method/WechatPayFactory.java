package com.zzy.tutorials100.factory.method;

/**
 * 微信支付工厂 —— 工厂方法模式中的"具体工厂"角色
 *
 * @author zzy
 * @since 1.0.0
 */
public class WechatPayFactory implements PaymentFactory {

    @Override
    public Payment createPayment() {
        return new WechatPay();
    }
}
