package com.game.helper.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetCheckCodeTask;
import com.game.helper.net.task.RegisterTask;
import com.game.helper.sdk.model.returns.GetCheckCode;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.StringUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.umeng.analytics.AnalyticsConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.RegisterActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午1:58:06
 * @Company
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_userpwd)
    EditText et_userpwd;
    @BindView(R.id.et_userpwdCheck)
    EditText et_userpwdCheck;

    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.cb_clause)
    CheckBox cb_clause;
    @BindView(R.id.tv_clause)
    TextView tv_clause;
    @BindView(R.id.btn_toregister)
    Button btn_register;

   /* @BindView(R.id.tv_isRight)
    ImageView tv_isRight;
    @BindView(R.id.delete1)
    ImageView delete1;
    @BindView(R.id.delete2)
    ImageView delete2;*/

    @BindView(R.id.et_code1)
    EditText et_code1;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    /**
     * 倒计时线程
     */
    private CountDownTask countDownTask;
    private String channelName;
    private String channelId;
    private String deviceName;
    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("注册账号");
        topLeftBack.setVisibility(View.VISIBLE);

        //电话状态权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            ToastUtil.showToast(RegisterActivity.this, "为了您更好的体验，请您打开权限设置");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //ToastUtil.showToast(RegisterActivity.this, "您允许状态权限");
            } else {
                //ToastUtil.showToast(RegisterActivity.this, "请您打开权限设置");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_code, R.id.tv_clause, R.id.btn_toregister
            /*, R.id.tv_isRight, R.id.delete1, R.id.delete2*/})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_code:
                //获取验证码
                if (tv_code.isEnabled()) {
                    getCode();
                }

                break;
            case R.id.tv_clause:
                startActivity(UserAgreementActivity.class);
                break;
            case R.id.btn_toregister:
                //注册
                channelName = AnalyticsConfig.getChannel(RegisterActivity.this);
                if (btn_register.isEnabled()) {
                    btn_register.setEnabled(false);
                    if (cb_clause.isChecked()) {
                        String p = et_userpwd.getText().toString();
                        String pc = et_userpwdCheck.getText().toString();
                        if (TextUtils.isEmpty(et_username.getText().toString())) {
                            ToastUtil.showToast(mContext, "请输入手机号");
                            btn_register.setEnabled(true);
                        } else if (!StringUtil.isRule(mContext, RULE_PHONE, et_username.getText().toString(), -1)) {
                            ToastUtil.showToast(mContext, "手机号格式不正确");
                            btn_register.setEnabled(true);
                        } else if (TextUtils.isEmpty(et_code.getText().toString())) {
                            ToastUtil.showToast(mContext, "请输入验证码");
                            btn_register.setEnabled(true);
                        } else if (TextUtils.isEmpty(p) || p.length() < 6) {
                            ToastUtil.showToast(mContext, "密码长度设置太短，请重新输入");
                            btn_register.setEnabled(true);
                        } else if (TextUtils.isEmpty(pc) || pc.length() < 6) {
                            ToastUtil.showToast(mContext, "密码长度设置太短，请重新输入");
                            btn_register.setEnabled(true);
                        } else if (p.equals(pc) == false) {
                            ToastUtil.showToast(mContext, "两次输入密码不一致，请重新输入");
                            btn_register.setEnabled(true);
                        } else if (!TextUtils.isEmpty(p) && p.length() > 5 && !TextUtils.isEmpty(pc) && pc.length() > 5 && p.equals(pc)) {
                            /**
                             * huawei {}
                             c360 {}
                             baidu {}
                             yingyongbao {}
                             ppzhushou {}
                             meizu {}
                             xiaomi {}
                             pc {}
                             h5 {}
                             other {}
                             */
                            channelId = "";
                            switch (channelName) {
                                case "huawei":
                                    channelId = "1001";
                                    break;

                                case "c360":
                                    channelId = "1002";
                                    break;

                                case "baidu":
                                    channelId = "1003";
                                    break;

                                case "yingyongbao":
                                    channelId = "1004";
                                    break;

                                case "ppzhushou":
                                    channelId = "1005";
                                    break;

                                case "meizu":
                                    channelId = "1006";
                                    break;

                                case "xiaomi":
                                    channelId = "1007";
                                    break;

                                case "pc":
                                    channelId = "2000";
                                    break;

                                case "h5":
                                    channelId = "3000";
                                    break;
                            }

                            try {
                                deviceName = Util.getDevicesId(RegisterActivity.this);
                            } catch (Exception e) {
                                deviceName = "";
                            }
//String channelId,String channelName,String deviceName
                            new RegisterTask(mContext, et_username.getText().toString(), et_code.getText().toString(),
                                    et_userpwd.getText().toString(), et_code1.getText().toString(), channelId, channelName, deviceName, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    // TODO Auto-generated method stub
                                    SharedPreUtil.putStringValue(mContext, SHA_PHONE, et_username.getText().toString());
                                    ToastUtil.showToast(mContext, "注册成功");
                                    finish1();
                                    btn_register.setEnabled(true);
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    btn_register.setEnabled(true);
                                    ToastUtil.showToast(mContext, "注册失败：" + msg);
                                }
                            }).start();
                        } else {
                            btn_register.setEnabled(true);
                        }

                    } else {
                        btn_register.setEnabled(true);
                        ToastUtil.showToast(mContext, "您还没同意用户协议");
                    }
                }
                break;
           /* case R.id.tv_isRight:
                if (et_username.getText().toString().length() > 0) {
                    et_username.setText("");
                }


                break;
            case R.id.delete1:
                if (et_userpwd.getText().toString().length() > 0) {
                    et_userpwd.setText("");
                }
                break;
            case R.id.delete2:
                if (et_userpwdCheck.getText().toString().length() > 0) {
                    et_userpwdCheck.setText("");
                }
                break;*/
            default:
                super.onClick(v);
                break;
        }

    }

    public void getCode() {
        String phone = et_username.getText().toString();
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
            tv_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
            tv_code.setTextColor(getResources().getColor(R.color.light_blue));
            tv_code.setText("获取验证码");
            tv_code.setEnabled(true);
        }
    }


    /**
     * 倒计时
     */
    private class CountDownTask extends CountDownTimer {

        public CountDownTask(int num) {
            super(num * 60 * 1000, 1000);
            tv_code.setEnabled(false);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onFinish() {
            tv_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
            tv_code.setTextColor(getResources().getColor(R.color.light_blue));
            tv_code.setText("获取验证码");
            tv_code.setEnabled(true);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onTick(long time) {
            tv_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.gh_line3_bg_shape_18));
            tv_code.setTextColor(getResources().getColor(R.color.white));
            int times = (int) (time / 1000);
            tv_code.setText(times + "秒");
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

}
