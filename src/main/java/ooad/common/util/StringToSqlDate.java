package ooad.common.util;

import java.text.SimpleDateFormat;

/**
 * Created by xingxiaoyu on 17/6/7.
 */
public class StringToSqlDate {
    public static java.sql.Date strToDate(String strDate) {
        String str = strDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }
}
