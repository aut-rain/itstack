package simple_orm;

import com.zzy.simple_orm.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    /**
     * TODO: 实现这个工具方法
     * 提示：
     *   1. clazz.getDeclaredConstructor().newInstance() 创建对象
     *   2. 遍历 map 的 keySet
     *   3. clazz.getDeclaredField(key) 获取字段
     *   4. field.setAccessible(true)
     *   5. field.set(instance, map.get(key))
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // TODO: 在这里写你的实现
         T t = clazz.getDeclaredConstructor().newInstance();

         for (String key : map.keySet()){
             Field declaredField = clazz.getDeclaredField(key);
             declaredField.setAccessible(true);
             declaredField.set(t, map.get(key));
         }
        return t;
    }

    @Test
    public void test_mapToObject() throws Exception {
        Map<String, Object> row = new HashMap<>();
        row.put("username", "张三");
        row.put("age", 25);
        row.put("email", "zhangsan@example.com");

        User user = mapToObject(row, User.class);

        // 验证
        // TODO: 打印 user 的字段值，确认填充成功
        System.out.println(user);
    }
}
