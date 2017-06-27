package com.ag.utils.message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.DefaultPropertiesPersister;

public class MessageSourceWriterImpl implements MessageSourceWriter{
	
	private Logger log = LogManager.getLogger();

	public void writeMessageSource(String localeCode) {
		/** TODO
		 * DB 연결
		 */
		OutputStream out = null;
		Properties props = new Properties();
		try{
			
			String test[]={ "hello", "한글", "初めまして。","您好！"};
			Random random = new Random();
			for(int i =1; i < 1000; i ++){
				props.setProperty("prop"+i, test[random.nextInt(3)]);
				
			}
			
			File f = new File("D:\\koreafilm\\message\\message-common_"+localeCode+".properties");
			out = new FileOutputStream(f);
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			p.store(props, out, "Release");
		}catch(Exception e){
			log.error("MessageSource Write Error");
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					log.error("MessageSource Write Error");
				}
			}
		}
		
	}

	public void reWriteMessageSource(String localeCode) {
		OutputStream out = null;
		Properties props = new Properties();
		try{
			
			String test[]={ "hello", "한글", "初めまして。","您好！"};
			Random random = new Random();
			for(int i =1; i < 1000; i ++){
				props.setProperty("prop"+i, test[random.nextInt(3)]);
				
			}
			
			File f = new File("D:\\koreafilm\\message\\message-common_"+localeCode+".properties");
			out = new FileOutputStream(f);
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			p.store(props, out, "Release");
		}catch(Exception e){
			log.error("MessageSource Write Error");
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					log.error("MessageSource Write Error");
				}
			}
		}
	}


}
