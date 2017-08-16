package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.SaveNoticeTask;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 发布公告
 * @Path com.game.helper.activity.community.ReleaseAnnouncementActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午5:39:35
 * @Company
 */
public class ReleaseAnnouncementActivity extends BaseActivity {
    /*@BindView(R.id.et_title) EditText et_title;
    @BindView(R.id.et_context) EditText et_context;*/
    String guildId;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_context)
    EditText et_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_release_announcement);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("发布公告");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        topRight.setText("确定");
    }

    @Override
    protected void initData() {
        guildId = getIntent().getStringExtra("guildId");
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                //确定
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    String noticeName = et_title.getText().toString();
                    String content = et_context.getText().toString();
                    if (TextUtils.isEmpty(noticeName)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入公告标题");
                    } else if (TextUtils.isEmpty(content)) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请输入公告内容");
                    } else {
                        new SaveNoticeTask(mContext, guildId, noticeName, content, new Back() {

                            @Override
                            public void success(Object object, String msg) {

                                ToastUtil.showToast(mContext, "发布成功");
                                topRight.setEnabled(true);
                                finish1();
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                topRight.setEnabled(true);
                                ToastUtil.showToast(mContext, msg);
                            }
                        }).start();
                    }
                }


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
        MobclickAgent.onPageEnd("ReleaseAnnouncementActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("ReleaseAnnouncementActivity");
        super.onResume();
    }

}

