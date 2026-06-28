package config_factory;

import com.zzy.config_factory.IPay;
import com.zzy.config_factory.PayConfigNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class Main {

    /**
     * TODO: 实现配置驱动工厂
     * 提示：
     *   1. 用 Properties 加载 pay.properties 文件
     *   2. 根据 type 获取全限定类名
     *   3. Class.forName(className) 加载类
     *   4. clazz.getDeclaredConstructor().newInstance() 创建实例
     *   5. 加分项：用 Map<String, Class<?>> 缓存已加载的 Class 对象
     */

    private static Properties properties = new Properties();
    private static final HashMap<String, Class<?>> cache = new HashMap<>();

    static {
        try (var in = Main.class.getResourceAsStream("/pay.properties")) {
            if (in != null) properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static IPay create(String type) {
        // TODO: 在这里写你的实现
        try {
            Class<?> clazz = getClass(type);
            return (IPay) clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new PayConfigNotFoundException("创建支付对象失败：" + type, e);
        }
    }

    private static void cacheClass(String type, Class<?> clazz) {
        cache.put(type, clazz);
    }

    private static Class<?> getClass(String type) {
        if (cache.containsKey(type)) return cache.get(type);
        String property = properties.getProperty(type);
        if (property == null) throw new PayConfigNotFoundException("没有找到对应的类: " + type);
        try {
            Class<?> clazz = Class.forName(property);
            cacheClass(type, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            throw new PayConfigNotFoundException("配置的类不存在: " + property, e);
        }
    }

    @Test
    public void test_create_alipay() {
        // TODO: 调用 create("alipay")，然后调用 pay 方法
        IPay alipay = create("alipay");
        alipay.pay("123456", 100.0);
    }

    @Test
    public void test_create_wechat() {
        // TODO: 调用 create("wechat")，然后调用 pay 方法
        IPay wechat = create("wechat");
        wechat.pay("123456", 100.0);
    }

    @Test
    public void test_create_unknown() {
        // TODO: 调用 create("unionpay")，预期抛出异常
        Assertions.assertThrows(
                PayConfigNotFoundException.class,
                () -> {
                    IPay unionpay = create("unionpay");
                    unionpay.pay("123456", 100.0);
                });
    }
}
