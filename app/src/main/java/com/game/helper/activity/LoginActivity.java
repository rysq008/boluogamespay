package com.game.helper.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetCheckCodeTask;
import com.game.helper.net.task.LoginTask;
import com.game.helper.net.task.LoginUseAuthTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetCheckCode;
import com.game.helper.sdk.model.returns.Login;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.LoginUtil;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.StringUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.mob.tools.utils.UIHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.AnalyticsConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @Description
 * @Path com.game.helper.activity.LoginAcitivty.java
 * @Author lbb
 * @Date 2016年8月18日 下午1:22:48
 * @Company
 */
public class LoginActivity extends BaseActivity implements Handler.Callback, PlatformActionListener {
    private static final int MSG_SET_ALIAS = 1001;
    private static final String TAG = "JPush";
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_userpwd)
    EditText et_userpwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_forgetPwd)
    Button btn_forgetPwd;
    @BindView(R.id.btn_register)
    Button btn_register;

    /* @BindView(R.id.tv_isRight)
     ImageView tv_isRight;*/
    @BindView(R.id.delete)
    ImageView delete;
    /* @BindView(R.id.top_title)
     TextView topTitle;*/
    @BindView(R.id.login_wechat_btn)
    Button loginWechatBtn;
    @BindView(R.id.login_qq_btn)
    Button loginQqBtn;
    @BindView(R.id.mThirdLoginResult)
    TextView mThirdLoginResult;
    @BindView(R.id.login_logo_iv)
    ImageView loginLogoIv;
    @BindView(R.id.login_back_iv)
    ImageView loginBackIv;
    @BindView(R.id.login_auth_btn)
    Button loginAuthBtn;
    @BindView(R.id.auth_et_username)
    EditText authEtUsername;
    @BindView(R.id.auth_btn_send_auth)
    Button authBtnSendAuth;
    @BindView(R.id.auth_et_userpwd)
    EditText authEtUserpwd;
    @BindView(R.id.auth_btn_login)
    Button authBtnLogin;
    @BindView(R.id.auth_login_usepwd_btn)
    Button authLoginUsepwdBtn;
    @BindView(R.id.login_auth_ll)
    LinearLayout loginAuthLl;
    @BindView(R.id.auth_login_pwd_ll)
    LinearLayout authLoginPwdLl;
    private Tencent mTencent;
    private static boolean isServerSideLogin = false;
    boolean ispwd = true;
    private int clocktime = 0;
    private CountDownTask countDownTask;

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        mTencent = Tencent.createInstance(BaseApplication.mAppid, this.getApplicationContext());
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        isMainActivity = false;//用来决定双击是否回退

    }

    @Override
    protected void initView() {
        //topTitle.setText("G9游戏");
    }

    @Override
    protected void initData() {
        et_username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    int i = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if (i == 0) {
            i = 1;
            String phone = SharedPreUtil.getStringValue(mContext, SHA_PHONE, "");
            et_username.setText(phone);
            et_username.setSelection(phone.length());
            if (TextUtils.isEmpty(phone)) {
                LoginData user = DBManager.getInstance(mContext).getUserMessage();
                if (user != null && !TextUtils.isEmpty(user.phone)) {
                    et_username.setText(user.phone);
                    et_username.setSelection(user.phone.length());
                }
            }
        }

    }

    @Override
    @OnClick({R.id.btn_login, R.id.btn_forgetPwd, R.id.btn_register,R.id.auth_btn_send_auth,
            R.id.login_auth_btn, R.id.auth_login_usepwd_btn,/* R.id.tv_isRight, */R.id.delete,R.id.auth_btn_login,
            R.id.login_logo_iv, R.id.login_back_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth_login_usepwd_btn:
                loginAuthLl.setVisibility(View.GONE);
                authLoginPwdLl.setVisibility(View.VISIBLE);
                break;
            case R.id.login_auth_btn:
                loginAuthLl.setVisibility(View.VISIBLE);
                authLoginPwdLl.setVisibility(View.GONE);
                break;

            case R.id.btn_login:
                if (btn_login.isEnabled()) {
                    btn_login.setEnabled(false);
                    if (StringUtil.isRule(mContext, RULE_PHONE, et_username.getText().toString(), R.string.err_phone)) {
                        final String pwd = et_userpwd.getText().toString();
                        final String phone = et_username.getText().toString();
                        if (!TextUtils.isEmpty(pwd) && pwd.length() > 5) {
                            new LoginTask(mContext, true, phone, pwd, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    btn_login.setEnabled(true);
                                    if (object != null && object instanceof Login) {
                                        Login mLogin = (Login) object;
                                        if (mLogin.data != null) {
                                            JPushInterface.init(getApplicationContext());
                                            JPushInterface.resumePush(getApplicationContext());
                                            //调用JPush API设置Alias
                                            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, phone));
                                            LoginUtil.loginSuccess(LoginActivity.this, mLogin.data.userId);

                                            mLogin.data.jsonData = null;
                                            mLogin.data.jsonData = new JsonBuild().setModel(mLogin.data).getJsonString1();
                                            DBManager.getInstance(mContext).insert(mLogin.data);
                                            DBManager.getInstance(mContext).insert(mLogin.data.userId, pwd, phone);
                                            ToastUtil.showToast(mContext, "登录成功");
                                            startActivity(MainActivity.class);
                                            finish();
                                        }
                                    }

                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    if (!TextUtils.isEmpty(status) && status.equals("502")) {
                                        //服务器已关闭
                                        ToastUtil.showToast(mContext, "服务器已关闭");
                                    } else {
                                        ToastUtil.showToast(mContext, msg);
                                    }
                                    btn_login.setEnabled(true);
                                }
                            }).start();
                        } else {
                            btn_login.setEnabled(true);
                            ToastUtil.showToast(mContext, "密码格式不正确，请重新输入");
                        }
                    } else {
                        btn_login.setEnabled(true);
                    }
                }
                break;
            case R.id.login_back_iv:
                finish1();
                break;
            case R.id.btn_forgetPwd:
                startActivity(ForgetPwdActivity.class);
                break;
            case R.id.btn_register:
                startActivity(RegisterActivity.class);
                break;
           /* case R.id.tv_isRight:
                if (et_username.getText().toString().length() > 0) {
                    et_username.setText("");
                }
                break;*/
            case R.id.delete:
               /* if (et_userpwd.getText().toString().length() > 0) {
                    et_userpwd.setText("");
                }*/
                if (ispwd) {//明文
                    delete.setImageDrawable(getResources().getDrawable(R.drawable.enter_open));
                    et_userpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ispwd = false;
                } else {//密文
                    delete.setImageDrawable(getResources().getDrawable(R.drawable.enter_close));
                    et_userpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ispwd = true;
                }
                et_userpwd.setSelection(et_userpwd.length());
                break;

            case R.id.login_logo_iv:
                clocktime++;
                if (clocktime > 6 && Config.Env == 1) {
                    String channelName = AnalyticsConfig.getChannel(LoginActivity.this);
                    ToastUtil.showToast(LoginActivity.this, channelName);
                    clocktime = 0;
                }
                break;

            case R.id.auth_btn_send_auth:
                if (authBtnSendAuth.isEnabled()) {
                    getCode();
                }
                break;

            case R.id.auth_btn_login:
                loginUseAuth();

                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void loginUseAuth() {
        if (authBtnLogin.isEnabled()) {
            authBtnLogin.setEnabled(false);
            if (StringUtil.isRule(mContext, RULE_PHONE, authEtUsername.getText().toString(), R.string.err_phone)) {
                final String authcode = authEtUserpwd.getText().toString();
                final String phone = authEtUsername.getText().toString();

                if (!TextUtils.isEmpty(authcode)) {

                    new LoginUseAuthTask(mContext, true, phone, authcode, new Back() {
                        @Override
                        public void success(Object object, String msg) {
                            btn_login.setEnabled(true);
                            if (object != null && object instanceof Login) {
                                Login mLogin = (Login) object;
                                if (mLogin.data != null) {
                                    JPushInterface.init(getApplicationContext());
                                    JPushInterface.resumePush(getApplicationContext());
                                    //调用JPush API设置Alias
                                    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, phone));
                                    LoginUtil.loginSuccess(LoginActivity.this, mLogin.data.userId);

                                    mLogin.data.jsonData = null;
                                    mLogin.data.jsonData = new JsonBuild().setModel(mLogin.data).getJsonString1();
                                    DBManager.getInstance(mContext).insert(mLogin.data);
                                    DBManager.getInstance(mContext).insert(mLogin.data.userId, "", phone);
                                    ToastUtil.showToast(mContext, "登录成功");
                                    startActivity(MainActivity.class);
                                    finish();
                                }
                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            if (!TextUtils.isEmpty(status) && status.equals("502")) {
                                //服务器已关闭
                                ToastUtil.showToast(mContext, "服务器已关闭");
                            } else {
                                ToastUtil.showToast(mContext, msg);
                            }
                            authBtnLogin.setEnabled(true);
                        }
                    }).start();
                } else {
                    authBtnLogin.setEnabled(true);
                    ToastUtil.showToast(mContext, "密码格式不正确，请重新输入");
                }
            } else {
                authBtnLogin.setEnabled(true);
            }
        }
    }


    public void getCode() {
        String phone = authEtUsername.getText().toString();
        if (StringUtil.isRule(mContext, RULE_PHONE, phone, R.string.err_phone)) {
            if (SystemUtil.getNetworkStatus(mContext)) {
                new GetCheckCodeTask(mContext, phone, new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        // TODO Auto-generated method stub
                        ToastUtil.showToast(mContext, "获取验证码");
                        if (object != null && object instanceof GetCheckCode) {
                            GetCheckCode code = (GetCheckCode) object;
                            if (code.data != null) {
                                if (!TextUtils.isEmpty(code.data.valid_time)) {
                                    startCountTask(Integer.parseInt(code.data.valid_time));
                                }

                            }
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        // TODO Auto-generated method stub
                        ToastUtil.showToast(mContext, "获取失败");
                    }
                }).start();
            } else {
                ToastUtil.showToast(mContext, "网络异常，获取失败");
            }
        }

    }

    //开始倒计时
    private void startCountTask(int num) {
        if (countDownTask != null) {
            stopCountTask();
        }
        countDownTask = new CountDownTask(num);
        countDownTask.start();
    }

    // 停止倒计时
    @SuppressWarnings("deprecation")
    private void stopCountTask() {
        try {
            if (countDownTask != null) {
                countDownTask.cancel();
                countDownTask = null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            authBtnSendAuth.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
            authBtnSendAuth.setTextColor(getResources().getColor(R.color.light_blue));
            authBtnSendAuth.setText("获取验证码");
            authBtnSendAuth.setEnabled(true);
        }
    }


    /**
     * 倒计时
     */
    private class CountDownTask extends CountDownTimer {

        public CountDownTask(int num) {
            super(num * 60 * 1000, 1000);
            authBtnSendAuth.setEnabled(false);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onFinish() {
            authBtnSendAuth.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
            authBtnSendAuth.setTextColor(getResources().getColor(R.color.light_blue));
            authBtnSendAuth.setText("获取验证码");
            authBtnSendAuth.setEnabled(true);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onTick(long time) {
            authBtnSendAuth.setBackgroundDrawable(getResources().getDrawable(R.drawable.gh_line3_bg_shape_18));
            authBtnSendAuth.setTextColor(getResources().getColor(R.color.white));
            int times = (int) (time / 1000);
            authBtnSendAuth.setText(times + "秒");
        }


    }

    @Override
    protected void onAppExit() {
        super.onAppExit();
        BaseApplication.mInstance.onAppExit();
    }

    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;

    private void authorize(Platform plat) {
        if (plat == null) {
            //popupOthers();
            ToastUtil.showToast(mContext, "xxxx");
            return;
        }
//判断指定平台是否已经完成授权
        System.out.println("---------------" + plat.isAuthValid());
        if (plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }

    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {

                String text = getString(R.string.logining, msg.obj);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                System.out.println("---------------");

//				Builder builder = new Builder(this);
//				builder.setTitle(R.string.if_register_needed);
//				builder.setMessage(R.string.after_auth);
//				builder.setPositiveButton(R.string.ok, null);
//				builder.create().show();
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_CANCEL--------");
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_ERROR--------");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
                System.out.println("--------MSG_AUTH_COMPLETE-------");
            }
            break;
        }
        return false;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        System.out.println(res);
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
    }

    @Override
    public void onError(Platform platform, int i, Throwable t) {
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    @OnClick({R.id.login_wechat_btn, R.id.login_qq_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_wechat_btn:
                authorize(new Wechat(this));
                break;
            case R.id.login_qq_btn:
                loginQQ();
                break;
        }
    }

    /**
     * ------------------------QQ第三方登录--------------------
     */
    public void loginQQ() {
        /** 判断是否登陆过 */
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }/** 登陆过注销之后在登录 */
        else {
            mTencent.logout(this);
            mTencent.login(this, "all", loginListener);
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    /**
     * QQ登录第二步：存储token和openid
     */
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    /**
     * QQ登录第三步：获取用户信息
     */
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                    Message msg = new Message();
                    msg.obj = "把手机时间改成获取网络时间";
                    msg.what = 1;
                    mHandlerqq.sendMessage(msg);
                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandlerqq.sendMessage(msg);
                }

                @Override
                public void onCancel() {
                    Message msg = new Message();
                    msg.obj = "获取用户信息失败";
                    msg.what = 2;
                    mHandlerqq.sendMessage(msg);
                }
            };
            UserInfo mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        } else {

        }
    }

    Handler mHandlerqq = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /** 获取用户信息成功 */
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        System.out.println("获取用户信息成功，返回结果：" + response.toString());
                        mThirdLoginResult.setText("登录成功\n" + "用户id:" + "\n昵称:" + response.getString("nickname") + "\n头像地址:" + response.get("figureurl_qq_1"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (msg.what == 1) {
                mThirdLoginResult.setText(msg + "");
            } else if (msg.what == 2) {
                mThirdLoginResult.setText(msg + "");
            }
        }

    };


    /**
     * QQ登录第一步：获取token和openid
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtil.showToast(mContext, "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtil.showToast(mContext, "登录失败");
                return;
            }
            System.out.println("QQ登录成功返回结果-" + response.toString());
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject response) {
        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(LoginActivity.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }
    /** -------------------------QQ第三方登录结束-------------------- */
}
