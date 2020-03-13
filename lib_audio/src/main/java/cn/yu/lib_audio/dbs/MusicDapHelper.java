package cn.yu.lib_audio.dbs;

import android.database.sqlite.SQLiteDatabase;


import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.bean.FavoriteBean;
import cn.yu.lib_audio_mediaplayer.db.DaoMaster;
import cn.yu.lib_audio_mediaplayer.db.DaoSession;
import cn.yu.lib_audio_mediaplayer.db.FavoriteBeanDao;

/**
 * Created on 2020-03-12
 * 这是一个音乐数据库的帮助类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicDapHelper {
    /**
     * 数据库名称
     */
    private static final String DB_NAME = "music_db";
    /**
     * 数据库帮助
     */
    private static DaoMaster.DevOpenHelper helper;
    /**
     * 得到一个可以写入的数据库
     */
    private SQLiteDatabase mSQLiteDatabase;
    /**
     * 表的管理
     */
    private DaoMaster mDaoMaster;
    /**
     * 用来操作表
     */
    private DaoSession mDaoSession;

    private static MusicDapHelper mMusicDapHelper = null;

    private MusicDapHelper() {
        /*
         * 初始化数据库
         */
        helper = new DaoMaster.DevOpenHelper(AudioHelper.getInstance().getContext(), DB_NAME);
        mSQLiteDatabase = helper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mSQLiteDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    public static MusicDapHelper getInstance() {
        synchronized (MusicDapHelper.class) {
            if (mMusicDapHelper == null) {
                mMusicDapHelper = new MusicDapHelper();
            }
        }
        return mMusicDapHelper;
    }

    /**
     * 添加一条数据到表中
     *
     * @param audioBean 音乐实体
     */
    public void addFavoriteMusic(AudioBean audioBean) {
        FavoriteBeanDao favoriteBeanDao = mDaoSession.getFavoriteBeanDao();
        FavoriteBean favoriteBean = new FavoriteBean();
        favoriteBean.audioId = audioBean.id;
        favoriteBean.mAudioBean = audioBean;
        favoriteBeanDao.insertOrReplace(favoriteBean);
    }

    /**
     * 查询是否有这条收藏记录
     *
     * @param audioBean 音乐实体
     * @return 收藏实体
     */
    public FavoriteBean searchFavoriteBean(AudioBean audioBean) {
        FavoriteBeanDao favoriteBeanDao = mDaoSession.getFavoriteBeanDao();
        FavoriteBean favoriteBean = favoriteBeanDao.queryBuilder().where(FavoriteBeanDao.Properties.AudioId.eq(audioBean.id)).unique();
        return favoriteBean;
    }

    /**
     * 删除这条收藏记录
     *
     * @param audioBean 音乐实体
     */
    public void removeFavoriteBean(AudioBean audioBean) {
        FavoriteBeanDao favoriteBeanDao = mDaoSession.getFavoriteBeanDao();
        FavoriteBean favoriteBean = favoriteBeanDao.queryBuilder().where(FavoriteBeanDao.Properties.AudioId.eq(audioBean.id)).unique();
        if (favoriteBean != null) {
            favoriteBeanDao.delete(favoriteBean);
        }
    }

}
