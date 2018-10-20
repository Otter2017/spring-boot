package org.springframework.boot.peach;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormat {
    public static String format(Date datetime){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simpleDateFormat.format(datetime);
    }

}
