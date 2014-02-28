package com.ericsson.drawing;

import java.util.Vector;

import com.ericsson.o2top.l2entity.Bridge;

public class InterfaceBr {

    
	private String downName;
	
	private String upName;
	
	private String name;
    
	public InterfaceBr(String outName, String inName, String name){
		this.downName= outName;
		this.upName = inName;
		this.name = name;
	}
	
	public InterfaceBr() {
		// TODO Auto-generated constructor stub
	}

	public String getDownName() {
		return downName;
	}

	public void setDownName(String outName) {
		this.downName = outName;
	}

	public String getUpName() {
		return upName;
	}

	public void setUpName(String name) {
		this.upName = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	
	
}
