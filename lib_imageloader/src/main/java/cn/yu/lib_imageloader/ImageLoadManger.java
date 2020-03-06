package cn.yu.lib_imageloader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


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

    /**
     * 加载图片到图片控件
     *
     * @param imageView 控件
     * @param url       路径
     */
    public void loadImage2ImageView(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    /**
     * 加载圆形图片到图片控件
     *
     * @param imageView 控件
     * @param url       路径
     */
    public void loadCircleImage2ImageView(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).apply(new RequestOptions().circleCrop()).into(imageView);
    }
}
