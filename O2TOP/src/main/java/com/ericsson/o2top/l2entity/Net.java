package com.ericsson.o2top.l2entity;

public class Net {

	private String id = "";
	private String name = "";
	private String subnetid = "";
	private String addr = "";
	
	public Net(String id, String name, String subnets) {
		super();
		this.id = id;
		this.name = name;
		this.subnetid = subnets;
	}
	
	public Net(String name, String addr){
		super();
		this.name = name;
		this.addr = addr;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSubnetid() {
		return subnetid;
	}

	public String getAddr() {
		return addr;
	}
	
	
}
