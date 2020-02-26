package cn.yu.hamusic.home.beans;

/**
 * Created on 2020-02-26
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public enum CHANNEL {

    MY("我的", 0x01),
    DISCOVERY("发现", 0x02),
    FRIEND("朋友", 0x03);
    /**
     * key
     */
    private final String key;
    /**
     * 值
     */
    private final int value;
    /**
     * 我的id
     */
    public static final int MINE_ID = 0x01;
    /**
     * 发现id
     */
    public static final int DISCORY_ID = 0x02;
    /**
     * 朋友的id
     */
    public static final int FRIEND_ID = 0x03;

    CHANNEL(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
