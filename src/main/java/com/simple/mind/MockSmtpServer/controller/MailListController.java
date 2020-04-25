package com.simple.mind.MockSmtpServer.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.mind.MockSmtpServer.resource.QueryParser;
import com.simple.mind.fakesmtpwebserver.storage.MailDetails;
import com.simple.mind.fakesmtpwebserver.storage.MailStorage;
import com.sun.net.httpserver.HttpExchange;

public class MailListController {
	public static void getAllMails(HttpExchange exchange) throws IOException {
		QueryParser qp = new QueryParser(exchange.getRequestURI().getQuery());
		@SuppressWarnings("unused")
		Integer lastIndex = qp.getInt("last_index");
		HashMap<Integer, MailDetails> toRet = MailStorage.getMails();
		StringBuilder sb = new StringBuilder();
		sb.append("{\"scuess\":");
		String s = null;
		try {
			s = new ObjectMapper().writeValueAsString(toRet);
			sb.append("true,");
		} catch (JsonProcessingException e) {
		}
		sb.append("\"data\":");
		sb.append(s);
		sb.append("}");
		String msg = sb.toString();
		exchange.getResponseHeaders().set("content-type", "application/json");
		exchange.sendResponseHeaders(200, msg.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(msg.getBytes());
		os.close();
	}
}
