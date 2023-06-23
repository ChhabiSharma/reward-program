package com.retailer.rewardprogram.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String getMonthYear(String date) {
        LocalDate localDate = LocalDate.parse(date);
        YearMonth yearMonth = YearMonth.from(localDate);
        return yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }
}
