package com.ericsson.drawing;
import java.util.Vector;

public class VirtualMachine extends O2Object {

	Vector<O2Path> pathes = new Vector<O2Path>();
	
	
//	private intBridge brg = null;
//	private Vector<vmPort> vmports = new Vector<vmPort>();
//
	public VirtualMachine(String name) {
		super(name);
	}
	
	public void addPath(){
		O2Path path = new O2Path();
		pathes.add(path);
	}
	
	public void addObject(O2Object obj, int index){
		pathes.get(index).addObject(obj);
	}

	public Vector<O2Path> getPathes() {
		return pathes;
	}

//	public void ConnectBridge(intBridge _brg){
//		brg = _brg;
//	}
//
//	public void AddPort(vmPort p){
//		vmports.add(p);
//	}
//
//	public intBridge GetBridge(){
//		return brg;
//	}
}
