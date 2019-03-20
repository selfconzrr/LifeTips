package com.example.zhangruirui.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.WorkerThread;

import com.example.zhangruirui.User;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

public class UserStorage {

  private SQLiteDatabase mDatabase;
  private UserDao mUserDao; // 获取 dao 对象
  private final static String DB_NAME = "user_name_age.db"; // 定义数据库名

  // 获取核心类的实例
  UserStorage(Context context) {
    DaoMaster.OpenHelper helper = new UserDBOpenHelper(context, DB_NAME, null);
    try {
      mDatabase = helper.getWritableDatabase();
      DaoMaster daoMaster = new DaoMaster(mDatabase);
      DaoSession daoSession = daoMaster.newSession();
      mUserDao = daoSession.getUserDao();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public User getUserByName(String name) {
    if (!isDataBaseValid()) {
      return null;
    }

    return mUserDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).unique();

  }

  /**
   * 根据用户的名字删除对应的记录
   */
  public void removeUser(String name) {
    if (!isDataBaseValid()) {
      return;
    }
    try {
      QueryBuilder<User> qb = mUserDao.queryBuilder();
      DeleteQuery<User> bd = qb.where(UserDao.Properties.Name.eq(name))
          .buildDelete();
      bd.executeDeleteWithoutDetachingEntities();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 新增记录，如果存在则更新，不存在直接插入
   */
  @WorkerThread
  public synchronized void addUser(String name, String json) {
    if (!isDataBaseValid()) {
      return;
    }

    try {
      User user = new User();
      user.setName(name);
      user.setAge(json);

      /**
       * 解决 Exception：
       * Cannot update entity without key - was it inserted before?
       */
      User oldUser = getUserByName(name);
      if (oldUser != null) {
        user.setId(oldUser.getId());
      }
      // mUserDao.save(user);
      if (getUserByName(name) == null) {
        mUserDao.insert(user);
      } else {
        mUserDao.update(user);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 删除所有数据
   */
  @WorkerThread
  public synchronized void deleteAll() {
    mUserDao.deleteAll();
  }

  private boolean isDataBaseValid() {
    return mUserDao != null;
  }

  public String getUserAge(String key) {
    return mUserDao.queryBuilder().where(UserDao.Properties.Name.eq(key)).list().get(0).getAge();
  }
}
