package com.ericsson.o2top.l2entity;


public class Subnet {

	private String id = "";
	private String name = "";
	private String cidr = "";
	private String start = "";
	private String end = "";
	
	public Subnet(String id, String name, String cidr, String start, String end) {
		super();
		this.id = id;
		this.name = name;
		this.cidr = cidr;
		this.start = start;
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCidr() {
		return cidr;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	
	
}
