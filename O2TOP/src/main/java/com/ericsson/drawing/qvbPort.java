package com.ericsson.drawing;

public class qvbPort extends O2Port {

	private qbrBridge _qbr;
	public qvbPort(String name, String macAddr, qbrBridge qbr) {
		super(name, macAddr);
		_qbr = qbr;
	}
	public qbrBridge GetBridge(){
		return _qbr;
	}

}
