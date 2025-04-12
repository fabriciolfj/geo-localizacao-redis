package com.github.fabriciolfj.geo_localizacao.configurations

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ValidationConfig {

    @Bean
    fun messageSource() : MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun getValidator() : LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource())
        return bean
    }
}