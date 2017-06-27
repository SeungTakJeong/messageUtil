package com.ag.utils.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.Assert;

public class SubscriberMessageSource<T> extends AbstractMessageSource implements Subscriber<T>, InitializingBean {

	private final static Logger logger = LogManager.getLogger();

	// cache 시간 - 음수 일 경우 DB 바로 조회
	private long cacheMillis = -1;

	// refresh여부 default false
	private boolean isRefresh = false;

	// refresh된 시간
	private volatile long refreshTimestamp = -2;

	private LocaleCodeService localeCodeService;

	// 문자code locale 실제message
	private Map<String, Map<String, String>> cachedMessageSource = new HashMap<String, Map<String, String>>();

	// injection
	public void setLocaleCodeService(LocaleCodeService localeCodeService) {
		this.localeCodeService = localeCodeService;
	}

	// injection
	public void setCacheSeconds(int cacheSeconds) {
		this.cacheMillis = (cacheSeconds * 1000);
	}

	// injection
	public void setIsRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	/**
	 * 언어 메시지 소스
	 * 
	 * @return
	 */
	private Map<String, Map<String, String>> getLocaleMessageSource() {
		if (this.cachedMessageSource != null) {
			return this.cachedMessageSource;
		} else {
			return getCachedMessageSource();
		}
	}

	/**
	 * 메시지 소스 없을 경우 조회
	 * 
	 * @return
	 */
	private Map<String, Map<String, String>> getCachedMessageSource() {
		return localeCodeService.getMessageCodeAll();
	}

	/**
	 * 메세지 소스 갱신 TODO synchronized 확인
	 */
	private void reloadCachedMessageSource() {
		synchronized (cachedMessageSource) {
			this.cachedMessageSource = localeCodeService.getMessageCodeAll();
		}
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		String message = resolveCodeWithoutArguments(code, locale);
		MessageFormat result = createMessageFormat(message, locale);
		return result;
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		String message = null;

		// 캐시 하지 않을 경우
		if (this.cacheMillis < 0) {
			message = getMessage(code, locale);
		} else {
			if (isRefresh) {
				if (refreshTimestamp < System.currentTimeMillis() - this.cacheMillis) {
					reloadCachedMessageSource();
					this.refreshTimestamp = System.currentTimeMillis();
				}
			}

			message = getCacheMessage(code, locale);
		}

		return message;
	}

	/**
	 * INIT시에 저장된 오브젝트에서 조회
	 * 
	 * @param code
	 * @param locale
	 * @return
	 */
	private String getCacheMessage(String code, Locale locale) {
		Map<String, Map<String, String>> messageSource = getLocaleMessageSource();
		Map<String, String> localeMessageSource = messageSource.get(code);

		String message = localeMessageSource.get(locale.getLanguage());

		if (message == null) {
			logger.error("Message Not FOUND");
			throw new NoSuchMessageException(code);
		}

		return message;
	}

	/**
	 * DB 조회
	 * 
	 * @param code
	 * @param locale
	 * @return
	 */
	private String getMessage(String code, Locale locale) {
		String message = localeCodeService.getMessageCode(code, locale.getLanguage());

		if (message == null) {
			logger.error("Message Not FOUND");
			throw new NoSuchMessageException(code);
		}
		return message;
	}

	public void init() {
		this.cachedMessageSource = localeCodeService.getMessageCodeAll();
		this.refreshTimestamp = System.currentTimeMillis();
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.localeCodeService, "An LocaleCodeService is required");
	}

	public void onSubscribe(Subscription s) {
		logger.info("onSubscribe");
		s.request(Long.MAX_VALUE);
	}

	public void onNext(T t) {
		logger.info("onNext");
		Map<String, Map<String, String>> messageSource = (Map<String, Map<String, String>>) t;
		messageSource.forEach((k,v)->this.cachedMessageSource.put(k,v));
	}

	public void onError(Throwable t) {
		logger.info("onError");
	}

	public void onComplete() {
		logger.info("onComplete");
	}



}
