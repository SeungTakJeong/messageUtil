1. 설정
bean 추가

실제 db조회 하는 localeCodeService 구현체 기재
	
<bean id="messageSource" class="com.ag.utils.message.DatabaseMessageSource" init-method="init">
	<property name="localeCodeService">
		<ref bean="localeCodeService"/>
	</property>
	<property name="localeCodeService">
		<ref bean="localeCodeService"/>
	</property>
	<property name="cacheSeconds">
		<value>60</value>
	</property>
	<property name="isRefresh" value="true"/>
</bean>

<bean id="localeCodeService" class="com.ag.utils.message.LocaleCodeServiceImpl" />

<bean id="localeResolver"
	class="org.springframework.web.servlet.i18n.SessionLocaleResolver">
	<property name="defaultLocale" value="ko" />
</bean>


