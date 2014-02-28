package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.Port;
import com.ericsson.o2top.session.SessionSsh;

public class QuantumPortList extends OpenStackCommand {
	private Vector<Port> mPortVector;

	public QuantumPortList(SessionSsh session) {
		super(session, "quantum port-list");
	}

	@Override
	public String exec() throws RemoteCliException {
		super.exec();
		mPortVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<Port> getPortVector() {
		return mPortVector;
	}

	public Vector<Port> parse() {

		Vector<Port> ports = new Vector<Port>();
		String portlist = getLastCommandPrintout();
		// TODO: linux \n: ^.*ID.*Networks.*$ does not work
		portlist = portlist.replaceAll("\\|.*id.*fixed_ips.*\r\n", "");
		portlist = portlist.replaceAll("\\+.*\\+\r\n", "");
		portlist = portlist.replaceAll("\\+.*\\+", "");

		String[] strports = portlist.split("\r\n");
		for (String strport : strports) {

			// NOTE: str[0] is empty
			String[] attrs = strport.split("\\|");
			String[] fixed_ips = attrs[4].trim().replaceAll("[\"\\{\\} ]", "").split(",");

			Port port = new Port(attrs[1].trim(), attrs[2].trim(), attrs[3].trim(), fixed_ips[0].split(":")[1],
					fixed_ips[1].split(":")[1]);
			ports.add(port);

		}

		return ports;
	}
}
