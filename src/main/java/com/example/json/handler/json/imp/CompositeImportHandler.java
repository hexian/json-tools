package com.example.json.handler.json.imp;

import java.util.List;

import com.example.json.bean.FieldInfo;
import com.example.json.handler.json.ImportHandler;
import com.example.json.ioc.annotation.Autowired;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 *
 */
@Component
public class CompositeImportHandler implements ImportHandler {

	@Autowired
	private List<ImportHandler> handlers;
	
	@Override
	public boolean isSupport(FieldInfo info) {
		for(ImportHandler handler : handlers) {
			if(handler.isSupport(info)) {
			   return true;
			}
		}
		
		return false;
	}

	@Override
	public String getImport(FieldInfo info) {
		for(ImportHandler handler : handlers) {
			if(handler.isSupport(info)) {
			   return handler.genImport(info);
			}
		}
		
		return null;
	}

}
