package cn.yu.lib_imageloader;

import android.widget.ImageView;


/**
 * Created on 2020-03-02
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class ImageLoadManger {
    private static ImageLoadManger mImageLoadManger = null;

    private ImageLoadManger() {
    }

    public static ImageLoadManger getInstance() {
        synchronized (ImageLoadManger.class) {
            if (mImageLoadManger == null) {
                mImageLoadManger = new ImageLoadManger();
            }
        }
        return mImageLoadManger;
    }

    public void loadImage2ImageView(ImageView imageView) {
    }
}
