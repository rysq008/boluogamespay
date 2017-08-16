package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.GivingGiftsAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGiftListTask;
import com.game.helper.sdk.model.returns.GetGiftList;
import com.game.helper.sdk.model.returns.GetGiftList.GetGiftListData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 送礼物
 * @Path com.game.helper.activity.community.GivingGiftsActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午1:42:54
 * @Company
 */
public class GivingGiftsActivity extends BaseActivity {
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.giving_gifts_listView)
    MyScrollviewGridView giving_gifts_listView;
    private GivingGiftsAdapter mGivingGiftsAdapter;
    private List<GetGiftListData> data = new ArrayList<GetGiftListData>();
    private String operId, nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_giving_gifts);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("送礼物");
        topLeftBack.setVisibility(View.VISIBLE);

        operId = getIntent().getStringExtra("operId");
        nickName = getIntent().getStringExtra("nickName");
        tv_name.setText(nickName);
        mGivingGiftsAdapter = new GivingGiftsAdapter(mContext, operId, data);
        giving_gifts_listView.setAdapter(mGivingGiftsAdapter);
    }

    @Override
    protected void initData() {

        new GetGiftListTask(mContext, true, new Back() {

            @Override
            public void success(Object object, String msg) {

                if (object != null && object instanceof GetGiftList) {
                    GetGiftList m = (GetGiftList) object;
                    if (m.data != null && m.data.size() >= 0) {
                        data.clear();
                        data.addAll(m.data);
                        mGivingGiftsAdapter.setData(data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetGiftList, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGiftList, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetGiftList.class, json);
                    if (mObject != null && mObject instanceof GetGiftList) {
                        GetGiftList mData = (GetGiftList) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            data.clear();
                            data.addAll(mData.data);
                            mGivingGiftsAdapter.setData(data);
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
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
        MobclickAgent.onPageEnd("GivingGiftsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GivingGiftsActivity");
        super.onResume();
    }


}

