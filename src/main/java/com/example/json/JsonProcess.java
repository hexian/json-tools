package com.example.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.example.json.bean.BeanInfo;
import com.example.json.bean.FieldInfo;
import com.example.json.bean.FiledConfig;
import com.example.json.bean.MethodGroup;
import com.example.json.bean.MethodInfo;
import com.example.json.constant.TypeMappings;
import com.example.json.constant.VariableType;
/**
 * 
 * @author hexian
 *
 */
public class JsonProcess {

	public static final String CLAZZ_PROP = "_class_";
	
	public static final String ARRAY_PROP = "___array___";
	
	private JsonProcess() {
		
	}
	
	/**
	 * 将 json 转换成 BeanDefinition(有重复代码 - 暂未优化)
	 */
	public static void jsonToBeanDefinition(String prettyFormatJson, String basePackage, List<BeanInfo> beanList) {
		if(basePackage == null) {
		   basePackage = "";
		}
		List<String> childJsonList = new LinkedList<>();
		Map<String, Object> jsonMap = JSON.parseObject(prettyFormatJson, HashMap.class);
		Iterator<Entry<String, Object>> iterator = jsonMap.entrySet().iterator();
		String clazz = (String)jsonMap.get(CLAZZ_PROP);  
		clazz = ((basePackage==null || "".equals(basePackage)) && basePackage.endsWith(".")) ? basePackage + clazz : basePackage + "." + clazz;
		
		List<FieldInfo> fieldList = new LinkedList<>();
		BeanInfo beanInfo = new BeanInfo();
		beanInfo.setFieldList(fieldList);
		beanList.add(beanInfo);
		
		List<MethodGroup> methodGroupList = new LinkedList<>();
		beanInfo.setMethodGroupList(methodGroupList);
		
		// 设置bean的名称
		beanInfo.setSimpleClassName(clazz.substring(clazz.lastIndexOf(".")+1));
		beanInfo.setFullClassName(clazz);
		
		int position = 0;
		while(iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			// 注意: 这里要求不能给null值
			Object val = entry.getValue();
			
			position++;
			
			// 字段
			FieldInfo fInfo = new FieldInfo();
			fieldList.add(fInfo);
			fInfo.setPosition(position);
			
			MethodGroup mGroup = new MethodGroup();
			mGroup.setPosition(position);
			// settter 方法
			mGroup.setSetter(new MethodInfo("setter", fInfo, position));
			// gettter 方法
			mGroup.setGetter(new MethodInfo("getter", fInfo, position));
			methodGroupList.add(mGroup);
			
			
			
			int pos = key.indexOf(':');
			// 没用配置项的key
			if(-1 == pos) {  
				fInfo.setJsonKeyName(key);
				// 数据类型是list/数组
				if(val instanceof List) {
					// 默认值使用
					fInfo.setVariableType(VariableType.LIST);
					List list = (List)val;
					// 取出list的第1个元素(这里认为所有的list中元素的格式都是一样的)
					Object item = list.get(0);
					// 如果是Map
					if(item instanceof Map) {
						Map child = (Map)item;
						String clz = (String)child.get(CLAZZ_PROP);
						Object ary = child.get(ARRAY_PROP);
						// 提供了 _class_ , 则生成相应的java bean
						if(clz!=null && !"".equals(clz)) {
							// 加入集合(下次处理)
							childJsonList.add(JSON.toJSONString(child, true));
							// 提供了包名
							if(-1 != clz.lastIndexOf('.')) {
								fInfo.setJavaType(clz.substring(clz.lastIndexOf('.')));
								fInfo.setJavaTypeFullName(clz);
							} else {// 没有提供包名
								fInfo.setJavaType(clz);
								String javaTypeFullName = ((basePackage==null || "".equals(basePackage)) && basePackage.endsWith(".")) ? basePackage + clz : basePackage + "." + clz;
								fInfo.setJavaTypeFullName(javaTypeFullName);
							}
						} else 
						// 经过特殊变换的数组
						if(ary!=null) {
							FiledConfig c1 = JSON.parseObject(JSON.toJSONString(child), FiledConfig.class);
							if(c1.isOverrideTypeFlag()) {
								String overrideType = c1.getOverrideType();
								String javaTypeFullName = TypeMappings.findJavaTypeFullName(overrideType);
								fInfo.setJavaType(javaTypeFullName.substring(javaTypeFullName.lastIndexOf(".")+1));
								fInfo.setJavaTypeFullName(javaTypeFullName);
								if(TypeMappings.isDateType(overrideType)) {
								   fInfo.setDateFormat(c1.getDateFormat());
								}
							} else {/** 应该不会进入这里    */
								fInfo.setJavaType("Map");
								fInfo.setJavaTypeFullName("java.util.Map");
							}
						} else {// 没有提供  _class_ 属性, 则只能生成 Map
							fInfo.setJavaType("Map");
							fInfo.setJavaTypeFullName("java.util.Map");
						}
					} else {// 否则认为是 TypeMappings.TYPE_MAPPINGS 中定义的类型 
						fInfo.setJavaType(item.getClass().getSimpleName());
						fInfo.setJavaTypeFullName(item.getClass().getName());
					}
				} else 
					
				// 数据类型是Map
				if(val instanceof Map) {
					// 默认使用map 
					fInfo.setVariableType(VariableType.MAP);
					Map child = (Map)val;
					String clz = (String)child.get(CLAZZ_PROP);
					// 没有提供  _class_ 属性, 则只能生成 Map
					if(clz==null || "".equals(clz)) {
						// 加入集合(下次处理)
						childJsonList.add(JSON.toJSONString(child, true));
						fInfo.setJavaType("Map");
						fInfo.setJavaTypeFullName("java.util.Map");
					} else {// 提供了 _class_ , 则生成相应的java bean
						// 提供了包名
						if(-1 != clz.lastIndexOf('.')) {
							fInfo.setJavaType(clz.substring(clz.lastIndexOf('.')+1));
							fInfo.setJavaTypeFullName(clz);
						} else {// 没有提供包名
							fInfo.setJavaType(clz);
							String javaTypeFullName = ((basePackage==null || "".equals(basePackage)) && basePackage.endsWith(".")) ? basePackage + clz : basePackage + "." + clz;
							fInfo.setJavaTypeFullName(javaTypeFullName);
						}
					}
				} else {
					fInfo.setJavaType(val.getClass().getSimpleName());
					fInfo.setJavaTypeFullName(val.getClass().getName());
				}
			} else {// 有配置项的key
				String newKey = key.substring(0, pos);
				String cfgStr = key.substring(pos+1);
				// 获取配置项
				FiledConfig cfg = JSON.parseObject(cfgStr, FiledConfig.class);
				// 不被忽略的属性
				if(!cfg.isIgnore()) {
					fInfo.setJsonKeyName(newKey);
					
					// 数据类型是list/数组
					if(val instanceof List) {
						// 是否覆盖集合类型
						boolean overrideVolType = cfg.isCollection();
						if(overrideVolType) {
						   fInfo.setVariableType(VariableType.getVariableType(cfg.getColType()));
						} else {
						   fInfo.setVariableType(VariableType.LIST);
						}
						
						List list = (List)val;
						// 取出list的第1个元素(这里认为所有的list中元素的格式都是一样的)
						Object item = list.get(0);
						// 如果是Map
						if(item instanceof Map) {
							Map child = (Map)item;
							String clz = (String)child.get(CLAZZ_PROP);
							Object ary = child.get(ARRAY_PROP);
							// 提供了 _class_ , 则生成相应的java bean
							if(clz!=null && !"".equals(clz)) {
								// 加入集合(下次处理)
								childJsonList.add(JSON.toJSONString(child, true));
								// 提供了包名
								if(-1 != clz.lastIndexOf('.')) {
									fInfo.setJavaType(clz.substring(clz.lastIndexOf('.')+1));
									fInfo.setJavaTypeFullName(clz);
								} else {// 没有提供包名
									fInfo.setJavaType(clz);
									String javaTypeFullName = ((basePackage==null || "".equals(basePackage)) && basePackage.endsWith(".")) ? basePackage + clz : basePackage + "." + clz;
									fInfo.setJavaTypeFullName(javaTypeFullName);
								}
							} else 
							// 经过特殊变换的数组
							if(ary!=null) {
								FiledConfig c1 = JSON.parseObject(JSON.toJSONString(child), FiledConfig.class);
								if(c1.isOverrideTypeFlag()) {
									String overrideType = c1.getOverrideType();
									String javaTypeFullName = TypeMappings.findJavaTypeFullName(overrideType);
									fInfo.setJavaType(javaTypeFullName.substring(javaTypeFullName.lastIndexOf(".")+1));
									fInfo.setJavaTypeFullName(javaTypeFullName);
									if(TypeMappings.isDateType(overrideType)) {
									   fInfo.setDateFormat(c1.getDateFormat());
									}
								} else {/** 应该不会进入这里    */
									fInfo.setJavaType("Map");
									fInfo.setJavaTypeFullName("java.util.Map");
								}
							} else {// 没有提供  _class_ 属性, 则只能生成 Map
								fInfo.setJavaType("Map");
								fInfo.setJavaTypeFullName("java.util.Map");
							}
						} else {// 否则认为是 TypeMappings.TYPE_MAPPINGS 中定义的类型 
							fInfo.setJavaType(item.getClass().getSimpleName());
							fInfo.setJavaTypeFullName(item.getClass().getName());
						}
				    } else 
				    	
				    // 数据类型是Map
				    if(val instanceof Map){
						Map child = (Map)val;
						String clz = (String)child.get(CLAZZ_PROP);
						// 提供了 _class_ , 则生成相应的java bean
						if(clz!=null && !"".equals(clz)) {
							// 加入集合(下次处理)
							childJsonList.add(JSON.toJSONString(child, true));
							// 提供了包名
							if(-1 != clz.lastIndexOf('.')) {
								fInfo.setJavaType(clz.substring(clz.lastIndexOf('.')));
								fInfo.setJavaTypeFullName(clz);
							} else {// 没有提供包名
								fInfo.setJavaType(clz);
								String javaTypeFullName = ((basePackage==null || "".equals(basePackage)) && basePackage.endsWith(".")) ? basePackage + clz : basePackage + "." + clz;
								fInfo.setJavaTypeFullName(javaTypeFullName);
							}
						} else {// 没有提供  _class_ 属性, 则只能生成 Map
							fInfo.setVariableType(VariableType.MAP);
							fInfo.setJavaType("Map");
							fInfo.setJavaTypeFullName("java.util.Map");
						}
				    } else 
				    	
					// 需要覆盖数据类型(单个数据类型)
					if(cfg.isOverrideTypeFlag()) {
					   String overrideType = cfg.getOverrideType();
					   String javaTypeFullName = TypeMappings.findJavaTypeFullName(overrideType);
					   if(TypeMappings.isDateType(overrideType)) {
						  fInfo.setDateFormat(cfg.getDateFormat());
					   }
					   
					   fInfo.setJavaType(javaTypeFullName.substring(javaTypeFullName.lastIndexOf(".")+1));
					   fInfo.setJavaTypeFullName(javaTypeFullName);
					} else {
						fInfo.setJavaType(val.getClass().getSimpleName());
						fInfo.setJavaTypeFullName(val.getClass().getName());
					}
				} else {
					position--;
					
					// 移除相关数据
					fieldList.remove(fInfo);
					methodGroupList.remove(mGroup);
				}
			}
		}
		
		// 递归处理子级
		if(!childJsonList.isEmpty()) {
			for(String subJson : childJsonList) {
				jsonToBeanDefinition(subJson, basePackage, beanList);
			}
		}
	}
	
	/**
	 * 重新组合json(调用此方法前先使用 Hijson 等工具格式化json字符串)
	 * 
	 *    注意: 格式化后的json如果有内容中有
	 */
	@SuppressWarnings({"unchecked" })
	public static String combiJson(String filePath) {
		String lineSeparator = System.lineSeparator();
		StringBuilder json  = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), StandardCharsets.UTF_8))) {
			// 单行注释的正则表达式
			Pattern pattern = Pattern.compile("\\/\\/(.*)");
			
			// 一行的数据 
			String lineContent = null;
			while(null != (lineContent = br.readLine())) {
				/**
				 *  "bigPic": "http://www.baidu.com", // {'ignore': false} 
				 * =====> 
				 *  "bigPic": "http:www.baidu.com", // {'ignore': false}
				 */
				if(lineContent.matches(".*?\".*?//.*?\".*?")) {// 处理json的value中包含有 "//" 双斜线的数据
				   lineContent = lineContent.replaceAll("(.*?\".*?)//(.*?\".*?)", "$1$2");
				}
				Matcher matcher = pattern.matcher(lineContent);
				// 默认认为只有一组
				if(matcher.find()) {
					/**
					 * 将:  
					 *    "aa": 123,       // {'ignore': false, 'overrideType': 'boolean'}  
					 * 转换成:
					 *    "aa:{'ignore': false, 'overrideType': 'boolean'}":123,
					 *    
					 * 也就是将注释后面的json字符串合并到当前json的key中作为新的key, value保持不变
					 */
					// 字段配置信息(单行注释中的信息) ===> "// {'ignore': false, 'overrideType': 'boolean'}"
					String fieldConfigInfo = matcher.group();
					// 当前读取的行去掉单行注释信息后的数据  ===> "aa": 123,  
					String newLineContent = lineContent.replace(fieldConfigInfo, "").trim();
					// "// {'ignore': false, 'overrideType': 'boolean'}"  ===>  "{'ignore': false, 'overrideType': 'boolean'}"
					fieldConfigInfo = fieldConfigInfo.substring(fieldConfigInfo.indexOf("//")+2).trim();
					
					int pos = newLineContent.indexOf(':');
					// 处理json的value数据中含有 ":" 冒号的数据(比如集合中的时间字符串)
					if(-1 != pos && !newLineContent.substring(0, pos).trim().matches("\".*?\"")) {
						// 防止 "2020-03-06 20:15:05" 这种数组中的字符串含有 : 来干扰判断
						newLineContent = newLineContent.replaceAll("\".*?:.*?\".*?,?.*?", newLineContent.replace(":", "") );
						/** newLineContent = newLineContent.replaceAll("\"(.*?):(.*?)\"", "\"$1$2\"");  */
					}
					pos = newLineContent.indexOf(':');
					// json 字符串的分隔符
					if(-1 == pos) {
						/**
						 * "lint": [11, 21, 31]  =====>  手工组装成为一个map
						 */
						if(-1 != newLineContent.indexOf('"')) {
							newLineContent = newLineContent.replace("\".*?,.*?\"", "");
						}
						boolean endEle = newLineContent.trim().endsWith(",");
						String part = endEle ? newLineContent.trim().substring(0, newLineContent.trim().length()-1) : newLineContent.trim();
						// ARRAY_PROP 集合属性得特殊标记
						json.append("{").append("\"") .append(ARRAY_PROP).append("\": ").append(part);
						if(fieldConfigInfo!=null && !"".equals(fieldConfigInfo.trim())) {
						   fieldConfigInfo = fieldConfigInfo.replace("'", "\"");
						   String cm = fieldConfigInfo.substring(fieldConfigInfo.indexOf("{")+1, fieldConfigInfo.indexOf("}"));
						   json.append(",").append(cm);
						}
						json.append("}").append(endEle ? "," : "").append(lineSeparator);
					} else {
						// 取出json的key  ===> "aa"
						String jsonKey = newLineContent.substring(0, pos).replace("\"", "");
						// 取出json的value ===> 123, 
						String jsonVal = newLineContent.substring(pos+1);
						
						// 将读取到的json的key和注释信息的json字符串组合程序新的key ===> "aa:{'ignore': false, 'overrideType': 'boolean'}"
						String newJsonKey = "\"" + jsonKey+":"+fieldConfigInfo + "\"";
						
						// 将新的key和原来的value组合成一行新的数据  ===> "aa:{'ignore': false, 'overrideType': 'boolean'}":123, 
						String newLine = newJsonKey + ":" + jsonVal;
						json.append(newLine).append(lineSeparator);
					}
				} else {// 没用单行注释的数据不做任何处理   
					json.append(lineContent.trim()).append(lineSeparator);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} 
		
		// 删除xml注释
		String data = json.toString().replaceAll("<!-[\\s\\S]*?-->", "");
		
		// 将json转成Map对象
		Map<String, Object> jsonMap = JSON.parseObject(data, HashMap.class);
		// 将Map对象转成json字符串并格式化
		String prettyFormatJson = JSON.toJSONString(jsonMap, true);
		/** System.out.println(prettyFormatJson);  */
		
		return prettyFormatJson;
	}
}
