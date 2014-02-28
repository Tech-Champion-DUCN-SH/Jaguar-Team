package com.ericsson.o2top.l2entity;


public class NetInterface {

	private String name = "";
	private String mac = "";
	private boolean isPhysical = false;

	public NetInterface(String name, String mac, boolean isPhysical) {
		super();
		this.name = name;
		this.mac = mac;
	}

	public String getName() {
		return name;
	}
	
	public String getMac(){
		return mac;
	}
	
	
}
