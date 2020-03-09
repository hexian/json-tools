package com.example.json.bean.aop;

import java.lang.reflect.Method;

import com.example.json.aop.advice.AroundAdvice;
import com.example.json.aop.annotation.Aspect;
import com.example.json.aop.annotation.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(2)
@Aspect(pointcut = "@within(com.example.json.ioc.annotation.Controller)")
public class DoodleAspect2 implements AroundAdvice {

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        //log.info("-----------before  DoodleAspect2-----------");
        log.info("前置调用 ----> class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        //log.info("-----------after  DoodleAspect2-----------");
    	log.info("后置调用 ----> class: {}, method: {}", clz, method.getName());
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        //log.error("-----------error  DoodleAspect2-----------");
    	log.error("异常时调用 ----> class: {}, method: {}, exception: {}", clz, method.getName(), e.getMessage());
    }
    
}