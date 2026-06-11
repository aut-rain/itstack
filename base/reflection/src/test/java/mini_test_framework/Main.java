package mini_test_framework;

import com.zzy.mini_test_framework.CalculatorTest;
import org.junit.jupiter.api.Test;

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
    public static void run(Class<?> testClass) {
        // TODO: 在这里写你的实现
    }

    @Test
    public void test_runner() {
        // TODO: 调用 run(CalculatorTest.class)
        // 预期输出：
        // 执行 testAdd
        // 执行 testSubtract
    }
}
