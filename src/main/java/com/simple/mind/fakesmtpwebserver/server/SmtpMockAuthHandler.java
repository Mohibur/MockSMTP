package com.simple.mind.fakesmtpwebserver.server;

import org.subethamail.smtp.AuthenticationHandler;

public final class SmtpMockAuthHandler implements AuthenticationHandler {
	private static final String USER_ID = "Sample User";
	private static final String USER_PASS = "334 SampleUser";

	@Override
	public String auth(String clientInput) {
		return USER_PASS;
	}

	@Override
	public Object getIdentity() {
		return SmtpMockAuthHandler.USER_ID;
	}
}
