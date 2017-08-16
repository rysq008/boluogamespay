package com.game.helper.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.RankingListGameAdapter;
import com.game.helper.adapter.home.SpecialgameAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.task.QueryGameBykindAndTypeTask;
import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class GameClassifyBtGameFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    private static List<AppContent> mmSpecialgameAdapter2List = new ArrayList<AppContent>();
    @BindView(R.id.game_classify_refresh_view_btgame)
    PullToRefreshLayout gameClassifyRefreshViewBtgame;
    @BindView(R.id.home_fragment_pagelist_btgame_lv)
    ListView homeFragmentPagelistBtgameLv;
    @BindView(R.id.nodate_img_rl)
    RelativeLayout nodateImgRl;
    private SpecialgameAdapter mSpecialgameAdapter;
    int page = 0;
   // private RankingListGameAdapter mRankingListGameAdapter;

    public GameClassifyBtGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_list_btgame, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static final GameClassifyBtGameFragment newInstance() {
        GameClassifyBtGameFragment listfragment = new GameClassifyBtGameFragment();
        Bundle args = new Bundle();

        listfragment.setArguments(args);
        return listfragment;
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
        gameClassifyRefreshViewBtgame.setOnRefreshListener(this);

        mSpecialgameAdapter = new SpecialgameAdapter(getActivity(),
                mmSpecialgameAdapter2List, 2, R.id.home_fragment_pagelist_btgame_lv);
        homeFragmentPagelistBtgameLv.setAdapter(mSpecialgameAdapter);

        /*mRankingListGameAdapter = new RankingListGameAdapter(getActivity(), mmSpecialgameAdapter2List, R.id.home_fragment_pagelist_btgame_lv);
        homeFragmentPagelistBtgameLv.setAdapter(mRankingListGameAdapter);*/


        if (null != mmSpecialgameAdapter2List && mmSpecialgameAdapter2List.size() > 0) {
            mmSpecialgameAdapter2List.clear();
        }

        getBtGame();
    }

    private void getBtGame() {
        new QueryGameBykindAndTypeTask(getActivity(), "4", "1", "field1", page + "", new BaseBBXTask.Back() {
            @Override
            public void success(Object object, String msg) {
                gameClassifyRefreshViewBtgame.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (object != null && object instanceof QueryGameBykindAndType) {
                    QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                    if (mData.data != null && mData.data.size() >= 0) {
                        nodateImgRl.setVisibility(View.GONE);
                        gameClassifyRefreshViewBtgame.setVisibility(View.VISIBLE);
                        mmSpecialgameAdapter2List.addAll(mData.data);
                        mSpecialgameAdapter.setmList(mmSpecialgameAdapter2List);
                        Util.setHeight(mmSpecialgameAdapter2List.size(), homeFragmentPagelistBtgameLv, getActivity());
                        //mRankingListGameAdapter.setmList(mmSpecialgameAdapter2List);
                        page++;

                        if (mData.data.size() < 9) {
                            ToastUtil.showToast(getActivity(), "已经到底了");
                        }
                    }

                    if (mmSpecialgameAdapter2List.size() <= 0) {
                        nodateImgRl.setVisibility(View.VISIBLE);
                        gameClassifyRefreshViewBtgame.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                gameClassifyRefreshViewBtgame.refreshFinish(PullToRefreshLayout.FAIL);
                ToastUtil.showToast(getActivity(), msg);
            }
        }).start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mSpecialgameAdapter.getData()) {
                    Log.e("lbb", "--------onEventMainThread71-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mSpecialgameAdapter.getData().set(mSpecialgameAdapter.getData().indexOf(md), event);
                        mSpecialgameAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getBtGame();
    }

}
