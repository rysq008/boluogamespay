package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.home.GameHistorySearchAdapter;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.model.home.SearchMsg;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 充值-选择游戏（充值）  不用该页面
 * @Path com.game.helper.activity.home.RechargeSelectGamesActivity.java
 * @Author lbb
 * @Date 2016年8月24日 上午11:08:05
 * @Company
 */
public class RechargeSelectGamesActivity extends BaseActivity {
    /**
     * 头部-搜索
     */
    @BindView(R.id.top_left_layout1)
    LinearLayout top_left_layout1;
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.tv_search1)
    TextView tv_search;

    @BindView(R.id.textView1)
    TextView textView1;//清空
    @BindView(R.id.lv_searchhistory)
    ListViewForScrollView lv_searchhistory;//历史搜索
    @BindView(R.id.lv_searchgame)
    ListViewForScrollView lv_searchgame;//我的游戏
    @BindView(R.id.lv_searchResult)
    ListViewForScrollView lv_searchResult;//搜索结果
    @BindView(R.id.mLinearLayout)
    LinearLayout mLinearLayout;

    protected GameHistorySearchAdapter mGameHistorySearchAdapter;
    protected List<SearchMsg> mSearchList = new ArrayList<SearchMsg>();
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    private List<AppContent> mList = new ArrayList<AppContent>();
    MineGameAdapter mMineGameAdapter;//我的游戏

    MineGameAdapter mMineGameAdapter1;//搜索结果
    private List<AppContent> mList1 = new ArrayList<AppContent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_recharge_select_games);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("选择游戏");
        topLeftBack.setVisibility(View.VISIBLE);
        lv_searchhistory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                key = 1;
                setViews();
            }
        });
        lv_searchgame.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                finish1();
            }
        });
        lv_searchResult.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                finish1();
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
                if (s.length() > 0) {
                    key = 1;
                } else {
                    key = 0;
                }

            }
        });
    }

    int key = 0;

    @Override
    protected void initData() {

        mGameHistorySearchAdapter = new GameHistorySearchAdapter(mContext, mSearchList);
        lv_searchhistory.setAdapter(mGameHistorySearchAdapter);

        mMineGameAdapter = new MineGameAdapter(mContext, mList, 2, R.id.lv_searchgame);
        lv_searchgame.setAdapter(mMineGameAdapter);

        mMineGameAdapter1 = new MineGameAdapter(mContext, mList1, 2, R.id.lv_searchResult);
        lv_searchResult.setAdapter(mMineGameAdapter1);

        setViews();
    }

    public void setViews() {
        if (1 == key) {
            mLinearLayout.setVisibility(View.GONE);
            lv_searchResult.setVisibility(View.VISIBLE);
        } else {
            mLinearLayout.setVisibility(View.VISIBLE);
            lv_searchResult.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick({R.id.top_left_layout1, R.id.textView1, R.id.tv_search1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout1:
                finish1();
                break;
            case R.id.textView1:

                //清空
                break;
            case R.id.tv_search1:
                //搜索
                setViews();
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
        MobclickAgent.onPageEnd("RechargeSelectGamesActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("RechargeSelectGamesActivity");
        super.onResume();
    }
}