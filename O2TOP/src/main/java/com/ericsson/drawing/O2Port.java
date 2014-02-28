package com.ericsson.drawing;

public class O2Port extends O2Object {

	private O2Port companion;
	private String mac;
	public O2Port(String name, String macAddr) {
		super(name);
		mac = macAddr;
		// TODO Auto-generated constructor stub
	}
	public void connect(O2Port c){
		companion = c;
	}
	public O2Port getCompanion(){
		return companion;
	}
	public String getMac(){
		return mac;
	}
}
