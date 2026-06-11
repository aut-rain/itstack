package com.zzy.tutorials100.factory.abstractfactory;

/**
 * 微信工厂 —— 抽象工厂模式中的"具体工厂"角色
 * <p>
 * 负责创建"微信产品族"的所有产品：
 * WechatPayment（支付）+ WechatReceipt（回执）
 * <p>
 * 保证两个产品属于同一族，风格一致。
 *
 * @author zzy
 * @since 1.0.0
 */
public class WechatFactory implements PaymentFactory {

    @Override
    public Payment createPayment() {
        return new WechatPayment();
    }

    @Override
    public Receipt createReceipt() {
        return new WechatReceipt();
    }
}
