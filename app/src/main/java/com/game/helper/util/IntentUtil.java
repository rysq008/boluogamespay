package com.game.helper.util;
import com.game.helper.BaseActivity;
import com.game.helper.BbxBaseActivity;
import com.game.helper.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

public class IntentUtil {

	public static String TAG_TEL = "tel:";

	private static void toIntent(Context context, Intent intent) {
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public static void toCall(Context context, String phoneNum) {
		toCall(context, Intent.ACTION_CALL, phoneNum);
	}

	public static void toDial(Context context, String phoneNum) {
		toCall(context, Intent.ACTION_DIAL, phoneNum);
	}

	public static void toCall(Context context, String action, String phoneNum) {
		if (!TextUtils.isEmpty(phoneNum)) {
			Uri uri = Uri.parse(TAG_TEL + phoneNum);
			Intent intent = new Intent(action, uri);
			toIntent(context, intent);
		}
	}

	public static void toActivity(Context context, Class<?> objectActivity,
			Bundle bundle) {
		Intent intent = new Intent(context, objectActivity);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		toIntent(context, intent);
	}

	public static void toActivity(Context context, Class<?> objectActivity) {
		toIntent(context, new Intent(context, objectActivity));
	}

	/**
	 * 
	 * @Title: toActivity
	 * @Description: TODO
	 * @param: @param context
	 * @param: @param objectActivity
	 * @param: @param delay 单位秒
	 * @return: void
	 * @throws
	 */
	public static void toActivity(final Context context,
			final Class<?> objectActivity, long delay) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BbxBaseActivity activity = ActivityUtil.isBaseActivity(context);
				if (activity.isFinishing()) {
					return;
				}
				toIntent(context, new Intent(context, objectActivity));
				if (activity != null)
					((BaseActivity)activity).finish();
			}
		}, delay);
	}
	/**
	 * 
	 * @Title: toActivity
	 * @Description: TODO
	 * @param: @param context
	 * @param: @param objectActivity
	 * @param: @param delay 单位秒
	 * @return: void
	 * @throws
	 */
	public static void toActivity(final Context context,
			final Class<?> objectActivity, long delay,final Bundle bundle) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				BbxBaseActivity activity = ActivityUtil.isBaseActivity(context);
				if (activity.isFinishing()) {
					return;
				}
				toActivity(context, objectActivity,bundle);
				if (activity != null)
					((BaseActivity)activity).finish();
			}
		}, delay);
	}
}
