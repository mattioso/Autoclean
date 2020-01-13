package uk.co.appoly.autoclean;

public class DateDatabase {

    static Boolean checkDate(int day, int month, int year, int currentDay, int currentMonth, int currentYear) {

        if(year > currentYear) {
            return false;
        } else if (year == currentYear && month < currentMonth) {
            return false;
        } else if (year == currentYear && month == currentMonth && day < currentDay) {
            return false;
        }

        return true;

    }

}
