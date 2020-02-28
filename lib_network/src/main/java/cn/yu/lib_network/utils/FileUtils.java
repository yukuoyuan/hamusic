package cn.yu.lib_network.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created on 2020-02-28
 * 这是一个文件工具类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class FileUtils {
    private static FileUtils mFileUtils = null;

    private FileUtils() {
    }

    public static FileUtils getInstance() {
        synchronized (FileUtils.class) {
            if (mFileUtils == null) {
                mFileUtils = new FileUtils();
            }
        }
        return mFileUtils;
    }

    /**
     * 检查文件路径,文件不存在就创建
     *
     * @param localFilePath 文件路径
     */
    public void checkLocalFilePath(String localFilePath) throws Exception {
        if (TextUtils.isEmpty(localFilePath)) {
            throw new Exception("文件路径不能传递空的老哥");
        }
        File path = new File(localFilePath.substring(0,
                localFilePath.lastIndexOf("/") + 1));
        File file = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
