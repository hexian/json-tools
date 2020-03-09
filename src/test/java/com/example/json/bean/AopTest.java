package com.example.json.bean;

import org.junit.Test;

import com.example.json.aop.Aop;
import com.example.json.bean.controller.DoodleController;
import com.example.json.ioc.BeanContainer;
import com.example.json.ioc.Ioc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AopTest {
	
    @Test
    public void doAop() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.example.json.bean");
        // Aop执行器必须要在Ioc执行器之前执行，不然注入到Bean中的实例将可能不是代理类。
        new Aop().doAop();
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.hello();
    }
    
}