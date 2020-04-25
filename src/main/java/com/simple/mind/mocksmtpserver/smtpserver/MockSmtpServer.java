package com.simple.mind.mocksmtpserver.smtpserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

public final class MockSmtpServer {
	private static boolean running;
	private static SMTPServer mockSmtpServer = null;

	/* Disable constructor */
	private MockSmtpServer() {
	}

	public static void Stop() {
		if (mockSmtpServer == null) {
			return;
		}
		mockSmtpServer.stop();
		mockSmtpServer = null;
		running = false;
	}

	public static void Start(String hostname, int port) throws UnknownHostException {
		if (mockSmtpServer != null) {
			return;
		}
		MailListener myListener = new MailListener();

		mockSmtpServer = new SMTPServer(new SimpleMessageListenerAdapter(myListener), new SmtpMockAuthHandlerFactory());
		InetAddress bindAddress = InetAddress.getByName(hostname);

		mockSmtpServer.setBindAddress(bindAddress);
		mockSmtpServer.setPort(port);
		mockSmtpServer.start();
		running = true;
	}

	public static boolean isRunning() {
		return running;
	}
}
