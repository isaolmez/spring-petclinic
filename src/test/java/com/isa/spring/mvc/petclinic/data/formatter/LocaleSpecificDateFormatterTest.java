package com.isa.spring.mvc.petclinic.data.formatter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocaleSpecificDateFormatterTest {

    @Autowired
    private LocaleSpecificDateFormatter dateFormatter;

    private Date date;

    private Locale enLocale;

    private Locale trLocale;

    @Before
    public void setUp(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 11, 30, 0, 0, 0);
        date = calendar.getTime();

        enLocale = new Locale("en");
        trLocale = new Locale("tr");
    }

    @Test
    public void shouldConvertFromDateToString_ForTurkish() {
        final String expected = "30/12/2000";

        String actual = dateFormatter.print(date, trLocale);

        assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertFromStringToDate_ForTurkish() {
        final String someDateString = "30/12/2000";

        Date actual = null;
        try {
            actual = dateFormatter.parse(someDateString, trLocale);
        } catch (ParseException e) {
            fail();
        }

        assertNotNull(actual);
        Assert.assertEquals(date.getTime() / 1000, actual.getTime() / 1000);
    }

    @Test
    public void shouldConvertFromDateToString_ForEnglish() {
        final String expected = "30-12-2000";

        String actual = dateFormatter.print(date, enLocale);

        assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertFromStringToDate_ForEnglish() {
        final String someDateString = "30-12-2000";

        Date actual = null;
        try {
            actual = dateFormatter.parse(someDateString, enLocale);
        } catch (ParseException e) {
            fail();
        }

        assertNotNull(actual);
        Assert.assertEquals(date.getTime() / 1000, actual.getTime() / 1000);
    }
}
