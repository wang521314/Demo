package bo.liu.myviewpager.util;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class StringUtils {
    public static boolean isNotEmptyString(final String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEmptyString(final String str) {
        return str == null || str.length() <= 0;
    }
}
