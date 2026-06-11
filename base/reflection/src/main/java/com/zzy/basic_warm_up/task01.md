## 题目 1：基础热身（⭐）

**要求：**
不直接使用 new 关键字，用反射创建以下三个类的实例并打印。

``` java
class Dog {
    public Dog() {
        System.out.println("汪汪");
    }
}

class Calculator {
    public Calculator() {
        System.out.println("计算器已启动");
    }
    
    public void add(int a, int b) {
        System.out.println(a + " + " + b + " = " + (a + b));
    }
}
```

**实现**：

1. 分别用三种方式获取 `Dog.class` 的 `Class` 对象
2. 用反射创建 `Dog` 和 `Calculator` 实例
3. 用反射调用 `Calculator.add(3,5)`