## 题目 2：破解私有方法（⭐⭐）

**场景：** 假设你拿到一个第三方 JAR 包，里面的类有个私有方法你想调用，但库没提供公开接口。

``` java
public class SecretService {
    private String encrypt(String data, int key) {
        StringBuilder sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            sb.append((char)(c ^ key));
        }
        return sb.toString();
    }
}
```

**要求：**

用反射调用 `encrypt("hello", 123)` 并输出加密结果。

**提示：** `setAccessible(true)`
