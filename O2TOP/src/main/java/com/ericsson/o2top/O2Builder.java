package com.ericsson.o2top;

import java.util.Vector;

import com.ericsson.drawing.InterfaceBr;
import com.ericsson.drawing.O2Object;
import com.ericsson.drawing.O2Path;
import com.ericsson.drawing.O2Port;
import com.ericsson.drawing.TapPort;
import com.ericsson.drawing.VirtualMachine;
import com.ericsson.o2top.l2entity.Bridge;
import com.ericsson.o2top.l2entity.Net;
import com.ericsson.o2top.l2entity.NetInterface;
import com.ericsson.o2top.l2entity.Port;
import com.ericsson.o2top.l2entity.Subnet;
import com.ericsson.o2top.l2entity.VM;

public class O2Builder {

	Vector<VM> vms = new Vector<VM>();
	Vector<Bridge> bridges_brctl = new Vector<Bridge>();
	Vector<Bridge> bridges_vsctl = new Vector<Bridge>();
	Vector<Net> nets = new Vector<Net>();
	Vector<NetInterface> netifs = new Vector<NetInterface>();
	Vector<Port> ports = new Vector<Port>();
	Vector<Subnet> subnets  = new Vector<Subnet>();
	
	Vector<VirtualMachine> myvms = new Vector<VirtualMachine>();
	Vector<com.ericsson.drawing.intBridge> intBridges = new Vector<com.ericsson.drawing.intBridge>();
	Vector<com.ericsson.drawing.outBridge> outBridges = new Vector<com.ericsson.drawing.outBridge>();
	Vector<com.ericsson.drawing.qbrBridge> qbrBridges = new Vector<com.ericsson.drawing.qbrBridge>();
	Vector<com.ericsson.drawing.qvoPort> qvoPorts = new Vector<com.ericsson.drawing.qvoPort>();
	Vector<com.ericsson.drawing.qvbPort> qvbPorts = new Vector<com.ericsson.drawing.qvbPort>();
	Vector<com.ericsson.drawing.TapPort> tapPorts = new Vector<com.ericsson.drawing.TapPort>();
	Vector<com.ericsson.drawing.brgPort> brgPorts = new Vector<com.ericsson.drawing.brgPort>();
	Vector<com.ericsson.drawing.PhyPort> phyPorts = new Vector<com.ericsson.drawing.PhyPort>();
	Vector<com.ericsson.drawing.vmPort> vmPorts = new Vector<com.ericsson.drawing.vmPort>();

	public void setVms(Vector<VM> vms) {
		this.vms.addAll(vms);
	}
	
	public void setBridgesBrctl(Vector<Bridge> bridges_brctl) {
		this.bridges_brctl.addAll(bridges_brctl);
	}
	
	public void setBridges_vsctl(Vector<Bridge> bridges_vsctl) {
		this.bridges_vsctl.addAll(bridges_vsctl);
	}
	
	public void setNets(Vector<Net> nets) {
		this.nets.addAll(nets);
	}
	
	public void setInterfaces(Vector<NetInterface> interfaces) {
		this.netifs.addAll(interfaces);
	}
	
	public void setPorts(Vector<Port> ports) {
		this.ports.addAll(ports);
	}
	
	public void setSubnets(Vector<Subnet> subnets) {
		this.subnets.addAll(subnets);
	}

	public Vector<com.ericsson.drawing.intBridge> getIntBridges() {
		return intBridges;
	}

	public void setIntBridges(Vector<com.ericsson.drawing.intBridge> intBridges) {
		this.intBridges = intBridges;
	}

	public Vector<com.ericsson.drawing.outBridge> getOutBridges() {
		return outBridges;
	}

	public void setOutBridges(Vector<com.ericsson.drawing.outBridge> outBridges) {
		this.outBridges = outBridges;
	}

	public Vector<com.ericsson.drawing.qbrBridge> getQbrBridges() {
		return qbrBridges;
	}

	public void setQbrBridges(Vector<com.ericsson.drawing.qbrBridge> qbrBridges) {
		this.qbrBridges = qbrBridges;
	}

	public Vector<com.ericsson.drawing.VirtualMachine> get_VMs() {
		return myvms;
	}

	public void set_VMs(Vector<com.ericsson.drawing.VirtualMachine> _VMs) {
		this.myvms = _VMs;
	}

	public Vector<com.ericsson.drawing.qvoPort> getQvoPorts() {
		return qvoPorts;
	}

	public void setQvoPorts(Vector<com.ericsson.drawing.qvoPort> qvoPorts) {
		this.qvoPorts = qvoPorts;
	}

	public Vector<com.ericsson.drawing.qvbPort> getQvbPorts() {
		return qvbPorts;
	}

	public void setQvbPorts(Vector<com.ericsson.drawing.qvbPort> qvbPorts) {
		this.qvbPorts = qvbPorts;
	}

	public Vector<com.ericsson.drawing.TapPort> getTapPorts() {
		return tapPorts;
	}

	public void setTapPorts(Vector<com.ericsson.drawing.TapPort> tapPorts) {
		this.tapPorts = tapPorts;
	}

	public Vector<com.ericsson.drawing.brgPort> getBrgPorts() {
		return brgPorts;
	}

	public void setBrgPorts(Vector<com.ericsson.drawing.brgPort> brgPorts) {
		this.brgPorts = brgPorts;
	}

	public Vector<com.ericsson.drawing.vmPort> getVmPorts() {
		return vmPorts;
	}

	public void setVmPorts(Vector<com.ericsson.drawing.vmPort> vmPorts) {
		this.vmPorts = vmPorts;
	}

	public Vector<Subnet> getSubnets() {
		return subnets;
	}
	
	//TODO: generics?
	private Vector<O2Port> findMacByNameInPortlist(String name){
		
		Vector<O2Port> myports = new Vector<O2Port>();
		for(Port myport:ports){
			
			if(myport.getName().equals(name)){
				O2Port o2port = new O2Port(myport.getName(),myport.getMac());
				myports.add(o2port);
			}
		}
		
		return myports;
	}
	
	private String findIfnameByMac(String mac){
		
		String ifname = "";
		
		mac = mac.substring(mac.indexOf(":")+1, mac.length());
		for(NetInterface netif:netifs){
			String mymac = netif.getMac();
			mymac = mymac.substring(mymac.indexOf(":")+1, mymac.length());
			if(mac.equalsIgnoreCase(mymac)){
				ifname = netif.getName();
			}
		}
		
		return ifname;
	}
	
	private Bridge findBridgeByIfname(String ifname){
		
		Bridge ret=null;
		for(Bridge bridge:bridges_brctl){
			Vector<String> ifs = bridge.getIfs();
			
			for(String strif:ifs){
				if(strif.equalsIgnoreCase(ifname)){
					ret = bridge;
				}
			}
		}
		return ret;
	}
	
	
	public void buildVMs(){
		
		for(VM vm:vms){
			VirtualMachine myvm = new VirtualMachine(vm.getName());
			
			Vector<O2Port> myo2ports = findMacByNameInPortlist(vm.getName());
			int index = 0;
			for(O2Port myo2port:myo2ports){
				
				String ifname = findIfnameByMac(myo2port.getMac());
				
				Bridge bridge = findBridgeByIfname(ifname);
				if(bridge != null){
					myvm.addPath();
					
					O2Object obj0 = new O2Object("eth"+index);
					myvm.addObject(obj0, index);
					
					O2Object obj1 = new O2Object(ifname);
					myvm.addObject(obj1, index);
					
					O2Object obj2 = new O2Object(bridge.getName());
					myvm.addObject(obj2, index);
					
					String peerif = bridge.getIfs().get(0);
					O2Object obj3 = new O2Object(peerif);
					myvm.addObject(obj3, index);
					
					peerif = peerif.replaceAll("qvb", "qvo");
					Bridge bridge2 = findBridgeByIfname(peerif);
					
					O2Object obj4 = new O2Object(peerif);
					myvm.addObject(obj4, index);
					
					O2Object obj5 = new O2Object(bridge2.getName());
					myvm.addObject(obj5, index);
					
					index++;
				}
				
			}
			myvms.add(myvm);
		}
	}

	public Vector<O2Path> buildExtPath(){
		
		ParseBr parser = new ParseBr();
		parser.setInterfaceBrs(bridges_brctl, netifs);
		parser.setPhyInterface(bridges_brctl, netifs);
		parser.setBrInterfaces(bridges_brctl, netifs);
		parser.setEths(bridges_brctl, netifs);
		
		Vector<O2Path> pathes = new Vector<O2Path>();
		for(InterfaceBr br1:parser.getInterfaceBrs() ){
			O2Path path = new O2Path();
			
			path.addObject(new O2Object(br1.getName()));
			
			for(InterfaceBr br2: parser.getPhyInterface()){
				if(br2.getName().equals(br1.getDownName())){
					path.addObject(new O2Object(br2.getName()));
					
					for(InterfaceBr br3: parser.getBrInterfaces()){
						if(br3.getName().equals(br2.getDownName())){
							path.addObject(new O2Object(br3.getName()));
							path.addObject(new O2Object(br3.getDownName()));
						}
					}
				}
			}
			pathes.add(path);
		}
		return pathes;
	}
}
