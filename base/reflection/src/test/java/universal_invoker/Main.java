package universal_invoker;

import com.zzy.universal_invoker.MathUtil;
import com.zzy.universal_invoker.Printer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class Main {

    /**
     * TODO: 实现万能调用器
     * 提示：
     *   1. 遍历 target.getClass().getMethods()
     *   2. 筛选方法名匹配的
     *   3. 比较参数个数和类型
     *   4. 调用 method.invoke(target, args)
     */
    public static Object invokeMethod(Object target, String methodName, Object... args) throws Exception {
        // TODO: 在这里写你的实现
        Class<?> clazz = target.getClass();
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(methodName)) continue;

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != args.length) continue;

            if (isMatch(parameterTypes, args)) return method.invoke(target, args);
        }

        throw new NoSuchMethodException(
                String.format("没有匹配到方法: %s.%s(%s)",
                        clazz.getSimpleName(),
                        methodName,
                        getArgsTypeString(args))
        );
    }

    private static boolean isMatch(Class<?>[] parameterTypes, Object... args) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (args[i] == null){
                if (parameterTypes[i].isPrimitive())
                    return false;
                continue;
            }
            Class<?> argType = args[i].getClass();
            Class<?> parameterType = parameterTypes[i];
            if (parameterType.isAssignableFrom(argType)){   // isAssignableFrom用于判断argType能否赋值给parameterType
                continue;
            }

            if (parameterType.isPrimitive()){
                if (!canConvertPrimitive(parameterType, argType)) return false;
            }else {
                return false;
            }
        }
        return true;
    }

    private static boolean canConvertPrimitive(Class<?> parameterType, Class<?> argType) {
        if (parameterType == int.class) return argType == Integer.class;
        if (parameterType == double.class) return argType == Double.class;
        if (parameterType == boolean.class) return argType == Boolean.class;
        if (parameterType == long.class) return argType == Long.class;
        if (parameterType == float.class) return argType == Float.class;
        if (parameterType == char.class) return argType == Character.class;
        if (parameterType == byte.class) return argType == Byte.class;
        if (parameterType == short.class) return argType == Short.class;
        return false;
    }

    private static String getArgsTypeString(Object... args) {
        if (args == null || args.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (Object arg : args){
            if (sb.length() > 0) sb.append(", ");
            sb.append(arg == null? "null" : arg.getClass().getSimpleName());
        }
        return sb.toString();
    }

    @Test
    public void test_invoke_add_int_int() throws Exception {
        MathUtil math = new MathUtil();
        // TODO: 调用 invokeMethod(math, "add", 10, 20) 并断言结果为 30
        assert (invokeMethod(math, "add", 10, 20).equals(30));
        System.out.println("OK");
    }

    @Test
    public void test_invoke_add_double_double() throws Exception {
        MathUtil math = new MathUtil();
        // TODO: 调用 invokeMethod(math, "add", 10.5, 20.5) 并断言结果为 31.0
        assert (invokeMethod(math, "add", 10.5, 20.5).equals(31.0));
        System.out.println("OK");

    }

    @Test
    public void test_invoke_add_three_args() throws Exception {
        MathUtil math = new MathUtil();
        // TODO: 调用 invokeMethod(math, "add", 1, 2, 3) 并断言结果为 6
        assert (invokeMethod(math, "add", 1, 2, 3).equals(6));
        System.out.println("OK");
    }

    @Test
    public void test_invoke_printer() throws Exception {
        Printer printer = new Printer();
        // TODO: 调用 invokeMethod(printer, "print", "Hello Reflection")
        invokeMethod(printer, "print", "Hello Reflection");
        System.out.println("OK");
    }

    @Test
    public void test(){
        System.out.println("String 赋值给 Object 是否合法：" + Object.class.isAssignableFrom(String.class));
        System.out.println("Object 赋值给 String 是否合法：" + String.class.isAssignableFrom(Object.class));
    }
}
