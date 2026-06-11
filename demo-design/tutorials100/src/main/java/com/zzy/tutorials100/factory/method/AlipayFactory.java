package com.zzy.tutorials100.factory.method;

/**
 * 支付宝支付工厂 —— 工厂方法模式中的"具体工厂"角色
 * <p>
 * 每种支付方式对应一个工厂，只负责创建自己的产品。
 *
 * @author zzy
 * @since 1.0.0
 */
public class AlipayFactory implements PaymentFactory {

    @Override
    public Payment createPayment() {
        return new Alipay();
    }
}
