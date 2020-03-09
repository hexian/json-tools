package com.example.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.example.json.bean.BeanInfo;
import com.example.json.bean.FieldInfo;
import com.example.json.bean.MethodGroup;
import com.example.json.bean.MethodInfo;
import com.example.json.constant.VariableType;
import com.example.json.kit.FileKit;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author hexian
 *
 */
@Slf4j
public class BeanWriter {
	
	public static final String LINE_SEPARATOR = System.lineSeparator();

	private BeanWriter() {
		
	}
	
	public static void writerBean(BeanInfo beanInfo, String baseDir) {
		// 首先检查基础目录是否存在
		FileKit.markDir(new File(baseDir));
		// 创建目录结果
		String packageDir = null;
		
		// 生成文件
		String fullClassName = beanInfo.getFullClassName();
		int pos = fullClassName.lastIndexOf('.');
		// 获取包名
		String packageName = fullClassName.substring(0, pos);
		// 获取类名
		String className = fullClassName.substring(pos+1);
		
		// 保存生成的类
		StringBuilder builder = new StringBuilder();
		// package部分
		if(packageName!=null && !"".equals(packageName)) {
		   builder.append("package ").append(packageName).append(";").append(LINE_SEPARATOR).append(LINE_SEPARATOR);
		   
		   packageDir = baseDir + packageName.replace(".", File.separator);
		   // 创建包结构
		   FileKit.markDir(new File(packageDir));
		}
		
		// import 部分
		StringBuilder impBuilder = new StringBuilder();
		// 字段部分
		StringBuilder fieldBuilder = new StringBuilder();
		// getter/setter方法部分
		StringBuilder methodBuilder = new StringBuilder();
		
		// 排序
		Set<String> importSet = new TreeSet<>((s1, s2) -> s1.compareTo(s2));
		importSet.add("import com.alibaba.fastjson.annotation.JSONField;");
		importSet.add("import com.fasterxml.jackson.annotation.JsonProperty;");
		importSet.add("import com.fasterxml.jackson.annotation.JsonFormat;");
		
		// 获取字段部分
		List<FieldInfo> fieldList = beanInfo.getFieldList();
		if(fieldList!=null && !fieldList.isEmpty()) {
		   for(FieldInfo fInfo : fieldList) {
			   /**
			    * 处理import (按照java bean的规范, json注解应该写在getter方法上面, 如果不想在字段上加上json注解可以将这段代码注释)
			    */
			   String imp = fInfo.getImport();
			   if(imp!=null && !"".equals(imp)) {
				  importSet.add(imp);
			   }
			   
			   // 如果是集合, 则需要导入list
			   if(VariableType.LIST == fInfo.getVariableType()) {
				  importSet.add("import java.util.List;");
			   }
			   
			   // 处理json注解
			   String annotation = fInfo.getAnnotation();
			   if(annotation!=null && !"".equals(annotation)) {
				  fieldBuilder.append(annotation).append(LINE_SEPARATOR);
			   }
			   // 处理字段
			   String fieldSignature = fInfo.getFieldSignature();
			   if(fieldSignature!=null && !"".equals(fieldSignature)) {
				  fieldBuilder.append(fieldSignature).append(LINE_SEPARATOR).append(LINE_SEPARATOR);
			   }
		   }
		}
		
		// 获取方法组部分
		List<MethodGroup> groupList = beanInfo.getMethodGroupList();
		if(groupList!=null && !groupList.isEmpty()) {
		   for(MethodGroup group : groupList) {
			   MethodInfo getter = group.getGetter();
			   MethodInfo setter = group.getSetter();
			   
			   // 处理getter 方法上的 json 注解
			   String annotation = getter.getFieldInfo().getAnnotation();
			   if(annotation!=null && !"".equals(annotation)) {
				  methodBuilder.append(annotation).append(LINE_SEPARATOR);
			   }
			   
			   // 处理 getter 方法
			   String getterSignature = getter.getMethodSignature();
			   if(getterSignature!=null && !"".equals(getterSignature)) {
				  methodBuilder.append(getterSignature).append(LINE_SEPARATOR).append(LINE_SEPARATOR);
			   }
				
			   // 处理 setter 方法
			   String setterSignature = setter.getMethodSignature();
			   if(setterSignature!=null && !"".equals(setterSignature)) {
				  methodBuilder.append(setterSignature).append(LINE_SEPARATOR).append(LINE_SEPARATOR);
			   }
		   }
		}
		
		// 拼接 import 
		if (!importSet.isEmpty()) {
			Iterator<String> iterator = importSet.iterator();
			while (iterator.hasNext()) {
				String imp = iterator.next();
				if (imp != null && !"".equals(imp)) {
					impBuilder.append(imp).append(LINE_SEPARATOR);
				}
			}
		}
		
		if(!importSet.isEmpty()) {
			impBuilder.append(LINE_SEPARATOR);
		}
		
		
		// 组装最终的 Bean 
		builder.append(impBuilder)
		.append("public class ").append(className).append(" { ").append(LINE_SEPARATOR).append(LINE_SEPARATOR)
		.append(fieldBuilder)
		.append(methodBuilder)
		.append("} ")
		;
		
		// 设置文件的最终目录
		String fileDir = (packageDir == null) ? baseDir : packageDir;
		String fileName = fileDir + File.separator + className + ".java";
		// 文件生成的最终路径
		File beanFile = new File(fileName);
		log.info("生成的 Bean 文件   ===> {}", fileName);
		// 创建文件
		FileKit.markFile(beanFile);
		
		try(BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName), true), StandardCharsets.UTF_8))){
			// 写入文件
			bw.write(builder.toString());
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void writerBeanList(List<BeanInfo> beanInfoList, String baseDir) {
		if(beanInfoList!=null && !beanInfoList.isEmpty()) {
			beanInfoList.forEach(beanInfo -> BeanWriter.writerBean(beanInfo, baseDir));
		}
	}


}
