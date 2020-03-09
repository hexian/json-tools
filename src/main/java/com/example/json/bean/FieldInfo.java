package com.example.json.bean;

import com.example.json.constant.VariableType;
import com.example.json.handler.json.annotation.CompositeAnnotationHandler;
import com.example.json.handler.json.field.CompositeFieldHandler;
import com.example.json.handler.json.imp.CompositeImportHandler;
import com.example.json.kit.CamelizeKit;
import com.example.json.kit.IocContext;

/**
 * 
 * @author hexian
 * 保存 字段的信息
 */
public class FieldInfo {

	// java类型(不包含包路径的名称)
	private String javaType;
	
	// java类型(包含包全路径的名称)
	private String javaTypeFullName;
	
	// json的key的名称
	private String jsonKeyName;
	
	// 变量的名称(字段的名称)
	private String variableName;
	
	// 当前变量是否是数组? list? map?
	private VariableType variableType = VariableType.NONE;
	
	// 多个字段之间的顺序,内部使用
	private int position;
	
	// 如果是日期类型, 则在这里指定它的 pattern 
	private String dateFormat;
	
	public FieldInfo() {
		
	}

	public FieldInfo(String javaType, String javaTypeFullName, String jsonKeyName, String variableName, VariableType variableType, String dateFormat) {
		setJavaType(javaType);
		this.javaTypeFullName = javaTypeFullName;
		this.jsonKeyName = jsonKeyName;
		this.variableName = variableName;
		this.variableType = variableType;
		this.dateFormat = dateFormat;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		if(javaType!=null && !"".equals(javaType) && javaType.startsWith(".")) {
		   javaType = javaType.substring(1);
		}
		this.javaType = javaType;
	}

	public String getJavaTypeFullName() {
		return javaTypeFullName;
	}

	public void setJavaTypeFullName(String javaTypeFullName) {
		this.javaTypeFullName = javaTypeFullName;
	}

	public String getVariableName() {
		if(variableName==null || "".equals(variableName)) {
		   // 需要先将中划线替换成下划线
		   variableName = CamelizeKit.underLine2Camelize(jsonKeyName.replace("-", "_"));
		}
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public VariableType getVariableType() {
		return variableType;
	}

	public void setVariableType(VariableType variableType) {
		this.variableType = variableType;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getJsonKeyName() {
		return jsonKeyName;
	}

	public void setJsonKeyName(String jsonKeyName) {
		this.jsonKeyName = jsonKeyName;
	}
	
	// 获取方法的字符串表达方式
	public String getFieldSignature() {
		CompositeFieldHandler fieldHandler = IocContext.getBean(CompositeFieldHandler.class);
		return fieldHandler.getFieldSignature(this);
	}
	
	// 获取 import 表达式
	public String getImport() {
		CompositeImportHandler impHandler = IocContext.getBean(CompositeImportHandler.class);
		return impHandler.getImport(this);
	}
	
	public String getAnnotation() {
		CompositeAnnotationHandler annotationHandler = IocContext.getBean(CompositeAnnotationHandler.class);
		return annotationHandler.getJsonAnnotation(this);
	}
}
