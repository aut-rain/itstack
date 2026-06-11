# 反射



## 题目清单

| 题号 | 难度 | 考察点                    | 场景         | 源码包 | 测试包 |
| :--- | :--- | :------------------------ | :----------- | :----- | :----- |
| 1    | ⭐    | 获取 Class 对象、创建实例 | 基础热身     | `com.zzy.basic_warm_up` | `basic_warm_up` |
| 2    | ⭐⭐   | 调用私有方法              | 破坏封装     | `com.zzy.crack_private` | `crack_private` |
| 3    | ⭐⭐   | 读取/修改私有字段         | ORM 模拟     | `com.zzy.simple_orm` | `simple_orm` |
| 4    | ⭐⭐⭐  | 动态调用不同参数的方法    | 框架基础     | `com.zzy.universal_invoker` | `universal_invoker` |
| 5    | ⭐⭐⭐⭐ | 配置文件驱动的工厂        | 开闭原则实战 | `com.zzy.config_factory` | `config_factory` |
| 6    | ⭐⭐⭐⭐ | 简单单元测试框架          | 注解+反射    | `com.zzy.mini_test_framework` | `mini_test_framework` |

## 目录结构

```
src/
├── main/
│   ├── java/com/zzy/
│   │   ├── basic_warm_up/        # 题目1：Dog、Calculator
│   │   ├── crack_private/        # 题目2：SecretService
│   │   ├── simple_orm/           # 题目3：User
│   │   ├── universal_invoker/    # 题目4：MathUtil、Printer
│   │   ├── config_factory/       # 题目5：IPay、AliPay、WechatPay
│   │   └── mini_test_framework/  # 题目6：@MyTest、CalculatorTest
│   └── resources/
│       └── pay.properties        # 题目5：配置文件
└── test/java/
    ├── basic_warm_up/            # 题目1 答案
    ├── crack_private/            # 题目2 答案
    ├── simple_orm/               # 题目3 答案
    ├── universal_invoker/        # 题目4 答案
    ├── config_factory/           # 题目5 答案
    └── mini_test_framework/      # 题目6 答案
```
