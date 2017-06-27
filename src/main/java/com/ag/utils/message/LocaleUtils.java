package com.ag.utils.message;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class LocaleUtils {

	private static Locale getDefaultLocale() {
		return Locale.getDefault();
	}

	public static Locale getLocale(HttpServletRequest request) {
		Locale locale = null;

		HttpSession session = request.getSession();
		locale = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if (locale == null) {
			locale = getDefaultLocale();
		}

		return locale;
	}

}
