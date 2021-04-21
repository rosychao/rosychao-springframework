package org.rosychao.springframework.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean相关工具类
 */
public abstract class BeanUtils {

    private static final Map<Class<?>, Object> DEFAULT_TYPE_VALUES;

    static {
        Map<Class<?>, Object> values = new HashMap<>();
        values.put(boolean.class, false);
        values.put(byte.class, (byte) 0);
        values.put(short.class, (short) 0);
        values.put(int.class, 0);
        values.put(long.class, (long) 0);
        DEFAULT_TYPE_VALUES = Collections.unmodifiableMap(values);
    }


    /**
     * 实例化类
     */
    public static <T> T instantiateClass(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return instantiateClass(clazz.getDeclaredConstructor());
    }

    /**
     * 使用给定构造函数实例化类的便捷方法
     */
    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?>[] parameterTypes = ctor.getParameterTypes();
        Object[] argsWithDefaultValues = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                Class<?> parameterType = parameterTypes[i];
                argsWithDefaultValues[i] = (parameterType.isPrimitive() ? DEFAULT_TYPE_VALUES.get(parameterType) : null);
            } else {
                argsWithDefaultValues[i] = args[i];
            }
        }
        return ctor.newInstance(argsWithDefaultValues);
    }


}
