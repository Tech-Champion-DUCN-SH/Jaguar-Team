package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.Bridge;
import com.ericsson.o2top.l2entity.Port;
import com.ericsson.o2top.session.SessionSsh;

public class OvsVsctlShow extends OpenStackCommand {
	private String mRootPassword;
	private Vector<Bridge> mBridgeVector;

	public OvsVsctlShow(SessionSsh session, String rootPassword) {
		super(session, "sudo ovs-vsctl show");
		mRootPassword = rootPassword;
	}

	@Override
	public String exec() throws RemoteCliException {
		String oldPrompt = getSession().setPrompt(".*?localadmin: ");
		super.exec();
		getSession().setPrompt(oldPrompt);
		mCommandPrintout = getSession().exec(mRootPassword);
		mBridgeVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<Bridge> getBridgeVector() {
		return mBridgeVector;
	}

	public Vector<Bridge> parse() {

		Vector<Bridge> bridges = new Vector<Bridge>();
		String vsctl = getLastCommandPrintout();
		// remove tail
		vsctl = vsctl.replaceAll("[ ]*ovs_version:.*", "");

		String[] strbridges = vsctl.split("Bridge");
		for (int i = 1; i < strbridges.length; i++) {
			Vector<Port> ports = new Vector<Port>();

			// NOTE: index[0] is invalid
			String[] strports = strbridges[i].split("Port");

			// index[0]->bridge name
			String bridgeName = strports[0].trim();

			for (int j = 1; j < strports.length; j++) {
				String strport = strports[j].trim();
				String strportname = strport.substring(0, strport.indexOf(" "));
				strportname = strportname.replaceAll("\r\n", "");

				int pos = -1, start;
				String tag = "";
				if ((pos = strport.indexOf("tag:")) != -1) {
					start = pos + "tag".length() + 1;
					tag = strport.substring(start, strport.indexOf("\n", start));
				}

				Port port = new Port(strportname, tag);
				ports.add(port);
			}

			Bridge bridge = new Bridge(bridgeName, "", "");
			bridge.addPorts(ports);

			bridges.add(bridge);

		}

		return bridges;
	}

}
