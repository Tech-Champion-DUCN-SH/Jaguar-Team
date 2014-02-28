package com.ericsson.drawing;

public class brgPort extends O2Port {

	private Bridge bridge;
	public brgPort(String name, String macAddr, Bridge brg) {
		super(name, macAddr);
		bridge = brg;
		// TODO Auto-generated constructor stub
	}
	public Bridge getBridge(){
		return bridge;
	}
}
