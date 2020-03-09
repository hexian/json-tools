package com.example.json.bean;

/**
 * 
 * @author hexian
 *
 */
public class MethodGroup {

	private int position = 0;
	
	private MethodInfo getter;
	
	private MethodInfo setter;

	public MethodInfo getGetter() {
		return getter;
	}

	public void setGetter(MethodInfo getter) {
		this.getter = getter;
	}

	public MethodInfo getSetter() {
		return setter;
	}

	public void setSetter(MethodInfo setter) {
		this.setter = setter;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	
	
}
