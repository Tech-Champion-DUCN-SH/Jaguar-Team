package com.ericsson.drawing;

public class vmPort extends O2Port {

	private VirtualMachine vm;
	public vmPort(String name, String macAddr, VirtualMachine _vm) {
		super(name, macAddr);
		vm = _vm;
	}
	public VirtualMachine getVM(){
		return vm;
	}
}
