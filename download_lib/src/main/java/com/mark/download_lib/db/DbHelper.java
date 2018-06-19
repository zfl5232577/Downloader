package com.mark.download_lib.db;

import android.content.Context;

import com.mark.download_lib.bean.DaoMaster;
import com.mark.download_lib.bean.DaoSession;

/**
 * Author: gaoxu
 * TIME: 2017/7/31
 * Description: This is DbHelper
 * Function:
 */

public class DbHelper {
    private static final String DB_NAME = "mark_load.db";
    private static DbHelper mInstance;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
//    private DBManager<User,Long> mUserDao;

    private DbHelper() {

    }

    /**
     *  不适用双重检查加锁,直接同步整个方法
     * @return
     */
    public static synchronized DbHelper getInstance() {
        if (mInstance == null) {
                if (mInstance == null) {
                    mInstance = new DbHelper();
            }
        }
        return mInstance;
    }

    /**
     * 初始化  放在application初始化
     * @param context
     */
    public void init(Context context) {
        init(context, DB_NAME);
    }

    private void init(Context context, String dbName) {
        mHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 清除数据库
     */
    private void clear() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
    private void close() {
        clear();
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

//    /**
//     * 二次封装DBManager
//     * @return
//     */
//    public DBManager<User,Long> getUserDao () {
//        if (mUserDao == null) {
//            mUserDao = new DBManager<User, Long>() {
//                @Override
//                public AbstractDao<User, Long> getAbstractDao() {
//                    return mDaoSession.getUserDao();
//                }
//            };
//        }
//        return mUserDao;
//    }
}
