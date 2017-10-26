package com.game.helper.fragment.gameclassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.task.QueryGameBykindAndTypeTask;
import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WebGameFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.classify_webgame_pagelist_lv)
    ListView classify_webgame_pagelist_lv;
    Unbinder unbinder;
    @BindView(R.id.game_web_refresh_view)
    PullToRefreshLayout gameWebRefreshView;
    private ArrayList<AppContent> webgamelist = new ArrayList<AppContent>();
    String typeId = "3";
    String kindId = "";//全部分类
    String orderby = "field1";//默认排序
    int offset = 0;//分页
    private MineGameAdapter mineGameAdapter;

    public WebGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static WebGameFragment newInstance() {

        Bundle args = new Bundle();

        WebGameFragment fragment = new WebGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gameclassify_web_page_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void init() {

    }

    private void initView() {
        gameWebRefreshView.setOnRefreshListener(this);
        mineGameAdapter = new MineGameAdapter(getActivity(),
                webgamelist, 2, R.id.classify_webgame_pagelist_lv);
        classify_webgame_pagelist_lv.setAdapter(mineGameAdapter);
        webgamelist.clear();
        getWebGameList();


    }

    private void getWebGameList() {
        new QueryGameBykindAndTypeTask(getActivity(), typeId, kindId, orderby, offset + "", new BaseBBXTask.Back() {
            @Override
            public void success(Object object, String msg) {
                if (null != gameWebRefreshView) {
                    gameWebRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                if (object != null && object instanceof QueryGameBykindAndType) {
                    QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                    if (mData.data != null && mData.data.size() >= 0) {
                        webgamelist.addAll(mData.data);
                        mineGameAdapter.setmList(webgamelist);
                        classify_webgame_pagelist_lv.deferNotifyDataSetChanged();
                        Util.setHeight(webgamelist.size(), classify_webgame_pagelist_lv, getActivity());
                        offset++;
                    }

                    if (mData.data != null && mData.data.size() < 9) {
                        ToastUtil.showToast(getActivity(), "已经到底了");
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                gameWebRefreshView.refreshFinish(PullToRefreshLayout.FAIL);
                ToastUtil.showToast(getActivity(), msg);
            }
        }).start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getWebGameList();
    }
}