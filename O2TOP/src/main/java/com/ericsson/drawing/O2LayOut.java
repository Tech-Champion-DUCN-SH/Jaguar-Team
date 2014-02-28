package com.ericsson.drawing;

import com.ericsson.drawing.O2Path;
import com.ericsson.o2top.O2Builder;

import java.util.Vector;

public class O2LayOut {
	public static int factor = 2;
	final static int G_INDENT_X1 = 5 * factor;
	final static int G_INDENT_X2 = 10 * factor;
	final static int G_INDENT_X3 = 20 * factor;
	final static int G_INDENT_Y1 = 5 * factor;
	final static int G_INDENT_Y2 = 10 * factor;

	final static int G_DEFAULT_H1 = 5 * factor;
	final static int G_DEFAULT_H2 = 10 * factor;
	final static int G_DEFAULT_W1 = 10 * factor;
	final static int G_DEFAULT_W2 = 20 * factor;

	final static int VM_X0 = G_INDENT_X1;
	final static int VM_Y0 = G_INDENT_Y1;
	final static int VM_YH = G_INDENT_Y2;

	final static int VM_PORT_X0 = VM_X0 + G_INDENT_X1;
	final static int VM_PORT_Y0 = VM_Y0 + VM_YH;
	final static int VM_PORT_XW = G_DEFAULT_W1;
	final static int VM_PORT_YH = G_INDENT_Y1;
	
	final static int TAP_PORT_X0 = VM_PORT_X0;
	final static int TAP_PORT_XW = VM_PORT_XW;
	final static int TAP_PORT_Y0 = VM_PORT_Y0 + +VM_PORT_YH + G_DEFAULT_H2;
	final static int TAP_PORT_YH = VM_PORT_YH;

	final static int QBR_BRG_X0 = VM_X0;
	final static int QBR_BRG_Y0 = TAP_PORT_Y0 + TAP_PORT_YH;
	final static int QBR_BRG_XW = TAP_PORT_XW + G_INDENT_X2;
	final static int QBR_BRG_YH = VM_YH;

	final static int QVB_PORT_X0 = TAP_PORT_X0;
	final static int QVB_PORT_Y0 = QBR_BRG_Y0 + QBR_BRG_YH;
	final static int QVB_PORT_XW = TAP_PORT_XW;
	final static int QVB_PORT_YH = TAP_PORT_YH;

	final static int QVO_PORT_X0 = QVB_PORT_X0;
	final static int QVO_PORT_Y0 = QVB_PORT_Y0 + QVB_PORT_YH + G_INDENT_Y2;
	final static int QVO_PORT_XW = QVB_PORT_XW;
	final static int QVO_PORT_YH = QVB_PORT_YH;

	final static int INT_BRG_X0 = 0;
	final static int INT_BRG_Y0 = QVO_PORT_Y0 + QVO_PORT_YH;
	final static int INT_BRG_YH = G_DEFAULT_H2;

	private O2Builder _b;
	public O2LayOut(O2Builder b) {
		_b = b;
	}
	public static void caluate1(O2Builder builder){
		int vm_x = VM_X0, vm_xw = 0;
		Vector<VirtualMachine> vms = builder.get_VMs();
		O2Object _ibr;
		for(VirtualMachine vm:vms){
			vm.setY(VM_Y0);
			vm.setH(VM_YH);
			Vector<O2Path> paths = vm.getPathes();
			int counter = paths.size();
			vm.setX(vm_x);
			vm_xw = QBR_BRG_XW;
			counter --;
			if(counter > 0){
				vm_xw = (G_INDENT_X1 + QBR_BRG_XW) * counter + vm_xw;
			}
			vm.setW(vm_xw);
			int path_x = vm_x;
			vm_x = vm_x + vm_xw + G_INDENT_X1;
			if (counter < 0){
				continue;
			}
			for(O2Path path:paths){
				Vector<O2Object> objs = path.getObjects();
				O2Object vmp = objs.get(0);
				O2Object tap = objs.get(1);
				O2Object qbr = objs.get(2);
				O2Object qvb = objs.get(3);
				O2Object qvo = objs.get(4);
				O2Object ibr = objs.get(5);
				_ibr = ibr;

				vmp.setW(VM_PORT_XW);
				vmp.setH(VM_PORT_YH);
				vmp.setY(VM_PORT_Y0);
				vmp.setX(path_x + G_INDENT_X1);

				tap.setW(TAP_PORT_XW);
				tap.setH(TAP_PORT_YH);
				tap.setY(TAP_PORT_Y0);
				tap.setX(path_x + G_INDENT_X1);

				qbr.setW(QBR_BRG_XW);
				qbr.setH(QBR_BRG_YH);
				qbr.setY(QBR_BRG_Y0);
				qbr.setX(path_x);

				qvb.setW(QVB_PORT_XW);
				qvb.setH(QVB_PORT_YH);
				qvb.setY(QVB_PORT_Y0);
				qvb.setX(path_x + G_INDENT_X1);

				qvo.setW(QVO_PORT_XW);
				qvo.setH(QVO_PORT_YH);
				qvo.setY(QVO_PORT_Y0);
				qvo.setX(path_x + G_INDENT_X1);
				path_x = path_x + QBR_BRG_XW + G_INDENT_X1;

				_ibr.setX(INT_BRG_X0);
				_ibr.setY(INT_BRG_Y0);
				_ibr.setH(INT_BRG_YH);
				_ibr.setW(vm_x + vm_xw);
			}
		}

	}
	public void caluate(){
		Vector<VirtualMachine> vms = _b.get_VMs();
		Vector<qbrBridge> qbrs = _b.getQbrBridges();
		Vector<TapPort> taps = _b.getTapPorts();
		for(qbrBridge qbr:qbrs){
			qbr.setW(QBR_BRG_XW);
			qbr.setH(QBR_BRG_YH);
		}
		
		int vm_x = VM_X0, vm_xw = 0;
		for(VirtualMachine vm:vms){
			int counter = 0;
			vm.setY(VM_Y0);
			vm.setH(VM_YH);
			for(TapPort tap:taps){
				vmPort vmp = (vmPort)tap.getCompanion();
				if(vmp.getVM() == vm) {
					//set X, Y, W, H;
					counter++;
				}
			}
			vm.setX(vm_x);
			vm_xw = (VM_PORT_XW + G_INDENT_X2);
			counter --;
			if(counter > 0){
				vm_xw = (G_INDENT_X1 + vm_xw) * counter + vm_xw;
			}
			vm.setW(vm_xw);
			vm_x = vm_x + vm_xw + G_INDENT_X1;
		}
	}

}
