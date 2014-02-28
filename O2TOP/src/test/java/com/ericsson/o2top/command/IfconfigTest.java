package com.ericsson.o2top.command;

import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ericsson.o2top.RawDataStub;
import com.ericsson.o2top.l2entity.NetInterface;
import com.ericsson.o2top.l2entity.VM;
import com.ericsson.o2top.command.Ifconfig;

public class IfconfigTest {
	
	private static Logger mLogger = LoggerFactory.getLogger(NovaListTest.class);

	@Test
	public void parseTest() {

		Ifconfig ifconfig = new Ifconfig(null);
		ifconfig.setLastCommandPrintout(RawDataStub.ifconfig);

		Vector<NetInterface> netifs = ifconfig.parse();
		
		for(NetInterface netif:netifs){
			mLogger.info("Name = " + netif.getName());
		}
	}

}
