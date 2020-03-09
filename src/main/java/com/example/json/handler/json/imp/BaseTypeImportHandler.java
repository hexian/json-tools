package com.example.json.handler.json.imp;

import com.example.json.JsonProcess;
import com.example.json.bean.FieldInfo;
import com.example.json.constant.TypeMappings;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.ImportHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 *
 */
@Component
public class BaseTypeImportHandler implements ImportHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		// "_class_" 这个key需要排除
		return (info.getVariableType() == VariableType.NONE) && (!JsonProcess.CLAZZ_PROP.equals(info.getJsonKeyName())) && (null != TypeMappings.findJavaTypeFullName(info.getJavaType()));
	}

	@Override
	public String getImport(FieldInfo info) {
		return genImport(info);
	}

}
