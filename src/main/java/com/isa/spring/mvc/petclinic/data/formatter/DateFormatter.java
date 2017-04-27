package com.isa.spring.mvc.petclinic.data.formatter;


import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    private final String datePattern;

    public DateFormatter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return getDateFormat().parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        return getDateFormat().format(date);
    }

    private DateFormat getDateFormat() {
        return new SimpleDateFormat(datePattern);
    }
}
