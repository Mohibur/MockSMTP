package com.simple.mind.mocksmtpserver.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.annotations.Expose;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MailDetails {
	private static Integer currentIndex = 0;
	private boolean readByTester = false;
	private String from;
	private String to;
	private String subject;
	private String mailBody;
	private String replyTo;
	private Date date;
	private String messageId;
	private String mimeVersion;
	@Setter(AccessLevel.NONE)
	private Integer index;

	@Expose (serialize = false, deserialize = false) 
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private HashMap<String, String> attachments = new HashMap<String, String>();
	@Setter(AccessLevel.NONE)
	private boolean attachement;
	private String contentType;
	private String contentTransferEncoding;
	private String format;

	private MailDetails() {
		index = currentIndex;
		currentIndex++;
		attachement = false;
	}

	public void setAttachments(String fileName, String fileContentInBase64) {
		attachments.put(fileName, fileContentInBase64);
		attachement = true;
	}

	private boolean setIfPossible(String line) {
		if (line.matches("^Date: .+$")) {
			String d = line.substring(6, line.length()).trim();
			String reg = "^(.+)?\\(([A-Z]{2,3})\\)$";
			Pattern pat = Pattern.compile(reg);
			Matcher m = pat.matcher(d);
			String mPart = "";
			@SuppressWarnings("unused")
			String timeZone = "";
			if (m.find()) {
				mPart = m.group(1);
				timeZone = m.group(2);
			} else {
				mPart = d;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
			try {
				date = formatter.parse(mPart);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (line.matches("^From: .+$")) {
			from = line.substring(6, line.length()).trim();
		} else if (line.matches("^Reply-To: +$")) {
			replyTo = line.substring(10, line.length()).trim();
		} else if (line.matches("^To: .+$")) {
			to = line.substring(4, line.length()).trim();
		} else if (line.matches("^Message-ID: .+$")) {
			messageId = line.substring(12, line.length()).trim();
		} else if (line.matches("^Subject: .+$")) {
			subject = line.substring(9, line.length()).trim();
		} else if (line.matches("^MIME-Version: .+$")) {
			mimeVersion = line.substring(13, line.length()).trim();
		} else if (line.matches("^Content-Type: .+$")) {
			contentType = line.substring(14, line.length()).trim();
		} else if (line.matches("^Content-Transfer-Encoding: .+$")) {
			contentTransferEncoding = line.substring(27, line.length()).trim();
		} else if (line.matches("^format: .+$")) {
			format = line.substring(8, line.length()).trim();
		}
		return true;
	}

	private void process(String messageBody) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < messageBody.length(); i++) {
			char cat = messageBody.charAt(i);
			char cat2 = messageBody.charAt(i + 1);
			if (cat == '\r' && cat2 == '\n' && messageBody.charAt(i + 2) == '\r' && messageBody.charAt(i + 3) == '\n') {
				setIfPossible(sb.toString());
				sb = new StringBuilder();
				i += 3;
				mailBody = messageBody.substring(i, messageBody.length());
				break;
				// read the message
			} else if (cat == '\r' && cat2 == '\n' && messageBody.charAt(i + 2) != ' ') {
				setIfPossible(sb.toString());
				i++;
				sb = new StringBuilder();
			} else {
				sb.append(cat);
			}
		}
		if (sb.toString().length() != 0) {
			setIfPossible(sb.toString());
		}
	}

	public static MailDetails getParsedMail(String messageBody) {
		//System.out.println(messageBody);
		MailDetails mailDet = new MailDetails();
		mailDet.process(messageBody);
		return mailDet;
	}
}
