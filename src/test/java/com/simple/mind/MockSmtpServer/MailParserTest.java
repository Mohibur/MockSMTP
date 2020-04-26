package com.simple.mind.MockSmtpServer;

import com.simple.mind.mocksmtpserver.storage.MailDetails;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MailParserTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public MailParserTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MailParserTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		//@formatter:off
		String s="Received: from johny (localhost [127.0.0.1])\r\n" + 
		"        by johny\r\n" + 
		"        with SMTP (SubEthaSMTP 3.1.7) id K9F550K6\r\n" + 
		"        for abc@mail;\r\n" + 
		"        Sat, 25 Apr 2020 13:45:47 +0900 (JST)\r\n" + 
		"Date: Sat, 25 Apr 2020 13:45:47 +0900 (JST)\r\n" + 
		"From: NoReply-JD <no_reply@example.com>\r\n" + 
		"Reply-To: no_reply@example.com\r\n" + 
		"To: abc@mail\r\n" + 
		"Message-ID: <405662939.0.1587789947657.JavaMail.root@johny>\r\n" + 
		"Subject: Sympathy\r\n" + 
		"MIME-Version: 1.0\r\n" + 
		"Content-Type: text/plain; charset=UTF-8\r\n" + 
		"Content-Transfer-Encoding: 7bit\r\n" + 
		"format: flowed\r\n" + 
		"\r\n" + 
		"Please\r\n";
		//@formatter:on

		MailDetails md = MailDetails.getParsedMail(s);
		System.out.println(md.getDate().toString());
		assertEquals(md.getDate().toString(), "Sat Apr 25 13:45:47 JST 2020");
		assertEquals(md.getMessageId(), "<405662939.0.1587789947657.JavaMail.root@johny>");
		assertEquals(md.getTo(), "abc@mail");
	}
}
