package cn.yu.lib_audio.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created on 2020-03-05
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
@Entity
public class AudioBean implements Serializable {
    private static final long serialVersionUID = 42L;
    /**
     * 音乐唯一id
     */
    @Id
    public String id;
    /**
     * 播放路径
     */
    @NotNull
    @Unique
    public String pathUrl;

    /**
     * 专辑名字
     */
    @NotNull
    public String albumName;
    /**
     * 作者名字
     */
    @NotNull
    public String authorName;
    /**
     * 歌曲名字
     */
    @NotNull
    public String musicName;
    /**
     * 专辑封面
     */
    @NotNull
    public String albumPic;

    @Generated(hash = 519536745)
    public AudioBean(String id, @NotNull String pathUrl, @NotNull String albumName,
                     @NotNull String authorName, @NotNull String musicName,
                     @NotNull String albumPic) {
        this.id = id;
        this.pathUrl = pathUrl;
        this.albumName = albumName;
        this.authorName = authorName;
        this.musicName = musicName;
        this.albumPic = albumPic;
    }

    @Generated(hash = 1628963493)
    public AudioBean() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathUrl() {
        return this.pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getMusicName() {
        return this.musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getAlbumPic() {
        return this.albumPic;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }


}
