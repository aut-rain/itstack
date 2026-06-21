# Java 基础知识补全

> 代码块、static、异常处理、instanceof vs isAssignableFrom

---

## 一、代码块（实例初始化块）

每次 `new` 对象时都会执行，在构造器**之前**运行：

```java
public class Dog {
    // 实例代码块 —— 每次 new 都执行
    {
        System.out.println("实例代码块执行");
    }

    public Dog() {
        System.out.println("构造器执行");
    }
}
```

```java
new Dog();
new Dog();
```

输出：

```text
实例代码块执行
构造器执行
实例代码块执行
构造器执行
```

**很少用**，了解就行。实际开发中直接把逻辑写在构造器里。

---

## 二、static 代码块（类初始化块）

**类加载时执行一次，且只执行一次**，不关心你 new 多少个对象：

```java
public class ConfigFactory {
    private static Map<String, String> map = new HashMap<>();

    // static 代码块 —— 类第一次加载时执行，之后不再执行
    static {
        System.out.println("static 代码块执行，加载配置...");
        map.put("alipay", "com.zzy.AliPay");
        map.put("wechat", "com.zzy.WechatPay");
    }
}
```

```java
new ConfigFactory();  // 打印 "static 代码块执行，加载配置..."
new ConfigFactory();  // 不打印了，类已经加载过了
```

### 和普通 static 字段初始化的区别

效果一样，只是写法不同：

```java
// 写法一：直接赋值
private static Map<String, String> map = new HashMap<>();

// 写法二：static 代码块
private static Map<String, String> map;
static {
    map = new HashMap<>();
    map.put("alipay", "com.zzy.AliPay");
}
```

static 代码块适合**初始化逻辑比较复杂**的场景（比如要 try-catch、要循环赋值等）。

### 执行顺序

```java
public class Demo {
    private static int count = 0;          // ① static 字段
    static { count++; }                    // ② static 代码块
    { System.out.println("实例代码块"); }  // ③ 实例代码块
    public Demo() { System.out.println("构造器"); } // ④ 构造器
}
```

执行顺序：**① → ②**（类加载时，只一次），然后每次 new：**③ → ④**

---

## 三、static 方法

`static` 方法属于**类**，不属于对象。调用时不需要 `new`：

```java
public class MathUtil {
    // static 方法 —— 通过类名直接调用
    public static int add(int a, int b) {
        return a + b;
    }
}

// 调用：不需要 new MathUtil()
int result = MathUtil.add(3, 5);
```

### static 方法里能用什么？

```java
public class Demo {
    private static int staticVar = 10;
    private int instanceVar = 20;

    public static void staticMethod() {
        System.out.println(staticVar);     // ✅ 能访问 static 变量
        // System.out.println(instanceVar); // ❌ 不能访问实例变量
        // this.xxx;                        // ❌ 没有 this
        // instanceMethod();                // ❌ 不能直接调用实例方法
    }

    public void instanceMethod() {
        System.out.println(staticVar);     // ✅ 实例方法能访问 static
        staticMethod();                    // ✅ 实例方法能调用 static 方法
    }
}
```

**一句话：static 方法里没有 `this`，只能访问 static 的东西。**

### 典型应用场景

```java
// 1. 工厂方法（你的反射练习题5）
public static IPay create(String type) { ... }
ConfigurablePayFactory.create("alipay");  // 类名.方法名

// 2. 工具方法
public static boolean isEmpty(String str) { return str == null || str.isEmpty(); }
StringUtils.isEmpty("hello");

// 3. 程序入口
public static void main(String[] args) { ... }
```

---

## 四、异常处理

### 异常体系

```
Object
  └── Throwable
       ├── Error      → JVM 层面的严重问题（OutOfMemoryError），你不用管
       └── Exception  → 你需要处理的
            ├── RuntimeException（非受检异常）→ 空指针、数组越界等
            └── 其他 Exception（受检异常）→ 必须 try-catch 或 throws
```

### 受检 vs 非受检

| 类型 | 编译器是否强制处理 | 常见例子 |
|------|-------------------|---------|
| 受检异常（Checked） | ✅ 强制 | `IOException`、`SQLException`、反射相关异常 |
| 非受检异常（Unchecked） | ❌ 不强制 | `NullPointerException`、`ArithmeticException`、`IllegalArgumentException` |

```java
// 受检异常 —— 编译器强制你处理
public void read() throws IOException {        // 要么 throws
    FileInputStream fis = new FileInputStream("a.txt");
}

public void read2() {
    try {                                       // 要么 try-catch
        FileInputStream fis = new FileInputStream("a.txt");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// 非受检异常 —— 编译器不强制，运行时才报错
public void div(int a, int b) {
    int result = a / b;  // b=0 时抛 ArithmeticException，但编译不报错
}
```

### 三种处理方式

```java
// 方式一：throws 往上抛（测试代码常用）
@Test
public void test() throws Exception {
    clazz.getDeclaredConstructor().newInstance();
}

// 方式二：try-catch 自己处理
@Test
public void test() {
    try {
        clazz.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException e) {
        System.out.println("找不到无参构造器: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("其他异常: " + e.getMessage());
    }
}

// 方式三：try-catch 转成 RuntimeException（工厂/工具类常用）
public static IPay create(String type) {
    try {
        return (IPay) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
        throw new RuntimeException("创建失败: " + clazz.getName(), e);
    }
}
```

### InvocationTargetException 的特殊性

反射调用方法时，如果方法**内部**抛了异常，不会被原始类型抛出来，而是被包在 `InvocationTargetException` 里：

```java
try {
    method.invoke(object);   // 反射调用
} catch (InvocationTargetException e) {
    // 真正的异常藏在 e.getCause() 里
    System.out.println("方法内部抛出异常: " + e.getCause());
}
```

比如题6里 `testFail()` 中 `assert false` 抛出的 `AssertionError` 就是这样被包住的。

### 常见受检异常速查

| 异常 | 触发场景 |
|------|---------|
| `IOException` | 文件读写、网络操作 |
| `ClassNotFoundException` | `Class.forName()` 找不到类 |
| `NoSuchMethodException` | `getDeclaredMethod()` 找不到方法 |
| `NoSuchFieldException` | `getDeclaredField()` 找不到字段 |
| `IllegalAccessException` | 访问 private 成员时没 `setAccessible(true)` |
| `InstantiationException` | 抽象类或接口无法实例化 |
| `InvocationTargetException` | 反射调用的方法内部抛了异常 |

---

## 五、instanceof vs isAssignableFrom

### instanceof：判断"这个对象是不是某个类型"

```java
Dog dog = new Dog();
System.out.println(dog instanceof Dog);    // true  —— dog 是 Dog
System.out.println(dog instanceof Animal); // true  —— Dog 继承 Animal
System.out.println(dog instanceof Cat);    // false —— dog 不是 Cat
System.out.println(null instanceof Dog);   // false —— null 不是任何类型
```

**用法：** `对象 instanceof 类名`，左边是对象实例，右边是类名。

### isAssignableFrom：判断"这个类能不能接受那个类"

```java
// Ipay.class 能不能接受 AliPay？
Ipay.class.isAssignableFrom(AliPay.class);   // true  —— AliPay 实现了 Ipay
Ipay.class.isAssignableFrom(Ipay.class);     // true  —— 自己接受自己
AliPay.class.isAssignableFrom(Ipay.class);   // false —— Ipay 不是 AliPay

// Object 能不能接受任何类？
Object.class.isAssignableFrom(String.class); // true  —— 所有类都是 Object
```

**用法：** `父类.isAssignableFrom(子类)`，两边都是 Class 对象。

### 对比

| | instanceof | isAssignableFrom |
|---|---|---|
| 操作对象 | `对象` instanceof `类名` | `类A`.isAssignableFrom(`类B`) |
| 问的问题 | "这个**对象**是不是这个类型？" | "这个**类**能不能装那个**类**？" |
| 返回 false 的情况 | 对象为 null 或类型不匹配 | 类型不匹配 |
| 典型场景 | 业务代码中类型判断 | 反射中类型兼容性检查 |

### 反射中的实际用法

```java
// 检查一个 Class 对象是否实现了某个接口
Class<?> clazz = Class.forName("com.zzy.AliPay");
if (Ipay.class.isAssignableFrom(clazz)) {
    Ipay pay = (Ipay) clazz.getDeclaredConstructor().newInstance();
}

// 等价于对实例用 instanceof
Object obj = clazz.getDeclaredConstructor().newInstance();
if (obj instanceof Ipay) {
    Ipay pay = (Ipay) obj;
}
```

**区别：** `isAssignableFrom` 在没有对象实例、只有 Class 对象时使用（反射场景）。`instanceof` 在已经有对象实例时使用（普通业务场景）。

---

## 速查总结

| 概念 | 核心要点 |
|------|---------|
| 实例代码块 `{}` | 每次 new 都执行，在构造器之前，很少用 |
| static 代码块 `static {}` | 类加载时执行一次，适合做复杂初始化 |
| static 方法 | 属于类不属于对象，里面没有 `this`，只能访问 static 成员 |
| 受检异常 | 编译器强制处理（try-catch 或 throws） |
| 非受检异常 | `RuntimeException` 及其子类，编译器不强制 |
| `instanceof` | 对象 → 类，"你是这个类型吗？" |
| `isAssignableFrom` | 类 → 类，"我能装你吗？" |
