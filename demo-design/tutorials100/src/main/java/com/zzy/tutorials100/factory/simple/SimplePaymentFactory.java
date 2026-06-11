package com.zzy.tutorials100.factory.simple;

/**
 * 简单支付工厂 —— 简单工厂模式中的"工厂"角色
 * <p>
 * 核心逻辑：提供一个静态方法，根据传入的类型参数创建对应的支付对象。
 * <p>
 * ⚠️ 问题：每新增一种支付方式，都需要修改此类的 switch 分支，
 * 违反了<b>开闭原则（OCP）</b>——对扩展开放，对修改关闭。
 * <p>
 * 例如：如果要新增"Apple Pay"，必须在这里加一个 case 分支。
 *
 * @author zzy
 * @since 1.0.0
 */
public class SimplePaymentFactory {

    /**
     * 根据支付类型创建对应的支付对象
     *
     * @param type 支付类型："alipay"、"wechat"、"bankcard"
     * @return 对应的支付实现
     * @throws IllegalArgumentException 不支持的支付类型
     */
    public static Payment create(String type) {
        return switch (type) {
            case "alipay" -> new Alipay();
            case "wechat" -> new WechatPay();
            case "bankcard" -> new BankCardPay();
            default -> throw new IllegalArgumentException("不支持的支付类型: " + type);
        };
    }
}
