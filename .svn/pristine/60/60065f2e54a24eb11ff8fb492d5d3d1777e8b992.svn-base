package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.SociatyMembersAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGuildUserTask;
import com.game.helper.sdk.model.returns.GetGuildUser;
import com.game.helper.sdk.model.returns.GetGuildUser.GetGuildUserData;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 公会成员
 * @Path com.game.helper.activity.community.SociatymembersActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午3:22:56
 * @Company
 */
public class SociatyMembersActivity extends BaseActivity {

    @BindView(R.id.LinearLayout1)
    LinearLayout LinearLayout1;
    @BindView(R.id.lv_searchSociatyMembers)
    ListView lv_searchSociatyMembers;
    SociatyMembersAdapter mSociatyMembersAdapter;
    List<GetGuildUserData> data = new ArrayList<GetGuildUserData>();
    String guildId, nickName, guild_userId;
    LoginData user;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_sociaty_members);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        guild_userId = getIntent().getStringExtra("guild_userId");
        user = DBManager.getInstance(mContext).getUserMessage();
        topTitle.setText("公会成员");
        topLeftBack.setVisibility(View.VISIBLE);

        if (user != null && !TextUtils.isEmpty(user.userId) && !TextUtils.isEmpty(guild_userId) && user.userId.equals(guild_userId)) {
            topRight.setText("编辑");//会长才显示
            topRight.setVisibility(View.VISIBLE);
        } else {
            topRight.setVisibility(View.GONE);
        }

        mSociatyMembersAdapter = new SociatyMembersAdapter(mContext, data);
        lv_searchSociatyMembers.setAdapter(mSociatyMembersAdapter);
        lv_searchSociatyMembers.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Bundle bundle = new Bundle();
                bundle.putString("userId", mSociatyMembersAdapter.getData().get(position).userId);
                startActivity(PersonalHomepageActivity.class, bundle);
            }
        });

    }

    @Override
    protected void initData() {

        guildId = getIntent().getStringExtra("guildId");
        nickName = getIntent().getStringExtra("nickName");
        new GetGuildUserTask(mContext, guildId, "", new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGuildUser) {
                    //ACTION_GetGuildUser
                    GetGuildUser mGetGuildUser = (GetGuildUser) object;
                    if (mGetGuildUser.data != null && mGetGuildUser.data.size() >= 0) {
                        data.clear();
                        data.addAll(mGetGuildUser.data);
                        mSociatyMembersAdapter.setData(data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetGuildUser + "_GET" + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGuildUser + "_GET" + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetGuildUser.class, json);
                    if (mObject != null && mObject instanceof GetGuildUser) {
                        GetGuildUser mData = (GetGuildUser) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            data.clear();
                            data.addAll(mData.data);
                            mSociatyMembersAdapter.setData(data);
                        }
                    }
                }

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right, R.id.LinearLayout1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.LinearLayout1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("guildId", guildId);
                startActivity(SearchSociatyMembersActivity.class, bundle1);
                break;
            case R.id.top_right:
                Bundle bundle = new Bundle();
                bundle.putString("guildId", guildId);
                bundle.putString("guild_userId", guild_userId);
                startActivity(DeleteSociatyMembersActivity.class, bundle);

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
        MobclickAgent.onPageEnd("SociatyMembersActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SociatyMembersActivity");
        super.onResume();
    }
}
