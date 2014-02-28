package com.ericsson.drawing;

import java.util.Vector;

public class O2Path {
	Vector<O2Object> objects = new Vector<O2Object>();
	
	public Vector<O2Object> getObjects() {
		return objects;
	}

	public void addObject(O2Object obj){
		objects.add(obj);
	}
	

}
