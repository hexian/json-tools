package com.example.json.handler.json.method;

import com.example.json.JsonProcess;
import com.example.json.bean.FieldInfo;
import com.example.json.bean.MethodInfo;
import com.example.json.constant.TypeMappings;
import com.example.json.constant.VariableType;
import com.example.json.handler.json.MethodHandler;
import com.example.json.ioc.annotation.Component;
import com.example.json.kit.CamelizeKit;
/**
 * 
 * @author hexian
 *
 */
@Component
public class BaseTypeMethodHandler implements MethodHandler {

	@Override
	public boolean isSupport(MethodInfo info) {
		FieldInfo fInfo = info.getFieldInfo();
		return (fInfo.getVariableType() == VariableType.NONE) && (!JsonProcess.CLAZZ_PROP.equals(fInfo.getJsonKeyName())) && (null != TypeMappings.findJavaTypeFullName(fInfo.getJavaType()));
	}

	@Override
	public String getMethodSignature(MethodInfo info) {
		String methodType = info.getMethodType();
		FieldInfo fInfo = info.getFieldInfo();
		if("getter".equals(methodType)) {
			String m = INDENT + "public " + fInfo.getJavaType()+" " + CamelizeKit.underLine2Camelize("get_"+fInfo.getVariableName())+ "() { "+LINE_SEPARATOR;
			m = m + getterBody(info);
			m = m + INDENT + "} ";
			
			return m;
		} else 
		if("setter".equals(methodType)) {
			String m = INDENT + "public " + "void " + CamelizeKit.underLine2Camelize("set_"+fInfo.getVariableName()) + "("+fInfo.getJavaType()+" "+fInfo.getVariableName()+") { "+LINE_SEPARATOR;
			m = m + setterBody(info);
			m = m + INDENT + "} ";
			
			return m;
		} else {// 其它方法不支持
			return null;
		}
	}

}
