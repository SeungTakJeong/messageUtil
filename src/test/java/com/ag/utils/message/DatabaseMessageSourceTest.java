package com.ag.utils.message;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/context-common.xml")
public class DatabaseMessageSourceTest<T> {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleCodeService publisherMessageSource;
	
	@Autowired
	private MessageSource subscriberMessageSource;
	
	@Test
	public void messageSourceTest(){
		Assert.assertEquals("안녕", messageSource.getMessage("hello", null, Locale.KOREAN));
		Assert.assertEquals("hello", messageSource.getMessage("hello", null, Locale.ENGLISH));
		Assert.assertEquals("login", messageSource.getMessage("login", null, Locale.ENGLISH));
		Assert.assertEquals("로그인", messageSource.getMessage("login", null, Locale.KOREAN));
	}
	@Test
	public void publisherMessageSourceTest(){
		Subscriber<T> sub = (Subscriber<T>) subscriberMessageSource;
		publisherMessageSource.update(sub);
		Assert.assertEquals("테스트안녕", subscriberMessageSource.getMessage("hello", null, Locale.KOREAN));
		
	}
}
