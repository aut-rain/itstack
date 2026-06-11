package com.zzy.tutorials100.factory.abstractfactory;

/**
 * 支付工厂接口 —— 抽象工厂模式中的"抽象工厂"角色
 * <p>
 * 与工厂方法的区别：
 * <ul>
 *   <li>工厂方法：一个工厂只创建<b>一种</b>产品（Payment）</li>
 *   <li>抽象工厂：一个工厂创建<b>一族</b>相关产品（Payment + Receipt）</li>
 * </ul>
 * <p>
 * 抽象工厂保证同一族的产品能配合工作。
 * 例如 AlipayFactory 创建的 AlipayPayment 和 AlipayReceipt 都属于"支付宝族"。
 *
 * @author zzy
 * @since 1.0.0
 */
public interface PaymentFactory {

    /**
     * 创建支付产品
     *
     * @return 支付对象
     */
    Payment createPayment();

    /**
     * 创建回执产品
     *
     * @return 回执对象
     */
    Receipt createReceipt();
}
