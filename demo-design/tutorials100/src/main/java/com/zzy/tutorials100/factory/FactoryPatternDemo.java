package com.zzy.tutorials100.factory;

import com.zzy.tutorials100.factory.abstractfactory.AlipayFactory;
import com.zzy.tutorials100.factory.abstractfactory.Receipt;
import com.zzy.tutorials100.factory.method.BankCardPayFactory;
import com.zzy.tutorials100.factory.method.PaymentFactory;
import com.zzy.tutorials100.factory.simple.SimplePaymentFactory;

import java.math.BigDecimal;

/**
 * 工厂模式演示入口
 * <p>
 * 依次演示三种工厂模式的使用方式和输出结果。
 * 可以直接在 IDE 中运行此类的 main 方法查看效果。
 *
 * @author zzy
 * @since 1.0.0
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("         工厂模式（Factory Pattern）演示");
        System.out.println("=".repeat(50));

        demoSimpleFactory();
        demoFactoryMethod();
        demoAbstractFactory();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("         演示结束");
        System.out.println("=".repeat(50));
    }

    /**
     * 演示一：简单工厂模式
     * <p>
     * 特点：一个工厂类，通过传入类型字符串创建不同产品。
     * 缺点：新增产品需要修改工厂类的 switch 分支（违反开闭原则）。
     */
    private static void demoSimpleFactory() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("【一】简单工厂模式（Simple Factory）");
        System.out.println("─".repeat(50));

        com.zzy.tutorials100.factory.simple.PaymentStore store =
                new com.zzy.tutorials100.factory.simple.PaymentStore();

        // 客户端传入类型字符串，工厂内部决定创建哪种支付对象
        store.orderPayment("ORDER_001", new BigDecimal("100.00"), "alipay");
        store.orderPayment("ORDER_002", new BigDecimal("200.00"), "wechat");
        store.orderPayment("ORDER_003", new BigDecimal("300.00"), "bankcard");

        System.out.println("💡 要点：客户端通过 SimplePaymentFactory.create(\"alipay\") 获取对象");
        System.out.println("⚠️ 问题：新增支付方式需要修改工厂的 switch 分支 → 违反开闭原则");
    }

    /**
     * 演示二：工厂方法模式
     * <p>
     * 特点：每种产品有对应的工厂类，通过注入不同工厂来创建不同产品。
     * 优点：新增产品只需新增产品类 + 工厂类，无需修改已有代码。
     */
    private static void demoFactoryMethod() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("【二】工厂方法模式（Factory Method）");
        System.out.println("─".repeat(50));

        // 通过注入不同的工厂，创建不同的支付对象
        PaymentFactory[] factories = {
                new com.zzy.tutorials100.factory.method.AlipayFactory(),
                new com.zzy.tutorials100.factory.method.WechatPayFactory(),
                new BankCardPayFactory()
        };

        String[] orderIds = {"ORDER_004", "ORDER_005", "ORDER_006"};
        BigDecimal[] amounts = {
                new BigDecimal("400.00"),
                new BigDecimal("500.00"),
                new BigDecimal("600.00")
        };

        for (int i = 0; i < factories.length; i++) {
            com.zzy.tutorials100.factory.method.PaymentStore store =
                    new com.zzy.tutorials100.factory.method.PaymentStore(factories[i]);
            store.orderPayment(orderIds[i], amounts[i]);
        }

        System.out.println("💡 要点：客户端注入 PaymentFactory 接口，由多态决定创建哪种对象");
        System.out.println("✅ 优点：新增支付方式只需新增 2 个类（产品+工厂），无需修改已有代码");
    }

    /**
     * 演示三：抽象工厂模式
     * <p>
     * 特点：一个工厂可以创建一族相关联的产品（Payment + Receipt）。
     * 优点：保证同一产品族的产品能配合工作。
     */
    private static void demoAbstractFactory() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("【三】抽象工厂模式（Abstract Factory）");
        System.out.println("─".repeat(50));

        // 支付宝产品族
        System.out.println("\n--- 支付宝产品族 ---");
        com.zzy.tutorials100.factory.abstractfactory.PaymentFactory alipayFactory =
                new AlipayFactory();

        com.zzy.tutorials100.factory.abstractfactory.Payment alipayPayment =
                alipayFactory.createPayment();
        Receipt alipayReceipt = alipayFactory.createReceipt();

        alipayPayment.pay("ORDER_007", new BigDecimal("700.00"));
        alipayReceipt.generate("ORDER_007", new BigDecimal("700.00"));

        // 微信产品族
        System.out.println("\n--- 微信产品族 ---");
        com.zzy.tutorials100.factory.abstractfactory.PaymentFactory wechatFactory =
                new com.zzy.tutorials100.factory.abstractfactory.WechatFactory();

        com.zzy.tutorials100.factory.abstractfactory.Payment wechatPayment =
                wechatFactory.createPayment();
        Receipt wechatReceipt = wechatFactory.createReceipt();

        wechatPayment.pay("ORDER_008", new BigDecimal("800.00"));
        wechatReceipt.generate("ORDER_008", new BigDecimal("800.00"));

        System.out.println("\n💡 要点：一个工厂创建一族产品（Payment + Receipt），保证风格一致");
        System.out.println("✅ 优点：保证同一族产品能配合工作；新增产品族只需新增工厂");
        System.out.println("⚠️ 缺点：新增产品等级（如新增「退款」）需要修改所有工厂类");
    }
}
