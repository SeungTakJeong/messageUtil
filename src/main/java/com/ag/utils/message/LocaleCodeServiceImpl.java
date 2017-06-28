package com.ag.utils.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Subscriber;

public class LocaleCodeServiceImpl implements LocaleCodeService {

	private Logger log = LogManager.getLogger();

	public Map<String, Map<String, String>> getMessageCodeAll() {

		log.debug("getMessageCodeAll");

		Map<String, Map<String, String>> messageSource = new HashMap<String, Map<String, String>>();

		/**
		 * TODO DB 구현
		 */
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("ko", "안녕");
		messageMap.put("en", "hello");

		messageSource.put("hello", messageMap);

		messageMap = new HashMap<String, String>();
		messageMap.put("ko", "로그인");
		messageMap.put("en", "login");

		messageSource.put("login", messageMap);

		return messageSource;
	}

	public String getMessageCode(String code, String language) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		params.put("language", language);
		/**
		 * TODO DB 구현
		 */
		String message = "안녕";
		return message;
	}

	@Override
	public void update(Subscriber subscriberMessageSource) {
	}


}
