package com.example.json.handler.json.field;

import com.example.json.bean.FieldInfo;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.FieldHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 * 对应集合类型
 */
@Component
public class ListFieldHandler implements FieldHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return (info.getVariableType() == VariableType.LIST);
	}

	@Override
	public String getFieldSignature(FieldInfo info) {
		return INDENT + "private List<" + info.getJavaType() + "> " + info.getVariableName()+";";
	}

}
