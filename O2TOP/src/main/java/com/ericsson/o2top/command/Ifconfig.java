package com.ericsson.o2top.command;

import java.util.Vector;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.l2entity.NetInterface;
import com.ericsson.o2top.session.SessionSsh;

public class Ifconfig extends OpenStackCommand {
	Vector<NetInterface> mNetInterfaceVector;

	public Ifconfig(SessionSsh session) {
		super(session, "ifconfig");
	}

	@Override
	public String exec() throws RemoteCliException {
		super.exec();
		mNetInterfaceVector = parse();
		return getLastCommandPrintout();
	}

	public Vector<NetInterface> getNetInterfaceVector() {
		return mNetInterfaceVector;
	}

	public Vector<NetInterface> parse() {

		Vector<NetInterface> netifs = new Vector<NetInterface>();
		String ifconfig = getLastCommandPrintout();
		String[] strnetifs = ifconfig.split("\r\n\r\n");

		for (String strnetif : strnetifs) {
			int i = strnetif.indexOf(" ");
			String ifname = strnetif.substring(0, i);
			int j = strnetif.indexOf("HWaddr");
			int start = j+"HWaddr".length()+1;
			String mac = strnetif.substring(start,strnetif.indexOf(" ",start));

			NetInterface netif = new NetInterface(ifname,mac,strnetif.contains("Interrupt"));
			netifs.add(netif);
		}

		return netifs;

	}

}
