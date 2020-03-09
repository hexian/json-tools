package com.example.json.handler.json;

import com.example.json.bean.FieldInfo;
/**
 * 
 * @author hexian
 * 字段生成器
 */
public interface FieldHandler extends Handler {

	// 是否支持
	public boolean isSupport(FieldInfo info);
	
	// 返回字段的字符串表达方式
	public String getFieldSignature(FieldInfo info);
	
	
}
