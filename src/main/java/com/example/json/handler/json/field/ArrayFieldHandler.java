package com.example.json.handler.json.field;

import com.example.json.bean.FieldInfo;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.FieldHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 * 对应数组
 */
@Component
public class ArrayFieldHandler implements FieldHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return (info.getVariableType() == VariableType.ARRAY);
	}

	@Override
	public String getFieldSignature(FieldInfo info) {
		return INDENT + "private " + info.getJavaType() + "[] " + info.getVariableName()+";";
	}

}
