package com.simple.mind.mocksmtpserver.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import com.simple.mind.mocksmtpserver.resource.HtmlResourceLoader;
import com.simple.mind.mocksmtpserver.resource.QueryParser;
import com.simple.mind.mocksmtpserver.smtpserver.MockSmtpServer;
import com.sun.net.httpserver.HttpExchange;

import lombok.Data;

public class WebController {
	@Data
	public static class ToggolResult {
		boolean success;
		boolean status;
	}

	public static void IndexhandleRequest(HttpExchange exchange) throws IOException {
		ResponseWriter.WriteResponse(exchange, HtmlResourceLoader.ResourceLoader("index"));
	}

	public static void ServerStatus(HttpExchange exchange) throws IOException {
		ToggolResult tr = new ToggolResult();
		tr.setStatus(MockSmtpServer.isRunning());
		tr.setSuccess(true);
		ResponseWriter.WriteResponse(exchange, tr);
	}

	public static void StartStopServer(HttpExchange exchange) throws IOException {
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
		ResponseWriter.WriteResponse(exchange, tr);
	}
}
