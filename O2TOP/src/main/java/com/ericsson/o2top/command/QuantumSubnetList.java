package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.Subnet;
import com.ericsson.o2top.session.SessionSsh;

public class QuantumSubnetList extends OpenStackCommand {
	private Vector<Subnet> mSubnetVector;

	public QuantumSubnetList(SessionSsh session) {
		super(session, "quantum subnet-list");
	}

	@Override
	public String exec() throws RemoteCliException {
		super.exec();
		mSubnetVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<Subnet> getSubnetVector() {
		return mSubnetVector;
	}

	public Vector<Subnet> parse() {

		Vector<Subnet> subnets = new Vector<Subnet>();
		String subnetlist = getLastCommandPrintout();
		// TODO: linux \n: ^.*ID.*Networks.*$
		subnetlist = subnetlist.replaceAll("\\|.*id.*allocation_pools.*\r\n", "");
		subnetlist = subnetlist.replaceAll("\\+.*\\+\r\n", "");
		subnetlist = subnetlist.replaceAll("\\+.*\\+", "");

		String[] strsubnets = subnetlist.split("\r\n");
		for (String strsubnet : strsubnets) {

			// NOTE: str[0] is empty
			String[] attrs = strsubnet.split("\\|");
			String[] allocation_pools = attrs[4].trim().replaceAll("[\"\\{\\} ]", "").split(",");

			Subnet subnet = new Subnet(attrs[1].trim(), attrs[2].trim(), attrs[3].trim(),
					allocation_pools[0].split(":")[1], allocation_pools[1].split(":")[1]);
			subnets.add(subnet);

		}

		return subnets;
	}
}
