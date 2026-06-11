package com.zzy.tutorials100.factory.method;

/**
 * 支付工厂接口 —— 工厂方法模式中的"抽象工厂"角色
 * <p>
 * 定义了创建支付对象的抽象方法，由具体的工厂子类决定创建哪种支付对象。
 * <p>
 * ✅ 对比简单工厂：新增支付方式只需新增一个产品类 + 一个工厂类，
 * <b>无需修改任何已有代码</b>，完美符合开闭原则。
 *
 * @author zzy
 * @since 1.0.0
 */
public interface PaymentFactory {

    /**
     * 创建支付对象（工厂方法）
     * <p>
     * 具体创建哪种支付对象由子类决定
     *
     * @return 支付对象
     */
    Payment createPayment();
}
