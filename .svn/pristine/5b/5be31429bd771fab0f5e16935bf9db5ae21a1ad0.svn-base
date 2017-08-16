package com.game.helper.activity.community;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylistview.pullListView.PullToRefreshLayout;
import com.example.mylistview.pullListView.PullToRefreshLayout.OnRefreshListener;
import com.example.mylistview.pullListView.PullableListView;
import com.example.mylistview.pullListView.PullableListView.OnLoadListener;
import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.PersonalDynamicAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetSelfDynamicTask;
import com.game.helper.sdk.model.returns.GetSelfDynamic;
import com.game.helper.sdk.model.returns.GetSelfDynamic.Dynamic;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 个人动态列表
 * @Path com.game.helper.activity.community.PersonalDynamicActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午5:47:50
 * @Company
 */
public class PersonalDynamicActivity extends BaseActivity implements OnLoadListener, OnRefreshListener {
    /*@BindView(R.id.MyListView) PullableListView mMyListView;
    @BindView(R.id.pullToRefreshLayout) PullToRefreshLayout pullToRefreshLayout;
    */
    PersonalDynamicAdapter mPersonalDynamicAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.MyListView)
    PullableListView mMyListView;
    @BindView(R.id.pullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    private List<Dynamic> list = new ArrayList<Dynamic>();
    private int page = 0;//页码 没传默认为第1页
    private int type = 0;//0：刷新，1：加载更多

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_personal_dynamic);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("个人动态");
        topLeftBack.setVisibility(View.VISIBLE);

        topRight.setText("个人详情页");
        topRight.setVisibility(View.VISIBLE);

        pullToRefreshLayout.setOnRefreshListener(this);
        mMyListView.setDividerHeight(0);
        mMyListView.setOnLoadListener(this);
        mMyListView.hideStateTextView(true);
        mMyListView.setBackgroundLoadingRelative(android.R.color.transparent);

        mPersonalDynamicAdapter = new PersonalDynamicAdapter(mContext, list);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerview = inflater.inflate(R.layout.headerview, null);
        //【主要，添加headerview，则在OnItenClick中的Position就会错乱】
        mMyListView.addHeaderView(headerview, null, false);
        mMyListView.setAdapter(mPersonalDynamicAdapter);
        mMyListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 【修正有Header的ListView的position的BUG】
                int headerViewsCount = mMyListView.getHeaderViewsCount();//得到header的总数量
                //【得到新的修正后的position】
                int newPosition = position - headerViewsCount;

                startActivity(DynamicDetailsActivity.class);

            }
        });
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");

        type = 0;
        page = 0;
        getRefresh();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                startActivity(PersonalHomepageActivity.class, bundle);
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

    public void getRefresh() {
        new GetSelfDynamicTask(mContext, userId, "" + page, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetSelfDynamic) {
                    GetSelfDynamic mGetSelfDynamic = (GetSelfDynamic) object;
                    if (mGetSelfDynamic.data != null && mGetSelfDynamic.data.list != null) {
                        if (type == 0) {// 刷新
                            if (mGetSelfDynamic.data.list.size() >= 0) {
                                list.clear();
                                list.addAll(mGetSelfDynamic.data.list);
                                page++;
                            }
                            if (mGetSelfDynamic.data.list.size() < 10) {
                                mMyListView.setHasMoreData(false);
                                mMyListView.hideStateTextView(true);
                            } else {
                                mMyListView.setHasMoreData(true);
                            }
                            mPersonalDynamicAdapter.setList(list);
                        } else {
                            if (mGetSelfDynamic.data.list.size() >= 0) {
                                list.addAll(mGetSelfDynamic.data.list);
                                page++;
                            }
                            if (mGetSelfDynamic.data.list.size() < 10) {
                                ToastUtil.showToast(mContext, "已经到底了");
                                mMyListView.setHasMoreData(false);
                                mMyListView.hideStateTextView(true);
                            } else {
                                mMyListView.setHasMoreData(true);
                            }
                            mPersonalDynamicAdapter.setList(list);
                        }
                    }
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                if (list.size() == 0) {
                    ToastUtil.showToast(mContext, "暂无数据");
                    mMyListView.hideStateTextView(true);
                    mMyListView.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {

                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                if (type == 0) {// 刷新
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }

            }
        }).start();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // TODO Auto-generated method stub
        type = 0;
        page = 0;
        getRefresh();
    }

    @Override
    public void onLoad(PullableListView pullableListView) {
        type = 1;
        getRefresh();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("PersonalDynamicActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("PersonalDynamicActivity");
        super.onResume();
    }

}
