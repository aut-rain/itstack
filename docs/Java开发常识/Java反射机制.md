# Java 反射机制（Reflection）

> 在运行时动态获取类的信息、创建对象、调用方法、访问字段的能力。

---

## 一、为什么需要反射？

正常情况下，Java 是**静态**的——编译时就知道用什么类：

```java
AliPay pay = new AliPay();   // 编译时确定类型
pay.pay("ORDER_001", 100.0);
```

但有些场景，**编译时不知道要用哪个类**：

- 工厂模式：根据参数动态创建不同的对象
- 框架底层：Spring 根据 `@Component` 注解自动创建 Bean
- 配置驱动：从配置文件读取类名，动态加载

反射就是解决这类问题的：**让 Java 在运行时才决定用哪个类**。

---

## 二、获取 Class 对象的三种方式

反射的起点永远是拿到 `Class` 对象，它代表一个类的"图纸"。

### 方式一：`类名.class`（最常用、最安全）

```java
Class<?> clazz = AliPay.class;
```

编译期就能检查类是否存在，**推荐优先使用**。

### 方式二：`对象.getClass()`

```java
AliPay pay = new AliPay();
Class<?> clazz = pay.getClass();
```

当你已经有一个对象，想知道它属于哪个类时用。

### 方式三：`Class.forName("全限定名")`（最灵活）

```java
Class<?> clazz = Class.forName("com.zzy.simple.pay.impl.AliPay");
```

传入的是**字符串**，编译期不检查类是否存在，运行时才加载。配置文件驱动的场景必须用这个。

### 三种方式对比

| 方式 | 编译期检查 | 适用场景 |
|------|-----------|---------|
| `AliPay.class` | ✅ 安全 | 已知具体类 |
| `pay.getClass()` | ✅ 安全 | 已有对象实例 |
| `Class.forName("...")` | ❌ 运行时报错 | 配置文件、动态加载 |

---

## 三、通过反射创建对象

### 1. 调用无参构造器（最常见）

```java
Class<?> clazz = AliPay.class;

// 方式一（Java 9+ 推荐）
Ipay pay = (Ipay) clazz.getDeclaredConstructor().newInstance();

// 方式二（旧写法，已过时）
Ipay pay = (Ipay) clazz.newInstance();
```

> ⚠️ 要求目标类必须有**无参构造器**。

### 2. 调用有参构造器

假设有个类：

```java
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

反射创建：

```java
Class<?> clazz = User.class;

// 获取指定参数类型的构造器
Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);

// 传入参数创建对象
User user = (User) constructor.newInstance("张三", 25);
```

---

## 四、通过反射调用方法

```java
// 假设不知道具体类，只有 Class 对象
Class<?> clazz = AliPay.class;

// 1. 创建实例
Object instance = clazz.getDeclaredConstructor().newInstance();

// 2. 获取方法（方法名 + 参数类型）
Method method = clazz.getMethod("pay", String.class, double.class);

// 3. 调用方法（对象实例 + 参数值）
method.invoke(instance, "ORDER_001", 100.0);
// 输出: [支付宝] 订单 ORDER_001 支付成功，金额: 100.0
```

### getMethod vs getDeclaredMethod

| 方法 | 能获取的范围 |
|------|------------|
| `getMethod("pay", ...)` | **public** 方法（包括继承的） |
| `getDeclaredMethod("pay", ...)` | **所有**方法（包括 private），不包括继承的 |

如果要调用 private 方法：

```java
Method method = clazz.getDeclaredMethod("secretMethod");
method.setAccessible(true);   // 破坏访问控制，允许调用 private 方法
method.invoke(instance);
```

---

## 五、通过反射访问字段

```java
public class User {
    private String name = "张三";
}
```

```java
Class<?> clazz = User.class;
Object instance = clazz.getDeclaredConstructor().newInstance();

// 获取字段（包括 private）
Field field = clazz.getDeclaredField("name");
field.setAccessible(true);           // 破坏访问控制

// 读取字段值
String name = (String) field.get(instance);    // "张三"

// 修改字段值
field.set(instance, "李四");
```

---

## 六、实际应用：反射工厂

回到你的简单工厂，用反射改造：

### 改造前（违反开闭原则）

```java
public static Ipay create(String type) {
    return switch (type) {
        case "alipay"   -> new AliPay();       // 每新增一种就要改这里
        case "wechat"   -> new WeChatPay();
        case "bankcard" -> new BankCardPay();
        default -> throw new IllegalArgumentException("不支持的支付类型: " + type);
    };
}
```

### 改造后（遵守开闭原则）

```java
public static Ipay create(Class<? extends Ipay> clazz) {
    try {
        return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
        throw new RuntimeException("创建支付对象失败: " + clazz.getName(), e);
    }
}
```

```java
// 调用
Ipay pay = SimplePayFactory.create(AliPay.class);
pay.pay("ORDER_001", 100.0);
```

新增支付方式时，**工厂代码一行都不用改**。

---

## 七、反射的代价

| 维度 | 正常调用 | 反射调用 |
|------|---------|---------|
| 性能 | 快 | 慢（约 10-100 倍） |
| 类型安全 | 编译期检查 | 运行时才知道 |
| 代码可读性 | 清晰直观 | 不如直接调用直观 |
| 灵活性 | 低 | 高 |

### 实际建议

- **框架开发**（Spring、MyBatis）→ 必须用反射，没有替代方案
- **业务代码** → 优先用正常调用，反射是最后手段
- **工厂模式** → 如果产品种类多且经常扩展，用反射有价值
- **性能敏感场景** → 反射结果可以缓存（`Class` 对象和 `Constructor` 缓存起来复用）

---

## 八、速查表

| 需求 | 代码 |
|------|------|
| 获取 Class 对象 | `Class<?> clazz = AliPay.class;` |
| 创建对象（无参） | `clazz.getDeclaredConstructor().newInstance()` |
| 创建对象（有参） | `clazz.getDeclaredConstructor(String.class).newInstance("张三")` |
| 调用 public 方法 | `clazz.getMethod("pay", String.class, double.class).invoke(obj, args)` |
| 调用 private 方法 | `getDeclaredMethod(...)` + `setAccessible(true)` + `invoke(...)` |
| 读取 private 字段 | `getDeclaredField("name")` + `setAccessible(true)` + `get(obj)` |
| 修改 private 字段 | `setAccessible(true)` + `set(obj, value)` |
| 判断是否实现某接口 | `Ipay.class.isAssignableFrom(clazz)` |
| 判断是否是某类的实例 | `clazz.isInstance(obj)` |
