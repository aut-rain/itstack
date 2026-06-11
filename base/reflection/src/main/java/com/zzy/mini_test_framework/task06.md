## 题目 6：手写极简测试框架（⭐⭐⭐⭐）

**场景：** 模仿 JUnit，用注解标记测试方法，然后用反射自动运行所有测试。

**提供注解：**

``` java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTest {
}
```

**测试类示例：**

``` java
public class CalculatorTest {

    @MyTest
    public void testAdd() {
        System.out.println("执行 testAdd");
    }

    @MyTest
    public void testSubtract() {
        System.out.println("执行 testSubtract");
    }

    public void notATest() {
        System.out.println("这个方法不应该被执行");
    }
}
```

**要求：**

实现 TestRunner：

``` java
public class TestRunner {
    public static void run(Class<?> testClass) {
        // 1. 创建测试类实例
        // 2. 找到所有标注了 @MyTest 的方法
        // 3. 逐个调用
    }
}
```

**调用：**

``` java
TestRunner.run(CalculatorTest.class);
```

**预期输出：**

``` text
执行 testAdd
执行 testSubtract
```

**进阶要求：**

- 收集运行结果（成功/失败/异常）
- 如果某个测试方法抛异常，捕获后继续运行其他测试
- 最后打印统计：共运行 2 个测试，通过 2 个，失败 0 个
