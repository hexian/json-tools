package com.example.json.handler.json.field;

import com.example.json.bean.FieldInfo;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.FieldHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 * 对应Map类型
 */
@Component
public class MapFieldHandler implements FieldHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return (info.getVariableType() == VariableType.MAP);
	}

	@Override
	public String getFieldSignature(FieldInfo info) {
		return INDENT + "private Map<String, Object> " + info.getVariableName()+";";
	}

}
