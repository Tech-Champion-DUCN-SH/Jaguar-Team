package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.Net;
import com.ericsson.o2top.l2entity.VM;
import com.ericsson.o2top.session.SessionSsh;

public class NovaList extends OpenStackCommand {
	private Vector<VM> mVmVector;

	public NovaList(SessionSsh session) {
		super(session, "nova list");
	}

	@Override
	public String exec() throws RemoteCliException {
		super.exec();
		mVmVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<VM> getVmVector() {
		return mVmVector;
	}

	public Vector<VM> parse() {

		Vector<VM> vms = new Vector<VM>();
		String novalist = getLastCommandPrintout();
		// TODO: linux \n: ^.*ID.*Networks.*$ does not work
		novalist = novalist.replaceAll("\\|.*ID.*Networks.*\r\n", "");
		novalist = novalist.replaceAll("\\+.*\\+\r\n", "");
		novalist = novalist.replaceAll("\\+.*\\+", "");

		String[] strvms = novalist.split("\r\n");
		for (String strvm : strvms) {

			// NOTE: str[0] is empty
			String[] attrs = strvm.split("\\|");

			VM vm = new VM(attrs[1].trim(), attrs[2].trim(), attrs[3].trim());

			Vector<Net> nets = parseNovaNetwork(attrs[4].trim());
			vm.addNet(nets);

			vms.add(vm);
		}

		return vms;
	}

	private Vector<Net> parseNovaNetwork(String strnetworks) {

		Vector<Net> nets = new Vector<Net>();

		String[] strnets = strnetworks.split(";");
		for (String strnet : strnets) {
			String[] attrs = strnet.split("=");
			Net net = new Net(attrs[0].trim(), attrs[1].trim());
			nets.add(net);
		}

		return nets;
	}
}
