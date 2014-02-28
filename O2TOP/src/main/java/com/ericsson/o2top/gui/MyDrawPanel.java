package com.ericsson.o2top.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.ericsson.drawing.O2Object;
import com.ericsson.drawing.O2Path;
import com.ericsson.drawing.VirtualMachine;
import com.ericsson.o2top.O2Builder;

import java.util.Vector;

class MyDrawPanel extends JPanel {
	private O2Builder _b = null;
	public void set_builder(O2Builder b){
		_b = b;
	}
	public void paintComponent(Graphics g) {
		if(_b == null){
			return;
		}
		com.ericsson.drawing.O2LayOut.caluate1(_b);
		int x = getX();
		int y = getY();
		O2Object _ibr = null;
		Vector<com.ericsson.drawing.VirtualMachine> vms = _b.get_VMs();
		for(VirtualMachine vm:vms){
			vm.paint(g);
			Vector<O2Path> paths = vm.getPathes();
			int counter = paths.size();
			if (counter == 0){
				continue;
			}
			for(O2Path path:paths){
				Vector<O2Object> objs = path.getObjects();
				O2Object vmp = objs.get(0);
				O2Object tap = objs.get(1);
				O2Object qbr = objs.get(2);
				O2Object qvb = objs.get(3);
				O2Object qvo = objs.get(4);
				_ibr = objs.get(5);
				vmp.paint(g);
				tap.paint(g);
				qbr.paint(g);
				qvb.paint(g);
				qvo.paint(g);
				g.drawLine(vmp.getX() + vmp.getW()/2, vmp.getY() + vmp.getH(), tap.getX() + tap.getW()/2, tap.getY());
				g.drawLine(qvb.getX() + qvb.getW()/2, qvb.getY() + qvb.getH(), qvo.getX() + qvo.getW()/2, qvo.getY());
//				g.drawRect(vmp.getX(), vmp.getY(), vmp.getW(), vmp.getH());
//				g.drawRect(tap.getX(), tap.getY(), tap.getW(), tap.getH());
//				g.drawRect(qbr.getX(), qbr.getY(), qbr.getW(), qbr.getH());
//				g.drawRect(qvb.getX(), qvb.getY(), qvb.getW(), qvb.getH());
//				g.drawRect(qvo.getX(), qvo.getY(), qvo.getW(), qvo.getH());
			}
		}
		if(_ibr != null){
			_ibr.paint(g);
		}
	}
}
