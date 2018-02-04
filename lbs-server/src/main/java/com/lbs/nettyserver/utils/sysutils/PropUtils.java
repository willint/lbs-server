package com.lbs.nettyserver.utils.sysutils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: PropertiesUtils
 * @Description: 常用配置项，配置文件操作
 * @author will
 * @date 2017-06-26
 * @version V1.0
 */
public class PropUtils {

	private static Map<String, String> properties = new HashMap<String, String>();

	public static void initConfigProperties(Properties props) {
		for (Object key : props.keySet()) {
			String keyTemp = String.valueOf(key);
			String valueTemp = props.getProperty(keyTemp);
			properties.put(keyTemp, valueTemp);
		}
		
		//初始化公私钥
//		RSAUtil.PUBLIC_KEY_FILE = get("sysfile.path.publicKey");
//		RSAUtil.PRIVATE_KEY_FILE = get("sysfile.path.privateKey");
	}

	/**
	 * @Description: 得到经过SecurityUtils加密后的原数据
	 * @param @param props
	 * @return Properties 返回类型
	 */
	public static Properties loadMetaProps(Properties props) {
		for (Object key : props.keySet()) {
			String keyTemp = String.valueOf(key);
			String value = props.getProperty(keyTemp);
			props.setProperty(keyTemp, value);
			properties.put(keyTemp, value);
		}
		return props;
	}

	/**
	 * @Description: 设置配置到系统变量中
	 * @param @param props
	 * @param @return 参数说明
	 * @return Properties 返回类型
	 */
	public static Properties loadMetaPropsToSystem(Properties props) {
		for (Object key : props.keySet()) {
			String keyTemp = String.valueOf(key);
			System.setProperty(keyTemp, props.getProperty(keyTemp));
		}
		return props;
	}

	public static Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @Description: 通过Key获取配置值
	 * @param key 唯一键
	 * @return String 返回类型
	 */
	public static String getProperty(String key) {
		return properties.get(key);
	}

	/**
	 * @Description: 通过Key获取配置值
	 * @param key 唯一键
	 * @return String 返回类型
	 */
	public static String get(String key) {
		return properties.get(key);
	}


	/**
	 * @Description: 设置配置键值对
	 * @param key 唯一键
	 * @param value key 对应的值
	 * @return String 返回类型
	 */
	public static void setProperty(String key, String value) {
		properties.put(key, value);
	}
}
