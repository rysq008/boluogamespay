package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetNoticeByIdTask;
import com.game.helper.sdk.model.returns.GetNoticeById;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 公告详情
 * @Path com.game.helper.activity.community.AnnouncementDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午5:47:50
 * @Company
 */
public class AnnouncementDetailsActivity extends BaseActivity {
/*    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    TextView tv_content;*/
    String noticeId;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_left_layout)
    LinearLayout topLeftLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.isDownload1)
    View isDownload1;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_announcement_details);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_title.setText("公告详情");
        topLeftBack.setVisibility(View.VISIBLE);
        noticeId = getIntent().getStringExtra("noticeId");
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetNoticeById + noticeId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetNoticeById.class, json);
            if (mObject != null && mObject instanceof GetNoticeById) {
                GetNoticeById mData = (GetNoticeById) mObject;
                if (mData != null && mData.data != null) {
                    tv_title.setText(mData.data.noticeName);
                    tv_time.setText(mData.data.createTimeString);
                    tv_content.setText(mData.data.content);
                }
            }
        }
    }

    @Override
    protected void initData() {
        new GetNoticeByIdTask(mContext, noticeId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetNoticeById) {
                    GetNoticeById mGetNoticeById = (GetNoticeById) object;
                    if (mGetNoticeById.data != null) {
                        tv_title.setText(mGetNoticeById.data.noticeName);
                        tv_time.setText(mGetNoticeById.data.createTimeString);
                        tv_content.setText(mGetNoticeById.data.content);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetNoticeById + noticeId,
                                new JsonBuild().setModel(object).getJson1());
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {

                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetNoticeById + noticeId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetNoticeById.class, json);
                    if (mObject != null && mObject instanceof GetNoticeById) {
                        GetNoticeById mData = (GetNoticeById) mObject;
                        if (mData != null && mData.data != null) {
                            tv_title.setText(mData.data.noticeName);
                            tv_time.setText(mData.data.createTimeString);
                            tv_content.setText(mData.data.content);
                        }
                    }
                }

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
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
        MobclickAgent.onPageEnd("AnnouncementDetailsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("AnnouncementDetailsActivity");
        super.onResume();
    }

}