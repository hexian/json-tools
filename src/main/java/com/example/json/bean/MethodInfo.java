package com.example.json.bean;

import com.example.json.handler.json.method.CompositeMethodHandler;
import com.example.json.kit.IocContext;

/**
 * 
 * @author hexian
 * 保存 方法的信息
 */
public class MethodInfo {

	private FieldInfo fieldInfo;
	
	// 取值getter/setter
	private String methodType;
	
	private int position = 0;
	
	public MethodInfo() {
		
	}
	
	public MethodInfo(String methodType, FieldInfo fieldInfo, int position){
		this.fieldInfo = fieldInfo;
		this.methodType = methodType;
		this.position = position;
	}

	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(FieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	} 
	
	// 获取方法的字符串表达方式
	public String getMethodSignature() {
		CompositeMethodHandler methodHandler = IocContext.getBean(CompositeMethodHandler.class);
		return methodHandler.getMethodSignature(this);
	}
	
	
}
