package com.example.json.bean;

/**
 * 
 * @author hexian
 * json中配置信息
 */
public class FiledConfig {

	// true 表示忽略该属性, false 表示不忽略该属性
	private boolean ignore = false;
	
	// 覆盖从数据读取到的属性, 比如原先是字符串格式的类型可以改为 Date 类型
	private String overrideType;
	
	// 如果是日期类型, 则在这里指定它的 pattern 
	private String dateFormat;
	
	// 是list? array? map?
	private String colType;
	
	public FiledConfig() {
		
	}

	public FiledConfig(boolean ignore, String overrideType) {
		this.ignore = ignore;
		this.setOverrideType(overrideType);
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public String getOverrideType() {
		return overrideType;
	}

	public void setOverrideType(String overrideType) {
		if(overrideType!=null) {
		   overrideType = overrideType.trim();
		}
		
		this.overrideType = overrideType;
	}
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}
	
	
	// 定义辅助方法 ==========================================================   
	
	


	// 是否有日期格式化
	public boolean hasDateFormat() { 
		return (dateFormat != null && !dateFormat.trim().equals(""));
	}

	// 计算属性: overrideType 不为空时标记才为true
	public boolean isOverrideTypeFlag() {
		return (overrideType != null && !overrideType.trim().equals(""));
	}
	
	public boolean isCollection() {
		return (colType != null && !colType.trim().equals(""));
	}
	
}
