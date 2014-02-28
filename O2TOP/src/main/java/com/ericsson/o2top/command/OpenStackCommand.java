package com.ericsson.o2top.command;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;
import com.ericsson.o2top.session.SessionSsh;

public abstract class OpenStackCommand {
	protected final SessionSsh mSession;
	protected String mExecutedCommand;
	protected String mCommandPrintout;

	public OpenStackCommand(SessionSsh session, String command) {
		mSession = session;
		mExecutedCommand = command;
	}

	public String exec() throws RemoteCliException {
		mCommandPrintout = mSession.exec(mExecutedCommand);
		return mCommandPrintout;
	}

	public SessionSsh getSession() {
		return mSession;
	}

	public String getExecutedCommand() {
		return mExecutedCommand;
	}

	public String getLastCommandPrintout() {
		return mCommandPrintout;
	}

	public void setLastCommandPrintout(String printout) {
		mCommandPrintout = printout;
	}
}
