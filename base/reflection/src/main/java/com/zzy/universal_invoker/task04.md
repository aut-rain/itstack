## 题目 4：万能调用器（⭐⭐⭐）

**场景：** 写一个工具，能根据方法名字符串，动态调用任意对象的任意方法（支持重载）。

``` java
public class MathUtil {
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; }
    public int add(int a, int b, int c) { return a + b + c; }
}

public class Printer {
    public void print(String msg) { System.out.println("打印：" + msg); }
}
```

**要求：**

实现：

``` java
public static Object invokeMethod(Object target, String methodName, Object... args)
```

**调用示例：**

``` java
MathUtil math = new MathUtil();

// 调用 int add(int, int)
int r1 = (int) invokeMethod(math, "add", 10, 20);

// 调用 double add(double, double)
double r2 = (double) invokeMethod(math, "add", 10.5, 20.5);

// 调用 add(int, int, int)
int r3 = (int) invokeMethod(math, "add", 1, 2, 3);
```

**难点：**

根据 args 的类型找到匹配的方法（考虑重载和类型兼容）

可以简化：只精确匹配参数类型，不处理继承/自动装箱
