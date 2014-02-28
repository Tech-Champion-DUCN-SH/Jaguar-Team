package com.ericsson.o2top.l2entity;

public class Port {

	private String id = "";
	private String name = "";
	private String mac = "";
	private String subnetID = "";
	private String ip = "";
	private String tag = "";
	
	public Port(String id, String name, String mac, String subnetID,String ip) {
		super();
		this.id = id;
		this.name = name;
		this.mac = mac;
		this.subnetID = subnetID;
		this.ip = ip;
	}
	
	public Port(String name, String tag){
		this.name = name;
		this.tag = tag;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMac() {
		return mac;
	}

	public String getSubnetID() {
		return subnetID;
	}

	public String getIp() {
		return ip;
	}

	public String getTag() {
		return tag;
	}
	
	
	
}
