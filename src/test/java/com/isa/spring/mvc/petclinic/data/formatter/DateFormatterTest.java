package com.isa.spring.mvc.petclinic.data.formatter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DateFormatterTest {

    private DateFormatter dateFormatter;

    private Date date;

    @Before
    public void setUp() {
        this.dateFormatter = new DateFormatter("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 11, 30, 0, 0, 0);
        date = calendar.getTime();
    }

    @Test
    public void shouldConvertFromDateToString() {
        final String expected = "30-12-2000";

        String actual = dateFormatter.print(date, null);

        assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertFromStringToDate() {
        final String someDateString = "30-12-2000";

        Date actual = null;
        try {
            actual = dateFormatter.parse(someDateString, null);
        } catch (ParseException e) {
            fail();
        }

        assertNotNull(actual);
        Assert.assertEquals(date.getTime() / 1000, actual.getTime() / 1000);
    }
}
