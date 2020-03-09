package com.example.json.handler.json;

import com.example.json.bean.FieldInfo;
/**
 * 
 * @author hexian
 * import生成器
 */
public interface ImportHandler extends Handler {

	// 是否支持
	public boolean isSupport(FieldInfo info);
	
	// 返回 import 的字符串表达方式
	public String getImport(FieldInfo info);
	
	public default String genImport(FieldInfo info) {
		String javaTypeFullName = info.getJavaTypeFullName();
		// java.lang.* 不需要导入
		if(javaTypeFullName!=null && javaTypeFullName.startsWith(Integer.class.getPackage().getName())) {
			return null;
		}
		
		return "import " + info.getJavaTypeFullName() + ";";
	}
	
}
