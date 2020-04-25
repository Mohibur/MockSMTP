package com.simple.mind.mocksmtpserver.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class ResponseWriter {
	public static void WriteResponse(HttpExchange exchange, String towrite) throws IOException {
		exchange.sendResponseHeaders(200, towrite.getBytes().length);// response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(towrite.getBytes());
		os.close();
	}

	public static void WriteResponse(HttpExchange exchange, Object obj) throws IOException {
		System.out.println("callled");
		exchange.getResponseHeaders().set("content-type", "application/json");
		String towrite = "";
		try {
			towrite = new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		exchange.sendResponseHeaders(200, towrite.getBytes().length);// response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(towrite.getBytes());
		os.close();
	}
}
