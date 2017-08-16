package com.game.helper.fragment.gameclassify;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.adapter.home.SortingOneAdapter;
import com.game.helper.adapter.home.SortingTwoAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.model.home.GameFrom;
import com.game.helper.model.home.Kind;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.task.GameFromTask;
import com.game.helper.net.task.QueryGameByPlatid;
import com.game.helper.net.task.QueryGameBykindAndTypeTask;
import com.game.helper.sdk.model.returns.Cgamekindlist;
import com.game.helper.sdk.model.returns.GameFromList;
import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class GameClassifyDiscountFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.game_classify_all_tv)
    TextView gameClassifyAllTv;
    @BindView(R.id.game_classify_discount_tv)
    TextView gameClassifyDiscountTv;
    @BindView(R.id.game_classify_discount_iv)
    ImageView gameClassifyDiscountIv;
    @BindView(R.id.game_classify_discount_ll)
    LinearLayout gameClassifyDiscountLl;
    @BindView(R.id.game_classify_from_tv)
    TextView gameClassifyFromTv;
    @BindView(R.id.game_classify_from_iv)
    ImageView gameClassifyFromIv;
    @BindView(R.id.game_classify_from_ll)
    LinearLayout gameClassifyFromLl;
    @BindView(R.id.game_classify_type_tv)
    TextView gameClassifyTypeTv;
    @BindView(R.id.game_classify_type_iv)
    ImageView gameClassifyTypeIv;
    @BindView(R.id.game_classify_type_ll)
    LinearLayout gameClassifyTypeLl;
    @BindView(R.id.ll_sortingdirectory)
    LinearLayout llSortingdirectory;
    @BindView(R.id.home_fragment_pagelist_newgame_lv)
    ListView homeFragmentPagelistNewgameLv;
    @BindView(R.id.ll_sortings)
    LinearLayout llSortings;
    @BindView(R.id.classify_all_client_lv)
    ListView classifyAllClientLv;
    @BindView(R.id.classify_all_type_lv)
    ListView classifyAllTypeLv;
    @BindView(R.id.game_classify_refresh_view)
    PullToRefreshLayout gameClassifyRefreshView;
    // private ArrayList<AppContent> mmSpecialgameAdapter2List = new ArrayList<AppContent>();
    private ArrayList<AppContent> mList = new ArrayList<AppContent>();
    private ArrayList<AppContent> mAllList = new ArrayList<AppContent>();
    private MineGameAdapter mMineGameAdapter;
    private SortingOneAdapter mSortingOneAdapter;
    private ArrayList<Kind> types = new ArrayList<Kind>(); //游戏分类
    private ArrayList<GameFrom> clientlist = new ArrayList<GameFrom>(); //遊戲平台
    Boolean type_click = false;
    Boolean client_click = false;
    private SortingTwoAdapter sortingTwoAdapter;
    int page=0;
    private String platId;
    private String kindId="";

    public GameClassifyDiscountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    public static GameClassifyDiscountFragment newInstance() {

        Bundle args = new Bundle();

        GameClassifyDiscountFragment fragment = new GameClassifyDiscountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_classify_discount_fragment_page_list, container, false);
        ButterKnife.bind(this, view);
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
        gameClassifyRefreshView.setOnRefreshListener(this);

        gameClassifyAllTv.setSelected(true);

        getQueryGameBykindAndTypeTask();

        //全部
        gameClassifyAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLvGone();
                gameClassifyFromTv.setSelected(false);
                gameClassifyDiscountTv.setSelected(false);
                gameClassifyAllTv.setSelected(true);
                gameClassifyTypeTv.setSelected(false);
                gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                type_click = false;
                client_click=false;
                gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                getQueryGameBykindAndTypeTask();
            }
        });

        //折扣
        gameClassifyDiscountLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLvGone();
                gameClassifyFromTv.setSelected(false);
                gameClassifyDiscountTv.setSelected(true);
                gameClassifyAllTv.setSelected(false);
                gameClassifyTypeTv.setSelected(false);
                Collections.reverse(mList);
                gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                type_click = false;
                client_click=false;
                gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.down));

                mMineGameAdapter = new MineGameAdapter(getActivity(), mList, 1, R.id.listView_gameClassify);
                homeFragmentPagelistNewgameLv.setAdapter(mMineGameAdapter);
            }
        });

        //客户端
        gameClassifyFromLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameClassifyFromTv.setSelected(true);
                gameClassifyDiscountTv.setSelected(false);
                gameClassifyAllTv.setSelected(false);
                gameClassifyTypeTv.setSelected(false);
                gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                type_click=false;

                if (client_click) {
                    typeLvGone();
                    gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                    client_click = false;
                } else {
                    llSortings.setVisibility(View.VISIBLE);
                    classifyAllClientLv.setVisibility(View.VISIBLE);
                    classifyAllTypeLv.setVisibility(View.GONE);
                    gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.up));
                    gameClassifyFromTv.setSelected(true);
                    client_click = true;
                }

            }
        });

        //类型
        gameClassifyTypeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameClassifyFromTv.setSelected(false);
                gameClassifyDiscountTv.setSelected(false);
                gameClassifyAllTv.setSelected(false);
                gameClassifyTypeTv.setSelected(true);
                gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                client_click = false;

                if (type_click) {
                    gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                    typeLvGone();
                    type_click = false;
                } else {
                    classifyAllTypeLv.setVisibility(View.VISIBLE);
                    llSortings.setVisibility(View.VISIBLE);
                    classifyAllClientLv.setVisibility(View.GONE);
                    gameClassifyTypeTv.setSelected(true);
                    gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.up));
                    type_click = true;
                }
            }
        });



        mMineGameAdapter = new MineGameAdapter(getActivity(), mList, 1, R.id.listView_gameClassify);
        homeFragmentPagelistNewgameLv.setAdapter(mMineGameAdapter);


        mSortingOneAdapter = new SortingOneAdapter(getActivity(), types);
        classifyAllTypeLv.setAdapter(mSortingOneAdapter);
        classifyAllTypeLv.setOnItemClickListener(new OneOnItemClick());

        sortingTwoAdapter = new SortingTwoAdapter(getActivity(), clientlist);
        classifyAllClientLv.setAdapter(sortingTwoAdapter);
        classifyAllClientLv.setOnItemClickListener(new OneOnItemClickClient());

        //游戏分类
        String type_json = SharedPreUtil.getStringValue(mContext, ACTION_Cgamekindlist, "");
        if (!TextUtils.isEmpty(type_json)) {
            Object mObject = new JsonBuild().getData(Cgamekindlist.class, type_json);
            if (mObject != null && mObject instanceof Cgamekindlist) {
                Cgamekindlist mData = (Cgamekindlist) mObject;
                if (mData.data != null && mData.data.size() >= 0) {
                    types.clear();
                    Kind mKind = new Kind();
                    mKind.kindId = "";
                    mKind.kindName = "全部分类";
                    types.add(mKind);
                    types.addAll(mData.data);
                    mSortingOneAdapter.setmDatas(types);

                }
            }
        }


        //遊戲客戶端
        new GameFromTask(getActivity(), new BaseBBXTask.Back() {
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GameFromList) {
                    GameFromList gameFromList = (GameFromList) object;
                    if (gameFromList.data != null && gameFromList.data.size() > 0) {
                        clientlist.clear();
                        GameFrom gameFrom = new GameFrom();
                        gameFrom.field2 = "";
                        gameFrom.platId = "";
                        gameFrom.platName = "全部";
                        clientlist.add(gameFrom);
                        clientlist.addAll(gameFromList.data);
                        sortingTwoAdapter.setmDatas(clientlist);

                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                //Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                ToastUtil.showToast(getActivity(), msg);
            }
        }).start();

     /*   gameclassifyPulltorefresh.setOnRefreshListener(new com.game.helper.view.PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(com.game.helper.view.PullToRefreshLayout pullToRefreshLayout) {
                System.out.println("refresh---------");
            }

            @Override
            public void onLoadMore(com.game.helper.view.PullToRefreshLayout pullToRefreshLayout) {
                System.out.println("more---------");
            }
        });*/
    }

    private void typeLvGone() {
        classifyAllTypeLv.setVisibility(View.GONE);
        classifyAllClientLv.setVisibility(View.GONE);
        llSortings.setVisibility(View.GONE);
    }

    /**
     * 全部游戏列表
     */
    private void getQueryGameBykindAndTypeTask() {
        page=0;
        mAllList.clear();
        if (mAllList.size() > 0) {
            mList = mAllList;
        } else {
            initData("1", "", "field1",page);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

/*
    String typeId = "3";
    String kindId = "";//全部分类
    String orderby = "field1";//默认排序
*/

    /**
     * @param typeId  类型Id 1:网游； 2：单机； 3：H5游戏
     * @param kindId  类别Id
     * @param orderby 排序字段（field1：排序值；field2：上线时间；field3：浏览量；field4：下载量）
     */
    public void initData(String typeId, String kindId, String orderby, int offset) {

        //获取网游，单机游戏
        new QueryGameBykindAndTypeTask(getActivity(), typeId, kindId, orderby, offset+"", new BaseBBXTask.Back() {

            @Override
            public void success(Object object, String msg) {
                gameClassifyRefreshView.refreshFinish((PullToRefreshLayout.SUCCEED));
                if (object != null && object instanceof QueryGameBykindAndType) {
                    QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                    if (mData.data != null && mData.data.size() >= 0) {
                        mAllList.addAll(mData.data);
                        // initStatus1();
                        mMineGameAdapter.setmList(mAllList);
                        mList = mAllList;
                        homeFragmentPagelistNewgameLv.deferNotifyDataSetChanged();
                        Util.setHeight(mList.size(), homeFragmentPagelistNewgameLv, getActivity());
                        page++;
                    }
                    if (mData.data != null && mData.data.size() <9) {
                        ToastUtil.showToast(getActivity(), "已经到底了");
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                gameClassifyRefreshView.refreshFinish((PullToRefreshLayout.FAIL));
                ToastUtil.showToast(getActivity(), msg);
            }
        }).start();

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (gameClassifyAllTv.isSelected() || gameClassifyTypeTv.isSelected()){
            initData("1", kindId, "field1",page);
        }else if(gameClassifyFromTv.isSelected()){
            if (!TextUtils.isEmpty(platId)) {
                QueryGameByPlatId(platId, page);
            }
        }
    }

    //根据客户端获取游戏列表
    private void QueryGameByPlatId(String platId, int offset) {
            new QueryGameByPlatid(getActivity(), platId, offset + "", new BaseBBXTask.Back() {

                @Override
                public void success(Object object, String msg) {
                    gameClassifyRefreshView.refreshFinish((PullToRefreshLayout.SUCCEED));
                    if (object != null && object instanceof QueryGameBykindAndType) {
                        QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                        if (mData.data != null && mData.data.size() >= 0) {
                            mAllList.addAll(mData.data);
                            mMineGameAdapter.setmList(mAllList);
                            mList = mAllList;
                            homeFragmentPagelistNewgameLv.deferNotifyDataSetChanged();
                            Util.setHeight(mList.size(), homeFragmentPagelistNewgameLv, getActivity());
                            page++;
                        }
                        if (mData.data != null && mData.data.size() < 9) {
                            ToastUtil.showToast(getActivity(), "已经到底了");
                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    gameClassifyRefreshView.refreshFinish((PullToRefreshLayout.SUCCEED));
                    ToastUtil.showToast(getActivity(), msg);
                }
            }).start();
    }

    //排序 客户端 的点击事件
    class OneOnItemClickClient implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
            GameFrom gameFrom = (GameFrom) sortingTwoAdapter.getmDatas().get(arg2);
            platId = gameFrom.platId;
            llSortings.setVisibility(View.GONE);
            classifyAllClientLv.setVisibility(View.GONE);
            client_click=false;
            gameClassifyFromTv.setText(gameFrom.platName);
            sortingTwoAdapter.setpos(arg2);
            sortingTwoAdapter.notifyDataSetChanged();
            //initData("1",kindId,"field1");
            page=0;
            mAllList.clear();
            gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
            if (TextUtils.isEmpty(platId)) {
                typeLvGone();
                gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                type_click = false;
                client_click=false;
                gameClassifyFromIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
                getQueryGameBykindAndTypeTask();
            }else{
                QueryGameByPlatId(platId, page);

            }
        }
    }

    //排序类型的点击事件
    class OneOnItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
            Kind mKind = (Kind) mSortingOneAdapter.getmDatas().get(arg2);
            kindId = mKind.kindId;
            llSortings.setVisibility(View.GONE);
            classifyAllTypeLv.setVisibility(View.GONE);
            gameClassifyTypeTv.setText(mKind.kindName);
            mSortingOneAdapter.setpos(arg2);
            mSortingOneAdapter.notifyDataSetChanged();
            page=0;
            mAllList.clear();
            gameClassifyTypeIv.setImageDrawable(getResources().getDrawable(R.drawable.down));
            type_click=false;
            initData("1", kindId, "field1", page);
        }
    }
    int count = 0;
    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mMineGameAdapter.getmList()) {
                    Log.e("lbb", "--------onEventMainThread71-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mMineGameAdapter.getmList().set(mMineGameAdapter.getmList().indexOf(md), event);
                        mMineGameAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
