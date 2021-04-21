package org.rosychao.springframework.context;

import org.rosychao.springframework.beans.factory.ListableBeanFactory;

import java.util.Properties;

/**
 * 中央接口，为应用程序提供配置。
 */
public interface ApplicationContext extends ListableBeanFactory {

    Properties getConfig();

}
