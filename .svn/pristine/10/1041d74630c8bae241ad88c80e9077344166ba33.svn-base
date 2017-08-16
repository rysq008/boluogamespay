package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.FollowPeopleAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetFocusListTask;
import com.game.helper.sdk.model.returns.GetFocusList;
import com.game.helper.sdk.model.returns.GetFocusList.GetFocusListData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 粉丝
 * @Path com.game.helper.activity.community.FollowerActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午1:35:04
 * @Company
 */
public class FollowerActivity extends BaseActivity {
    FollowPeopleAdapter mFollowPeopleAdapter;
    List<GetFocusListData> data = new ArrayList<GetFocusListData>();

    String userId;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.follower_listView)
    ListViewForScrollView follower_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_follower);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("粉丝");
        topLeftBack.setVisibility(View.VISIBLE);

        mFollowPeopleAdapter = new FollowPeopleAdapter(mContext, data, 1);
        follower_listView.setAdapter(mFollowPeopleAdapter);
        follower_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mFollowPeopleAdapter.getData().get(position).operId);
                startActivity(PersonalHomepageActivity.class, bundle);
            }
        });

    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");

        new GetFocusListTask(mContext, true, userId, "1", new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetFocusList) {
                    GetFocusList m = (GetFocusList) object;
                    if (m.data != null && m.data.size() >= 0) {
                        data.clear();
                        data.addAll(m.data);
                        mFollowPeopleAdapter.setData(data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetFocusList2 + userId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetFocusList2 + userId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetFocusList.class, json);
                    if (mObject != null && mObject instanceof GetFocusList) {
                        GetFocusList mData = (GetFocusList) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            data.clear();
                            data.addAll(mData.data);
                            mFollowPeopleAdapter.setData(data);
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
        MobclickAgent.onPageEnd("FollowerActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("FollowerActivity");
        super.onResume();
    }

}
