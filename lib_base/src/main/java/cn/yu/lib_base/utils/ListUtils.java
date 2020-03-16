package cn.yu.lib_base.utils;

import java.util.List;

/**
 * Created on 2020-03-06
 * 这是一个列表的工具类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class ListUtils {

    private static ListUtils mListUtils = null;

    private ListUtils() {
    }

    public static ListUtils getInstance() {
        synchronized (ListUtils.class) {
            if (mListUtils == null) {
                mListUtils = new ListUtils();
            }
        }
        return mListUtils;
    }

    /**
     * 列表是否为空
     *
     * @param list 列表
     * @return 是否为空
     */
    public boolean isEmpty(List<?> list) {
        if (list != null && list.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * 索引是否有用
     *
     * @return 是否有用
     */
    public boolean indexIsEffective(int index, List<?> list) {
        if (isEmpty(list)) {
            return false;
        }
        if (index >= 0 && index < list.size()) {
            return true;
        }
        return false;
    }

}
