package com.ericsson.o2top;

import java.util.Vector;

import com.ericsson.o2top.l2entity.Bridge;
import com.ericsson.o2top.l2entity.Net;
import com.ericsson.o2top.l2entity.NetInterface;
import com.ericsson.o2top.l2entity.Port;
import com.ericsson.o2top.l2entity.Subnet;
import com.ericsson.o2top.l2entity.VM;


public class O2Parser {
	public static Vector<VM> parseNoveList(String novalist){
		
		Vector<VM> vms = new Vector<VM>();

		// TODO: linux \n: ^.*ID.*Networks.*$ does not work

		novalist = novalist.replaceAll("\\|.*ID.*Networks.*\n", "");
		novalist = novalist.replaceAll("\\+.*\\+\n", "");
		
		String[] strvms = novalist.split("\\n");
		for(String strvm : strvms){
			
			//NOTE: str[0] is empty

			String[] attrs = strvm.split("\\|");
			
			VM vm = new VM(attrs[1].trim(),attrs[2].trim(),attrs[3].trim());
			
			Vector<Net> nets = parseNovaNetwork(attrs[4].trim());
			vm.addNet(nets);
			
			vms.add(vm);
		}

		return vms;
	}

	private static Vector<Net> parseNovaNetwork(String strnetworks){
		
		Vector<Net> nets = new Vector<Net>();
		
		String[] strnets = strnetworks.split(";");
		for(String strnet: strnets){
			String[] attrs = strnet.split("=");
			Net net = new Net(attrs[0].trim(), attrs[1].trim());
			nets.add(net);
		}
		
		return nets;
	}
	
	public static Vector<Port> parsePortlist(String portlist){
		
		Vector<Port> ports = new Vector<Port>();

		// TODO: linux \n: ^.*ID.*Networks.*$ does not work

		portlist = portlist.replaceAll("\\|.*id.*fixed_ips.*\n", "");
		portlist = portlist.replaceAll("\\+.*\\+\n", "");
		
		String[] strports = portlist.split("\\n");
		for(String strport : strports){
			
			//NOTE: str[0] is empty

			String[] attrs = strport.split("\\|");
			String[] fixed_ips = attrs[4].trim().replaceAll("[\"\\{\\} ]", "").split(",");

			Port port = new Port(
					attrs[1].trim(),
					attrs[2].trim(),
					attrs[3].trim(),
					fixed_ips[0].split(":")[1],
					fixed_ips[1].split(":")[1]
					);
			ports.add(port);
			
		}

		return ports;
	}
	
	
	public static Vector<Subnet> parseSubnetlist(String subnetlist){
		
		Vector<Subnet> subnets = new Vector<Subnet>();

		// TODO: linux \n: ^.*ID.*Networks.*$ 

		subnetlist = subnetlist.replaceAll("\\|.*id.*allocation_pools.*\n", "");
		subnetlist = subnetlist.replaceAll("\\+.*\\+\n", "");
		
		String[] strsubnets = subnetlist.split("\\n");
		for(String strsubnet : strsubnets){
			
			//NOTE: str[0] is empty

			String[] attrs = strsubnet.split("\\|");
			String[] allocation_pools = attrs[4].trim().replaceAll("[\"\\{\\} ]", "").split(",");
			
			Subnet subnet = new Subnet(
					attrs[1].trim(),
					attrs[2].trim(),
					attrs[3].trim(),
					allocation_pools[0].split(":")[1],
					allocation_pools[1].split(":")[1]
					);
			subnets.add(subnet);
			
		}

		return subnets;
	}

	public static Vector<Net> parseNetlist(String netlist){
		
		Vector<Net> nets = new Vector<Net>();

		// TODO: linux \n: ^.*ID.*Networks.*$ does not work

		netlist = netlist.replaceAll("\\|.*id.*subnets.*\n", "");
		netlist = netlist.replaceAll("\\+.*\\+\n", "");
		
		String[] strnets = netlist.split("\\n");
		for(String strnet : strnets){
			
			//NOTE: str[0] is empty

			String[] attrs = strnet.split("\\|");
			
			Net net = new Net(
					attrs[1].trim(),
					attrs[2].trim(),
					attrs[3].trim().split(" ")[0]
					);
			nets.add(net);
			
		}

		return nets;
	}

	public static Vector<Bridge> parseVsctl(String vsctl){
		
		Vector<Bridge> bridges = new Vector<Bridge>();
		
		//remove tail

		vsctl = vsctl.replaceAll("[ ]*ovs_version:.*\\n", "");
		
		String[] strbridges = vsctl.split("Bridge");
		for(int i=1; i<strbridges.length; i++)
		{
			Vector<Port> ports = new Vector<Port>();
			
			//NOTE: index[0] is invalid

			String[] strports = strbridges[i].split("Port");
			
			//index[0]->bridge name

			String bridgeName = strports[0].trim();
			
			for(int j=1; j<strports.length; j++)
			{
				String strport = strports[j].trim();
				String strportname = strport.substring(0,strport.indexOf(" "));
				
				int pos = -1, start;
				String tag="";
				if((pos = strport.indexOf("tag:")) != -1){
					start = pos + "tag".length() + 1;
					tag = strport.substring(start,strport.indexOf("\n",start));
				}
				
				Port port = new Port(strportname, tag);
				ports.add(port);
			}
			
			Bridge bridge = new Bridge(bridgeName,"","");
			bridge.addPorts(ports);
			
			bridges.add(bridge);
			
		}
		
		return bridges;
	}
	
	public static Vector<Bridge> parseBridgectl(String brctl){
		
		Vector<Bridge> bridges = new Vector<Bridge>();
		
		brctl = brctl.replaceAll("bridge.*interfaces.*\\n", "");
		String[] strlines = brctl.split("\\n");
		
		for(int i=0;i<strlines.length;i++){
			String strline = strlines[i].trim();
			
			if(strline.contains(" ")){
				String[] strbridge = strline.split("[ ]+");
				Bridge bridge = new Bridge(strbridge[0],strbridge[1],strbridge[2]);
				if(strbridge.length == 4){
					bridge.addInterface(strbridge[3].trim());
				}/* else leave interface empty */
				bridges.add(bridge);
			}
			else
			{
				bridges.lastElement().addInterface(strline);
			}
		}
		
		return bridges;
	}
	
	public static Vector<NetInterface> parseIfconfig(String ifconfig){

		Vector<NetInterface> netifs = new Vector<NetInterface>();
		
		String[] strnetifs = ifconfig.split("\\n[ ]*\\n");
		
		for (String strnetif : strnetifs) {
			int i = strnetif.indexOf(" ");
			String ifname = strnetif.substring(0, i);
			int j = strnetif.indexOf("HWaddr");
			int start = j+"HWaddr".length()+1;
			String mac = strnetif.substring(start,strnetif.indexOf(" ",start));

			NetInterface netif = new NetInterface(ifname,mac,strnetif.contains("Interrupt"));
			netifs.add(netif);
		}
		
		return netifs;

	}

}
