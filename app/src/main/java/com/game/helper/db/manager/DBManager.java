package com.game.helper.db.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import com.game.helper.db.DBComm;
import com.game.helper.db.helper.DBHelper;
import com.game.helper.download.bean.AppContent;
import com.game.helper.download.bean.DownloadInfo;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.model.PushModel;
import com.game.helper.model.home.SearchMsg;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * @Description
 * @Path com.game.helper.db.helper.manager.DBManager.java
 * @Author lbb
 * @Date 2016年9月13日 下午6:34:45
 * @Company 
 */
public class DBManager implements DBComm,CommValues {
	protected Context context;
	public DBManager(Context context) {
		this.context=context;
		mDatabaseHelper = new DBHelper(context, "");
	}

	private AtomicInteger mOpenCounter = new AtomicInteger();
	public static DBManager instance;
	public static SQLiteOpenHelper mDatabaseHelper;
	private SQLiteDatabase mDatabase;

	// 初始化
	public static synchronized void initializeInstance(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
		}
	}

	// 获取单例
	public static synchronized DBManager getInstance(Context context) {
		if (instance == null) {
			initializeInstance(context);
		}
		return instance;
	}

	// 获取带有锁的读
	public synchronized SQLiteDatabase getWritableDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			// Opening new database
			mDatabase = mDatabaseHelper.getWritableDatabase();
		}
		return mDatabase;
	}

	// 获取带有锁的读
	public synchronized SQLiteDatabase getReadableDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			// Opening new database
			mDatabase = mDatabaseHelper.getReadableDatabase();
		}
		return mDatabase;
	}

	// 关闭数据库
	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			// Closing database
			mDatabase.close();
		}
	}
	private void closeCurosr(Cursor cursor) {
		if (cursor != null && !cursor.isClosed())
			cursor.close();
	}

	private boolean hasUserExit(SQLiteDatabase db) {
		String sql = "select  * from " + T_USER_MSG + " where  "
				+ FIELD_user_u + " = '" + FIELD_user_u + "'";
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		return isExit;
	}

	// 检索用户信息
	public boolean hasUserExit() {

		SQLiteDatabase database = getReadableDatabase();
		String sql = "select  * from " + T_USER_MSG + " where  "
				+ FIELD_user_u + " = '" + FIELD_user_u + "'";
		Cursor cursor = database.rawQuery(sql, null);
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		closeDatabase();
		return isExit;
	}

	private synchronized void insertUser(SQLiteDatabase db,LoginData user)
			throws IllegalAccessException {
		ContentValues val = new ContentValues();
		val.put(FIELD_user_u, FIELD_user_u);
		val.put(FIELD_user_userid, user.userId);
		val.put(FIELD_user_pwd, user.pwd);
		val.put(FIELD_user_phone, user.phone);
		val.put(FIELD_user_json, user.jsonData);
		long result=db.insert(T_USER_MSG, null, val);
		Log.e("lbb", "----insertUser--resu--"+result);
	}
	private synchronized void insertUser(SQLiteDatabase db,String userId,String pwd,String phone)
			throws IllegalAccessException {
		ContentValues val = new ContentValues();
		val.put(FIELD_user_u, FIELD_user_u);
		val.put(FIELD_user_userid, userId);
		val.put(FIELD_user_pwd, pwd);
		val.put(FIELD_user_phone, phone);
		long result=db.insert(T_USER_MSG, null, val);
		Log.e("lbb", "----insertUser--resu--"+result);
	}

	private synchronized void insert(SQLiteDatabase db,LoginData user) {
		try {
			if (hasUserExit(db)) {
				Log.e("xxx", "-----------updataUser---------");
				updataUser(db,user);
			} else {
				Log.e("xxx", "-----------insertUser---------");
				insertUser(db,user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private synchronized void insert(SQLiteDatabase db,String userId,String pwd,String phone) {
		try {
			if (hasUserExit(db)) {
				Log.e("xxx", "-----------updataUser---------");
				updataUser(db,userId, pwd, phone);
			} else {
				Log.e("xxx", "-----------insertUser---------");
				insertUser(db,userId, pwd, phone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 插入用户信息
	public synchronized void insert(LoginData user) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			insert(db,  user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return;
	}
	// 插入用户信息
	public synchronized void insert(String userId,String pwd,String phone) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			insert(db,  userId, pwd, phone);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return;
	}
	private synchronized void updataUser(SQLiteDatabase db,LoginData user) {
		try {
			ContentValues val = new ContentValues();
			val.put(FIELD_user_u, FIELD_user_u);
			val.put(FIELD_user_userid, user.userId);
			val.put(FIELD_user_phone, user.phone);
			val.put(FIELD_user_pwd, user.pwd);
			val.put(FIELD_user_json, user.jsonData);
			int result = db.update(T_USER_MSG, val, FIELD_user_u + "='" + FIELD_user_u + "'", null);
			Log.e("lbb", "----updataUser--resu--"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private synchronized void updataUser(SQLiteDatabase db,String userId,String pwd,String phone) {
		try {
			ContentValues val = new ContentValues();
			val.put(FIELD_user_u, FIELD_user_u);
			val.put(FIELD_user_userid, userId);
			val.put(FIELD_user_phone, phone);
			val.put(FIELD_user_pwd, pwd);
			int result = db.update(T_USER_MSG, val, FIELD_user_u + "='" + FIELD_user_u + "'", null);
			Log.e("lbb", "----updataUser--resu--"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// 更新用户信息
	public synchronized void updapteUser(LoginData user){

		SQLiteDatabase database = getWritableDatabase();
		updataUser( database ,user);
		closeDatabase();
	}
	public synchronized LoginData getUserMessage() {
		LoginData user=new LoginData();
		String sql = "select  * from " + T_USER_MSG + " where  " + FIELD_user_u + " = '" + FIELD_user_u + "'";
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
			user = new LoginData();
			String jsonData = cursor.getString(cursor.getColumnIndex(FIELD_user_json));
			if (!TextUtils.isEmpty(jsonData)) {
				user =(LoginData) new JsonBuild().getData(LoginData.class, jsonData);
			}
			if(user==null){
				user = new LoginData();
			}
			user.userId = cursor.getString(cursor.getColumnIndex(FIELD_user_userid));
			user.phone = cursor.getString(cursor.getColumnIndex(FIELD_user_phone));
			user.pwd=cursor.getString(cursor.getColumnIndex(FIELD_user_pwd));
		}
		closeCurosr(cursor);
		closeDatabase();
		return user;
	}

	public synchronized void updateUserPhone(String userId,String phone) {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIELD_user_phone, phone);
		database.update(T_USER_MSG, contentValues, FIELD_user_userid + " = ?",
				new String[] { userId });
		closeDatabase();
	}
	public synchronized void updateUserPwd(String userId,String pwd) {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIELD_user_pwd, pwd);
		database.update(T_USER_MSG, contentValues, FIELD_user_userid + " = ?",
				new String[] { userId });
		closeDatabase();
	}
	//---------------------------------------Search----------------------------------//
	private boolean hasSearchExit(SQLiteDatabase db,String userId,String msg,int type) {
		String sql = "select  * from " + T_Search_MSG + " where  "
				+ FIELD_user_userid + " = ? and " + FIELD_msg_searchType + " = ? and " +FIELD_msg_search+ " = ? " ;
		Cursor cursor = db.rawQuery(sql, new String[]{userId, ""+type,msg});
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		return isExit;
	}
	// 检索信息
	public boolean hasSearchExit(String userId,String msg,int type) {

		SQLiteDatabase database = getReadableDatabase();
		String sql = "select  * from " + T_Search_MSG + " where  "
				+ FIELD_user_userid + " = ? and " + FIELD_msg_searchType + " = ? and " +FIELD_msg_search+ " = ? " ;
		Cursor cursor = database.rawQuery(sql, new String[]{userId, ""+type,msg});
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		closeDatabase();
		return isExit;
	}


	private synchronized void insertSearch(SQLiteDatabase db,String userId,String msg,int type)
			throws IllegalAccessException {
		ContentValues val = new ContentValues();
		val.put(FIELD_user_userid, userId);
		val.put(FIELD_msg_search, msg);
		val.put(FIELD_msg_searchType, type);
		long result=db.insert(T_Search_MSG, null, val);
		Log.e("lbb", "----inserSearch--resu--"+result);
	}
	private synchronized void insert(SQLiteDatabase db,String userId,String msg,int type) {
		try {
			if (hasSearchExit(db,userId,msg,type)) {
				Log.e("xxx", "-----------updataSearch---------");
				updateSearch(db, userId,msg,type);
			} else {
				Log.e("xxx", "-----------insertSearch---------");
				insertSearch(db, userId,msg,type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 插入信息
	public synchronized void insert(String userId,String msg,int type) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			insert(db,  userId,msg,type);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return;
	}
	private synchronized void updateSearch(SQLiteDatabase db,String userId,String msg,int type) {
		try {
			ContentValues val = new ContentValues();
			val.put(FIELD_user_userid, userId);
			val.put(FIELD_msg_search, msg);
			val.put(FIELD_msg_searchType, type);
			int result = db.update(T_Search_MSG, val, FIELD_user_userid + "='" + userId + "'", null);
			Log.e("lbb", "----updataSearch--resu--"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized List<SearchMsg> getSearchMsg(String userId,int searchType) {
		List<SearchMsg> map = new ArrayList<SearchMsg>();
		SQLiteDatabase db = getReadableDatabase();

		String sql = "select * from " + T_Search_MSG + " order by " + FIELD_ID
				+ " desc ";

		Cursor cursor = db.rawQuery(sql, null);
		try {
			if (cursor != null && cursor.moveToFirst()) {
				if (cursor.getCount() == 0)
					return map;
				do {
					String uid = cursor.getString(cursor.getColumnIndex(FIELD_user_userid));
					if (TextUtils.isEmpty(uid)||uid.equals(userId)==false) {
						continue;
					}
					int type=cursor.getInt(cursor.getColumnIndex(FIELD_msg_searchType));
					if(searchType!=type){
						continue;
					}
					SearchMsg mSearchMsg=new SearchMsg();
					String mString = cursor.getString(cursor.getColumnIndex(FIELD_msg_search));
					mSearchMsg.msg=mString;
					mSearchMsg.type=type;
					map.add(mSearchMsg);
				} while (cursor.moveToNext());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return map;
	}
	/**
	 * 删除该表下所有数据
	 */
	public void deletedAllSearch() {
		//helper.deletedAll(T_ORDER_LIST);
		SQLiteDatabase db = getWritableDatabase();
		String sql = "delete from " + T_Search_MSG;
		db.execSQL(sql);
	}
	//----------------------------------------------------------------------------------------------------------------------------------//
	private static final String TAG = "DownloadInfoDAO";



	// 检索用户信息
	public boolean hasDownloadInfoExit(SQLiteDatabase db,DownloadInfo downloadInfo) {

		String sql = "select * from "+T_Download_Info+" where "+FIELD_url+"=? and "+FIELD_task_id+"=?";
		Cursor cursor = db.rawQuery(sql, new String[] { downloadInfo.getUrl(), String.valueOf(downloadInfo.getTaskId()) });
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		return isExit;
	}

	private synchronized void insertDownloadInfo(SQLiteDatabase db,DownloadInfo downloadInfo)
			throws IllegalAccessException {
		ContentValues val = new ContentValues();
		val.put(FIELD_task_id, downloadInfo.getTaskId());
		val.put(FIELD_download_length, downloadInfo.getDownloadLength());
		val.put(FIELD_url, downloadInfo.getUrl());
		val.put(FIELD_is_success, downloadInfo.isDownloadSuccess());
		val.put(FIELD_packageName, downloadInfo.getPackageName());
		long result=db.insert(T_Download_Info, null, val);
		Log.e("lbb", "----insertDownloadInfo--resu--"+result);
	}
	/**
	 * 更新下载信息
	 * @param downloadInfo
	 */
	private synchronized void updateDownloadInfo(SQLiteDatabase db,DownloadInfo downloadInfo) {
		if(downloadInfo == null) {
			return;
		}
		try {
			ContentValues val = new ContentValues();
			val.put(FIELD_task_id,  downloadInfo.getTaskId());
			val.put(FIELD_download_length, downloadInfo.getDownloadLength());
			val.put(FIELD_url, downloadInfo.getUrl());
			val.put(FIELD_is_success, downloadInfo.isDownloadSuccess());
			val.put(FIELD_packageName, downloadInfo.getPackageName());

			int result = db.update(T_Download_Info, val, FIELD_task_id + "='" + downloadInfo.getTaskId() + "'"
					+ " and "+FIELD_url + "='" + downloadInfo.getUrl() + "'", null);
			Log.e("lbb", "----updateDownloadInfo--resu--"+result);

			/*String sql = "update "+T_Download_Info+" set "+FIELD_download_length+"=?, "
					+FIELD_is_success+"=?, "+FIELD_packageName+"=? where "+FIELD_task_id+"=? and "+FIELD_url+"=?";
			Object[] bindArgs = { downloadInfo.getDownloadLength(), downloadInfo.isDownloadSuccess(),
					downloadInfo.getPackageName(), downloadInfo.getTaskId(), downloadInfo.getUrl() };
			db.execSQL(sql, bindArgs);*/
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} 
	}


	private synchronized void insert(SQLiteDatabase db,DownloadInfo downloadInfo) {
		try {
			if (hasDownloadInfoExit(db,downloadInfo)) {
				Log.e("xxx", "-----------updataDownloadInfo---------");
				updateDownloadInfo(db,downloadInfo);
			} else {
				Log.e("xxx", "-----------insertDownloadInfo---------");
				insertDownloadInfo(db,downloadInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入用户信息
	public synchronized void insertDownloadInfo(DownloadInfo downloadInfo) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			insert(db,  downloadInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return;
	}
	/**
	 * 插入数据
	 * @param downloadInfo
	 */
	/*public synchronized void insertDownloadInfo(DownloadInfo downloadInfo) {
		if(downloadInfo == null) {
			return;
		}
		//如果本地已经存在，直接修改
		if(getDownloadInfoByTaskIdAndUrl(downloadInfo.getTaskId(), downloadInfo.getUrl()) != null) {
			updateDownloadInfo(downloadInfo);
			return;
		}
		try {
			SQLiteDatabase database = getWritableDatabase();
			ContentValues val = new ContentValues();
			val.put(FIELD_task_id, downloadInfo.getTaskId());
			val.put(FIELD_download_length, downloadInfo.getDownloadLength());
			val.put(FIELD_url, downloadInfo.getUrl());
			val.put(FIELD_is_success, downloadInfo.isDownloadSuccess());
			val.put(FIELD_packageName, downloadInfo.getPackageName());
			long result=database.insert(T_Download_Info, null, val);
			Log.e("lbb", "----insertDownloadInfo--resu--"+result);
			 String sql = "insert into "+T_Download_Info+" ("+FIELD_task_id+", "+ FIELD_download_length+", "
            +FIELD_url+", "+ FIELD_is_success+", "+ FIELD_packageName+" ) values (?,?,?,?,?)";
            Object[] bindArgs = { downloadInfo.getTaskId(), downloadInfo.getDownloadLength(),
                    downloadInfo.getUrl(), downloadInfo.isDownloadSuccess(), downloadInfo.getPackageName()};
            database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}*/
	public synchronized List<DownloadInfo> getDownloadInfos() {

		SQLiteDatabase database = getReadableDatabase();
		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
		Cursor cursor = null;
		try {
			String sql = "select * from "+T_Download_Info+" order by " + FIELD_ID
					+ " desc ";
			cursor = database.rawQuery(sql, null);
			if (cursor != null && cursor.moveToFirst()) {
				if (cursor.getCount() == 0)
					return list;
				do {
					DownloadInfo info = new DownloadInfo();
					/*info.setTaskId(cursor.getInt(1));
	                info.setDownloadLength(cursor.getLong(2));
	                info.setUrl(cursor.getString(3));
	                info.setDownloadSuccess(cursor.getInt(4));
	                info.setPackageName(cursor.getString(5));*/

					info.setTaskId(cursor.getInt(cursor.getColumnIndex(FIELD_task_id)));
					info.setDownloadLength(cursor.getLong(cursor.getColumnIndex(FIELD_download_length)));
					info.setUrl(cursor.getString(cursor.getColumnIndex(FIELD_url)));
					info.setDownloadSuccess(cursor.getInt(cursor.getColumnIndex(FIELD_is_success)));
					info.setPackageName(cursor.getString(cursor.getColumnIndex(FIELD_packageName)));

					list.add(info);
				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return list;
	}
	public synchronized List<DownloadInfo> getDownloadInfosByUrl(String url) {
		if(TextUtils.isEmpty(url)) {
			return null;
		}
		SQLiteDatabase database = getReadableDatabase();
		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
		Cursor cursor = null;
		try {
			String sql = "select * from "+T_Download_Info+" where "+FIELD_url+" =?";
			cursor = database.rawQuery(sql, new String[] { url });
			if (cursor != null && cursor.moveToFirst()) {
				if (cursor.getCount() == 0)
					return list;
				do {
					DownloadInfo info = new DownloadInfo();
					/* info.setTaskId(cursor.getInt(1));
		                info.setDownloadLength(cursor.getLong(2));
		                info.setUrl(cursor.getString(3));
		                info.setDownloadSuccess(cursor.getInt(4));
		                info.setPackageName(cursor.getString(5));*/
					info.setTaskId(cursor.getInt(cursor.getColumnIndex(FIELD_task_id)));
					info.setDownloadLength(cursor.getLong(cursor.getColumnIndex(FIELD_download_length)));
					info.setUrl(cursor.getString(cursor.getColumnIndex(FIELD_url)));
					info.setDownloadSuccess(cursor.getInt(cursor.getColumnIndex(FIELD_is_success)));
					info.setPackageName(cursor.getString(cursor.getColumnIndex(FIELD_packageName)));
					list.add(info);
				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return list;
	}

	/**
	 * 根据taskid和url获取下载信息
	 * @param taskId
	 * @param url
	 * @return
	 */
	public synchronized DownloadInfo getDownloadInfoByTaskIdAndUrl(int taskId, String url) {
		if(TextUtils.isEmpty(url)) {
			return null;
		}
		SQLiteDatabase database = getReadableDatabase();
		DownloadInfo info =null;
		Cursor cursor = null;
		try {
			String sql = "select * from "+T_Download_Info+" where "+FIELD_url+"=? and "+FIELD_task_id+"=?";
			cursor = database.rawQuery(sql, new String[] { url, String.valueOf(taskId) });
			if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
				info = new DownloadInfo();
				/*info.setTaskId(cursor.getInt(1));
                info.setDownloadLength(cursor.getLong(2));
                info.setUrl(cursor.getString(3));
                info.setDownloadSuccess(cursor.getInt(4));
                info.setPackageName(cursor.getString(5));*/
				info.setTaskId(cursor.getInt(cursor.getColumnIndex(FIELD_task_id)));
				info.setDownloadLength(cursor.getLong(cursor.getColumnIndex(FIELD_download_length)));
				info.setUrl(cursor.getString(cursor.getColumnIndex(FIELD_url)));
				info.setDownloadSuccess(cursor.getInt(cursor.getColumnIndex(FIELD_is_success)));
				info.setPackageName(cursor.getString(cursor.getColumnIndex(FIELD_packageName)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return info;
	}



	/**
	 * 根据url删除记录
	 * @param url
	 */
	public synchronized void delDownloadInfoByUrl(String url) {
		if(TextUtils.isEmpty(url)) {
			return;
		}
		SQLiteDatabase database = getWritableDatabase();
		try {
			String sql = "delete from "+T_Download_Info+" where "+FIELD_url+"=?";
			Object[] bindArgs = { url };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}
	/**
	 * 根据packageName删除记录
	 * @param url
	 *//*
	public synchronized void delDownloadInfoByPackageName(String packageName) {
		if(TextUtils.isEmpty(packageName)) {
			return;
		}
		SQLiteDatabase database = getWritableDatabase();
		try {
			String sql = "delete from "+T_Download_Info+" where "+FIELD_packageName+"=?";
			Object[] bindArgs = { packageName };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}*/
	//-------------------------------------------------------------------------------------------------------------------------//
	// 检索用户信息
	public boolean hasDownloadFileExit(SQLiteDatabase db,AppContent appContent) {

		String sql = "select * from "+T_Download_File+" where "+FIELD_url+" =? ";
		Cursor cursor = db.rawQuery(sql, new String[] { appContent.downloadPath.trim() });
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		return isExit;
	}

	private synchronized void insertDownloadFile(SQLiteDatabase db,AppContent appContent)
			throws IllegalAccessException {
		ContentValues val = new ContentValues();
		val.put(FIELD_app_name, appContent.gameName);
		val.put(FIELD_url, appContent.downloadPath.trim());
		val.put(FIELD_download_percent, appContent.downloadPercent);
		val.put(FIELD_status, appContent.status.getValue());
		val.put(FIELD_packageName, appContent.packageName);
		val.put(FIELD_app_json, appContent.jsonData);
		long result=db.insert(T_Download_File, null, val);
		Log.e("lbb", "----insertDownloadFile--resu--"+result);
	}
	/**
	 * 更新下载信息
	 * @param downloadInfo
	 */
	private synchronized void updateDownloadFile(SQLiteDatabase db,AppContent appContent) {

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(FIELD_app_name, appContent.gameName);
			contentValues.put(FIELD_url, appContent.downloadPath.trim());
			contentValues.put(FIELD_download_percent, appContent.downloadPercent);
			contentValues.put(FIELD_status, appContent.status.getValue());
			contentValues.put(FIELD_packageName, appContent.packageName);
			contentValues.put(FIELD_app_json,  appContent.jsonData);
			long result=db.update(T_Download_File, contentValues, FIELD_url + " = ? ",
					new String[] { appContent.downloadPath.trim() });
			Log.e("lbb", "----updateDownloadFile--resu--"+result);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} 
	}
	/**
	 * 更新下载信息
	 * @param downloadInfo
	 */
	public synchronized void updateDownloadFile(AppContent appContent) {

		try {
			if(appContent == null||TextUtils.isEmpty(appContent.downloadPath)) {
				return;
			}
			appContent.jsonData=null;
			appContent.jsonData=new JsonBuild().setModel(appContent).getJsonString1();

			SQLiteDatabase db = getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put(FIELD_app_name, appContent.gameName);
			contentValues.put(FIELD_url, appContent.downloadPath.trim());
			contentValues.put(FIELD_download_percent, appContent.downloadPercent);
			contentValues.put(FIELD_status, appContent.status.getValue());
			contentValues.put(FIELD_packageName, appContent.packageName);
			contentValues.put(FIELD_app_json,  appContent.jsonData);
			long result=db.update(T_Download_File, contentValues, FIELD_url + " = ? ",
					new String[] { appContent.downloadPath.trim() });
			Log.e("lbb", "----updateDownloadFile--resu--"+result);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}finally {
			closeDatabase();
		} 
	}

	private synchronized void insert(SQLiteDatabase db,AppContent appContent) {
		try {
			if (hasDownloadFileExit(db,appContent)) {
				Log.e("xxx", "-----------updataDownloadFile---------");
				updateDownloadFile(db,appContent);
			} else {
				Log.e("xxx", "-----------insertDownloadFile---------");
				insertDownloadFile(db,appContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入用户信息
	public synchronized void insertDownloadFile(AppContent appContent) {
		if(appContent == null||TextUtils.isEmpty(appContent.downloadPath)) {
			return;
		}
		appContent.jsonData=null;
		appContent.jsonData=new JsonBuild().setModel(appContent).getJsonString1();
		SQLiteDatabase db = getWritableDatabase();
		try {
			insert(db,  appContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return;
	}
	/**
	 * 插入数据
	 * @param appContent
	 */
	/*public synchronized void insertDownloadFile(AppContent appContent) {
		if(appContent == null||TextUtils.isEmpty(appContent.downloadPath)) {
			return;
		}
		appContent.jsonData=null;
		appContent.jsonData=new JsonBuild().setModel(appContent).getJsonString1();
		//如果本地已经存在，直接修改
		if(getAppContentByUrl(appContent.downloadPath.trim()) != null) {
			updateDownloadFile(appContent);
			return;
		}
		SQLiteDatabase database = getWritableDatabase();
		try {

			ContentValues val = new ContentValues();
			val.put(FIELD_app_name, appContent.gameName);
			val.put(FIELD_url, appContent.downloadPath.trim());
			val.put(FIELD_download_percent, appContent.downloadPercent);
			val.put(FIELD_status, appContent.status.getValue());
			val.put(FIELD_packageName, appContent.packageName);
			val.put(FIELD_app_json, appContent.jsonData);
			long result=database.insert(T_Download_File, null, val);
			Log.e("lbb", "----insertDownloadFile--resu--"+result);

			  String sql = "insert into "+T_Download_File+"("+FIELD_app_name+", "+FIELD_url+", "
               +FIELD_download_percent+", "+FIELD_status+", "+FIELD_packageName+", "+ FIELD_app_json+") values (?,?,?,?,?,?)";
            Object[] bindArgs = { appContent.gameName, appContent.downloadPath.trim(), appContent.downloadPercent
                    , appContent.status.getValue() , appContent.packageName,appContent.jsonData};
            database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}*/

	/**
	 * 根据url获取下载文件信息
	 * @param url
	 * @return
	 */
	public synchronized AppContent getAppContentByUrl(String url) {
		if(TextUtils.isEmpty(url)) {
			return null;
		}
		SQLiteDatabase database = getReadableDatabase();
		AppContent appContent = null;
		Cursor cursor = null;
		try {
			String sql = "select * from "+T_Download_File+" where "+FIELD_url+" =? ";
			cursor = database.rawQuery(sql, new String[] { url.trim() });
			if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
				appContent = new AppContent();
				String jsonData= cursor.getString(cursor.getColumnIndex(FIELD_app_json));
				if (!TextUtils.isEmpty(jsonData)) {
					appContent =(AppContent) new JsonBuild().getData(AppContent.class, jsonData);
				}
				if(appContent==null){
					appContent = new AppContent();
				}
				/* String gameName= cursor.getString(1);
                int downloadPercent=cursor.getInt(2);
                String downloadPath=cursor.getString(3);
                AppContent.Status status=AppContent.Status.getByValue(cursor.getInt(4));
                String packageName= cursor.getString(5);*/
				String gameName= cursor.getString(cursor.getColumnIndex(FIELD_app_name));
				int downloadPercent= cursor.getInt(cursor.getColumnIndex(FIELD_download_percent));
				String downloadPath= cursor.getString(cursor.getColumnIndex(FIELD_url));
				AppContent.Status status= AppContent.Status.getByValue(cursor.getInt(cursor.getColumnIndex(FIELD_status)));
				String packageName= cursor.getString(cursor.getColumnIndex(FIELD_packageName));

				appContent.gameName=gameName;
				appContent.downloadPercent=downloadPercent;
				appContent.downloadPath=downloadPath;
				appContent.status=status;
				appContent.packageName=packageName;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return appContent;
	}

	/**
	 * 更新下载信息
	 * @param appContent
	 */
	/*private synchronized void updateDownloadFile(AppContent appContent) {
		if(appContent == null||TextUtils.isEmpty(appContent.downloadPath)) {
			return;
		}
		appContent.jsonData=null;
		appContent.jsonData=new JsonBuild().setModel(appContent).getJsonString1();

		SQLiteDatabase database = getWritableDatabase();
		try { 
			ContentValues contentValues = new ContentValues();
			contentValues.put(FIELD_app_name, appContent.gameName);
			contentValues.put(FIELD_url, appContent.downloadPath.trim());
			contentValues.put(FIELD_download_percent, appContent.downloadPercent);
			contentValues.put(FIELD_status, appContent.status.getValue());
			contentValues.put(FIELD_packageName, appContent.packageName);
			contentValues.put(FIELD_app_json,  appContent.jsonData);
			long result=database.update(T_Download_File, contentValues, FIELD_url + " = ?",
					new String[] { appContent.downloadPath.trim() });

			Log.e("lbb", "----updateDownloadFile--resu--"+result);
			 Log.e(TAG, "update download_file,app name:" + appContent.gameName + ",url:" + appContent.downloadPath
                    + ",percent" + appContent.downloadPercent + ",status:" + appContent.status.getValue());
			String sql = "update "+T_Download_File+" set "+FIELD_app_name+"=?, "+FIELD_url+"=?, "+FIELD_download_percent+"=?, "
					+FIELD_status+"=?, " +FIELD_packageName+"=?, " +FIELD_app_json+"=? where "+FIELD_url+"=?";
			Object[] bindArgs = {appContent.gameName, appContent.downloadPath.trim(), appContent.downloadPercent
					, appContent.status.getValue(), appContent.packageName, appContent.jsonData, appContent.downloadPath.trim()};
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}*/

	/**
	 * 获取所有下载文件记录
	 * @return
	 */
	public synchronized List<AppContent> getAll() {
		

		SQLiteDatabase database = getReadableDatabase();
		List<AppContent> list = new ArrayList<AppContent>();
		Cursor cursor = null;
		try {
			String sql = "select * from "+T_Download_File+" order by " + FIELD_ID
					+ " desc ";

			cursor = database.rawQuery(sql, null);
			if (cursor != null && cursor.moveToFirst()) {
				if (cursor.getCount() == 0)
					return list;
				do {
					AppContent appContent = new AppContent();
					String jsonData= cursor.getString(cursor.getColumnIndex(FIELD_app_json));
					if (!TextUtils.isEmpty(jsonData)) {
						appContent =(AppContent) new JsonBuild().getData(AppContent.class, jsonData);
					}
					if(appContent==null){
						appContent = new AppContent();
					}
					/* String gameName= cursor.getString(1);
	                int downloadPercent=cursor.getInt(2);
	                String downloadPath=cursor.getString(3);
	                AppContent.Status status=AppContent.Status.getByValue(cursor.getInt(4));
	                String packageName= cursor.getString(5);*/
					String gameName= cursor.getString(cursor.getColumnIndex(FIELD_app_name));
					int downloadPercent= cursor.getInt(cursor.getColumnIndex(FIELD_download_percent));
					String downloadPath= cursor.getString(cursor.getColumnIndex(FIELD_url));
					AppContent.Status status= AppContent.Status.getByValue(cursor.getInt(cursor.getColumnIndex(FIELD_status)));
					String packageName= cursor.getString(cursor.getColumnIndex(FIELD_packageName));

					appContent.gameName=gameName;
					appContent.downloadPercent=downloadPercent;
					appContent.downloadPath=downloadPath;
					appContent.status=status;
					appContent.packageName=packageName;
					list.add(appContent);
				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return list;
	}

	/**
	 * 根据url删除记录
	 * @param url
	 */
	public synchronized void delByUrl(String url) {
		if(TextUtils.isEmpty(url)) {
			return;
		}
		SQLiteDatabase database = getWritableDatabase();
		try {
			String sql = "delete from "+T_Download_File+" where "+FIELD_url+"=?";
			Object[] bindArgs = { url.trim() };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}
/*	*//**
	 * 根据packageName删除记录
	 * @param url
	 *//*
	public synchronized void delByPackageName(String packageName) {
		if(TextUtils.isEmpty(packageName)) {
			return;
		}
		SQLiteDatabase database = getWritableDatabase();
		try {
			String sql = "delete from "+T_Download_File+" where "+FIELD_packageName+"=?";
			Object[] bindArgs = { packageName };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		} finally {
			closeDatabase();
		}
	}*/
	//---------------------------------------Push--------------------------------------------------------//
	
	private boolean hasPushExit(SQLiteDatabase db,PushModel push) {
		String sql = "select  * from " + T_Push_MSG + " where  "
				+ FIELD_msg_id + " = '" + push.pushId + "'";
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExit = false;
		if (cursor.getCount() > 0) {
			isExit = true;
		}
		closeCurosr(cursor);
		return isExit;
	}
	private synchronized void insertPush(SQLiteDatabase db,PushModel push)
			throws IllegalAccessException {
		ContentValues val = new ContentValues();
		val.put(FIELD_msg_id, push.pushId);
		
		val.put(FIELD_push_title, push.title);
		val.put(FIELD_user_userid, push.userId);
		val.put(FIELD_msg_type, push.tradetype);
		val.put(FIELD_push_msg, push.content);
		val.put(FIELD_push_id, push.id);
		val.put(FIELD_push_time, push.tradedate);
		val.put(FIELD_push_isRead, push.isRead);
		long result=db.insert(T_Push_MSG, null, val);
		Log.e("lbb", "----insertPush--resu--"+result);
	}
	

	private synchronized void insert(SQLiteDatabase db,PushModel push) {
		try {
			
			if (hasPushExit(db,push)) {
				Log.e("xxx", "-----------updataPush---------");
				updatePush(db,push);
			} else {
				Log.e("xxx", "-----------insertPush---------");
				insertPush(db,push);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 插入信息
	public synchronized void insert(PushModel push) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			insert(db,  push);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return;
	}
	
	private synchronized void updatePush(SQLiteDatabase db,PushModel push) {
		try {
			ContentValues val = new ContentValues();
			val.put(FIELD_msg_id, push.pushId);
			
			val.put(FIELD_push_title, push.title);
			val.put(FIELD_user_userid, push.userId);
			val.put(FIELD_msg_type, push.tradetype);
			val.put(FIELD_push_msg, push.content);
			val.put(FIELD_push_id, push.id);
			val.put(FIELD_push_time, push.tradedate);
			val.put(FIELD_push_isRead, push.isRead);
			
			int result = db.update(T_Push_MSG, val, FIELD_msg_id + "='" + push.pushId + "'", null);
			Log.e("lbb", "----updataPush--resu--"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 更新信息
	public synchronized void updaptePush(PushModel push){
		SQLiteDatabase database = getWritableDatabase();
		updatePush(database ,push);
		closeDatabase();
	}
	public synchronized void updatePush(String pushId,int isRead) {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(FIELD_push_isRead, isRead);
		database.update(T_Push_MSG, contentValues, FIELD_msg_id + " = ?",
				new String[] { pushId });
		closeDatabase();
	}
	
	
	public synchronized List<PushModel> getPushAllMessage() {
		LoginData user=getUserMessage();
		List<PushModel> map =new ArrayList<PushModel>();
		SQLiteDatabase db = getReadableDatabase();

		String sql = "select * from " + T_Push_MSG + " order by " + FIELD_ID
				+ " desc ";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			if (cursor != null && cursor.moveToFirst()) {
				if (cursor.getCount() == 0)
					return map;
				do {
					PushModel push = new PushModel();
					push.pushId = cursor.getString(cursor.getColumnIndex(FIELD_msg_id));
					push.title = cursor.getString(cursor.getColumnIndex(FIELD_push_title));
					push.userId = cursor.getString(cursor.getColumnIndex(FIELD_user_userid));
					push.tradetype = cursor.getString(cursor.getColumnIndex(FIELD_msg_type));
					push.content = cursor.getString(cursor.getColumnIndex(FIELD_push_msg));
					push.id = cursor.getString(cursor.getColumnIndex(FIELD_push_id));
					push.tradedate = cursor.getString(cursor.getColumnIndex(FIELD_push_time));
					push.isRead = cursor.getInt(cursor.getColumnIndex(FIELD_push_isRead));

					if (TextUtils.isEmpty(push.userId)||push.userId.equals(user.userId)==false) {
						continue;
					}
					map.add(push);
				} while (cursor.moveToNext());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeCurosr(cursor);
			closeDatabase();
		}
		return map;
		
	}

	
	
}
