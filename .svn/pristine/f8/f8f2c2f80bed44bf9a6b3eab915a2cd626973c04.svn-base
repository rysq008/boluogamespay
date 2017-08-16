package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.SearchSociatyMembersAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGuildUserTask;
import com.game.helper.sdk.model.returns.GetGuildUser;
import com.game.helper.sdk.model.returns.GetGuildUser.GetGuildUserData;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 搜索公会成员
 * @Path com.game.helper.activity.community.SearchSociatyMembersActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午3:50:27
 * @Company
 */
public class SearchSociatyMembersActivity extends BaseActivity {
    /**
     * 头部-搜索
     */
    /*@BindView(R.id.ed_search) EditText ed_search;
	@BindView(R.id.lv_searchSociatyMember) ListView lv_searchSociatyMember;*/
    SearchSociatyMembersAdapter mSearchSociatyMembersAdapter;
    List<GetGuildUserData> data = new ArrayList<GetGuildUserData>();
    String guildId;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.lv_searchSociatyMember)
    ListView lv_searchSociatyMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_search_sociaty_members);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("搜索公会成员");
        topLeftBack.setVisibility(View.VISIBLE);

        mSearchSociatyMembersAdapter = new SearchSociatyMembersAdapter(mContext, data);
        lv_searchSociatyMember.setAdapter(mSearchSociatyMembersAdapter);
        lv_searchSociatyMember.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mSearchSociatyMembersAdapter.getData().get(position).userId);
                startActivity(PersonalHomepageActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        guildId = getIntent().getStringExtra("guildId");
    }

    @Override
    @OnClick({R.id.top_left_layout1, R.id.tv_search1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout1:
                finish1();
                break;
            case R.id.tv_search1:
                String nickname = ed_search.getText().toString();
                if (!TextUtils.isEmpty(nickname)) {
                    new GetGuildUserTask(mContext, guildId, nickname, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof GetGuildUser) {
                                //ACTION_GetGuildUser
                                GetGuildUser mGetGuildUser = (GetGuildUser) object;
                                if (mGetGuildUser.data != null && mGetGuildUser.data.size() >= 0) {
                                    data.clear();
                                    data.addAll(mGetGuildUser.data);
                                    mSearchSociatyMembersAdapter.setData(data);
                                }
                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
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
        MobclickAgent.onPageEnd("SearchSociatyMembersActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SearchSociatyMembersActivity");
        super.onResume();
    }
}
