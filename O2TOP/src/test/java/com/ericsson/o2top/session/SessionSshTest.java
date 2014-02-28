package com.ericsson.o2top.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;

public class SessionSshTest {
	private static Logger mLogger = LoggerFactory.getLogger(SessionSshTest.class);
	private SessionSsh mSession;
	private static final String USER_NAME = "localadmin";
	private static final String PASS_WORD = "3r1cs50n";
	private static final String HOST_NAME = "127.0.0.1";
	private static final int PORT = 6666;
	private static final int TIME_OUT = 300;

	@BeforeClass
	public void prepare() throws RemoteCliException {
		mSession = new SessionSsh(USER_NAME, PASS_WORD, HOST_NAME, PORT, TIME_OUT);
	}

	@Test
	public void execTest() throws RemoteCliException {
		String printout = mSession.exec("nova list");
		mLogger.info(printout);

		String oldPrompt = mSession.setPrompt(".*?localadmin: ");
		mSession.exec("sudo ovs-vsctl show");
		mSession.setPrompt(oldPrompt);
		printout = mSession.exec("3r1cs50n");
		mLogger.info(printout);

		printout = mSession.exec("quantum net-list");
		mLogger.info(printout);
	}
}
