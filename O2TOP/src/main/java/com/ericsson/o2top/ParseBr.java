package com.ericsson.o2top;

import java.util.Vector;

import com.ericsson.drawing.InterfaceBr;
import com.ericsson.o2top.l2entity.Bridge;
import com.ericsson.o2top.l2entity.NetInterface;

public class ParseBr {
	private Vector<InterfaceBr> interfaceBrs = new Vector<InterfaceBr>();
	
	private Vector<InterfaceBr> phyInterfaces = new Vector<InterfaceBr>();
	
	private Vector<InterfaceBr> brInterfaces = new Vector<InterfaceBr>();
	
	private Vector<InterfaceBr> eths = new Vector<InterfaceBr>();
	
	public Vector<InterfaceBr> getEths() {
		return eths;
	}
	
	public Vector<InterfaceBr> getBrInterfaces() {
		return brInterfaces;
	}

	public Vector<InterfaceBr> getPhyInterface() {
		return phyInterfaces;
	}

	public Vector<InterfaceBr> getInterfaceBrs() {
		return interfaceBrs;
	}
	

	public void setInterfaceBrs(Vector<Bridge> bridges, Vector<NetInterface> netInerfaces) {
		String phyIntName;
		
		for(Bridge bridge : bridges){
			if(bridge.getName().equals("br-int")){
				Vector<String> interfaces = bridge.getIfs();
				for(String interf : interfaces){
					if(interf.startsWith("int-br-")){
						InterfaceBr interBr = new InterfaceBr();
						
						interBr.setName(interf);
						interBr.setUpName(bridge.getName());
						
						phyIntName = interf.replace("int", "phy");
						
						for(NetInterface netIn:netInerfaces){
							if(netIn.getName().equals(phyIntName)){
								interBr.setDownName(phyIntName);
								break;
							}
						}
							
						this.interfaceBrs.add(interBr);
					}
				}
			}	
		}
	}
	
	
	public void setPhyInterface(Vector<Bridge> bridges, Vector<NetInterface> netInerfaces) {
		
		
		for(Bridge bridge : bridges){
		   Vector<String> ints= bridge.getIfs();
		   
		   for(String inter : ints){
			   if(inter.startsWith("phy-br-")){
				   InterfaceBr phyinterBr =  new InterfaceBr();
				   phyinterBr.setName(inter);
				   phyinterBr.setDownName(bridge.getName());
                   
				   for(InterfaceBr inter1: this.interfaceBrs){
					   if(inter1.getDownName().equals(phyinterBr.getName())){
						   phyinterBr.setUpName(inter1.getName());
						   break;
					   }
				   }
				   
				   this.phyInterfaces.add(phyinterBr);
			   }
				   
		   }
		}
	}
	
	public void setBrInterfaces(Vector<Bridge> bridges, Vector<NetInterface> netInerfaces) {
		
		
		for(Bridge bridge : bridges){
			   Vector<String> ints= bridge.getIfs();
			   
			   for(String inter : ints){
				   if(inter.startsWith("phy-br-")){
					   InterfaceBr brInterface =  new InterfaceBr();
					   
					   brInterface.setName(bridge.getName());
					   brInterface.setUpName(inter);
					   brInterface.setDownName(ints.firstElement());
					   
					   this.brInterfaces.add(brInterface);
				   }	   
			   }
			}
	}
	
	public void setEths(Vector<Bridge> bridges, Vector<NetInterface> netInerfaces) {
        
		
        for(InterfaceBr brInter : brInterfaces){
        	InterfaceBr eth =  new InterfaceBr();
        	
        	eth.setName(brInter.getDownName());
        	eth.setUpName(brInter.getName());
        	eth.setDownName("");
        	this.eths.add(eth);
        }
        
    }
}
