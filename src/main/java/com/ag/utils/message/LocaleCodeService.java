package com.ag.utils.message;

import java.util.Map;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public interface LocaleCodeService<T> {

	Map<String, Map<String, String>> getMessageCodeAll();

	String getMessageCode(String code, String language);
	
	void update(Subscriber subscriberMessageSource);

}
