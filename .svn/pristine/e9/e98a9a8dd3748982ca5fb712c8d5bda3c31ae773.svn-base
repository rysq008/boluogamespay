package com.game.helper.activity.home;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.GameHistorySearchAdapter;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.installPackage.MonitorSysReceiver;
import com.game.helper.model.home.SearchMsg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.MygameTask;
import com.game.helper.net.task.QueryGameByHotWordTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.Mygame;
import com.game.helper.sdk.model.returns.QueryGameByHotWord;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.game.helper.view.widget.ListViewForScrollView;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectGameActivity extends BaseActivity implements PullToRefreshLayout.OnRefreshListener {
    /**
     * 头部-搜索
     */
    @BindView(R.id.top_left_layout1)
    LinearLayout top_left_layout1;
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.tv_search1)
    TextView tv_search;

    @BindView(R.id.mLinearLayout)
    LinearLayout mLinearLayout;

    @BindView(R.id.cleanHistory)
    ImageView cleanHistory;//清空

    @BindView(R.id.search_ListView)
    ListViewForScrollView search_ListView;//历史搜索

    @BindView(R.id.minegame_listView)
    ListViewForScrollView minegame_listView;//我的游戏
    @BindView(R.id.searchGameResult_listView)
    ListViewForScrollView searchGameResult_listView;//搜索游戏的结果
    @BindView(R.id.mLinear_MineGame)
    LinearLayout mLinear_MineGame;

    GameHistorySearchAdapter mGameHistorySearchAdapter;
    protected List<SearchMsg> mSearchList = new ArrayList<SearchMsg>();

    MineGameAdapter mGameResultAdapter;
    @BindView(R.id.selectgame_refresh_view)
    PullToRefreshLayout selectgameRefreshView;
    private List<AppContent> mList = new ArrayList<AppContent>();
    private List<AppContent> mGameResultList = new ArrayList<AppContent>();
    MineGameAdapter mMineGameAdapter;
    LoginData user;
    String platId = null;

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_select_game);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        /*topTitle.setText("搜索");
		topLeftBack.setVisibility(View.VISIBLE);*/
        platId = getIntent().getStringExtra("platId");

        mGameHistorySearchAdapter = new GameHistorySearchAdapter(mContext, mSearchList);
        search_ListView.setAdapter(mGameHistorySearchAdapter);

        mMineGameAdapter = new MineGameAdapter(mContext, mList, 2, R.id.minegame_listView);
        minegame_listView.setAdapter(mMineGameAdapter);

        mGameResultAdapter = new MineGameAdapter(mContext, mGameResultList, 2, R.id.searchGameResult_listView);
        searchGameResult_listView.setAdapter(mGameResultAdapter);
        mGameResultList.clear();


        mGameResultAdapter.setmList(mGameResultList);
        search_ListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (mGameHistorySearchAdapter.getmList().size() > 0) {

                    ed_search.setText("" + mGameHistorySearchAdapter.getmList().get(arg2).msg);
                    ed_search.setSelection(ed_search.getText().length());
                }
                //setViews();
            }
        });
        minegame_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
				/*Bundle bundle=new Bundle();
				bundle.putInt("currIndex", 0);
				bundle.putString("gameId", mMineGameAdapter.getmList().get(arg2).gameId);
				startActivity(GameDetailActivity.class);*/

                //返回到充值页面
                BaseApplication.mInstance.put(KEY_SelectGame, mMineGameAdapter.getItem(arg2).getmAppContent());
                finish();
                sendBroadcast(new Intent(ACTION_SelectGame));
            }
        });
        searchGameResult_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
				/*Bundle bundle=new Bundle();
				bundle.putInt("currIndex", 0);
				bundle.putString("gameId", mGameResultAdapter.getmList().get(arg2).gameId);
				startActivity(GameDetailActivity.class);*/

                String msg = ed_search.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    DBManager.getInstance(mContext).insert(user.userId, msg, 0);

                }
                //返回到充值页面
                BaseApplication.mInstance.put(KEY_SelectGame, mGameResultAdapter.getItem(arg2).getmAppContent());
                finish();
                sendBroadcast(new Intent(ACTION_SelectGame));
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
                setViews();

            }
        });
        user = DBManager.getInstance(mContext).getUserMessage();
        key = 0;
    }

    int key = 0;//0：显示历史搜索记录和我的游戏，1：显示搜索结果列表

    @Override
    protected void initData() {

        setViews();


    }

    public void setViews() {
        selectgameRefreshView.setOnRefreshListener(this);

        if (key == 0) {
            if (!TextUtils.isEmpty(platId)) {
                mLinear_MineGame.setVisibility(View.GONE);
                minegame_listView.setVisibility(View.GONE);
            } else {
                mLinear_MineGame.setVisibility(View.VISIBLE);
                minegame_listView.setVisibility(View.VISIBLE);
            }
            mLinearLayout.setVisibility(View.VISIBLE);
            searchGameResult_listView.setVisibility(View.GONE);

            mSearchList.clear();
            List<SearchMsg> ls = DBManager.getInstance(mContext).getSearchMsg(user.userId, 0);//0：代表搜索游戏，1：代表搜索礼包
            mSearchList.addAll(ls);
            mGameHistorySearchAdapter.setmList(mSearchList);

            new MygameTask(mContext, true, user.userId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof Mygame) {
                        Mygame mData = (Mygame) object;
                        if (mData.data != null && mData.data.size() >= 0) {
                            mList.clear();
                            //mList.addAll(mData.data);
                            for (AppContent mAppContent : mData.data) {
                                if (MonitorSysReceiver.checkApkExist(mContext, mAppContent.packageName, mAppContent.gameId)) {
                                    mList.add(mAppContent);
                                }
                            }
                            mMineGameAdapter.setmList(mList);

                            SharedPreUtil.putStringValue(mContext, ACTION_Mygame + "_" + user.userId, new JsonBuild().setModel(object).getJson1());
                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
        } else {
            mLinearLayout.setVisibility(View.GONE);
            searchGameResult_listView.setVisibility(View.VISIBLE);
            mGameResultList.clear();
            searchGame(0);
        }


    }

    @Override
    @OnClick({R.id.top_left_layout1, R.id.tv_search1, R.id.cleanHistory})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout1:
                finish1();
                break;
            case R.id.cleanHistory:
                //清空
                DBManager.getInstance(mContext).deletedAllSearch();

                mSearchList.clear();
                List<SearchMsg> ls = DBManager.getInstance(mContext).getSearchMsg(user.userId, 0);
                mSearchList.addAll(ls);
                mGameHistorySearchAdapter.setmList(mSearchList);
                break;
            case R.id.tv_search1:
                //搜索
                String msg = ed_search.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    DBManager.getInstance(mContext).insert(user.userId, msg, 0);
                    key = 1;
                    setViews();
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
        //DownLoadManager.getManager().stopAllTask();
        MobclickAgent.onPageEnd("SelectGameActivity");
        super.onPause();
    }

    int i = 0;

    @Override
    protected void onResume() {
		/*if(i==0){
			i=1;
		}else{
			initData();
		}*/
        MobclickAgent.onPageStart("SelectGameActivity");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        searchGame(page);
    }

    private void searchGame(int offset) {

        new QueryGameByHotWordTask(mContext, ed_search.getText().toString(), platId, offset + "", new Back() {

            @Override
            public void success(Object object, String msg) {
                selectgameRefreshView.refreshFinish((PullToRefreshLayout.SUCCEED));
                if (object != null && object instanceof QueryGameByHotWord) {
                    QueryGameByHotWord mQueryHotWord = (QueryGameByHotWord) object;
                    if (mQueryHotWord.data != null && mQueryHotWord.data.size() > 0) {
                        mGameResultList.addAll(mQueryHotWord.data);
                        mGameResultAdapter.setmList(mGameResultList);
                        searchGameResult_listView.deferNotifyDataSetChanged();
                        Util.setHeight(mList.size(), searchGameResult_listView,SelectGameActivity.this );

                        page++;
                    }
                    if (mQueryHotWord.data != null && mQueryHotWord.data.size() < 9) {
                        ToastUtil.showToast(SelectGameActivity.this, "已经到底了");
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                selectgameRefreshView.refreshFinish((PullToRefreshLayout.SUCCEED));
                ToastUtil.showToast(SelectGameActivity.this, msg);
            }
        }).start();
    }

}
