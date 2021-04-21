package org.rosychao.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosychao.springframework.util.PathUtils;

import java.net.URISyntaxException;

/**
 * 用于添加对指定配置位置的通用处理
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 配置文件路径
     */
    private String[] configLocations;

    /**
     * 设置配置文件路径
     */
    public void setConfigLocation(String location) {
        setConfigLocations(location);
    }

    /**
     * 设置配置文件路径
     */
    public void setConfigLocations(String... locations) {
        logger.info("开始获取配置文件路径...");
        if (locations != null) {
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                // 解析配置文件路径替换特殊位置符号：classpath:
                try {
                    this.configLocations[i] = resolvePath(locations[i]).trim();
                } catch (URISyntaxException e) {
                    logger.info("读取配置文件失败，请检查路径:" + locations[i]);
                    e.printStackTrace();
                }
                logger.info("获取配置文件路径：" + locations[i] + " 转变为---> " + this.configLocations[i]);
            }
        } else {
            this.configLocations = locations;
        }
        logger.info("获取配置文件路径结束...");
    }

    /**
     * 获取配置文件
     */
    protected String[] getConfigLocations() {
        return this.configLocations;
    }

    /**
     * 解析配置文件路径
     */
    protected String resolvePath(String path) throws URISyntaxException {
        if(path != null) {
            // 解析classpath:
            if(path.startsWith("classpath:")){
                return PathUtils.getClassPathAndPackagePath(null) + path.replace("classpath:", "");
            }
        }
        return path;
    }

}
