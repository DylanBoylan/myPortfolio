package abc_app;

import java.util.Date;

public class DateUtils {
  /** Given a Date, returns a String "Day, Month Number, Year", 
   *  e.g. "Wednesday, November 2, 2011". For results page.
   */
  public static String formatDate(Date date) {
    if (date == null) {
      return("");
    } else {
      return(String.format("%tA, %tB %te, %tY", 
                           date, date, date, date));
    }
  }
  /** Given a Date, returns a String of the form "hh:mm:ss am on Day, Month Number, Year", 
   *  e.g., "12:23:42 pm on Wednesday, November 13, 2013". Used by getSampleTime.
   */
  public static String formatTime(Date date) {
    if (date == null) {
      return("");
    } else {
      return(String.format("%tl:%tM:%tS %tp on %tA, %tB %te, %tY", 
                            date, date, date, date, date, date, date, date));
    }
  }
  
  /** Returns a Date 24 hours later than the input Date. */
  
  public static Date nextDay(Date day) {
    long millisPerDay = 24 * 60 * 60 * 1000;
    return(new Date(day.getTime() + millisPerDay));
  }
  
  /** Returns true if the first Date is between the other two; returns false otherwise. */
  
  public static boolean between(Date testDate, Date startDate, Date endDate) {
    return(testDate.after(startDate) && testDate.before(endDate));
  }
  private DateUtils() {} // Class cannot be instantiated
}
