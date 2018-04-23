package com.yw.mvp.db.helper;

import android.content.Context;

import com.yw.mvp.db.dao.DaoMaster;
import com.yw.mvp.db.dao.UserBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * 作者：create by YW
 * 日期：2017.03.27 16:57
 * 描述：GreenDao helper
 */
public class HMROpenHelper extends DaoMaster.DevOpenHelper {

    private String dBName;

    /**
     * 初始化一个AbSDDBHelper.
     *
     * @param context 应用context
     * @param name    数据库名
     */
    public HMROpenHelper(Context context, String name) {
        super(context, name);
        this.dBName = name;
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新
        if (dBName.equals(GreenDbManager.DBNAME)) {
            MigrationHelper.migrate(db, UserBeanDao.class);
        } else {
            MigrationHelper.migrate(db, UserBeanDao.class);
        }
    }

}
