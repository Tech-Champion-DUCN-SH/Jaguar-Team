package com.ericsson.o2top.l2entity;

import java.util.Vector;


public class VM {

	private String id= "";
	private String name = "";
	private String status= "";
	private Vector<Net> networks = new Vector<Net>();
	
	public VM(String id, String name, String status) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
	}

	public void addNet(Vector<Net> nets){
		networks.addAll(nets);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public Vector<Net> getNetworks() {
		return networks;
	}

	
}
