## 题目 5：配置驱动工厂（⭐⭐⭐⭐）

**场景：** 你的支付工厂不再硬编码类名，而是从配置文件读取。

**配置文件 `pay.properties`：**

``` properties
alipay=com.zzy.config_factory.AliPay
wechat=com.zzy.config_factory.WechatPay
```

**接口和实现类：**

``` java
public interface IPay {
    void pay(String orderId, double amount);
}

public class AliPay implements IPay {
    public void pay(String orderId, double amount) {
        System.out.println("[支付宝] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}

public class WechatPay implements IPay {
    public void pay(String orderId, double amount) {
        System.out.println("[微信] 订单 " + orderId + " 支付成功，金额: " + amount);
    }
}
```

**要求：**

实现一个工厂，根据类型字符串返回对应实例：

``` java
public class ConfigurablePayFactory {
    public static IPay create(String type) {
        // 从 properties 文件读取 class 全名
        // 用反射创建实例
    }
}
```

**调用：**

``` java
IPay pay = ConfigurablePayFactory.create("alipay");
pay.pay("ORDER_001", 100.0);
// 输出：[支付宝] 订单 ORDER_001 支付成功，金额: 100.0
```

**加分项：**

- 缓存 Class 对象，避免重复解析
- 如果配置文件中没有对应 key，抛出自定义异常
