package com.simple.mind.mocksmtpserver.storage;

import java.util.HashMap;

public class MailStorage {
	static Integer index = 0;
	static MailStorage mailStorage;

	private HashMap<Integer, MailDetails> mailMap;
	static {

		mailStorage = new MailStorage();
		mailStorage.mailMap = new HashMap<Integer, MailDetails>();
	}

	private MailStorage() {

	}

	public static final HashMap<Integer, MailDetails> getMails() {
		return mailStorage.mailMap;
	}

	public static void addMail(String messageBody) {
		MailDetails md = MailDetails.getParsedMail(messageBody);
		mailStorage.mailMap.put(index, md);
		index++;
	}

	public static boolean DeleteMail(Integer msgIndex, String msgId) {
		MailDetails md = mailStorage.mailMap.get(msgIndex);
		if (md == null)
			return false;
		if (md.getMessageId().compareTo(msgId) != 0)
			return false;
		mailStorage.mailMap.remove(msgIndex);
		return true;
	}

	public static MailDetails ReadMail(Integer msgIndex, String msgId) {
		MailDetails md = mailStorage.mailMap.get(msgIndex);
		if (md == null)
			return null;
		if (md.getMessageId().compareTo(msgId) != 0)
			return null;
		md.setReadByTester(true);
		return md;
	}
}
