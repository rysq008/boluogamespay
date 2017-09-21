package com.game.helper.activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.AllPermission;
import java.security.Permission;
import java.util.List;
import java.util.Set;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.guide.GuideActivity;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.LoginTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.comm.VersionBuild;
import com.game.helper.sdk.model.returns.Login;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.Version;
import com.game.helper.sdk.model.returns.Version.Versions;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.sdk.net.comm.VersionNet;
import com.game.helper.services.UpgradeService;
import com.game.helper.util.IntentUtil;
import com.game.helper.util.LoginUtil;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.TimeUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.umeng.analytics.MobclickAgent;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @Description
 * @Path com.game.helper.activity.Welcoming.java
 * @Author lbb
 * @Date 2016年9月16日 下午3:06:50
 * @Company
 */
public class WelcomeActivity extends BaseActivity {
    private static final int MSG_SET_ALIAS = 1001;
    private static final String TAG = "JPush";
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.e("LoginUtil", "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);

                    break;

				/*case MSG_SET_TAGS:
                Log.d(TAG, "Set tags in handler.");
                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                break;
				 */
                default:
                    Log.e("LoginUtil", "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

        }

    };
    public static WelcomeActivity instance;

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
         }*/
        HttpDbUtil.initHttpDB(this);
        //DownLoadManager.getManager().removeAllTask();
        List<DownloadInfo> downloadInfoList = HttpDbUtil.initHttpDB(this).queryFileInfo(0);
        for (DownloadInfo info : downloadInfoList) {
            if (info != null && info.getState() == DownLoadManager.STATE_DOWNLOADING) {
                //info.setState(DownLoadManager.STATE_PAUSE);
                //HttpDbUtil.initHttpDB(this).update(info);
            } else {
                if (info.getState() == DownLoadManager.STATE_FINISH) {
                    if (((int) ((float) info.getProgress() / info.getFileLength() * 100)) == 100) {

                    } else {
                        info.setState(DownLoadManager.STATE_WAITING);
                        HttpDbUtil.initHttpDB(this).update(info);
                    }
                }
            }
        }
        instance = this;
        setNoTitle(this);
        setContentView(R.layout.activity_welcome);
        //System.out.println("+++++-----------"+getDeviceInfo(getApplicationContext()));
        super.onCreate(savedInstanceState);
    }

    LoginData user;
    boolean auoth = true;

    @Override
    protected void initView() {
        if (BaseApplication.mInstance != null) {
            BaseApplication.mInstance.isCancelable = false;
        }
        user = DBManager.getInstance(WelcomeActivity.this).getUserMessage();
        new UpdateTask(WelcomeActivity.this).start();

    }

    @Override
    protected void initData() {

    }

    public int getIsFirst() {
        try {
            int isFirst = 1;
            String time1 = TimeUtil.changeTimeFormat("2016-12-21 23:59:59", TimeUtil.TIME_FORMAT_FULL);
            String time2 = TimeUtil.getTime();
            int a = TimeUtil.parserTime(TimeUtil.TIME_FORMAT_FULL, time1, time2);
            //int a=0;
            if (a == 1) {
                isFirst = 1;
            } else {
                isFirst = SharedPreUtil.getIntValue(mContext, "isFirstGuide", 0);
            }
            return isFirst;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

    }

    public void toLogin() {
        final int isFirst = getIsFirst();//是否首次进入程序:0为首次否则为非首次进入程序
        if (isFirst != 1) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
            jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
        } else {
            if (SharedPreUtil.isLogin()) {
                Login mLogin = new Login();
                mLogin.data = user;
                if (mLogin.data != null) {
                    JPushInterface.init(getApplicationContext());
                    JPushInterface.resumePush(getApplicationContext());
                    //调用JPush API设置Alias
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, user.phone));
                    jumpActivity(LoginTask.time_start, LoginTask.time_end, MainActivity.class);
                    LoginUtil.loginSuccess(mContext, mLogin.data.userId);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
                    jumpActivity(MainActivity.class, LOGO_TIME_DELAY1, bundle);
                }
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
                jumpActivity(MainActivity.class, LOGO_TIME_DELAY1, bundle);
            }
        }

//        if (user != null) {//存储用户信息为非空
//            if (!TextUtils.isEmpty(user.phone) && !TextUtils.isEmpty(user.pwd)) {//用户名密码登录
//                if (SystemUtil.getNetworkStatus(mContext)) {//网络连接正常
//                    if (isFirst == 1) {//非首次进入程序
//                        new LoginTask(mContext, false, user.phone, user.pwd, new Back() {
//
//                            @Override
//                            public void success(Object object, String msg) {
//                                if (object != null && object instanceof Login) {
//                                    Login mLogin = (Login) object;
//                                    if (mLogin.data != null) {
//                                        JPushInterface.init(getApplicationContext());
//                                        JPushInterface.resumePush(getApplicationContext());
//                                        //调用JPush API设置Alias
//                                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, user.phone));
//                                        //ToastUtil.showToast(mContext, "登录成功");
//                                        mLogin.data.jsonData = null;
//                                        mLogin.data.jsonData = new JsonBuild().setModel(mLogin.data).getJsonString1();
//                                        DBManager.getInstance(mContext).insert(mLogin.data);
//                                        //DBManager.getInstance(mContext).insert(mLogin.data.userId,pwd, phone);
//                                        if (isFirst == 1) {//非首次进入程序
//                                            jumpActivity(LoginTask.time_start, LoginTask.time_end, MainActivity.class);
//                                            LoginUtil.loginSuccess(mContext, mLogin.data.userId);
//                                        } else {
//                                            Bundle bundle = new Bundle();
//                                            bundle.putInt(KEY_FIRSTUSER, 1);//1:登录成功，0：登录失败
//                                            jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
//                                        }
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void fail(String status, String msg, Object object) {
//                                //ToastUtil.showToast(mContext, msg);
//                                //startActivity(LoginActivity.class);
//                                if (isFirst == 1) {//非首次进入程序
//                                    //jumpActivity(LoginTask.time_start, LoginTask.time_end, LoginActivity.class);
//                                    jumpActivity(LoginTask.time_start, LoginTask.time_end, MainActivity.class);
//                                    //finish();
//                                } else {
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
//                                    jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
//                                }
//                            }
//                        }).start();
//                    } else {//首次进入程序
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
//                        jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
//                    }
//
//                } else {//网络连接异常
//                    if (isFirst == 1) {//非首次进入程序
//                        //jumpActivity(LoginActivity.class, LOGO_TIME_DELAY);
//                        jumpActivity(LoginTask.time_start, LoginTask.time_end, MainActivity.class);
//                    } else {
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
//                        jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
//                    }
//                }
//            } else {//用户名或者密码为空
//                // 不存在 ，直接跳转到登陆界面
//                if (isFirst == 1) {//非首次进入程序
//                    //jumpActivity(LoginActivity.class, LOGO_TIME_DELAY);
//                    jumpActivity(LoginTask.time_start, LoginTask.time_end, MainActivity.class);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
//                    jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
//                }
//            }
//        } else {//存储用户信息为空
//            if (isFirst == 1) {//非首次进入程序
//                //jumpActivity(LoginActivity.class, LOGO_TIME_DELAY);
//                jumpActivity(LoginTask.time_start, LoginTask.time_end, MainActivity.class);
//            } else {
//                Bundle bundle = new Bundle();
//                bundle.putInt(KEY_FIRSTUSER, 0);//1:登录成功，0：登录失败
//                jumpActivity(GuideActivity.class, LOGO_TIME_DELAY1, bundle);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
    }

    private void jumpActivity(long start, long end, Class<?> act) {
        long time = end - start;
        time = time > LOGO_TIME_DELAY ? 100 : LOGO_TIME_DELAY - time;
        jumpActivity(act, time);
    }

    private void jumpActivity(Class<?> act, long time) {
        if (!isFinishing()) {
            IntentUtil.toActivity(mContext, act, time);
        }
    }

    private void jumpActivity(Class<?> act, long time, Bundle bundle) {
        if (!isFinishing()) {
            IntentUtil.toActivity(mContext, act, time, bundle);
        }
    }


    public class UpdateTask extends BaseBBXTask {
        public UpdateTask(Context context) {
            super(context, false);
        }

        private void showNewDialog(final Versions update) {

            final MyAlertDailog dialog = new MyAlertDailog(context);
            String remark = update.remark;
            if (update.status.equals("1")) {//一般更新
                if (!TextUtils.isEmpty(update.remark)) {
                    dialog.setTitle("是否下载最新版本并安装？");
                    try {
                        dialog.setContent1("" + remark);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.setContent1("" + remark);
                } else {
                    dialog.setContent("是否下载最新版本并安装？");
                }

                dialog.setLeftButtonText("取消");
                dialog.setRightButtonText("更新");
                dialog.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog.dismiss();
                        toLogin();
                    }
                });
                dialog.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        dialog.dismiss();

                        Intent intent = new Intent(context, UpgradeService.class);
                        intent.putExtra(INTENT_URL, update.loadPath);
                        intent.putExtra(INTENT_VERSION, update.version);
                        context.startService(intent);

                    }
                });
            } else if (update.status.equals("2")) {//强制更新
                if (!TextUtils.isEmpty(update.remark)) {
                    dialog.setTitle("请更新版本");
                    try {
                        dialog.setContent1("" + remark);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.setContent1("" + remark);
                } else {
                    dialog.setContent("请更新版本");
                }
                dialog.setSingle("更新");
                dialog.setOnClickSingleListener(new onClickSingleListener() {

                    @Override
                    public void onClickSingle() {
                        dialog.dismiss();

                        Intent intent = new Intent(context, UpgradeService.class);
                        intent.putExtra(INTENT_URL, update.loadPath);
                        intent.putExtra(INTENT_VERSION, update.version);
                        context.startService(intent);
                    }
                });
            } else if (update.status.equals("3")) {//系统维护中
                if (!TextUtils.isEmpty(update.note)) {
                    dialog.setTitle("系统维护");
                    try {
                        dialog.setContent1(update.note);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.setContent1(update.note);
                } else {
                    dialog.setContent("系统维护");
                }
                dialog.setNoBtn();

            }

            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onSuccess(Object object, String msg) {
            try {
                if (object != null && object instanceof Version) {
                    Version update = (Version) object;
                    if (update != null && update.data != null) {
                        showNewDialog(update.data);
                    } else {
                        toLogin();
                    }
                } else {
                    toLogin();
                }

            } catch (Exception e) {
                e.printStackTrace();
                toLogin();
            }
        }

        @Override
        public void onFailed(String status, String msg, Object result) {
            // TODO Auto-generated method stub
            super.onFailed(status, msg, result);
            toLogin();
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            // TODO Auto-generated method stub
            VersionBuild version = new VersionBuild(context, API_version_check_Url, SystemUtil.getMetaValue(context, "UMENG_CHANNEL"), SystemUtil.getAppVersionName(context));
            return new VersionNet(context, version.toJson1());
        }

    }


    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            Permission permission = new AllPermission();
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
        MobclickAgent.onPageStart("WelcomeActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        MobclickAgent.onPageEnd("WelcomeActivity");
    }
}
