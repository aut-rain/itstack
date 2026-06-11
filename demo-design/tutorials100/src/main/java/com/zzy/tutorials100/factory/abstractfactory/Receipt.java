package com.zzy.tutorials100.factory.abstractfactory;

import java.math.BigDecimal;

/**
 * 回执接口 —— 抽象工厂模式中的"抽象产品B"角色
 * <p>
 * 抽象工厂的核心特点：一个工厂可以创建<b>多个相关联的产品</b>。
 * Payment（支付）和 Receipt（回执）属于同一个产品族，
 * 例如"支付宝产品族"包含 AlipayPayment + AlipayReceipt。
 *
 * @author zzy
 * @since 1.0.0
 */
public interface Receipt {

    /**
     * 生成支付回执
     *
     * @param orderId 订单号
     * @param amount  支付金额
     */
    void generate(String orderId, BigDecimal amount);
}
