package com.ericsson.o2top.command;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ericsson.o2top.RawDataStub;
import com.ericsson.o2top.l2entity.Bridge;
import com.ericsson.o2top.l2entity.Port;

public class OvsVsctlShowTest {

	private static Logger mLogger = LoggerFactory.getLogger(NovaListTest.class);

	@Test
	public void parseTest() {

		OvsVsctlShow show = new OvsVsctlShow(null, null);
		show.setLastCommandPrintout(RawDataStub.vsctlshow);

		Vector<Bridge> bridges = show.parse();
		for (Bridge bridge : bridges) {

			mLogger.info("ID = " + bridge.getId());
			mLogger.info("Name = " + bridge.getName());
			mLogger.info("STP = " + bridge.getStp());

			Vector<String> ifs = bridge.getIfs();
			mLogger.info("Interface:");
			for (String strif : ifs) {
				mLogger.info("    :=" + strif);
			}

			Vector<Port> ports = bridge.getPorts();
			mLogger.info("Ports:");
			for (Port port : ports) {
				mLogger.info("     :=" + port.getId() + "," + port.getIp() + "," + port.getMac() + "," + port.getName()
						+ "," + port.getSubnetID() + "," + port.getTag());
			}
		}
	}

}
