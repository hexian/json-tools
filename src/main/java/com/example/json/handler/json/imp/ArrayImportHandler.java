package com.example.json.handler.json.imp;

import com.example.json.bean.FieldInfo;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.ImportHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 *
 */
@Component
public class ArrayImportHandler implements ImportHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return (info.getVariableType() == VariableType.ARRAY);
	}

	@Override
	public String getImport(FieldInfo info) {
		return genImport(info);
	}

}
