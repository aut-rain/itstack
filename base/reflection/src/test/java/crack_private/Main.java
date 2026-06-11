package crack_private;

import com.zzy.crack_private.SecretService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Main {

    @Test
    public void test_encrypt() throws Exception {
        // TODO: 用反射调用 SecretService 的私有方法 encrypt("hello", 123)
        // 提示：getDeclaredMethod → setAccessible(true) → invoke
        Class<?> clazz = SecretService.class;
        SecretService secretService = (SecretService)clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getDeclaredMethod("encrypt", String.class, int.class);
        method.setAccessible(true);
        System.out.println(method.invoke(secretService, "hello", 123));
    }
}
