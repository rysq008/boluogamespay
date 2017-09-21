package com.game.helper.guide;

import java.util.List;

import com.game.helper.R;
import com.game.helper.activity.LoginActivity;
import com.game.helper.activity.MainActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.IntentUtil;
import com.game.helper.util.LoginUtil;
import com.game.helper.util.SharedPreUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 *

 *
 *
 */
public class ViewPagerAdapter extends PagerAdapter {

    // 界面列表
    private List<View> views;
    private Activity activity;
    private int intenttype;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    public ViewPagerAdapter(List<View> views, Activity activity, int intenttype) {
        this.views = views;
        this.activity = activity;
        this.intenttype = intenttype;
    }

    // 销毁arg1位置的界面
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    // 获得当前界面数
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    public static boolean isClisck = false;

    // 初始化arg1位置的界面
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {
            Button mStartWeiboImageButton = (Button) arg0
                    .findViewById(R.id.btn_experience);
            ImageView iv_guide = (ImageView) arg0
                    .findViewById(R.id.iv_guide);
            mStartWeiboImageButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 设置已经引导
                    isClisck = true;
                    setGuided();
                    goIntent();

                }

            });

			/*iv_guide.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 设置已经引导
					setGuided();
					goIntent();
				}
			});*/
        }
        return views.get(arg1);
    }

    private void goIntent() {

        if (intenttype == 1) {
            LoginData user = DBManager.getInstance(activity).getUserMessage();
            jumpActivity(MainActivity.class, 0, null);
            LoginUtil.loginSuccess(activity, user.userId);
        } else {
            jumpActivity(LoginActivity.class, 0, null);
        }

    }

    private void jumpActivity(Class<?> act, long time, Bundle bundle) {
        if (!activity.isFinishing()) {
            IntentUtil.toActivity(activity, act, time, bundle);
        }
    }

    /**
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
        //存储数据
        //SharedPreEncryptUtil.putBooleanValue(activity, SharedPreEncryptUtil.isFirstIn, false);
        SharedPreUtil.putIntValue(activity, "isFirstGuide", 1);
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}
