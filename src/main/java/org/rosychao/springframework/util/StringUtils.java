package org.rosychao.springframework.util;

import java.util.Collection;

/**
 * 字符串操作工具类
 */
public class StringUtils {

    /**
     * 将给定的Collection复制到String数组中。
     */
    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[collection.size()]);
    }

    /**
     * 字符串判空
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 注入的时候获取的 clazz.getName() 为驼峰首字母大写
     * （但不排除不规范的写法，这里默认都是规范的类命名，驼峰首字母大写）
     * 这时候要转成 驼峰首字母小写
     */
    public static String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //利用了ASCII码，大写字母和小写相差32这个规律
        chars[0] = chars[0] >= 'A' && chars[0] <= 'Z' ? (char) (chars[0] + 32) : chars[0];
        return String.valueOf(chars);
    }

    /**
     * 处理特殊字符，将其转义
     */
    public static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }

}
