package com.simple.mind.fakesmtpwebserver.server;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.subethamail.smtp.helper.SimpleMessageListener;

import com.simple.mind.fakesmtpwebserver.storage.MailStorage;

public final class MailListener implements SimpleMessageListener {

	public MailListener() {
	}

	public boolean accept(String from, String recipient) {
		return true;
	}

	@Override
	public void deliver(String from, String recipient, InputStream data) throws IOException {
		String message = IOUtils.toString(data);
		MailStorage.addMail(message);
	}
}
