package com.example.json.aop;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.json.aop.advice.Advice;
import com.example.json.aop.annotation.Aspect;
import com.example.json.aop.annotation.Order;
import com.example.json.ioc.BeanContainer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hexian
 * Aop执行器
 */
@Slf4j
public class Aop {

    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public Aop() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
    	 //创建所有的代理通知列表
        List<ProxyAdvisor> proxyList = beanContainer.getClassesBySuper(Advice.class)
            .stream()
            .filter(clz -> clz.isAnnotationPresent(Aspect.class))
            .map(this::createProxyAdvisor)
            .collect(Collectors.toList());
        
      //创建代理类并注入到Bean容器中
        beanContainer.getClasses()
            .stream()
            .filter(clz -> !Advice.class.isAssignableFrom(clz))
            .filter(clz -> !clz.isAnnotationPresent(Aspect.class))
            .forEach(clz -> {
                List<ProxyAdvisor> matchProxies = createMatchProxies(proxyList, clz);
                if (matchProxies.size() > 0) {
                    Object proxyBean = ProxyCreator.createProxy(clz, matchProxies);
                    beanContainer.addBean(clz, proxyBean);
                }
            });
    }
    
    /**
     * 通过Aspect切面类创建代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        int order = 0;
        if (aspectClass.isAnnotationPresent(Order.class)) {
            order = aspectClass.getAnnotation(Order.class).value();
        }
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointcut proxyPointcut = new ProxyPointcut();
        proxyPointcut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointcut, order);
    }
    
    /**
     * 获取目标类匹配的代理通知列表
     */
    private List<ProxyAdvisor> createMatchProxies(List<ProxyAdvisor> proxyList, Class<?> targetClass) {
        Object targetBean = beanContainer.getBean(targetClass);
        return proxyList
            .stream()
            .filter(advisor -> advisor.getPointcut().matches(targetBean.getClass()))
            .sorted(Comparator.comparingInt(ProxyAdvisor::getOrder))
            .collect(Collectors.toList());
    }
    
    
}