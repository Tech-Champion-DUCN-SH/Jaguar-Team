package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.Net;
import com.ericsson.o2top.session.SessionSsh;

public class QuantumNetList extends OpenStackCommand {
	private Vector<Net> mNetVector;

	public QuantumNetList(SessionSsh session) {
		super(session, "quantum net-list");
	}

	@Override
	public String exec() throws RemoteCliException {
		super.exec();
		mNetVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<Net> getNetVector() {
		return mNetVector;
	}

	public Vector<Net> parse() {

		Vector<Net> nets = new Vector<Net>();
		String netlist = getLastCommandPrintout();
		// TODO: linux \n: ^.*ID.*Networks.*$ does not work
		netlist = netlist.replaceAll("\\|.*id.*subnets.*\r\n", "");
		netlist = netlist.replaceAll("\\+.*\\+\r\n", "");
		netlist = netlist.replaceAll("\\+.*\\+", "");

		String[] strnets = netlist.split("\r\n");
		for (String strnet : strnets) {

			// NOTE: str[0] is empty
			String[] attrs = strnet.split("\\|");

			Net net = new Net(attrs[1].trim(), attrs[2].trim(), attrs[3].trim().split(" ")[0]);
			nets.add(net);

		}

		return nets;
	}

}
