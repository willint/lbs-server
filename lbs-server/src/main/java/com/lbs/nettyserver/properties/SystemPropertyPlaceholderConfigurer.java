package com.lbs.nettyserver.properties;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.lbs.nettyserver.utils.sysutils.PropUtils;
import org.springframework.stereotype.Component;


/**
 * @ClassName: SystemPropertyPlaceholderConfigurer
 * @Description: 系统配置文件的属性加载
 * @author will
 * @date 2017-06-26
 * @version V1.0
 */
public class SystemPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private Properties props;
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		this.props = props;
		PropUtils.initConfigProperties(props);
	}

	public String getProperty(String key){
		return this.props.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return this.props.getProperty(key, defaultValue);
	}


}