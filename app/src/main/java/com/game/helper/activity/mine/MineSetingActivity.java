package com.game.helper.activity.mine;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.LoginActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.fragment.HomeFragment;
import com.game.helper.muldownload.utils.AppUtil;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.VersionBuild;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.Version;
import com.game.helper.sdk.model.returns.Version.Versions;
import com.game.helper.sdk.net.comm.VersionNet;
import com.game.helper.services.UpgradeService;
import com.game.helper.util.DataCleanManager;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.umeng.analytics.MobclickAgent;
import com.yuan.leopardkit.download.DownLoadManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @Description 我的设置
 * @Path com.game.helper.activity.mine.MineSetingActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午12:57:32
 * @Company
 */
public class MineSetingActivity extends BaseActivity {

    @BindView(R.id.iv_Open)
    ImageView iv_Open;
    @BindView(R.id.relat_problem)
    RelativeLayout relat_problem;
    @BindView(R.id.relat_feedback)
    RelativeLayout relat_feedback;
    @BindView(R.id.relat_aboutUs)
    RelativeLayout relat_aboutUs;
    @BindView(R.id.relat_Gengxin)
    RelativeLayout relat_Gengxin;
    @BindView(R.id.relat_cache)
    RelativeLayout relat_cache;
    //@BindView(R.id.tv_codeNo) 	TextView tv_codeNo;
    @BindView(R.id.btn_goOut)
    Button btn_goOut;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private boolean isOpen = false;
    @BindView(R.id.tvCache)
    TextView tvCache;

    public static MineSetingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_seting);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    protected void initView() {
        if (BaseApplication.mInstance != null) {
            BaseApplication.mInstance.isCancelable = true;
        }
        topTitle.setText("设置");
        topLeftBack.setVisibility(View.VISIBLE);
        isOpen = SharedPreUtil.getBooleanValue(mContext, "key_voices", true);
        if (isOpen) {
            iv_Open.setImageResource(R.drawable.wode_77);
            //获取声音管理器：
            AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
            ringAndVibrate(audioManager, 0);
        } else {
            iv_Open.setImageResource(R.drawable.wode_79);
            //获取声音管理器：
            AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
            vibrate(audioManager, 0);
        }

		/*
        iv_Open.setImageResource(R.drawable.wode_77);
		isOpen=true;
		//获取声音管理器：
		AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
		ringAndVibrate(audioManager);*/

        new UpdateTask(MineSetingActivity.this, 0, false).start();
    }

    @Override
    protected void initData() {
        try {
            tvCache.setText("" + DataCleanManager.getTotalCacheSize(mContext.getApplicationContext()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    @OnClick({R.id.top_left_layout, R.id.iv_Open, R.id.relat_problem, R.id.relat_feedback, R.id.relat_aboutUs
            , R.id.relat_Gengxin, R.id.relat_cache, R.id.btn_goOut})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.iv_Open:
                if (isOpen) {
                    iv_Open.setImageResource(R.drawable.wode_79);
                    isOpen = false;
                    //获取声音管理器：
                    AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
                    vibrate(audioManager, 1);
                } else {
                    iv_Open.setImageResource(R.drawable.wode_77);
                    isOpen = true;
                    //获取声音管理器：
                    AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
                    ringAndVibrate(audioManager, 1);
                }
                SharedPreUtil.putBooleanValue(mContext, "key_voices", isOpen);
                break;
            case R.id.relat_problem:
                startActivity(MineProblemsActivity.class);
                break;

            case R.id.relat_feedback:
                startActivity(MineFeedBackActivity.class);
                break;
            case R.id.relat_aboutUs:
                startActivity(MineAboutUsActivity.class);
                break;
            case R.id.relat_Gengxin:
                //更新
                //ToastUtil.showToast(mContext, "已是最新版本");
                new UpdateTask(MineSetingActivity.this, 1, true).start();
                break;
            case R.id.relat_cache:
                //清除缓存
                final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                dialog1.setTitle("提醒");
                dialog1.setContent("是否清除缓存？");
                dialog1.setLeftButtonText("取消");
                dialog1.setRightButtonText("确定");
                if (dialog1 != null && !dialog1.isShowing()) {
                    dialog1.show();
                }
                dialog1.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog1.dismiss();
                    }
                });
                dialog1.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        dialog1.dismiss();
                        DataCleanManager.clearAllCache(mContext.getApplicationContext());
                        AppUtil.post(new Runnable() {
                            @Override
                            public void run() {
                                // Glide.get(mContext.getApplicationContext()).clearDiskCache();
                                //	Glide.get(mContext.getApplicationContext()).clearMemory();
                            }
                        });
                        ;
                        try {
                            tvCache.setText("0KB");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_goOut:
                //退出登录
                final MyAlertDailog dialog = new MyAlertDailog(mContext);
                dialog.setTitle("提醒");
                dialog.setContent("是否退出当前账号？");
                dialog.setLeftButtonText("取消");
                dialog.setRightButtonText("确定");
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                dialog.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog.dismiss();
                    }
                });
                dialog.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        dialog.dismiss();
                        BaseApplication.mInstance.isonAppExit = true;
                        exit(mContext);
                    }
                });

                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //即有声音也有振动
    @SuppressWarnings("deprecation")
    public void ringAndVibrate(AudioManager audio, int tag) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 24
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            getApplicationContext().startActivity(intent);
            return;
        }
        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
                AudioManager.VIBRATE_SETTING_ON);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
                AudioManager.VIBRATE_SETTING_ON);
        if (tag == 1) {
            Toast.makeText(this, "设置成功！当前为声音模式", Toast.LENGTH_LONG).show();
        }
    }

    //只能振动：
    @SuppressWarnings("deprecation")
    public void vibrate(AudioManager audio, int tag) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 24
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            getApplicationContext().startActivity(intent);
            return;
        }
        audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
                AudioManager.VIBRATE_SETTING_ON);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
                AudioManager.VIBRATE_SETTING_ON);
        if (tag == 1) {
            Toast.makeText(this, "设置成功！当前为振动模式", Toast.LENGTH_LONG).show();
        }
    }

    //无声无振动：
    @SuppressWarnings("deprecation")
    public void noRingAndVibrate(AudioManager audio) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 24
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            getApplicationContext().startActivity(intent);
            return;
        }
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
                AudioManager.VIBRATE_SETTING_OFF);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
                AudioManager.VIBRATE_SETTING_OFF);
        Toast.makeText(this, "设置成功！当前为静音模式", Toast.LENGTH_LONG).show();
    }

    /**
     * @throws
     * @Title: clearCacheData
     * @Description: TODO
     * @param:
     * @return: void
     */
    public static void clearCacheData(Context context) {
        BaseApplication.mInstance.clear();

        DBManager.instance = null;

    }

    //账号退出
    public static void exit(Context context) {


        LoginData user = DBManager.getInstance(context).getUserMessage();
        user.pwd = "";
        DBManager.getInstance(context).insert(user);
        HomeFragment.isStop = true;
        DownLoadManager.getManager().pauseAllTask();

        clearCacheData(context);

        JPushInterface.clearAllNotifications(context.getApplicationContext());
        JPushInterface.stopPush(context.getApplicationContext());

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        if (BaseActivity.activityList != null) {
            for (Activity activity : BaseActivity.activityList) {
                if (!(activity instanceof LoginActivity)) {
                    activity.finish();
                }
            }
        }

    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("MineSetingActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineSetingActivity");
        super.onResume();
    }


    public class UpdateTask extends BaseBBXTask {
        private int tag;

        public UpdateTask(Context context, int tag, boolean isShow) {
            super(context, isShow);
            this.tag = tag;
        }

        private void showNewDialog(final Versions update) {

            final MyAlertDailog dialog = new MyAlertDailog(context);
            String data = update.remark;
            if (!TextUtils.isEmpty(update.remark)) {
                dialog.setTitle("是否下载最新版本并安装？");
                try {
                    dialog.setContent1("" + data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.setContent1("" + data);
            } else {
                dialog.setContent("是否下载最新版本并安装？");
            }

            dialog.setOnClickLeftListener(new onClickLeftListener() {
                @Override
                public void onClickLeft() {
                    dialog.dismiss();

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

            dialog.setLeftButtonText("取消");
            dialog.setRightButtonText("更新");
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onSuccess(Object object, String msg) {
            // TODO Auto-generated method stub
            try {
                if (object != null && object instanceof Version) {
                    Version update = (Version) object;
                    if (update != null && update.data != null) {
                        showNewDialog(update.data);
                    } else {
                        if (this.tag == 1) {
                            if (!TextUtils.isEmpty(msg)) {
                                onshowNoUpdate(msg);
                            } else {
                                onshowNoUpdate("当前已经是最新版本!");
                            }
                        }

                    }
                } else {
                    if (this.tag == 1) {
                        if (!TextUtils.isEmpty(msg)) {
                            onshowNoUpdate(msg);
                        } else {
                            onshowNoUpdate("当前已经是最新版本!");
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                if (this.tag == 1) {
                    if (!TextUtils.isEmpty(msg)) {
                        onshowNoUpdate(msg);
                    } else {
                        onshowNoUpdate("当前已经是最新版本!");
                    }
                }

            }
        }

        @Override
        public void onFailed(String status, String msg, Object result) {
            // TODO Auto-generated method stub
            super.onFailed(status, msg, result);
            if (status.equals("" + NET_ERROR)) {
                ToastUtil.showToast(context, getResources().getString(R.string.no_network));
            } else {
                if (this.tag == 1) {
                    if (!TextUtils.isEmpty(msg)) {
                        onshowNoUpdate(msg);
                    } else {
                        onshowNoUpdate("当前已经是最新版本!");
                    }
                }

            }
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            // TODO Auto-generated method stub
            VersionBuild version = new VersionBuild(context, API_version_check_Url, SystemUtil.getMetaValue(context, "UMENG_CHANNEL"), SystemUtil.getAppVersionName(context));
            return new VersionNet(context, version.toJson1());
        }

    }

    /**
     * 已经是最新版本了
     */
    public void onshowNoUpdate(String msg) {
        final MyAlertDailog dialog = new MyAlertDailog(this);
        dialog.setTitle("提醒");
        dialog.setContent(msg);//"当前已经是最新版本!"
        dialog.setSingle("确定");
        dialog.show();
        dialog.setOnClickSingleListener(new onClickSingleListener() {
            @Override
            public void onClickSingle() {
                dialog.dismiss();
            }
        });
    }
}
