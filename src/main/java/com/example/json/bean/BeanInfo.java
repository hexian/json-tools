package com.example.json.bean;

import java.util.List;

/**
 * 
 * @author hexian
 *
 * 保存bean信息
 */
public class BeanInfo {
	
	// 类的简单名称(不含包名)
	private String simpleClassName;
	
	// 类的全名称(包括包名)
	private String fullClassName;

	// 字段信息
	private List<FieldInfo> fieldList;
	
	// getter/setter方法组
	private List<MethodGroup> methodGroupList;

	public BeanInfo() {
		
	}
	
	public BeanInfo(List<FieldInfo> fieldList, List<MethodGroup> methodGroupList) {
		this.fieldList = fieldList;
		this.methodGroupList = methodGroupList;
	}

	public List<FieldInfo> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldInfo> fieldList) {
		this.fieldList = fieldList;
	}

	public List<MethodGroup> getMethodGroupList() {
		return methodGroupList;
	}

	public void setMethodGroupList(List<MethodGroup> methodGroupList) {
		this.methodGroupList = methodGroupList;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public void setSimpleClassName(String simpleClassName) {
		if(simpleClassName!=null && simpleClassName.startsWith(".")) {
		   simpleClassName = simpleClassName.substring(1);
		}
		
		this.simpleClassName = simpleClassName;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		if(fullClassName!=null && fullClassName.startsWith(".")) {
		   fullClassName = fullClassName.substring(1);
		}
		
		this.fullClassName = fullClassName;
	}
	
	
	
}
