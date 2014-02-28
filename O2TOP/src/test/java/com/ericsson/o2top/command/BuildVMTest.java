package com.ericsson.o2top.command;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ericsson.o2top.O2Builder;
import com.ericsson.o2top.O2Parser;
import com.ericsson.o2top.RawDataStub;
import com.ericsson.o2top.l2entity.NetInterface;

public class BuildVMTest {

	private static Logger mLogger = LoggerFactory.getLogger(BuildVMTest.class);

	@Test
	public void parseTest() {

		O2Builder builder = new O2Builder();
		
		builder.setVms(O2Parser.parseNoveList(RawDataStub.novalist));
		builder.setPorts(O2Parser.parsePortlist(RawDataStub.portlist));
		builder.setSubnets(O2Parser.parseSubnetlist(RawDataStub.subnetlist));
		builder.setNets(O2Parser.parseNetlist(RawDataStub.netlist));
		builder.setInterfaces(O2Parser.parseIfconfig(RawDataStub.ifconfig));
		builder.setBridges_vsctl(O2Parser.parseVsctl(RawDataStub.vsctlshow));
		builder.setBridgesBrctl(O2Parser.parseBridgectl(RawDataStub.bridgectl));
		
//		builder.buildVMs();
		builder.buildExtPath();

	}

}
