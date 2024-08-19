package com.street.one.manage.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * spring上下文
 * 
 * @author tjl
 * 
 */
@Configuration
public class SpringApplicationContext implements ApplicationContextAware {

	protected static ApplicationContext context;

	public static boolean isStarted() {
		return context != null;
	}
	public static void setContext(ApplicationContext Context) {
		if (context == null)  SpringApplicationContext.context = Context;
		System.out.println("---SpringApplicationContext setContext ---");
	}	
	@Override
	public void setApplicationContext(ApplicationContext Context) throws BeansException {
		if (Context != null && Context != context)  SpringApplicationContext.context = Context;
		System.out.println("---SpringApplicationContext setApplicationContext ---");
	}

	/**
	 * 根据beanid返回对象
	 * 
	 * @param BeanID
	 * @return
	 */
	public static Object getBean(String beanId) {
		if (context.containsBean(beanId)) {
			return context.getBean(beanId);
		} else {
			return null;
		}
	}

	/**
	 * 根据beanid返回对象
	 * 
	 * @param BeanID
	 * @return
	 */
	public static <T> T getBean(String beanId, Class<T> beanClass) {
		if (context != null && context.containsBean(beanId)) {
			return (T) context.getBean(beanId, beanClass);
		} else {
			return null;
		}
	}

	/**
	 * 根据类型返回对象
	 * 
	 * @param BeanClass
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

	/**
	 * 返回符合类型的所有对象
	 * 
	 * @param BeanClass
	 * @return
	 */
	public static <T> List<T> getBeans(Class<T> beanClass) {
		List<T> list = new ArrayList<T>();
		String[] beanNames = context.getBeanNamesForType(beanClass);
		for (String name : beanNames) {
			T bean = getBean(name, beanClass);
			if (bean != null)
				list.add(bean);
		}
		return list;
	}
}
