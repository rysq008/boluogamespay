package com.game.helper.util;

import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreUtil {

	private static final String SharedPreference_Name = "g9_android_myConfig_lbb";

	private static SharedPreferences sp;

	private static Editor editor;

	private static SharedPreferences getSharedPre(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(SharedPreference_Name,
					Context.MODE_PRIVATE);
			editor = sp.edit();
		}
		return sp;
	}

	/**
	 * 写入int类型的值
	 * 
	 * @param context
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 */
	public static void putIntValue(Context context, String name, int value) {

		getSharedPre(context);
		editor.putInt(name, value);
		editor.commit();
	}

	/**
	 * 读取int类型的值
	 * 
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static int getIntValue(Context context, String name, int defaultValue) {

		getSharedPre(context);
		return sp.getInt(name, defaultValue);
	}

	/**
	 * 写入String类型的值
	 * 
	 * @param context
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 */
	public static void putStringValue(Context context, String name, String value) {

		getSharedPre(context);
		editor.putString(name, value);
		editor.commit();
	}

	/**
	 * 读取String类型的值
	 * 
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getStringValue(Context context, String name,
			String defaultValue) {

		getSharedPre(context);
		return sp.getString(name, defaultValue);
	}

	/**
	 * 写入boolean类型的值
	 * 
	 * @param context
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 */
	public static void putBooleanValue(Context context, String name,
			boolean value) {

		getSharedPre(context);
		editor.putBoolean(name, value);
		editor.commit();
	}

	/**
	 * 读取boolean类型的值
	 * 
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanValue(Context context, String name,
			boolean defaultValue) {

		getSharedPre(context);
		return sp.getBoolean(name, defaultValue);
	}

	public static void putLongValue(Context context, String name, long value) {

		getSharedPre(context);
		editor.putLong(name, value);
		editor.commit();
	}

	public static Long getLongValue(Context context, String name,
			long defaultValue) {
		getSharedPre(context);
		return sp.getLong(name, defaultValue);
	}

	/**
	 * 去除某些配置
	 * 
	 * @param context
	 * @param name
	 */
	public static void cleatValue(Context context, String... name) {
		getSharedPre(context);
		for (int i = 0; i < name.length; i++) {
			editor.remove(name[i]);
		}
		editor.commit();
	}
	public static void clearAll(Context context) {
		getSharedPre(context);
		editor.clear();
	}
	
	public static void delete(Context context, String start_tag) {
		getSharedPre(context);
		Map<String, ?> maps = sp.getAll();
		if(maps != null){
			for (String key : maps.keySet()) {
				if (key.startsWith(start_tag))
					editor.remove(key);
			}
			editor.commit();
		}
	}
	public  static void commit(Context context){
		getSharedPre(context);
		editor.commit();
	}
}
