package cn.yu.lib_audio.bean;

import java.io.Serializable;

/**
 * Created on 2020-03-05
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioBean implements Serializable {
    /**
     * 音乐唯一id
     */
    public String id;
    /**
     * 播放路径
     */
    public String pathUrl;
    /**
     * 歌曲名字
     */
    public String musicName;
    /**
     * 作者名字
     */
    public String authorName;
    /**
     * 专辑名字
     */
    public String albumName;
    /**
     * 专辑封面
     */
    public String albumPic;


    public AudioBean(String id, String pathUrl, String musicName, String authorName, String albumName, String albumPic) {
        this.id = id;
        this.pathUrl = pathUrl;
        this.musicName = musicName;
        this.authorName = authorName;
        this.albumName = albumName;
        this.albumPic = albumPic;

    }
}
