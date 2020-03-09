package com.example.json.handler.json;

import com.example.json.bean.FieldInfo;
import com.example.json.constant.TypeMappings;

/**
 * 
 * @author hexian
 * 注解的生成器
 */
public interface AnnotationHandler extends Handler {

	// 是否支持
	public boolean isSupport(FieldInfo info);
		
	// 生成 json 注解
	public String getJsonAnnotation(FieldInfo info);
	
	public default String annotationStr(FieldInfo info) {
		String str = INDENT + "@JSONField(name =\""+info.getJsonKeyName()+"\"#{dateFormat})" + LINE_SEPARATOR + 
		INDENT + "@JsonProperty(value = \"" + info.getJsonKeyName() + "\")";
		if(TypeMappings.isDateType(info.getJavaType())) {
			String dateFormat = info.getDateFormat();
			if(dateFormat == null || "".equals(dateFormat)) {
			   dateFormat = "yyyy-MM-dd HH:mm:ss";
			}
			str = str.replace("#{dateFormat}", " , format=\"" + dateFormat + "\"") + LINE_SEPARATOR + 
				  //INDENT + "@JSONField(format=\"" + dateFormat + "\")" + LINE_SEPARATOR + 
				  INDENT + "@JsonFormat(pattern=\"" + dateFormat + "\", timezone=\"GMT+8\")"  ;
		} else {
			str = str.replace("#{dateFormat}", "");
		}
		
		return str;
	}
}
