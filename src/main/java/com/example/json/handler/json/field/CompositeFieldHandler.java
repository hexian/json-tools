package com.example.json.handler.json.field;

import java.util.List;

import com.example.json.bean.FieldInfo;
import com.example.json.handler.json.FieldHandler;
import com.example.json.ioc.annotation.Autowired;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 * 组合类
 */
@Component
public class CompositeFieldHandler implements FieldHandler {

	@Autowired
	private List<FieldHandler> handlers;
	
	@Override
	public boolean isSupport(FieldInfo info) {
		for(FieldHandler handler : handlers) {
			if(handler.isSupport(info)) {
			   return true;
			}
		}
		
		return false;
	}

	@Override
	public String getFieldSignature(FieldInfo info) {
		for(FieldHandler handler : handlers) {
			if(handler.isSupport(info)) {
			   return handler.getFieldSignature(info);
			}
		}
		
		return null;
	}

}
