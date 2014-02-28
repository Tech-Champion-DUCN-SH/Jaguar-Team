package com.ericsson.o2top.command;

import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ericsson.o2top.RawDataStub;
import com.ericsson.o2top.l2entity.Net;
import com.ericsson.o2top.l2entity.VM;

public class NovaListTest {
	private static Logger mLogger = LoggerFactory.getLogger(NovaListTest.class);

	@Test
	public void parseTest() {
		NovaList novaList = new NovaList(null);
		novaList.setLastCommandPrintout(RawDataStub.novalist);
		Vector<VM> vms = novaList.parse();
		
		for(VM vm:vms){
			mLogger.info("Id = " + vm.getId());
			mLogger.info("Name = " + vm.getName());
			mLogger.info("Status = " + vm.getStatus());
			
			Vector<Net> nets = vm.getNetworks();
			for(Net net: nets){
				mLogger.info("  Net id = "+net.getId());
				mLogger.info("      name = "+net.getName());
				mLogger.info("      addr = "+net.getAddr());
				mLogger.info("      subnetid = "+net.getSubnetid());
			}
		}
	}
}
