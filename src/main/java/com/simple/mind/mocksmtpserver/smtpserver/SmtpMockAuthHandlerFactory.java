package com.simple.mind.mocksmtpserver.smtpserver;

import java.util.ArrayList;
import java.util.List;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

final public class SmtpMockAuthHandlerFactory implements AuthenticationHandlerFactory {
	private static final String MECHANISM = "LOGIN";

	@Override
	public AuthenticationHandler create() {
		return new SmtpMockAuthHandler();
	}

	@Override
	public List<String> getAuthenticationMechanisms() {
		List<String> result = new ArrayList<String>();
		result.add(SmtpMockAuthHandlerFactory.MECHANISM);
		return result;
	}
}
