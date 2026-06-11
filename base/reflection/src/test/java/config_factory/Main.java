package config_factory;

import com.zzy.config_factory.IPay;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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

    private static HashMap<String, Class<?>> cache = new HashMap<>();

    public static IPay create(String type) throws Exception {
        // TODO: 在这里写你的实现
        Class<?> clazz = getClass(type);
        return (IPay) clazz.getDeclaredConstructor().newInstance();
    }

    private static void cacheClass(String type, Class<?> clazz) {
        cache.put(type, clazz);
    }

    private static Class<?> getClass(String type) throws Exception {
        if (cache.containsKey(type)) return cache.get(type);
        Properties properties = new Properties();
        properties.load(Main.class.getResourceAsStream("/pay.properties"));
        String property = properties.getProperty(type);
        if (property != null) {
            Class<?> clazz = Class.forName(property);
            cacheClass(type, clazz);
            return clazz;
        }
        throw new Exception("没有找到对应的类: " + type);
    }

    @Test
    public void test_create_alipay() throws Exception {
        // TODO: 调用 create("alipay")，然后调用 pay 方法
        IPay alipay = create("alipay");
        alipay.pay("123456", 100.0);
    }

    @Test
    public void test_create_wechat() throws Exception {
        // TODO: 调用 create("wechat")，然后调用 pay 方法
        IPay wechat = create("wechat");
        wechat.pay("123456", 100.0);
    }

    @Test
    public void test_create_unknown() throws Exception {
        // TODO: 调用 create("unionpay")，预期抛出异常
        IPay unionpay = create("unionpay");
        unionpay.pay("123456", 100.0);
    }
}
