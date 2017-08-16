
package com.game.helper.db;

/**
 * @Description

 */
public interface DBComm {
	// 各种表名
	String T_Push_MSG = "Push_msg";
	
	String T_Download_Info = "download_info";
	String T_Download_File = "download_file";
	String T_Search_MSG = "search_msg";
	String T_USER_MSG = "user_msg";
	String T_ZONE = "zone";
	String T_DB = "game_helper_s";
	String T_ORDER_HISTORY_LIST="order_history_list";
	
	String FIELD_user_u = "user_u";
	String FIELD_user_userid = "user_userid";
	String FIELD_user_pwd = "user_pwd";
	String FIELD_user_phone = "user_phone";
	String FIELD_user_json="user_json";
	
	String T_MESSAGE = "message";
	// 版本号      1.1.0 918
	int VERSION = 19;

	String FIELD_NAME = "name";
	String FIELD_KEY = "key";
	
	String FIELD_PARENT = "parent";
	
	String FIELD_PLACE = "place";
	String FIELD_PLACE_ID = "place_id";
	
	String FIELD_msg_type = "msg_type";
	String FIELD_msg_json="msg_json";
	String FIELD_msg_id = "msg_id";
	String FIELD_msg_isread = "msg_isread";
	String FIELD_msg_isshow = "msg_isshow";
	String FIELD_msg_createtime = "msg_createtime";
	
	String FIELD_msg_search = "msg_search";
	String FIELD_msg_searchType = "msg_searchType";
	
	
	String FIELD_task_id = "task_id";
	String FIELD_download_length = "download_length";
	String FIELD_url = "url";
	String FIELD_is_success = "is_success";
	
	String FIELD_app_name = "app_name";
	String FIELD_download_percent = "download_percent";
	String FIELD_status = "status";
	String FIELD_packageName = "packageName";
	String FIELD_app_json="app_json";
	
	String FIELD_push_msg = "push_msg";
	String FIELD_push_id = "push_id";
	String FIELD_push_time = "push_time";
	String FIELD_push_isRead = "push_isRead";
	String FIELD_push_title = "push_title";
	
	// 各种字段名
	String FIELD_ID = "db_id";

	String FIELD_id = "id";
	
	
	
	/**
	 * 消息
	 */
	String FIELD_table_message ="table_message";
	String FIELD_type = "type";	
	String FIELD_json = "json";	
	
	
	//sql语句
	String creatT = "CREATE TABLE IF NOT EXISTS ";
}
