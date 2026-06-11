package com.zzy.simple;

import com.zzy.simple.pay.Ipay;
import com.zzy.simple.pay.impl.AliPay;
import com.zzy.simple.pay.impl.BankCardPay;
import com.zzy.simple.pay.impl.WeChatPay;

/**
 * 简单支付工厂 —— 简单工厂模式中的"工厂"角色
 * 核心逻辑：提供一个静态方法，根据传入的类型参数创建对应的支付对象。
 *
 * 问题：每新增一种支付方式，都需要修改此类的 switch 分支，
 * 违反了开闭原则（OCP——对扩展开放，对修改关闭。
 *
 * 例如：如果要新增"Apple Pay"，必须在这里加一个 case 分支。
 *
 * @author zzy
 * @since 1.0.0
 */
public class SimplePayFactory {
    /**
     * 创建支付对象
     *
     * @param type 支付类型
     * @return 支付对象
     */
    public static Ipay create(String type) {
        return switch (type) {
            case "alipay" -> new AliPay();
            case "wechat" -> new WeChatPay();
            case "bankcard" -> new BankCardPay();
            default -> throw new IllegalArgumentException("不支持的支付类型: " + type);
        };
    }

    public static Ipay create(Class<? extends Ipay> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建支付对象失败: " + clazz.getName(), e);
        }
    }
}
