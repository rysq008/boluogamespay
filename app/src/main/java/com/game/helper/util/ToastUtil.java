package com.game.helper.util;



import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

//	private static Context mContext = null;
	
	private static Toast mToast = null;
	
	private static int ShowTime = 500;
	
	private static void setCurrentToast(Context ctx, int contentId) {
		//如果有Toast正在显示 取消当前Toast显示最新的Toast
		if (mToast != null) {
//			mToast.cancel();
			mToast.setText(contentId);
		} else {
			mToast = Toast.makeText(ctx, contentId, ShowTime);
//			mContext = ctx;
		}
	}
	
	private static void setCurrentToast(Context ctx, String content) {
//		if (mContext == ctx) {
		if (mToast != null) {
//			mToast.cancel();
			mToast.setText(content);
		} else {
			mToast = Toast.makeText(ctx, content, Toast.LENGTH_LONG);
//			mContext = ctx;
		}
	}
	/**
	 * 显示toast
	 * @param context 
	 * @param contentId 要显示的文本ID
	 */
	public static void showToast(Context context, int contentId) {
			setCurrentToast(context, contentId);
			mToast.show();
	}
	/**
	 * 显示toast
	 * @param context
	 * @param content 要显示的文本
	 */
	public static void showToast(Context context, String content) {
		if (!TextUtils.isEmpty(content)){
			setCurrentToast(context, content);
			mToast.show();
		}
	}

	/**
	 * 在程序退出时调用 可立即关闭Toast
	 */
	public static void cancelToast() {
		if (mToast != null)
			mToast.cancel();
	}
}
