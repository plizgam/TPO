/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time {
    public static String passed(String s, String s1){

        try {
            if (s.contains("T")) {
                return parseDateTime(s, s1);
            } else {
                return parseDate(s, s1);
            }
        }catch(DateTimeParseException ex){
            return "*** " + ex.toString();
        }
    }

    public static String parseDateTime(String s, String s1) {
        ZonedDateTime dateFrom = LocalDateTime.parse(s).atZone(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime dateTo = LocalDateTime.parse(s1).atZone(ZoneId.of("Europe/Warsaw"));

        String dateLapse = "Od " + getDateTimeLapse(dateFrom.toLocalDateTime()) + " do " + getDateTimeLapse(dateTo.toLocalDateTime());
        return dateLapse + "\n" + getDetailsDateLapse(dateFrom.toLocalDate(), dateTo.toLocalDate()) + "\n" + getDetailsTimeLapse(dateFrom, dateTo) + "\n" + getCalendarLapse(dateFrom.toLocalDate(), dateTo.toLocalDate());
    }


    public static String parseDate(String s, String s1) {
        LocalDate dateFrom = LocalDate.parse(s);
        LocalDate dateTo = LocalDate.parse(s1);
        String dateLapse = "Od " + getDateLapse(dateFrom) + " do " + getDateLapse(dateTo);
        return dateLapse  + "\n" + getDetailsDateLapse(dateFrom, dateTo) + "\n" + getCalendarLapse(dateFrom, dateTo);
    }



    public static String getDateLapse(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("d MMMM YYYY (EEEE)").withLocale(new Locale("PL")));
    }

    public static String getDateTimeLapse(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("d MMMM YYYY (EEEE) 'godz.' hh:mm").withLocale(new Locale("PL")));
    }

    public static String getDetailsDateLapse(LocalDate fromDate, LocalDate toDate){

        long days = ChronoUnit.DAYS.between(fromDate, toDate);
        long weeks = ChronoUnit.WEEKS.between(fromDate, toDate);


        float roundWeeks = BigDecimal.valueOf((float)(days%7)/7).setScale(2, RoundingMode.HALF_UP).floatValue();
        String dayName = languageBreakerDays((int) days);

        return " - mija: " + dayName+ "tygodni " + weeks + "." + String.valueOf(roundWeeks).replace("0.", "");
    }

    public static String getCalendarLapse(LocalDate fromDate, LocalDate toDate){
        Period period = Period.between(fromDate, toDate);

        String yearName = languageBreaker(period.getYears(), new String[]{"rok", "lata", "lat"});
        String monthName = languageBreaker(period.getMonths(), new String[]{"miesiąc", "miesiące", "miesięcy"});
        String dayName = languageBreakerDays(period.getDays());
        String output =  " - kalendarzowo: " + yearName + monthName + dayName;

        return output.substring(0, output.length() - 2);
    }

    public static String getDetailsTimeLapse(ZonedDateTime fromDate, ZonedDateTime toDate){

        long hours = ChronoUnit.HOURS.between(fromDate, toDate);
        long minutes = ChronoUnit.MINUTES.between(fromDate, toDate);

        return " - godzin: " + hours + ", minut: " + minutes;
    }

    public static String languageBreaker(int value, String[] names){
        String output;
        switch (value){
            case 0:
                output = "";
                break;
            case 1:
                output = value + " " + names[0] + ", ";
                break;
            case 2:
            case 3:
            case 4:
                output = value + " " + names[1] + ", ";
                break;

            default:
                output = value + " " + names[2] + ", ";
        }
        return output;
    }
    public static String languageBreakerDays(int days){
        String dayName;
        switch ((int) days){
            case 0:
                dayName = "";
                break;
            case 1:
                dayName = days + " dzień, ";
                break;
            default:
                dayName = days + " dni, ";
        }
        return dayName;
    }
}
