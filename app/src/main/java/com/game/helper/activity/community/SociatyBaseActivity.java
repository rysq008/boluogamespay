package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.HotSociatyAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGuildListTask;
import com.game.helper.sdk.model.returns.GetGuildList;
import com.game.helper.sdk.model.returns.GetGuildList.GetGuildListData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 公会基地
 * @Path com.game.helper.activity.community.SociatyBaseActivity.java
 * @Author lbb
 * @Date 2016年8月25日 上午10:04:40
 * @Company
 */
public class SociatyBaseActivity extends BaseActivity {
    protected HotSociatyAdapter mHotCommunityAdapter;
    protected List<GetGuildListData> data = new ArrayList<GetGuildListData>();
    @BindView(R.id.top_right)
    TextView topTitle;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.lv_hot_community)
    ListView lv_hot_community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_sociaty_base);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("公会基地");
        topTitle.setEnabled(true);
        topTitle.setFocusable(true);
        topTitle.setFocusableInTouchMode(true);
        topTitle.requestFocus();
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.shouye_19));
        top_liner_right.setVisibility(View.VISIBLE);

        mHotCommunityAdapter = new HotSociatyAdapter(mContext, data, 0);
        lv_hot_community.setAdapter(mHotCommunityAdapter);
        lv_hot_community.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                i = 0;
                Bundle bundle = new Bundle();
                bundle.putString("guildId", mHotCommunityAdapter.getData().get(arg2).guildId);
                startActivity(SociatyDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {

        new GetGuildListTask(mContext, true, "", new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGuildList) {
                    GetGuildList mGetGuildList = (GetGuildList) object;
                    if (mGetGuildList.data != null && mGetGuildList.data.size() >= 0) {
                        data.clear();
                        data.addAll(mGetGuildList.data);
                        mHotCommunityAdapter.setData(data);

                        SharedPreUtil.putStringValue(mContext, ACTION_GetGuildListBASE, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGuildListBASE, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetGuildList.class, json);
                    if (mObject != null && mObject instanceof GetGuildList) {
                        GetGuildList mData = (GetGuildList) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            data.clear();
                            data.addAll(mData.data);
                            mHotCommunityAdapter.setData(data);
                        }
                    }
                }

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_gets, R.id.top_liner_right
            , R.id.relat_hot_community, R.id.mLAdd})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.mLAdd:
            case R.id.tv_gets:
                i = 1;
                startActivity(CreateSociatyActivity.class);
                break;
            case R.id.top_liner_right:
                i = 0;
                startActivity(SearchSociatyActivity.class);
                break;
            case R.id.relat_hot_community:
                i = 0;
                startActivity(HotSociatyActivity.class);
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    int i = 0;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SociatyBaseActivity");
        super.onResume();
        if (i == 0) {
            i = 1;
        } else {
            new GetGuildListTask(mContext, false, "", new Back() {

                @Override
                public void success(Object object, String msg) {
                    // TODO Auto-generated method stub
                    if (object != null && object instanceof GetGuildList) {
                        GetGuildList mGetGuildList = (GetGuildList) object;
                        if (mGetGuildList.data != null && mGetGuildList.data.size() >= 0) {
                            data.clear();
                            data.addAll(mGetGuildList.data);
                            mHotCommunityAdapter.setData(data);

                            SharedPreUtil.putStringValue(mContext, ACTION_GetGuildListBASE, new JsonBuild().setModel(object).getJson1());
                        }
                    }
                }

                @Override
                public void fail(String status, String msg, Object object) {
                    String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGuildListBASE, "");
                    if (!TextUtils.isEmpty(json)) {
                        Object mObject = new JsonBuild().getData(GetGuildList.class, json);
                        if (mObject != null && mObject instanceof GetGuildList) {
                            GetGuildList mData = (GetGuildList) mObject;
                            if (mData != null && mData.data != null && mData.data.size() >= 0) {
                                data.clear();
                                data.addAll(mData.data);
                                mHotCommunityAdapter.setData(data);
                            }
                        }
                    }

                }
            }).start();
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
        MobclickAgent.onPageEnd("SociatyBaseActivity");
        super.onPause();
    }


}
