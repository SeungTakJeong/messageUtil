package com.ag.utils.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class PublisherMessageSource<T> implements LocaleCodeService<T>, InitializingBean{
	
	private Logger log = LogManager.getLogger();
	
	private Publisher<Map<String,Map<String,String>>> publisher;
	
	public Publisher<Map<String,Map<String,String>>> getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher<Map<String,Map<String,String>>> publisher) {
		this.publisher = publisher;
	}

	private Map<String, Map<String, String>> cachedMessageSource = null;
	
	public Map<String, Map<String, String>> getMessageCodeAll() {

		log.info("getMessageCodeAll");

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


	

	public void update(Subscriber subscriberMessageSource) {
		
		this.cachedMessageSource = new HashMap<String, Map<String, String>>();

		/**
		 * TODO DB 구현
		 */
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("ko", "테스트안녕");

		cachedMessageSource.put("hello", messageMap);

		publisher.subscribe(subscriberMessageSource);
		
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {}
	
	public void init(){
		
		log.info("init");
		this.publisher = sub -> {
			
			sub.onSubscribe(new Subscription() {
				
				@Override
				public void request(long n) {
					log.info("request");
					try{
						sub.onNext(cachedMessageSource);
						
					}catch(Exception e){
						sub.onError(e);
					}finally{
						sub.onComplete();
					}
				}
				
				@Override
				public void cancel() {
					log.info("cancel");
				}
			});
		};
		
	}

}
