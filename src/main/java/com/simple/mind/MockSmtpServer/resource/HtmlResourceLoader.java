package com.simple.mind.MockSmtpServer.resource;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class HtmlResourceLoader {
	static final String BASE_PATH = "htmls/";

	public static String ResourceLoader(String htmlfile) throws IOException {
		InputStream inputStream = HtmlResourceLoader.class.getClassLoader()
				.getResourceAsStream(BASE_PATH + htmlfile + ".html");
		return IOUtils.toString(inputStream);
	}
}
