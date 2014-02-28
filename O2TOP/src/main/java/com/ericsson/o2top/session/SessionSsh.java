package com.ericsson.o2top.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.commonlibrary.remotecli.Cli;
import com.ericsson.commonlibrary.remotecli.CliBuilder;
import com.ericsson.commonlibrary.remotecli.CliFactory;
import com.ericsson.commonlibrary.remotecli.exceptions.RemoteCliException;

public class SessionSsh {
	private static final int MILLISECOND_MODIFIER = 1000;
	private CliBuilder mBuilder = null;
	private Cli mClient;
	private String mCurrentPrompt = null;
	private static Logger mLogger = LoggerFactory.getLogger(SessionSsh.class);

	public SessionSsh(String username, String password, String hostname, int port, int timeout)
			throws RemoteCliException {
		mBuilder = CliFactory.newSshBuilder();
		mBuilder.setPort(port).setHost(hostname).setUsername(username).setPassword(password)
				.setSendTimeoutMillis(timeout * MILLISECOND_MODIFIER).setNewline("\n");
		mCurrentPrompt = ".*?\\$ ";
		mBuilder.setExpectedRegexPrompt(mCurrentPrompt);
		mClient = mBuilder.build();
		mClient.connect();
	}

	public void open() throws RemoteCliException {
		mClient.connect();
	}

	public void close() {
		mClient.disconnect();
	}

	public String exec(String command) throws RemoteCliException {
		mLogger.info(getMatchedPrompt() + command);
		String result = mClient.send(command);
		mLogger.info(result);
		return result;
	}

	public String getCurrentPrompt() {
		return mClient.getExpectedRegexPrompt();
	}

	public String getHost() {
		return mBuilder.getHost();
	}

	public String getMatchedPrompt() {
		return mClient.getMatchedPrompt();
	}

	public int getPort() {
		return mBuilder.getPort();
	}

	public int getTimeout() {
		return mClient.getSendTimeoutMillis();
	}

	public String getUsername() {
		return mBuilder.getUsername();
	}

	public void setHost(String host) {
		mBuilder.setHost(host);
	}

	public void setPassword(String password) {
		mBuilder.setPassword(password);
	}

	public void setPort(int port) {
		mBuilder.setPort(port);
	}

	public String setPrompt(String newPrompt) {
		String oldPrompt = mCurrentPrompt;
		mCurrentPrompt = newPrompt;
		mClient.setExpectedRegexPrompt(mCurrentPrompt);
		return oldPrompt;
	}

	public void setTimeout(int time) {
		mClient.setSendTimeoutMillis(time * MILLISECOND_MODIFIER);
	}

	public void setUsername(String username) {
		mBuilder.setUsername(username);
	}
}
