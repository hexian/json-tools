package com.example.json.handler.json.annotation;

import com.example.json.JsonProcess;
import com.example.json.bean.FieldInfo;
import com.example.json.handler.json.AnnotationHandler;
import com.example.json.ioc.annotation.Component;
/**
 * 
 * @author hexian
 *
 */
@Component
public class CompositeAnnotationHandler implements AnnotationHandler {

	@Override
	public boolean isSupport(FieldInfo info) {
		return !JsonProcess.CLAZZ_PROP.equals(info.getJsonKeyName());
	}

	@Override
	public String getJsonAnnotation(FieldInfo info) {
		if(isSupport(info)) {
			return annotationStr(info);
		} else {
			return null;
		}
	}

}
