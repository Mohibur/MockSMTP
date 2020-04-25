package com.simple.mind.mocksmtpserver.controller;

import java.io.IOException;
import java.util.HashMap;

import com.simple.mind.mocksmtpserver.storage.MailDetails;
import com.simple.mind.mocksmtpserver.storage.MailStorage;
import com.sun.net.httpserver.HttpExchange;

public class MailListController {
	public static class RetData {
		boolean success;
		public HashMap<Integer, MailDetails> data;
	}

	public static void getAllMails(HttpExchange exchange) throws IOException {
		RetData rd = new RetData();
		rd.success = true;
		rd.data = MailStorage.getMails();
		ResponseWriter.WriteResponse(exchange, rd);
	}
}
