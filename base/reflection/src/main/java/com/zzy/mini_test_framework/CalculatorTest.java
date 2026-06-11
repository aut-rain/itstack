package com.zzy.mini_test_framework;

/**
 * 测试示例类 —— 供 TestRunner 扫描运行
 */
public class CalculatorTest {

    @MyTest
    public void testAdd() {
        System.out.println("执行 testAdd");
    }

    @MyTest
    public void testSubtract() {
        System.out.println("执行 testSubtract");
    }

    @MyTest
    public void testFail() {
        System.out.println("执行 testFail");
        assert false;
    }

    public void notATest() {
        System.out.println("这个方法不应该被执行");
    }
}
