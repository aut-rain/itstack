package mini_test_framework;

import com.zzy.mini_test_framework.CalculatorTest;
import com.zzy.mini_test_framework.MyTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Main {

    /**
     * TODO: 实现极简测试框架
     * 提示：
     *   1. testClass.getDeclaredConstructor().newInstance() 创建实例
     *   2. testClass.getDeclaredMethods() 获取所有方法
     *   3. method.isAnnotationPresent(MyTest.class) 检查是否有注解
     *   4. method.setAccessible(true)
     *   5. method.invoke(instance) 调用
     *   进阶：try-catch 每个方法调用，统计成功/失败次数
     */
    public static void run(Class<?> testClass) throws Exception {
        // TODO: 在这里写你的实现
        Object object = testClass.getDeclaredConstructor().newInstance();
        Method[] methods = testClass.getDeclaredMethods();
        int success = 0;
        int failure = 0;
        for (Method method : methods) {
            if (!method.isAnnotationPresent(MyTest.class)) continue;

            method.setAccessible(true);
            try {
                method.invoke(object);
                success++;
            }catch (Exception e){
                failure++;
                e.printStackTrace();
            }
        }
        System.out.println("Success: " + success);
        System.out.println("Failure: " + failure);
    }

    @Test
    public void test_runner() throws Exception {
        // TODO: 调用 run(CalculatorTest.class)
        run(CalculatorTest.class);
        // 预期输出：
        // 执行 testAdd
        // 执行 testSubtract
    }
}
