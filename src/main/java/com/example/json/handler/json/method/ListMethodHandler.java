package com.example.json.handler.json.method;

import com.example.json.bean.FieldInfo;
import com.example.json.bean.MethodInfo;
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
public class ListMethodHandler implements MethodHandler {

	@Override
	public boolean isSupport(MethodInfo info) {
		return (info.getFieldInfo().getVariableType() == VariableType.LIST);
	}

	@Override
	public String getMethodSignature(MethodInfo info) {
		String methodType = info.getMethodType();
		FieldInfo fInfo = info.getFieldInfo();
		if("getter".equals(methodType)) {
			String m = INDENT + "public List<" + fInfo.getJavaType()+"> " + CamelizeKit.underLine2Camelize("get_"+fInfo.getVariableName())+ "() { "+LINE_SEPARATOR;
			m = m + getterBody(info);
			m = m + INDENT + "} ";
			
			return m;
		} else 
		if("setter".equals(methodType)) {
			String m = INDENT + "public " + "void " + CamelizeKit.underLine2Camelize("set_"+fInfo.getVariableName()) + "(List<"+fInfo.getJavaType()+"> "+fInfo.getVariableName()+") { "+LINE_SEPARATOR;
			m = m + setterBody(info);
			m = m + INDENT + "} ";
			
			return m;
		} else {// 其它方法不支持
			return null;
		}
	}

}
