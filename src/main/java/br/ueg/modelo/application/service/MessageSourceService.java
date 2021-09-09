package br.ueg.modelo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceService {
    private MessageSourceAccessor acessor;

    @Autowired
    public MessageSourceService(final MessageSource messageSource){
        this.acessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    public String get(String key){
        return this.acessor.getMessage(key);
    }
}
