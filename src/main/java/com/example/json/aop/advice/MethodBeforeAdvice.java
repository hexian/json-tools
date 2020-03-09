package com.example.json.aop.advice;

import java.lang.reflect.Method;

/**
 * @author hexian
 * 前置通知接口
 */
public interface MethodBeforeAdvice extends Advice {
	
    /**
     * 前置方法
     */
    void before(Class<?> clz, Method method, Object[] args) throws Throwable;
    
}