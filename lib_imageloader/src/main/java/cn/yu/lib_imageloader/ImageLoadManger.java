package cn.yu.lib_imageloader;

import android.app.Notification;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.Target;


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

    /**
     * 加载图片到通知的图片控件中
     *
     * @param context        上下文
     * @param url            路径
     * @param viewId         控件id
     * @param remoteViews    自定义的通知view
     * @param notification   通知
     * @param notificationId 通知id
     */
    public void loadImage2NotificationImageView(Context context, String url,
                                                int viewId, RemoteViews remoteViews, Notification notification, int notificationId) {
        NotificationTarget notificationTarget = new NotificationTarget(context, viewId, remoteViews, notification, notificationId);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into((Target) notificationTarget);
    }
}
