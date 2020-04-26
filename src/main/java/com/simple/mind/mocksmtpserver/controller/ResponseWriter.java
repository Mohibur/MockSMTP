package com.simple.mind.mocksmtpserver.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class ResponseWriter {
	public static void WriteResponse(HttpExchange exchange, String towrite) throws IOException {
		exchange.sendResponseHeaders(200, towrite.getBytes().length);// response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(towrite.getBytes());
		os.close();
	}

	public static void WriteResponse(HttpExchange exchange, Object obj) throws IOException {
		exchange.getResponseHeaders().set("content-type", "application/json");
		String towrite = "";
		try {
			//towrite = new ObjectMapper().writeValueAsString(obj);
			towrite = new Gson().toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		exchange.sendResponseHeaders(200, towrite.getBytes().length);// response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(towrite.getBytes());
		os.close();
	}
}
