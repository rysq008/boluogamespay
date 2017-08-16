package com.game.helper;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class BbxBaseActivity extends FragmentActivity implements
        OnClickListener {

    /**
     * 通用上下文
     */
    protected Context mContext;
    /**
     * 通用后退按钮
     */
    protected Button btn_back, btn_right;
    /**
     * 通用标题
     */
    protected TextView textView_title;
    /**
     * 通用滚动条弹出窗
     */
    protected ProgressDialog progressDialog;
    // 通用滚动条（右上角 非弹出）
    // protected ProgressBar title_progressBar;
    /**
     * 是否为tab页面
     */
    protected boolean isMainActivity = false;
    /**
     * 是否为入口Activity 务必在入口Activity处把此值设为true
     */
    protected boolean isFirstActivity = false;
    /**
     * 全局Activity列表 每次开启一个新的Activity 就add进此变量 以便在退出程序时 可以finish所有Activity
     */
    public static List<Activity> activityList = new ArrayList<Activity>();

    /**
     * 初始化控件
     */
    abstract protected void initView();

    /**
     * 初始化传递过来的数据
     */
    abstract protected void initData();

    /**
     * 在Activity被finish后 需要释放的资源写在此方法中
     */
    abstract protected void desotryItems();

    /**
     * 程序退出时回调此方法
     */
    abstract protected void onAppExit();

    int id_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        addActivityToList(this);
        init();

    }

//	protected void setOnBaseTitle(int leftResId, String title, int rightResId) {
//
//		id_btn_back = context.getResources().getIdentifier("btn_title_left",
//				"id", context.getPackageName());
//		btn_back = (Button) findViewById(id_btn_back);
//		int id_btn_right = context.getResources().getIdentifier(
//				"btn_title_right", "id", context.getPackageName());
//		btn_right = (Button) findViewById(id_btn_right);
//		if (btn_back != null) {
//			btn_back.setOnClickListener(this);
//			setTitleButton(btn_back, leftResId);
//		}
//		if (btn_right != null) {
//			btn_right.setOnClickListener(this);
//			setTitleButton(btn_right, rightResId);
//		}
//		int id_title = context.getResources().getIdentifier(
//				"textview_title_content", "id", context.getPackageName());
//		TextView tv_title = (TextView) findViewById(id_title);
//		if (tv_title != null && !TextUtils.isEmpty(title)) {
//			tv_title.setText(title);
//		}
//	}
//
//	private void setTitleButton(Button button, int resId) {
//		if (button == null)
//			return;
//		if (resId == -1) {
//			button.setVisibility(View.GONE);
//		} else {
//			button.setOnClickListener(this);
//
//			int bottom = button.getPaddingBottom();
//			int top = button.getPaddingTop();
//			int right = button.getPaddingRight();
//			int left = button.getPaddingLeft();
//			button.setBackgroundResource(resId);
//			button.setPadding(left, top, right, bottom);
//		}
//	}


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == id_btn_back) {
            finish();
        }
    }
//
//
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		
//	}

    /**
     * activity初始化方法
     */
    protected void init() {
        initView();
        initData();
        // 如果是入口activity 初始化全局异常捕获
//		if (isFirstActivity) {
//			initError();
//		}
    }

    private void initError() {
    /*	CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(mContext);*/
    }

    /**
     * 通用滚动条对话框
     *
     * @param msg 滚动条展示的内容
     */
    public void showProgressDialog(String msg, boolean isCanCancle) {

        progressDialog = new ProgressDialog(mContext);

        if (!progressDialog.isShowing()) {
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(isCanCancle);
            progressDialog.setCancelable(isCanCancle);
            // if(hasWindowFocus())
            progressDialog.show();
        }
    }

    /**
     * 关闭通用滚动条对话框
     */
    public void closeProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void setOnBaseClickListener(int... id) {
        for (int i = 0; i < id.length; i++) {
            try {
                findViewById(id[i]).setOnClickListener(this);
            } catch (NullPointerException e) {
                Log.e("lbb", "！！！！！！此布局未找到该ID：" + id[i]);
                e.printStackTrace();
            }
        }
    }

    /**
     * super.onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        desotryItems();
        removeActivityFromList(this);
    }

    /**
     * 取消正在运行的AsyncTask
     *
     * @param tasks
     */
    protected void destroyTask(AsyncTask<?, ?, ?>... tasks) {

        for (AsyncTask<?, ?, ?> task : tasks) {
            if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                task.cancel(true);
            }
        }
    }

    private static void addActivityToList(Activity activity) {
        activityList.add(activity);
    }

    private static void removeActivityFromList(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 完全退出程序
     *
     * @param isErr 是否为异常退出
     */
    private void ExitApplication() {

        ToastUtil.cancelToast();
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            activity.finish();
        }
        onAppExit();
    }

    /**
     * 和isMainActivity关联 若值为true 则实现按两次后退键退出程序的功能
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isMainActivity) {
                press2TimesToExit();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    // 标志位:按两次back键退出的
    private long exitTime = 0;

    /**
     * 实现按两次退出，实现按时间，2秒以内按两次退出
     */
    protected void press2TimesToExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast(mContext, "再按一次退出");
            exitTime = System.currentTimeMillis();// 时间判断在2秒之内连续按两次则退出。
        } else {
            ExitApplication();
        }

        if (Util.popupWindow != null && Util.popupWindow.isShowing()) {
            Util.popupWindow.dismiss();
        }
    }

    public boolean isMainActivity() {
        return isMainActivity;
    }
}