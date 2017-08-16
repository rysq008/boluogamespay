package com.game.helper.activity.mine;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetCheckCodeTask;
import com.game.helper.net.task.UpdPhoneTask;
import com.game.helper.sdk.model.returns.GetCheckCode;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.StringUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 绑定手机号
 * @Path com.game.helper.activity.mine.MineSetingPhoneActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午5:48:55
 * @Company
 */
public class MineSetingPhoneActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_userpwd)
    EditText et_userpwd;

    @BindView(R.id.tv_code)
    TextView tv_code;

    @BindView(R.id.tv_isRight)
    ImageView tv_isRight;
    @BindView(R.id.delete1)
    ImageView delete1;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    /**
     * 倒计时线程
     */
    private CountDownTask countDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_setingphone);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("手机号");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        topRight.setText("确认");
    }

    LoginData user;

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
                // TODO Auto-generated method stub
                if (StringUtil.isRule(mContext, RULE_PHONE, s.toString(), -1)) {
                    tv_isRight.setImageDrawable(getResources().getDrawable(R.drawable.shouye_63));
                } else {
                    tv_isRight.setImageDrawable(getResources().getDrawable(R.drawable.shouye_67));
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
        });

        user = DBManager.getInstance(mContext).getUserMessage();
        if (user != null && !TextUtils.isEmpty(user.phone)) {
            et_username.setText("" + user.phone);
            et_username.setSelection(et_username.getText().length());
        }
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_code, R.id.top_right, R.id.tv_isRight, R.id.delete1})
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
            case R.id.top_right:
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    final String phone = et_username.getText().toString();
                    String code = et_code.getText().toString();
                    String pwd = et_userpwd.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        ToastUtil.showToast(mContext, "请输入手机号");
                        topRight.setEnabled(true);
                    } else if (!StringUtil.isRule(mContext, RULE_PHONE, phone, -1)) {
                        ToastUtil.showToast(mContext, "手机号格式不正确");
                        topRight.setEnabled(true);
                    } else if (TextUtils.isEmpty(code)) {
                        ToastUtil.showToast(mContext, "请输入验证码");
                        topRight.setEnabled(true);
                    } else if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 30) {
                        ToastUtil.showToast(mContext, "密码长度不正确，请重新输入");
                        topRight.setEnabled(true);
                    } else {
                        new UpdPhoneTask(mContext, user.userId, phone, code, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                user.jsonData = null;
                                user.phone = phone;
                                user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                DBManager.getInstance(mContext).insert(user);
                                ToastUtil.showToast(mContext, "手机号修改成功");
                                topRight.setEnabled(true);
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                // TODO Auto-generated method stub
                                topRight.setEnabled(true);
                                ToastUtil.showToast(mContext, msg);
                            }
                        }).start();
                    }
                }
                finish1();
                break;
            case R.id.tv_isRight:
                if (et_username.getText().toString().length() > 0) {
                    et_username.setText("");
                }
                break;
            case R.id.delete1:
                if (et_userpwd.getText().toString().length() > 0) {
                    et_userpwd.setText("");
                }
                break;
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

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("MineSetingPhoneActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineSetingPhoneActivity");
        super.onResume();
    }

}
