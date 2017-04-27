package com.isa.spring.mvc.petclinic.data.formatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocaleSpecificDateFormatter implements Formatter<Date> {
    private final MessageSource messageSource;

    @Autowired
    public LocaleSpecificDateFormatter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return getDateFormat(locale).parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        return getDateFormat(locale).format(date);
    }

    private DateFormat getDateFormat(Locale locale) {
        final String datePattern = messageSource.getMessage("date.format", null, locale);
        final DateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}
