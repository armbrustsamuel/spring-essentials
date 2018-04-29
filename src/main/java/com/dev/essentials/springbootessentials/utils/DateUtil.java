package com.dev.essentials.springbootessentials.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Samuel on 28/04/18.
 */
@Component
public class DateUtil {

    public String formatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

}
