## 题目 3：简易 ORM（⭐⭐）

**场景：** 模拟一个极简的 ORM 框架，自动将数据库查询结果（Map）填充到 Java 对象中。

``` java
public class User {
    private String username;
    private int age;
    private String email;
}
```

**要求：**

编写一个工具方法：

``` java
public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz)
```

**调用示例：**

``` java
Map<String, Object> row = new HashMap<>();
row.put("username", "张三");
row.put("age", 25);
row.put("email", "zhangsan@example.com");

User user = mapToObject(row, User.class);
System.out.println(user.getUsername()); // 张三
```

**约束：**

- Map 的 key 与字段名完全一致
- 字段都是 private，需要 `setAccessible(true)`
- 类型转换可以简化处理（假设 Map 值的类型与字段类型一致）
