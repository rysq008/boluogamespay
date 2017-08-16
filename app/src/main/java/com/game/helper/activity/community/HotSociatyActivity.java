package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.HotSociatyAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetHotGuildTask;
import com.game.helper.sdk.model.returns.GetGuildList.GetGuildListData;
import com.game.helper.sdk.model.returns.GetHotGuild;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 热门公会推荐
 * @Path com.game.helper.activity.community.HotSociatyActivity.java
 * @Author lbb
 * @Date 2016年8月25日 上午10:28:35
 * @Company
 */
public class HotSociatyActivity extends BaseActivity {
    //@BindView(R.id.lv_hotSociaty) ListView lv_hotSociaty;
    protected HotSociatyAdapter mHotCommunityAdapter;
    protected List<GetGuildListData> datas = new ArrayList<GetGuildListData>();
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.lv_hotSociaty)
    ListView lv_hotSociaty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_hot_sociaty);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("热门公会推荐");
        topLeftBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        mHotCommunityAdapter = new HotSociatyAdapter(mContext, datas, 0);
        lv_hotSociaty.setAdapter(mHotCommunityAdapter);
        lv_hotSociaty.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putString("guildId", mHotCommunityAdapter.getData().get(arg2).guildId);
                startActivity(SociatyDetailsActivity.class, bundle);
            }
        });
        new GetHotGuildTask(mContext, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetHotGuild) {
                    GetHotGuild mGetHotGuild = (GetHotGuild) object;
                    if (mGetHotGuild.data != null && mGetHotGuild.data.size() >= 0) {
                        datas.clear();
                        datas.addAll(mGetHotGuild.data);
                        mHotCommunityAdapter.setData(datas);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetHotGuild, new JsonBuild().setModel(object).getJson1());

                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {

                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetHotGuild, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetHotGuild.class, json);
                    if (mObject != null && mObject instanceof GetHotGuild) {
                        GetHotGuild mData = (GetHotGuild) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            datas.clear();
                            datas.addAll(mData.data);
                            mHotCommunityAdapter.setData(datas);
                        }
                    }
                } else {
                    ToastUtil.showToast(mContext, msg);
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
        MobclickAgent.onPageEnd("HotSociatyActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("HotSociatyActivity");
        super.onResume();
    }
}