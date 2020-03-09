package com.example.json.kit;

import com.example.json.Main;
import com.example.json.aop.Aop;
import com.example.json.ioc.BeanContainer;
import com.example.json.ioc.Ioc;
/**
 * 
 * @author hexian
 * 
 * 简单的 IOC 容器
 */
public class IocContext {

	private final BeanContainer beanContainer;
	
	private static final IocContext CONTEXT = new IocContext();
	
	private IocContext() {
		beanContainer = BeanContainer.getInstance();
		// 加载bean
        beanContainer.loadBeans(Main.class.getPackage().getName());
        // Aop执行器必须要在Ioc执行器之前执行，不然注入到Bean中的实例将可能不是代理类。
        new Aop().doAop();
        // 执行扫描
        new Ioc().doIoc();
	}
	
	public static BeanContainer getBeanContainer() {
		return CONTEXT.beanContainer;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clz) {
		return (T)getBeanContainer().getBean(clz);
	}
	
}
