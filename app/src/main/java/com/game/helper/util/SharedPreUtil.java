package com.game.helper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

public class SharedPreUtil {

    private static final String SharedPreference_Name = "g9_android_myConfig_lbb";
    private static final String SharedPreference_SessionId = "sessionId";

    private static SharedPreferences sp;

    private static Editor editor;

    private static SharedPreferences getSharedPre(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SharedPreference_Name, Context.MODE_PRIVATE);
            editor = sp.edit();
        }
        return sp;
    }

    public static void init(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    /**
     * 写入int类型的值
     *
     * @param context
     * @param name    名称
     * @param value   值
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
     * @param name    名称
     * @param value   值
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
     * @param name    名称
     * @param value   值
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
        if (maps != null) {
            for (String key : maps.keySet()) {
                if (key.startsWith(start_tag))
                    editor.remove(key);
            }
            editor.commit();
        }
    }

    public static void commit(Context context) {
        getSharedPre(context);
        editor.commit();
    }


    //********************************************************************************************************************************************************************************//
    public static String getSessionId() {
        return sp.getString(SharedPreference_SessionId, "");
    }

    public static void saveSessionId(String sissonid) {
        editor.putString(SharedPreference_SessionId, sissonid).apply();
    }

    public static void clearSessionId() {
        sp.edit().remove(SharedPreference_SessionId).apply();
    }

    public static boolean isLogin() {
        String id = getSessionId();
        return !TextUtils.isEmpty(id);
    }

    private static <T> T getObject(String key, Class<T> c) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String encryptedJson = sp.getString(key, null);
        if (TextUtils.isEmpty(encryptedJson)) {
            return null;
        }
        try {
//            String decryptedJson = des.decrypt(encryptedJson);
            return new Gson().fromJson(encryptedJson, c);
        } catch (Throwable t) {
            return null;
        }
    }

    public static void saveObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oosw = new ObjectOutputStream(baos);
            oosw.writeObject(oosw);
//            String objstr = DESUtil.encrypt(baos.toString()/*new String(baos.toByteArray(), "utf-8")*/, DESUtil.DEFAULT_KEY);
            String objstr = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            editor.putString(key, objstr).commit();
            baos.close();
            oosw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObject(String key) {
        T t = null;
        String objstr = sp.getString(key, "");
        if (TextUtils.isEmpty(objstr)) {
            return t;
        }
        try {
//            byte[] bytes = DESUtil.decrypt(objstr, DESUtil.DEFAULT_KEY).getBytes();
            byte[] bytes = Base64.decode(objstr, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            t = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
