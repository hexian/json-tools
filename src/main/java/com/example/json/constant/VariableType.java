package com.example.json.constant;

/**
 * 
 * @author hexian
 *
 */
public enum VariableType {
	
	NONE("none",   "当前变量不是集合, MAP,数组"),
	ARRAY("array", "当前变量是数组"),
	LIST("list",   "当前变量是集合"),
	MAP("map",     "当前变量是MAP");
	
	private String key;
	
	private String remark;
	
	private VariableType(String key, String remark) {
		this.key = key;
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
	
	public static VariableType getVariableType(String typeName) {  
		if(typeName==null || "".equals(typeName.trim())) {
		   return VariableType.NONE;  
		}
		typeName = typeName.trim();
		
        for(VariableType c : VariableType.values()) {  
            if(c.key.equalsIgnoreCase(typeName)) {  
               return c;
            }  
        } 
        
        return VariableType.NONE;  
    } 
	
}