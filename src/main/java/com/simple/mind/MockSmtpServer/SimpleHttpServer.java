package com.simple.mind.MockSmtpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.simple.mind.mocksmtpserver.controller.MailListController;
import com.simple.mind.mocksmtpserver.controller.WebController;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {
	HttpServer server = null;

	public SimpleHttpServer() throws IOException {
		server = HttpServer.create(new InetSocketAddress(8500), 0);
	}

	public void setContext(String path, HttpHandler httpHandler) {
		HttpContext context = server.createContext(path);
		context.setHandler(httpHandler);
	}

	public void start() {
		server.start();
	}

	public static void main(String[] args) throws IOException {
		SimpleHttpServer sm = new SimpleHttpServer();
		sm.setContext("/", WebController::IndexhandleRequest);
		sm.setContext("/toggol", WebController::StartStopServer);
		sm.setContext("/status", WebController::ServerStatus);
		sm.setContext("/messages", MailListController::getAllMails);
		sm.setContext("/delete", MailListController::deleteMail);
		sm.setContext("/read", MailListController::readMail);
		sm.start();
	}
}
