package com.ericsson.drawing;

public class PhyPort extends O2Port {

	private String host;
	private String ip;
	public PhyPort(String name, String mac, String ipaddr, String HostName) {
		super(name, mac);
		host = HostName;
		ip = ipaddr;
	}
	public String GetHostName(){
		return host;
	}
	public String GetIP(){
		return ip;
	}
}
