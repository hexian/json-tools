package com.example.json.handler.json.method;

import java.util.List;

import com.example.json.bean.MethodInfo;
import com.example.json.handler.json.MethodHandler;
import com.example.json.ioc.annotation.Autowired;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 *
 */
@Component
public class CompositeMethodHandler implements MethodHandler {

	@Autowired
	private List<MethodHandler> handlers;
	
	@Override
	public boolean isSupport(MethodInfo info) {
		for(MethodHandler handler : handlers) {
			if(handler.isSupport(info)) {
			   return true;
			}
		}
		
		return false;
	}

	@Override
	public String getMethodSignature(MethodInfo info) {
		for(MethodHandler handler : handlers) {
			if(handler.isSupport(info)) {
			   return handler.getMethodSignature(info);
			}
		}
		
		return null;
	}

}
