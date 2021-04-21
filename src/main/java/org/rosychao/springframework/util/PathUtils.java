package org.rosychao.springframework.util;

import java.net.URISyntaxException;

/**
 * 路径操作工具类
 */
public class PathUtils {

    /**
     * 获取classPath+Package路径
     */
    public static String getClassPathAndPackagePath(String scanPackage){
        if(StringUtils.isEmpty(scanPackage)){
            scanPackage = "";
        } else {
            scanPackage = scanPackage.replaceAll("\\.", "/");
        }
        try {
            return Thread.currentThread().getContextClassLoader().getResource(scanPackage).toURI().getPath();
        } catch (URISyntaxException e) {
            System.out.println("读取配置文件失败，请检查路径:" + scanPackage);
            e.printStackTrace();
        }
        return null;
    }

}
