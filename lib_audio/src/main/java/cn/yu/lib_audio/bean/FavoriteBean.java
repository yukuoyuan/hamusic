package cn.yu.lib_audio.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import cn.yu.lib_audio_mediaplayer.db.DaoSession;
import cn.yu.lib_audio_mediaplayer.db.AudioBeanDao;
import cn.yu.lib_audio_mediaplayer.db.FavoriteBeanDao;

/**
 * Created on 2020-03-13
 * 收藏表
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
@Entity
public class FavoriteBean {
    /**
     * 主键并且自增
     */
    @Id(autoincrement = true)
    public Long favouriteId;

    @NotNull
    public String audioId;

    @ToOne(joinProperty = "audioId")
    public AudioBean mAudioBean;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 287838093)
    private transient FavoriteBeanDao myDao;

    @Generated(hash = 1189700618)
    public FavoriteBean(Long favouriteId, @NotNull String audioId) {
        this.favouriteId = favouriteId;
        this.audioId = audioId;
    }

    @Generated(hash = 653294794)
    public FavoriteBean() {
    }

    public Long getFavouriteId() {
        return this.favouriteId;
    }

    public void setFavouriteId(Long favouriteId) {
        this.favouriteId = favouriteId;
    }

    public String getAudioId() {
        return this.audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    @Generated(hash = 608298093)
    private transient String mAudioBean__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1742330199)
    public AudioBean getMAudioBean() {
        String __key = this.audioId;
        if (mAudioBean__resolvedKey == null || mAudioBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AudioBeanDao targetDao = daoSession.getAudioBeanDao();
            AudioBean mAudioBeanNew = targetDao.load(__key);
            synchronized (this) {
                mAudioBean = mAudioBeanNew;
                mAudioBean__resolvedKey = __key;
            }
        }
        return mAudioBean;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1491270612)
    public void setMAudioBean(@NotNull AudioBean mAudioBean) {
        if (mAudioBean == null) {
            throw new DaoException(
                    "To-one property 'audioId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.mAudioBean = mAudioBean;
            audioId = mAudioBean.getId();
            mAudioBean__resolvedKey = audioId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1852909385)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFavoriteBeanDao() : null;
    }
}
