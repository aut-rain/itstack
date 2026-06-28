# Java 异常处理（Exception）

> 程序运行时出了错怎么办——捕获它、往外抛、还是包成更合适的异常。这是 Java 工程里天天打交道的事。

---

## 一、异常体系：一张图看懂

所有异常和错误的"祖宗"都是 `Throwable`，它分两支：

```
Throwable
  ├── Error            → JVM 级别的严重故障（OutOfMemoryError、StackOverflowError）
  │                      不是代码能处理的，不用 catch
  └── Exception        → 你需要关心的
       ├── RuntimeException（非受检 / Unchecked）
       │     ├── NullPointerException        空指针
       │     ├── IllegalArgumentException    非法参数
       │     ├── ArithmeticException         算术错误（除以 0）
       │     └── ClassCastException          类型转换
       └── 其他 Exception（受检 / Checked）
             ├── IOException                 文件 / 网络 IO
             ├── SQLException                数据库
             └── 一堆反射异常                NoSuchMethodException 等
```

两个关键点：

- **Error 别管**：`OutOfMemoryError` 这种，catch 住也救不回来，让它崩。
- **Exception 才是你处理的范围**，又分两小支——下面单独讲。

---

## 二、受检 vs 非受检（Checked vs Unchecked）

|  | 受检异常 Checked | 非受检异常 Unchecked |
|---|---|---|
| 是谁 | `Exception` 的子类，但**不是** `RuntimeException` 的子类 | `RuntimeException` 及其子类 |
| 编译器 | ✅ **强制**处理（try-catch 或 throws），不处理编译不过 | ❌ 不强制，运行时才报 |
| 代表 | `IOException`、`SQLException`、反射异常 | `NullPointerException`、`IllegalArgumentException` |
| 含义 | "外部环境可能出问题，你得想好怎么办"（文件可能不存在） | "你的代码逻辑有 bug"（不该传 null） |

```java
// 受检异常 —— 不处理直接编译报错
public void read() throws IOException {            // ① 声明 throws
    var fis = new FileInputStream("a.txt");
}

public void read2() {
    try {                                           // ② 或者 try-catch
        var fis = new FileInputStream("a.txt");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// 非受检异常 —— 编译器不管你，运行时才炸
public int div(int a, int b) {
    return a / b;   // b=0 时抛 ArithmeticException，但编译完全正常
}
```

### 那到底该用哪种？

**现代 Java 开发的共识：优先用非受检异常（RuntimeException）。**

原因：

- 受检异常会"污染"方法签名——每层调用都得 `throws` 或 `try-catch`，代码被异常声明淹没。
- 实际工程里，底层异常往往无法在中间层处理，只能一路往上抛，受检异常反而成了负担。
- 主流框架都这么干：JDBC 的 `SQLException`（受检）被 Spring 包装成 `DataAccessException`（非受检）。

所以**自定义异常时，默认继承 `RuntimeException`**（见第六节）。

---

## 三、抛出异常：throw vs throws

这俩长得像，是最容易混的一对。记住：**throws 是"声明"，throw 是"动手"**。

|  | `throws` | `throw` |
|---|---|---|
| 位置 | 方法签名上 | 方法体内 |
| 作用 | 告诉调用者"我可能抛这些异常" | 真正抛出一个异常对象 |
| 后面跟 | 异常类名（可多个，逗号隔开） | 一个异常对象（`new` 出来的） |
| 谁用 | 抛受检异常**必须**写（除非 try-catch） | 想抛的时候随时用 |

```java
// throws：方法签名声明"我可能抛 Exception"
public static IPay create(String type) throws Exception { ... }

// throw：方法体内真正抛一个异常对象
throw new Exception("没有找到对应的类: " + type);
```

---

## 四、捕获异常：try-catch-finally

### 1. 基本 try-catch

```java
try {
    clazz.getDeclaredConstructor().newInstance();
} catch (NoSuchMethodException e) {
    // 精确捕获：找不到无参构造器
    System.out.println("找不到无参构造器: " + e.getMessage());
} catch (Exception e) {
    // 兜底：其他所有异常
    System.out.println("其他异常: " + e.getMessage());
}
```

> ⚠️ **catch 的顺序：子类在前，父类在后**。`Exception` 是父类，写前面会导致后面的 `NoSuchMethodException` 永远捕获不到（编译器会报 "exception has already been caught"）。

### 2. multi-catch：一次捕获多种异常

几种异常处理方式相同时，用 `|` 合并：

```java
try {
    // ...
} catch (NoSuchMethodException | NoSuchFieldException e) {
    // e 的类型按它们共同的父类处理
    log.error("反射找不到成员", e);
}
```

### 3. finally：无论如何都执行

`finally` 块**一定会执行**（不管 try 是正常结束、抛异常、还是 return），用来做清理（关资源、释放锁）：

```java
FileInputStream fis = null;
try {
    fis = new FileInputStream("a.txt");
    // 读数据...
} catch (IOException e) {
    e.printStackTrace();
} finally {
    // 不管上面成没成功，这里都会执行
    if (fis != null) {
        try { fis.close(); } catch (IOException ignored) {}
    }
}
```

> ⚠️ **一个坑**：finally 里如果也 `return`，会**覆盖** try 里的返回值，还会**吞掉** try 里没抛完的异常。所以**千万别在 finally 里 return**。

这种"手动关资源"的写法又长又容易漏，Java 7 给了更好的方案 ↓

---

## 五、try-with-resources：自动关资源（推荐）

任何实现了 `AutoCloseable` 的资源（`InputStream`、`FileInputStream`、`Connection` 等），放进 `try(...)` 里，**用完自动关闭**，即使中间抛异常也会关。

```java
// 改造前：手动关，容易忘
Properties props = new Properties();
InputStream in = Main.class.getResourceAsStream("/pay.properties");
props.load(in);     // ⚠️ 如果这里抛异常，in 永远关不掉 → 资源泄漏
in.close();         // 还得记得写这行

// 改造后：try-with-resources，自动关闭
Properties props = new Properties();
try (InputStream in = Main.class.getResourceAsStream("/pay.properties")) {
    props.load(in);
}   // ← 走出 try 块，in.close() 自动调用，异常也会关
```

你 `config_factory` 里读 `pay.properties` 就属于"忘了关"——读完流没关。改成 try-with-resources 就对了。

> 多个资源用分号隔开：`try (var a = ...; var b = ...) { }`，关闭顺序是**声明的逆序**。

---

## 六、自定义异常

task05 的加分项提到"抛出自定义异常"，做法是：**继承 `RuntimeException`（推荐）或 `Exception`**，加几个构造器。

```java
// 1. 定义一个语义化的业务异常
public class PayConfigNotFoundException extends RuntimeException {

    public PayConfigNotFoundException(String message) {
        super(message);
    }

    public PayConfigNotFoundException(String message, Throwable cause) {
        super(message, cause);     // 保留原始异常，见第七节
    }
}
```

用到工厂里，替换掉原来"抛个万能 Exception"的含糊写法：

```java
// 改造前：抛一个万能 Exception，调用方根本不知道是什么错
throw new Exception("没有找到对应的类: " + type);

// 改造后：抛语义化的自定义异常，调用方一眼就懂
throw new PayConfigNotFoundException("没有找到对应的支付配置: " + type);
```

好处：

- **类型即语义**：`catch (PayConfigNotFoundException e)` 比 `catch (Exception e)` 清晰得多。
- **不受检**：继承 `RuntimeException` 不用到处 `throws`。

---

## 七、异常链：保留原始原因（cause）

底层异常往往要包成上层能理解的业务异常，但**别把原始异常丢了**——否则排错时看不到根因。做法：把原异常作为 `cause` 传进去。

你在反射工厂里其实已经写过这个模式：

```java
public static Ipay create(Class<? extends Ipay> clazz) {
    try {
        return (Ipay) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
        //                              ↓ 把 e 作为 cause 传进去，别丢
        throw new RuntimeException("创建支付失败: " + clazz.getName(), e);
    }
}
```

这样外层异常的 `getCause()` 就能拿到原始的 `e`，堆栈里会显示 `Caused by: ...`，一眼看到根因。**捕获再抛新异常时，养成带上 cause 的习惯。**

---

## 八、反射与测试中的异常

### InvocationTargetException：方法内部抛的异常被包了一层

反射调用方法时，如果方法**内部**抛了异常，不会被原始类型抛出来，而是被包在 `InvocationTargetException` 里：

```java
try {
    method.invoke(object);   // 反射调用
} catch (InvocationTargetException e) {
    // 真正的异常藏在 e.getCause() 里，不在 e 本身
    System.out.println("方法内部抛出: " + e.getCause());
}
```

比如你题6 的 `testFail()` 里 `assert false` 抛出的 `AssertionError`，就是这样被包住的——所以要拿 `getCause()` 才看得到。

### 测试中如何"预期"一个异常：assertThrows

`config_factory` 的 `test_create_unknown()` 现在是这么写的：

```java
@Test
public void test_create_unknown() throws Exception {
    IPay unionpay = create("unionpay");   // ← 这里直接抛异常
    unionpay.pay("123456", 100.0);
}
```

问题：`create("unionpay")` 会抛异常，而测试方法里没接住 → JUnit 把它当成**测试失败**，而不是"预期行为"。这个测试永远是红的。

正确写法是用 `assertThrows` 声明"我预期这里抛某个异常"：

```java
import org.junit.jupiter.api.Assertions;

@Test
public void test_create_unknown() {
    // 断言：create("unionpay") 应该抛 PayConfigNotFoundException
    Assertions.assertThrows(
        PayConfigNotFoundException.class,
        () -> create("unionpay")
    );
    // 真抛了 → 测试通过；没抛 → 测试失败
}
```

### 常见受检异常速查

| 异常 | 触发场景 |
|------|---------|
| `IOException` | 文件读写、网络操作 |
| `ClassNotFoundException` | `Class.forName()` 找不到类 |
| `NoSuchMethodException` | `getDeclaredMethod()` 找不到方法 |
| `NoSuchFieldException` | `getDeclaredField()` 找不到字段 |
| `IllegalAccessException` | 访问 private 成员时没 `setAccessible(true)` |
| `InstantiationException` | 抽象类或接口无法实例化 |
| `InvocationTargetException` | 反射调用的方法**内部**抛了异常（看 `getCause()`） |

---

## 九、常见误区与最佳实践

❌ **空捕获（吞异常）**——最恶劣：

```java
try { ... } catch (Exception e) { }   // 出了错你根本不知道
```

❌ **只 printStackTrace 当处理了**——开发凑合，生产得用日志框架（SLF4J）：

```java
} catch (Exception e) {
    e.printStackTrace();   // 生产环境没人看控制台
}
```

❌ **用异常控制正常流程**——异常叫"异常"，别当 if 用：

```java
// 反例：用异常判断是否为数字
try { Integer.parseInt(s); return true; }
catch (NumberFormatException e) { return false; }

// 正例：用工具方法
return s != null && s.matches("-?\\d+");
```

✅ **异常信息带上下文**——别只抛个空消息：

```java
throw new PayConfigNotFoundException("type=" + type + ", 请检查 pay.properties");
```

✅ **捕获后转成语义化异常再抛**——底层异常翻译成业务异常（带上 cause，见第七节）。

✅ **try 块尽量小**——只包住真正会抛异常的几行，别把整个方法体塞进去。

✅ **能用 if 提前判断的，别等它抛**——`if (obj == null) throw ...` 比 `catch NullPointerException` 干净。

---

## 速查总结

| 概念 | 一句话 |
|------|--------|
| `Error` | JVM 故障，不用管 |
| 受检异常 | `Exception` 子类（非 `RuntimeException`），编译器强制处理 |
| 非受检异常 | `RuntimeException` 子类，编译器不管，现代开发优先用 |
| `throws` | 方法签名上**声明**可能抛的异常 |
| `throw` | 方法体内**真正抛**一个异常对象 |
| `finally` | 无论如何都执行，用于清理；别在里面 return |
| try-with-resources | 自动关 `AutoCloseable` 资源，优先用 |
| 自定义异常 | 继承 `RuntimeException`，加 `(message)` 和 `(message, cause)` 构造器 |
| 异常链 | 捕获再抛时用 `new XxxException(msg, e)` 保留 cause |
| `InvocationTargetException` | 反射调用的方法内部抛了异常，看 `getCause()` |
| 测试预期异常 | 用 `Assertions.assertThrows(Xxx.class, () -> {...})` |
