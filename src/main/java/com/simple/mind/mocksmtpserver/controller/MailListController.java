package com.simple.mind.mocksmtpserver.controller;

import java.io.IOException;
import java.util.HashMap;

import com.google.common.base.Strings;
import com.simple.mind.mocksmtpserver.resource.QueryParser;
import com.simple.mind.mocksmtpserver.storage.MailDetails;
import com.simple.mind.mocksmtpserver.storage.MailStorage;
import com.sun.net.httpserver.HttpExchange;

public class MailListController {

	public static class Ret {
		boolean success;
		public MailDetails data;
	}

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

	public static void deleteMail(HttpExchange exchange) throws IOException {
		QueryParser qp = new QueryParser(exchange);
		Integer index = qp.getInt("index");
		String msgId = qp.getString("messageId");
		boolean m;
		if (index == null || Strings.isNullOrEmpty(msgId) == true) {
			m = false;
		} else {
			m = MailStorage.DeleteMail(index, msgId);
		}

		exchange.getResponseHeaders().set("content-type", "application/json");
		String towrite = "{\"success\":" + m + "}";
		ResponseWriter.WriteResponse(exchange, towrite);
	}

	public static void readMail(HttpExchange exchange) throws IOException {
		QueryParser qp = new QueryParser(exchange);
		Integer index = qp.getInt("index");
		String msgId = qp.getString("messageId");
		Ret towrite = new Ret();

		if (index == null || Strings.isNullOrEmpty(msgId) == true) {
			towrite.success = false;
			towrite.data = null;
		} else {

			towrite.data = MailStorage.ReadMail(index, msgId);
			towrite.success = !(towrite.data == null);
		}
		ResponseWriter.WriteResponse(exchange, towrite);
	}
}
