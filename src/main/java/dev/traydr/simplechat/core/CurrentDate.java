package dev.traydr.simplechat.core;

import java.sql.Date;

public class CurrentDate {
    public static Date getCurrentDate(long offset) {
        return new Date(System.currentTimeMillis() + (offset * 1000));
    }
}
