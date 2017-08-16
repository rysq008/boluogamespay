package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.game.helper.adapter.community.HotSociatyAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGuildListTask;
import com.game.helper.sdk.model.returns.GetGuildList;
import com.game.helper.sdk.model.returns.GetGuildList.GetGuildListData;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 搜索结果-公会
 * @Path com.game.helper.activity.community.SearchSociatyActivity.java
 * @Author lbb
 * @Date 2016年8月25日 上午10:31:13
 * @Company
 */
public class SearchSociatyActivity extends BaseActivity {
    /**
     * 头部-搜索
     */
    protected HotSociatyAdapter mHotCommunityAdapter;
    protected List<GetGuildListData> data = new ArrayList<GetGuildListData>();
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.lv_searchSociaty)
    ListView lv_searchSociaty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_search_sociaty);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("搜索");
        topLeftBack.setVisibility(View.VISIBLE);

        mHotCommunityAdapter = new HotSociatyAdapter(mContext, data, 1);
        lv_searchSociaty.setAdapter(mHotCommunityAdapter);

        lv_searchSociaty.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                startActivity(SociatyDetailsActivity.class);
            }
        });
        ed_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.top_left_layout1, R.id.tv_search1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout1:
                finish1();
                break;
            case R.id.tv_search1:
                //搜索
                String keyword = ed_search.getText().toString();
                if (!TextUtils.isEmpty(keyword)) {
                    new GetGuildListTask(mContext, true, keyword, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            // TODO Auto-generated method stub
                            if (object != null && object instanceof GetGuildList) {
                                GetGuildList mGetGuildList = (GetGuildList) object;
                                if (mGetGuildList.data != null && mGetGuildList.data.size() >= 0) {
                                    data.clear();
                                    data.addAll(mGetGuildList.data);
                                    mHotCommunityAdapter.setData(data);
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
        MobclickAgent.onPageEnd("SearchSociatyActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SearchSociatyActivity");
        super.onResume();
    }
}
