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
 * 对应自定义的类
 */
@Component
public class BeanFieldHandler implements FieldHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return (info.getVariableType() == VariableType.NONE) && (!JsonProcess.CLAZZ_PROP.equals(info.getJsonKeyName())) && (null == TypeMappings.findJavaTypeFullName(info.getJavaType()));
	}

	@Override
	public String getFieldSignature(FieldInfo info) {
		return INDENT + "private " + info.getJavaType() + " " + info.getVariableName()+";";
	}

}
