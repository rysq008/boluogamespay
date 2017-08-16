package com.game.helper.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetCheckCodeTask;
import com.game.helper.net.task.ResetPwdTask;
import com.game.helper.sdk.model.returns.GetCheckCode;
import com.game.helper.util.StringUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.ForgetPwdActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午1:55:47
 * @Company
 */
public class ForgetPwdActivity extends BaseActivity {

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
    @BindView(R.id.btn_set)
    Button btn_set;

    /*@BindView(R.id.tv_isRight)
    ImageView tv_isRight;
    @BindView(R.id.delete1)
    ImageView delete1;
    @BindView(R.id.delete2)
    ImageView delete2;*/
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    /**
     * 倒计时线程
     */
    private CountDownTask countDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_forget_pwd);
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
        topTitle.setText("忘记密码");
        topLeftBack.setVisibility(View.VISIBLE);

        /*et_username.addTextChangedListener(new TextWatcher() {

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
                // TODO Auto-generated method stub
                if (StringUtil.isRule(mContext, RULE_PHONE, s.toString(), -1)) {
                    tv_isRight.setImageDrawable(getResources().getDrawable(R.drawable.shouye_63));
                } else {
                    tv_isRight.setImageDrawable(getResources().getDrawable(R.drawable.shouye_67));
                }
            }
        });
        et_userpwdCheck.addTextChangedListener(new TextWatcher() {

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
                if (s.length() > 5 && s.length() < 31) {
                    if (s.toString().equals(et_userpwd.getText().toString())) {
                        delete2.setImageDrawable(getResources().getDrawable(R.drawable.shouye_63));
                    } else {
                        delete2.setImageDrawable(getResources().getDrawable(R.drawable.shouye_67));
                    }
                } else {
                    delete2.setImageDrawable(getResources().getDrawable(R.drawable.shouye_67));
                }
            }
        });
        et_userpwd.addTextChangedListener(new TextWatcher() {

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
                if (s.length() > 5 && s.length() < 31) {
                    delete1.setImageDrawable(getResources().getDrawable(R.drawable.shouye_63));
                } else {
                    delete1.setImageDrawable(getResources().getDrawable(R.drawable.shouye_67));
                }
            }
        });*/
    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_code, R.id.btn_set
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
            case R.id.btn_set:
                //提交
                if (btn_set.isEnabled()) {
                    btn_set.setEnabled(false);
                    String p = et_userpwd.getText().toString();
                    String pc = et_userpwdCheck.getText().toString();
                    if (TextUtils.isEmpty(et_username.getText().toString())) {
                        ToastUtil.showToast(mContext, "请输入手机号");
                        btn_set.setEnabled(true);
                    } else if (!StringUtil.isRule(mContext, RULE_PHONE, et_username.getText().toString(), -1)) {
                        ToastUtil.showToast(mContext, "手机号格式不正确");
                        btn_set.setEnabled(true);
                    } else if (TextUtils.isEmpty(et_code.getText().toString())) {
                        ToastUtil.showToast(mContext, "请输入验证码");
                        btn_set.setEnabled(true);
                    } else if (TextUtils.isEmpty(p) || p.length() < 6) {
                        ToastUtil.showToast(mContext, "密码长度设置太短，请重新输入");
                        btn_set.setEnabled(true);
                    } else if (TextUtils.isEmpty(pc) || pc.length() < 6) {
                        ToastUtil.showToast(mContext, "密码长度设置太短，请重新输入");
                        btn_set.setEnabled(true);
                    } else if (p.equals(pc) == false) {
                        ToastUtil.showToast(mContext, "两次输入密码不一致，请重新输入");
                        btn_set.setEnabled(true);
                    } else if (!TextUtils.isEmpty(p) && p.length() > 5 && !TextUtils.isEmpty(pc) && pc.length() > 5 && p.equals(pc)) {

                        new ResetPwdTask(mContext, et_username.getText().toString(), et_code.getText().toString(),
                                et_userpwdCheck.getText().toString(), new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                // TODO Auto-generated method stub
                                ToastUtil.showToast(mContext, "密码重置成功");
                                finish1();
                                btn_set.setEnabled(true);
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                btn_set.setEnabled(true);
                                ToastUtil.showToast(mContext, "密码重置失败：" + msg);
                            }
                        }).start();
                    } else {
                        btn_set.setEnabled(true);
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
            super(num * 10 * 1000, 1000);
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
