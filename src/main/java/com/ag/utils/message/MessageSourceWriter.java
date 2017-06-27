package com.ag.utils.message;

public interface MessageSourceWriter {

	public void writeMessageSource(String localeCode) throws Exception;
	
	public void reWriteMessageSource(String localeCode) throws Exception;
		

}
