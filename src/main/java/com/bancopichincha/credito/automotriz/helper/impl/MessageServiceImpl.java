package com.bancopichincha.credito.automotriz.helper.impl;

import com.bancopichincha.credito.automotriz.helper.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
  private final MessageSource messageSource;

  @Override
  public String getMessage(String key) {
    return getMessage(key, null);
  }

  @Override
  public String getMessage(String key, Object[] args) {
    return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
  }
}
