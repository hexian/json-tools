package com.example.json.constant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author hexian
 *
 */
public class TypeMappings {
	
	private TypeMappings() {
		
	}
	
	public static String findJavaTypeFullName(String javaType) {
		return TYPE_MAPPINGS.get(javaType);
	}
	
	public static boolean isDateType(String javaType) {
		return ( (javaType!=null && !"".equals(javaType.trim())) && (-1 != "date,Date,time,Time,timestamp,Timestamp".trim().indexOf(javaType.trim())) );
	}

	// 数据类型映射表
	private static final Map<String, String> TYPE_MAPPINGS = new HashMap<>(); 
	
	static {
		// 数字类型映射
		TYPE_MAPPINGS.put("byte", Byte.class.getName());
		TYPE_MAPPINGS.put("Byte", Byte.class.getName());
		TYPE_MAPPINGS.put("java.lang.Byte", Byte.class.getName());
		
		TYPE_MAPPINGS.put("short", Short.class.getName());
		TYPE_MAPPINGS.put("Short", Short.class.getName());
		TYPE_MAPPINGS.put("java.lang.Short", Short.class.getName());
		
		TYPE_MAPPINGS.put("int", Integer.class.getName());
		TYPE_MAPPINGS.put("integer", Integer.class.getName());
		TYPE_MAPPINGS.put("Integer", Integer.class.getName());
		TYPE_MAPPINGS.put("java.lang.Integer", Integer.class.getName());
		
		TYPE_MAPPINGS.put("long", Long.class.getName());
		TYPE_MAPPINGS.put("Long", Long.class.getName());
		TYPE_MAPPINGS.put("java.lang.Long", Long.class.getName());
		
		TYPE_MAPPINGS.put("float", Float.class.getName());
		TYPE_MAPPINGS.put("Float", Float.class.getName());
		TYPE_MAPPINGS.put("java.lang.Float", Float.class.getName());
		
		TYPE_MAPPINGS.put("double", Double.class.getName());
		TYPE_MAPPINGS.put("Double", Double.class.getName());
		TYPE_MAPPINGS.put("java.lang.Double", Double.class.getName());
		
		// 字符类型映射
		TYPE_MAPPINGS.put("char", Character.class.getName());
		TYPE_MAPPINGS.put("Char", Character.class.getName());
		TYPE_MAPPINGS.put("Character", Character.class.getName());
		TYPE_MAPPINGS.put("java.lang.Character", Character.class.getName());
		
		TYPE_MAPPINGS.put("str", String.class.getName());
		TYPE_MAPPINGS.put("string", String.class.getName());
		TYPE_MAPPINGS.put("String", String.class.getName());
		TYPE_MAPPINGS.put("java.lang.String", String.class.getName());
		
		// 布尔类型映射
		TYPE_MAPPINGS.put("bool", Boolean.class.getName());
		TYPE_MAPPINGS.put("boolean", Boolean.class.getName());
		TYPE_MAPPINGS.put("Boolean", Boolean.class.getName());
		TYPE_MAPPINGS.put("java.lang.Boolean", Boolean.class.getName());
		
		// 日期类型映射
		TYPE_MAPPINGS.put("date", Date.class.getName());
		TYPE_MAPPINGS.put("Date", Date.class.getName());
		TYPE_MAPPINGS.put("java.util.Date", Date.class.getName());
		
		TYPE_MAPPINGS.put("time", Date.class.getName());
		TYPE_MAPPINGS.put("Time", Date.class.getName());
		TYPE_MAPPINGS.put("java.sql.Time", Date.class.getName());
		
		TYPE_MAPPINGS.put("tt", Date.class.getName());
		TYPE_MAPPINGS.put("timestamp", Date.class.getName());
		TYPE_MAPPINGS.put("Timestamp", Date.class.getName());
		TYPE_MAPPINGS.put("java.sql.Timestamp", Date.class.getName());
	}
	
}
