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
import com.game.helper.adapter.community.CommunityAnnoucementAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetNoticeListTask;
import com.game.helper.sdk.model.returns.GetNoticeList;
import com.game.helper.sdk.model.returns.GetNoticeList.GetNoticeListData;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.TimeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 公会公告
 * @Path com.game.helper.activity.community.SociatyAnnoucementActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午5:47:50
 * @Company
 */
public class SociatyAnnoucementActivity extends BaseActivity {

    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.listView_CommunityAnnoucement)
    ListView listView_CommunityAnnoucement;
    private CommunityAnnoucementAdapter mCommunityAnnoucementAdapter;
    private List<GetNoticeListData> data = new ArrayList<GetNoticeListData>();
    private String guildId, guild_userId;
    private LoginData user;

    private Comparator comp1 = new Comparator() {
        public int compare(Object o1, Object o2) {
            GetNoticeListData p1 = (GetNoticeListData) o1;
            GetNoticeListData p2 = (GetNoticeListData) o2;
            int a = 0;
            a = TimeUtil.parserTime(TimeUtil.TIME_FORMAT_FULL, p1.createTimeString, p2.createTimeString);
            if (a != 2) {
                if (a == 1) {
                    return -1;
                } else if (a == 1) {
                    return -1;
                } else {
                    return a;
                }
            }
            return 0;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_announcement_sociaty);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("公会公告");
        topLeftBack.setVisibility(View.VISIBLE);
        guildId = getIntent().getStringExtra("guildId");
        guild_userId = getIntent().getStringExtra("guild_userId");
        user = DBManager.getInstance(mContext).getUserMessage();
        if (!TextUtils.isEmpty(guild_userId) && user != null && !TextUtils.isEmpty(user.userId) && guild_userId.equals(user.userId)) {//会长

            topRight.setText("添加");
            topRight.setVisibility(View.VISIBLE);
        }

        mCommunityAnnoucementAdapter = new CommunityAnnoucementAdapter(mContext, data);
        listView_CommunityAnnoucement.setAdapter(mCommunityAnnoucementAdapter);

        listView_CommunityAnnoucement.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Bundle bu = new Bundle();
                bu.putString("noticeId", mCommunityAnnoucementAdapter.getData().get(arg2).noticeId);
                startActivity(AnnouncementDetailsActivity.class, bu);
            }
        });
    }

    @Override
    protected void initData() {


        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetNoticeList + guildId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetNoticeList.class, json);
            if (mObject != null && mObject instanceof GetNoticeList) {
                GetNoticeList mGetNoticeList = (GetNoticeList) mObject;
                if (mGetNoticeList != null && mGetNoticeList.data != null && mGetNoticeList.data.size() >= 0) {
                    Collections.sort(mGetNoticeList.data, comp1);
                    data.clear();
                    data.addAll(mGetNoticeList.data);
                    mCommunityAnnoucementAdapter.setData(data);
                }
            }
        }
        //获取工会公告
        new GetNoticeListTask(mContext, true, guildId, new Back() {

            @Override
            public void success(Object object, String msg) {

                if (object != null && object instanceof GetNoticeList) {
                    GetNoticeList mGetNoticeList = (GetNoticeList) object;
                    if (mGetNoticeList.data != null && mGetNoticeList.data.size() >= 0) {
                        Collections.sort(mGetNoticeList.data, comp1);
                        data.clear();
                        data.addAll(mGetNoticeList.data);
                        mCommunityAnnoucementAdapter.setData(data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetNoticeList + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetNoticeList + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetNoticeList.class, json);
                    if (mObject != null && mObject instanceof GetNoticeList) {
                        GetNoticeList mGetNoticeList = (GetNoticeList) mObject;
                        if (mGetNoticeList != null && mGetNoticeList.data != null && mGetNoticeList.data.size() >= 0) {
                            Collections.sort(mGetNoticeList.data, comp1);
                            data.clear();
                            data.addAll(mGetNoticeList.data);
                            mCommunityAnnoucementAdapter.setData(data);
                        }
                    }
                }
            }
        }).start();

    }

    int i = 0;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SociatyAnnoucementActivity");
        super.onResume();
        if (i == 0) {
            i = 1;
        } else {
            initData();
        }
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                //发布公告
                Bundle bundle = new Bundle();
                bundle.putString("guildId", guildId);
                startActivity(ReleaseAnnouncementActivity.class, bundle);
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
        MobclickAgent.onPageEnd("SociatyAnnoucementActivity");
        super.onPause();
    }


}
