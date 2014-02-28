package com.ericsson.drawing;

public class qvoPort extends O2Port {

	private intBridge brg;
	public qvoPort(String name, String mac, intBridge bridge) {
		super(name, mac);
		brg = bridge;
	}
	public intBridge GetBridge(){
		return brg;
	}
}
