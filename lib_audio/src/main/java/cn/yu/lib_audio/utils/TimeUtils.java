package cn.yu.lib_audio.utils;

/**
 * Created on 2020-03-13
 * 这是一个时间工具类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class TimeUtils {
    private static TimeUtils mTimeUtils = null;

    private TimeUtils() {
    }

    public static TimeUtils getInstance() {
        synchronized (TimeUtils.class) {
            if (mTimeUtils == null) {
                mTimeUtils = new TimeUtils();
            }
        }
        return mTimeUtils;
    }

    /**
     * ms 转换为分钟和秒
     *
     * @param ms 毫秒
     * @return 分钟:秒
     */
    public String msConvertMinutesAndSeconds(int ms) {
        /*
         * 几分钟
         */
        String minutes = String.valueOf(ms / (1000 * 60));
        /*
         * 多少秒
         */
        String seconds = String.valueOf(ms % (1000 * 60) / 1000);
        /*
         * 封装为01格式
         */
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        /*
         * 封装为01格式
         */
        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        return minutes + ":" + seconds;
    }
}
