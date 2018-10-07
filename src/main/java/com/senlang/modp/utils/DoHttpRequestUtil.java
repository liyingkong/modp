package com.senlang.modp.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map.Entry;

@Service
public class DoHttpRequestUtil {
	private Logger logger = LogManager.getLogger();
	private String PostTypeJson = "application/json";
	private String ReturnOriString = "RETURN_ORI_STRING";
	
	public HashMap<String, Object> doHttpRequest(String url) {
		return doHttpRequest(url, "GET", 20000, null, "json", PostTypeJson);
	}

	public HashMap<String, Object> doPostHttpRequest(String url, String content) {
		return doHttpRequest(url, "POST", 20000, content, "json", PostTypeJson);
	}

	public HashMap<String, Object> doGetHttpRequest(String url, String content) {
		return doHttpRequest(url, "GET", 20000, content, "json", PostTypeJson);
	}

	private HashMap<String, Object> doHttpRequest(String url, String method, int timeout, String content,
			String returnType, String postType) {
		try {
			logger.info("请求url:" + url);
			logger.info("请求内容:" + content);
			String str = HttpHelper.DoHttpRequest(url, method, timeout, content, postType);
			System.out.println("str:"+str);
			switch (returnType) {
			case "json": {
				// var textReader = new JsonTextReader(str);
				// JsonConvert.DeserializeObject<Account>(json);
				HashMap<String, Object> dict = new ObjectMapper().readValue(str,
						new TypeReference<HashMap<String, Object>>() {
						});
				// if (dict.ContainsKey("errcode"))
				// {
				// reader.Close();
				// textReader.Close();
				// return new Dictionary<string, string>();
				// }
				// textReader.Close();
//				logger.info(String.format("HttpReturn:%s", toXml(dict)));
				//dict.put(ReturnOriString, str);
//				logger.info("dict:");
//				logger.info(dict);
				logger.info(dict.get("resultcode"));
				return dict;
			}
			
			}
			logger.error("不支持的返回格式设置！");
			return new HashMap<String, Object>();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new HashMap<String, Object>();
		}
	}
	
	public String toXml(HashMap<String, Object> dict) {
		// 数据为空时不能转化为xml格式
		if (dict.isEmpty()) {
			logger.error("Url字典数据为空!");
			return null;
			// throw new WxPayException("WxPayData数据为空!");
		}

		String xml = "<xml>";
		for (Entry<String, Object> pair : dict.entrySet()) {
			// 字段值不能为null，会影响后续流程
			if (pair.getValue() == null) {
				logger.error("Url字典含有值为null的字段!");
				return null;
				// throw new WxPayException("WxPayData内部含有值为null的字段!");
			}

			if (pair.getValue().getClass().isPrimitive()) {
				xml += "<" + pair.getKey() + ">" + pair.getValue() + "</" + pair.getKey() + ">";
			} else {
				xml += "<" + pair.getKey() + ">" + "<![CDATA[" + pair.getValue() + "]]></" + pair.getKey() + ">";
			}
		}
		xml += "</xml>";
		return xml;
	}
}
