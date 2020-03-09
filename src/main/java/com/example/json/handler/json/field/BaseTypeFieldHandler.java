package com.example.json.handler.json.field;

import com.example.json.JsonProcess;
import com.example.json.bean.FieldInfo;
import com.example.json.constant.TypeMappings;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.FieldHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 * 对应基本数据类型+字符串+日期
 */
@Component
public class BaseTypeFieldHandler implements FieldHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		// "_class_" 这个key需要排除
		return (info.getVariableType() == VariableType.NONE) && (!JsonProcess.CLAZZ_PROP.equals(info.getJsonKeyName())) && (null != TypeMappings.findJavaTypeFullName(info.getJavaType()));
	}

	@Override
	public String getFieldSignature(FieldInfo info) {
		return INDENT + "private " + info.getJavaType() + " " + info.getVariableName()+";";
	}

}
