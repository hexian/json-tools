package com.example.json.handler.json;

import com.example.json.bean.FieldInfo;
import com.example.json.bean.MethodInfo;
/**
 * 
 * @author hexian
 * 方法生成器
 */
public interface MethodHandler extends Handler {

	// 是否支持
	public boolean isSupport(MethodInfo info);
	
	// 返回方法的字符串表达方式
	public String getMethodSignature(MethodInfo info);
	
	public default String getterBody(MethodInfo info) {
		FieldInfo fInfo = info.getFieldInfo();
		
		return INDENT + INDENT + "return this."+fInfo.getVariableName()+"; " + LINE_SEPARATOR;
	}
	
    public default String setterBody(MethodInfo info) {
    	FieldInfo fInfo = info.getFieldInfo();
		
		return INDENT + INDENT + "this."+fInfo.getVariableName()+" = "+fInfo.getVariableName()+";" + LINE_SEPARATOR;
	}
	
}
