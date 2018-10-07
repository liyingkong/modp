package com.senlang.modp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Mc.D
 *
 */
public class HttpHelper {
	private static final Logger logger = LogManager.getLogger(HttpHelper.class);

	public static String DoHttpRequest(String url) throws Exception {
		return DoHttpRequest(url, "post", 2000, null, "json");
	}

	public static String DoHttpRequest(String url, String method, int timeout, String content, String postType)
			throws Exception {

		HttpURLConnection request = null;
		URL u;
		try {
			u = new URL(url);
			request = (HttpURLConnection) u.openConnection();
			request.setConnectTimeout(timeout < 0 ? 1000 : timeout);
			request.setRequestMethod(StringHelper.isNullOrEmpty(method) ? "POST" : method.toUpperCase());
			// request.Method = string.IsNullOrEmpty(method) ?
			// WebRequestMethods.Http.Get : method;
			// request.Proxy = null;
			request.setRequestProperty("Content-Type", postType);
			if (request.getRequestMethod().equals("POST") && !StringHelper.isNullOrEmpty(content)) {
				request.setDoOutput(true);
				logger.debug(content);
				// request.ServicePoint.Expect100Continue = false;
				// request.ContentType = postType;
				// var data = Encoding.UTF8.GetBytes(content);
				// request.ContentLength = data.Length;
				logger.debug("Getting Request Stream...");
				OutputStreamWriter ws = new OutputStreamWriter(request.getOutputStream(), "utf-8");
				logger.debug("Getted Request Stream...");
				ws.write(content);
				ws.flush();
				ws.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.disconnect();
			throw e;
		}
		int i = 3;
		InputStream response = null;
		while (i > 0) {
			try {
				logger.debug(String.format("Getting Response - {%d}", 3 - i));
				response = request.getInputStream();
				logger.debug(String.format("Geted Response - {%d}", 3 - i));
				break;
			} catch (Exception e) {
				// new Logger().Warn(e);
				logger.warn(e.getMessage());
				request.disconnect();
				try {
					request = (HttpURLConnection) u.openConnection();
					request.setConnectTimeout(timeout < 0 ? 1000 : timeout);
					request.setRequestMethod(StringHelper.isNullOrEmpty(method) ? "POST" : method.toUpperCase());
					// request.Method = string.IsNullOrEmpty(method) ?
					// WebRequestMethods.Http.Get : method;
					// request.Proxy = null;
					request.setRequestProperty("Content-Type", postType);
					if (request.getRequestMethod().equals("POST") && !StringHelper.isNullOrEmpty(content)) {
						request.setDoOutput(true);
						logger.debug(content);
						// request.ServicePoint.Expect100Continue = false;
						// request.ContentType = postType;
						// var data = Encoding.UTF8.GetBytes(content);
						// request.ContentLength = data.Length;
						logger.debug("Getting Request Stream...");
						OutputStreamWriter ws = new OutputStreamWriter(request.getOutputStream(), "utf-8");
						logger.debug("Getted Request Stream...");
						ws.write(content);
						ws.flush();
						ws.close();

					}
				} catch (Exception ee) {
					logger.error(ee.getMessage());
					request.disconnect();
					throw ee;
				}
				i--;
			}
		}

		if (response == null) {
			request.disconnect();
			throw new Exception("连接中断！");
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response, "utf-8"));
			String str = reader.lines().reduce((a, b) -> a + b).get();
			reader.close();
			return str;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			response.close();
			request.disconnect();
		}
	}
}
