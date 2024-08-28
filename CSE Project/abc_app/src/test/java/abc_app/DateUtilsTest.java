package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {

    private SimpleDateFormat dateFormat;
    private Date testDate;

    @BeforeEach
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        testDate = dateFormat.parse("2024-08-20");
    }

    @Test
    public void testFormatDate() throws ParseException {
        String expected = "Tuesday, August 20, 2024";
        String actual = DateUtils.formatDate(testDate);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatDateWithNull() {
        String actual = DateUtils.formatDate(null);
        assertEquals("", actual);
    }

    @Test
    public void testFormatTime() throws ParseException {
        Date timeTestDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-08-20 12:23:42");
        String expected = "12:23:42 pm on Tuesday, August 20, 2024";
        String actual = DateUtils.formatTime(timeTestDate);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatTimeWithNull() {
        String actual = DateUtils.formatTime(null);
        assertEquals("", actual);
    }

    @Test
    public void testNextDay() {
        Date nextDay = DateUtils.nextDay(testDate);
        String expected = "Wednesday, August 21, 2024";
        String actual = DateUtils.formatDate(nextDay);
        assertEquals(expected, actual);
    }

    @Test
    public void testBetweenTrue() throws ParseException {
        Date startDate = dateFormat.parse("2024-08-19");
        Date endDate = dateFormat.parse("2024-08-21");
        assertTrue(DateUtils.between(testDate, startDate, endDate));
    }

    @Test
    public void testBetweenFalseBefore() throws ParseException {
        Date startDate = dateFormat.parse("2024-08-21");
        Date endDate = dateFormat.parse("2024-08-22");
        assertFalse(DateUtils.between(testDate, startDate, endDate));
    }

    @Test
    public void testBetweenFalseAfter() throws ParseException {
        Date startDate = dateFormat.parse("2024-08-18");
        Date endDate = dateFormat.parse("2024-08-19");
        assertFalse(DateUtils.between(testDate, startDate, endDate));
    }

    @Test
    public void testBetweenEdgeCases() throws ParseException {
        Date startDate = dateFormat.parse("2024-08-20");
        Date endDate = dateFormat.parse("2024-08-21");
        // Test Date equals startDate or endDate
        assertFalse(DateUtils.between(testDate, startDate, endDate));
    }
}
