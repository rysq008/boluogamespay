package com.game.helper.db.helper;

import com.game.helper.db.DBComm;
import com.game.helper.interfaces.comm.CommValues;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 	 
 * @Description 
 * @Path	
 * @Author	
 * @Date	2016年9月6日 下午4:22:32 
 * @Company	 	
 */
public class DBHelper extends SQLiteOpenHelper implements CommValues, DBComm {


	public DBHelper(Context context,String uid) {
		//+ uid
		super(context, T_DB , null, VERSION);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		CreateIfNotExists(db);
	}

	public void CreateIfNotExists(SQLiteDatabase db) {
		
		
		db.execSQL(creatT + T_USER_MSG + "(" 
				+ FIELD_ID + " integer primary key autoincrement," 
				+ FIELD_user_u + " varchar(25)," 
				+ FIELD_user_userid + " varchar(25)," 
				+ FIELD_user_pwd + " varchar(25)," 
				+ FIELD_user_phone + " varchar(25),"
				+ FIELD_user_json + " text"
				+ ");");
		
		db.execSQL(creatT + T_Search_MSG + "(" 
				+ FIELD_ID + " integer primary key autoincrement," 
				+ FIELD_user_userid + " varchar(25)," 
				+ FIELD_msg_searchType + " integer,"
				+ FIELD_msg_search + " varchar(25)" 
				
				+ ");");
		db.execSQL(creatT + T_Download_Info + "(" 
				+ FIELD_ID + " integer primary key autoincrement," 
				+ FIELD_task_id + " integer," 
				+ FIELD_download_length + " integer,"
				+ FIELD_url + " varchar(255)," 
				+ FIELD_is_success + " integer,"
				+ FIELD_packageName+ " varchar(255)"
				+ ");");
		
        db.execSQL(creatT + T_Download_File + "(" 
				+ FIELD_ID + " integer primary key autoincrement," 
				+ FIELD_app_name + " varchar(255)," 
				+ FIELD_download_percent + " integer,"
				+ FIELD_url + " varchar(255)," 
				+ FIELD_status + " integer,"
				+ FIELD_packageName+ " varchar(255)," 
				+ FIELD_app_json + " text"
				
				+ ");");
        
        db.execSQL(creatT + T_Push_MSG + "(" 
				+ FIELD_ID + " integer primary key autoincrement," 
				+ FIELD_msg_id+ " varchar(25)," 
				+ FIELD_user_userid + " varchar(25)," 
				+ FIELD_msg_type + " varchar(25)," //"tradetype":推送类型：
				+ FIELD_push_msg + " varchar(25)," //"content":推送内容,
				+ FIELD_push_id + " varchar(25)," // "id":跳转id,
				+ FIELD_push_time+ " varchar(25)," //tradedate
				+ FIELD_push_title+ " varchar(25)," //title
				+ FIELD_push_isRead+ " integer" //是否已读
				
				+ ");");
	}

	@Override
	public synchronized void  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion  ) {
		
			dropTable(T_USER_MSG, db);
			dropTable(T_Search_MSG, db);
			dropTable(T_Download_Info, db);
			dropTable(T_Download_File, db);
			dropTable(T_Push_MSG, db);
			CreateIfNotExists(db);
		}
	}

	/**
	 * 删除指定表下的所有数据
	 * 
	 * @param T_name
	 * @return
	 */
	public void deletedAll(String T_name) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "delete from " + T_name;
		db.execSQL(sql);
	}

	public void dropTable(String T_name, SQLiteDatabase db) {
		String sql = "DROP TABLE IF EXISTS " + T_name;
		db.execSQL(sql);
	}
}
