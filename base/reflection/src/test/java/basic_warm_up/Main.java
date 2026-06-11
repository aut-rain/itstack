package basic_warm_up;

import com.zzy.basic_warm_up.Dog;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;


public class Main {
    @Test
    public void test_1() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Dog.class;
        Dog dog = (Dog) clazz.getDeclaredConstructor().newInstance();

    }

    @Test
    public void test_2() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName("com.zzy.basic_warm_up.Calculator");
        Object object = clazz.getDeclaredConstructor().newInstance();
        clazz.getMethod("add", int.class, int.class).invoke(object, 1, 2);
    }
}
