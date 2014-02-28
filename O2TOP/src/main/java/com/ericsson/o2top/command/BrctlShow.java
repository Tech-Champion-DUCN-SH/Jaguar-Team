package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.Bridge;
import com.ericsson.o2top.session.SessionSsh;

public class BrctlShow extends OpenStackCommand {
	private Vector<Bridge> mBridgeVector;

	public BrctlShow(SessionSsh session) {
		super(session, "brctl show");
	}

	@Override
	public String exec() throws RemoteCliException {
		super.exec();
		mBridgeVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<Bridge> getBridgeVector() {
		return mBridgeVector;
	}

	public Vector<Bridge> parse() {

		Vector<Bridge> bridges = new Vector<Bridge>();
		String brctl = getLastCommandPrintout();
		brctl = brctl.replaceAll("bridge.*interfaces.*\r\n", "");
		brctl = brctl.replaceAll("\t", " ");
		String[] strlines = brctl.split("\r\n");

		for (int i = 0; i < strlines.length; i++) {
			String strline = strlines[i].trim();

			if (strline.contains(" ")) {
				String[] strbridge = strline.split("[ ]+");
				Bridge bridge = new Bridge(strbridge[0], strbridge[1], strbridge[2]);
				if (strbridge.length == 4) {
					bridge.addInterface(strbridge[3].trim());
				}/* else leave interface empty */
				bridges.add(bridge);
			} else {
				bridges.lastElement().addInterface(strline);
			}
		}

		return bridges;
	}

}
