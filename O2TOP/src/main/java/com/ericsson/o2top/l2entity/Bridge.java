package com.ericsson.o2top.l2entity;


import java.util.Vector;

public class Bridge {

	private String name = "";
	private String id = "";
	private String stp = "";
	private Vector<String> ifs = new Vector<String>() ;
	private Vector<Port> ports = new Vector<Port>();
	
	public Bridge(String name, String id, String stp) {
		super();
		this.name = name;
		this.id = id;
		this.stp = stp;
	}
	
	public void addInterface(String strInterface){
		ifs.add(strInterface);
	}
	
	public void addPorts(Vector<Port> ports){
		this.ports.addAll(ports);
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getStp() {
		return stp;
	}

	public Vector<String> getIfs() {
		return ifs;
	}

	public Vector<Port> getPorts() {
		return ports;
	}
	
	
}
