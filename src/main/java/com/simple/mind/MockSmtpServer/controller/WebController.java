package com.simple.mind.MockSmtpServer.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.mind.MockSmtpServer.resource.HtmlResourceLoader;
import com.simple.mind.MockSmtpServer.resource.QueryParser;
import com.simple.mind.fakesmtpwebserver.server.MockSmtpServer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import lombok.Data;

public class WebController {
	@Data
	public static class ToggolResult {
		boolean success;
		boolean status;
	}

	public static void IndexhandleRequest(HttpExchange exchange) throws IOException {
		String response = HtmlResourceLoader.ResourceLoader("index");
		exchange.sendResponseHeaders(200, response.getBytes().length);// response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	public static void startStopServer(HttpExchange exchange) throws IOException {
		QueryParser qp = new QueryParser(exchange.getRequestURI().getQuery());
		String hostname = qp.getString("hostname", "localhost");
		Integer port = qp.getInt("port", 2525);
		ToggolResult tr = new ToggolResult();
		if (MockSmtpServer.isRunning() == false) {
			try {
				MockSmtpServer.Start(hostname, port);
				tr.success = true;
			} catch (UnknownHostException e) {
				tr.success = false;
				e.printStackTrace();
			}
		} else {
			try {
				MockSmtpServer.Stop();
				tr.success = true;
			} catch (Exception e) {
				tr.success = false;
				e.printStackTrace();
			}
		}
		tr.status = MockSmtpServer.isRunning();
		String toRet = "";
		try {
			toRet = new ObjectMapper().writeValueAsString(tr);
		} catch (Exception e) {

		}
		Headers response = exchange.getResponseHeaders();
		response.set("content-type", "application/json");

		exchange.sendResponseHeaders(200, toRet.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(toRet.getBytes());
		os.close();
	}
}
