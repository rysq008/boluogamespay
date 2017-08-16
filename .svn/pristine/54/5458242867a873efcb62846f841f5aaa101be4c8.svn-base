package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.SavefeedBackTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 意见反馈
 * @Path com.game.helper.activity.mine.MineFeedBackActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午1:30:40
 * @Company
 */
public class MineFeedBackActivity extends BaseActivity {

    @BindView(R.id.feedback_tv_number)
    TextView feedback_tv_number;
    @BindView(R.id.ed_fbfeedback)
    EditText ed_fbfeedback;

    @BindView(R.id.btn_set)
    Button btn_set;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_feedback);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("意见反馈");
        topLeftBack.setVisibility(View.VISIBLE);
        ed_fbfeedback.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 当输入信息改变之后
                feedback_tv_number.setText(s.length() + "/300");
            }

        });
    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.btn_set})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.btn_set:
                //提交
                LoginData user = DBManager.getInstance(mContext).getUserMessage();
                new SavefeedBackTask(mContext, user.userId, ed_fbfeedback.getText().toString(), new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        ToastUtil.showToast(mContext, "意见反馈提交成功，感谢您的支持！");
                        finish1();
                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        ToastUtil.showToast(mContext, msg);

                    }
                }).start();

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

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("MineFeedBackActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineFeedBackActivity");
        super.onResume();
    }
}
