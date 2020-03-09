package com.example.json.handler.json.imp;

import java.util.Map;

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
public class MapImportHandler implements ImportHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return (info.getVariableType() == VariableType.MAP);
	}

	@Override
	public String getImport(FieldInfo info) {
		return "import " + Map.class.getName() + ";";
	}

}
