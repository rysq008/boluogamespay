package com.game.helper.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.adapter.home.RankingListGameAdapter;
import com.game.helper.adapter.home.RankingListPlayerAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.leopardkit.DownLoadRankingModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.ConnectUserTask;
import com.game.helper.net.task.GetHotGameTask;
import com.game.helper.net.task.GetNetGameTask;
import com.game.helper.net.task.GetNoNetGameTask;
import com.game.helper.net.task.IncomeUserTask;
import com.game.helper.net.task.QueryGameByModularTask;
import com.game.helper.net.task.StarUserTask;
import com.game.helper.sdk.model.returns.ConnectUser;
import com.game.helper.sdk.model.returns.GetHotGame;
import com.game.helper.sdk.model.returns.GetNetGame;
import com.game.helper.sdk.model.returns.GetNoNetGame;
import com.game.helper.sdk.model.returns.IncomeUser;
import com.game.helper.sdk.model.returns.StarUser;
import com.game.helper.sdk.model.returns.StarUser.Star;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @Description 排行榜
 * @Path com.game.helper.activity.home.RankingListActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午2:31:33
 * @Company
 */
public class RankingListActivity extends BaseActivity implements OnRefreshListener {
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refresh_view;
    //@InjectView(R.id.mScrollView) ScrollView mScrollView;

    @BindView(R.id.tv_rankinglist_hot)
    TextView tv_rankinglist_hot;
    @BindView(R.id.tv_rankinglist_onlinegame)
    TextView tv_rankinglist_onlinegame;
    @BindView(R.id.tv_rankinglist_standalone)
    TextView tv_rankinglist_standalone;
    @BindView(R.id.tv_rankinglist_starplayer)
    TextView tv_rankinglist_starplayer;
    @BindView(R.id.tv_rankinglist_connectionplayer)
    TextView tv_rankinglist_connectionplayer;
    @BindView(R.id.tv_rankinglist_lucre_player)
    TextView tv_rankinglist_lucre_player;

    @BindView(R.id.rankinglist_listView_game)
    ListView rankinglist_listView_game;
    @BindView(R.id.rankinglist_listView_player)
    ListView rankinglist_listView_player;
    RankingListGameAdapter mRankingListGameAdapter;
    RankingListPlayerAdapter mRankingListPlayerAdapter;
    public List<Star> data = new ArrayList<Star>();
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    private List<AppContent> mList = new ArrayList<AppContent>();

    int num = 0;
    String gameId1;
    String gameId2;
    String gameId3;

    String userId1;
    String userId2;
    String userId3;

    private int page = 0;//页码 没传默认为第1页
    private int type = 0;//0：刷新，1：加载更多
    String refreshDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_ranking_list);
        ButterKnife.bind(this);
        // Register
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        refresh_view.setOnRefreshListener(this);

        topTitle.setText("排行榜");
        topLeftBack.setVisibility(View.VISIBLE);

        //mRelative_RankingList1.setVisibility(View.GONE);
        //mRelative_RankingList2.setVisibility(View.GONE);
        rankinglist_listView_game.setVisibility(View.GONE);
        rankinglist_listView_player.setVisibility(View.GONE);

        mRankingListGameAdapter = new RankingListGameAdapter(mContext, mList, R.id.rankinglist_listView_game);
        rankinglist_listView_game.setAdapter(mRankingListGameAdapter);

        mRankingListPlayerAdapter = new RankingListPlayerAdapter(mContext, data);
        rankinglist_listView_player.setAdapter(mRankingListPlayerAdapter);


        tv_rankinglist_hot.setSelected(true);
        tv_rankinglist_onlinegame.setSelected(false);
        tv_rankinglist_standalone.setSelected(false);
        tv_rankinglist_starplayer.setSelected(false);
        tv_rankinglist_connectionplayer.setSelected(false);
        tv_rankinglist_lucre_player.setSelected(false);

        serchType = 0;
        refreshDate = "";
        page = 0;
    }

    int serchType = 0;

    @Override
    protected void initData() {
        if (serchType == 0) {
            setHot();
        } else if (serchType == 1) {
            getNetGame();
        } else if (serchType == 2) {
            getNoNetGame();
        } else if (serchType == 3) {
            starUser();
        } else if (serchType == 4) {
            connectUser();
        } else if (serchType == 5) {
            incomeUser();
        }

    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_rankinglist_hot, R.id.tv_rankinglist_onlinegame
            , R.id.tv_rankinglist_standalone, R.id.tv_rankinglist_starplayer, R.id.tv_rankinglist_connectionplayer,
            R.id.tv_rankinglist_lucre_player})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_rankinglist_hot:
                tv_rankinglist_hot.setSelected(true);
                tv_rankinglist_onlinegame.setSelected(false);
                tv_rankinglist_standalone.setSelected(false);
                tv_rankinglist_starplayer.setSelected(false);
                tv_rankinglist_connectionplayer.setSelected(false);
                tv_rankinglist_lucre_player.setSelected(false);
                serchType = 0;
                refreshDate = "";
                page = 0;
                type = 0;
                setHot();
            /*mScrollView.post(new Runnable() {
                public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
                break;
            case R.id.tv_rankinglist_onlinegame:
                tv_rankinglist_hot.setSelected(false);
                tv_rankinglist_onlinegame.setSelected(true);
                tv_rankinglist_standalone.setSelected(false);
                tv_rankinglist_starplayer.setSelected(false);
                tv_rankinglist_connectionplayer.setSelected(false);
                tv_rankinglist_lucre_player.setSelected(false);
                serchType = 1;
                refreshDate = "";
                page = 0;
                type = 0;
                getNetGame();
			/*mScrollView.post(new Runnable() {
				public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
                break;
            case R.id.tv_rankinglist_standalone:
                tv_rankinglist_hot.setSelected(false);
                tv_rankinglist_onlinegame.setSelected(false);
                tv_rankinglist_standalone.setSelected(true);
                tv_rankinglist_starplayer.setSelected(false);
                tv_rankinglist_connectionplayer.setSelected(false);
                tv_rankinglist_lucre_player.setSelected(false);
                serchType = 2;
                refreshDate = "";
                page = 0;
                type = 0;
                getNoNetGame();
			/*mScrollView.post(new Runnable() {
				public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
                break;
            case R.id.tv_rankinglist_starplayer:
                tv_rankinglist_hot.setSelected(false);
                tv_rankinglist_onlinegame.setSelected(false);
                tv_rankinglist_standalone.setSelected(false);
                tv_rankinglist_starplayer.setSelected(true);
                tv_rankinglist_connectionplayer.setSelected(false);
                tv_rankinglist_lucre_player.setSelected(false);
                serchType = 3;
                refreshDate = "";
                page = 0;
                type = 0;
                starUser();


			/*mScrollView.post(new Runnable() {
				public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
                break;
            case R.id.tv_rankinglist_connectionplayer:
                tv_rankinglist_hot.setSelected(false);
                tv_rankinglist_onlinegame.setSelected(false);
                tv_rankinglist_standalone.setSelected(false);
                tv_rankinglist_starplayer.setSelected(false);
                tv_rankinglist_connectionplayer.setSelected(true);
                tv_rankinglist_lucre_player.setSelected(false);
                serchType = 4;
                refreshDate = "";
                page = 0;
                type = 0;
                connectUser();

			/*mScrollView.post(new Runnable() {
				public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
                break;
            case R.id.tv_rankinglist_lucre_player:
                tv_rankinglist_hot.setSelected(false);
                tv_rankinglist_onlinegame.setSelected(false);
                tv_rankinglist_standalone.setSelected(false);
                tv_rankinglist_starplayer.setSelected(false);
                tv_rankinglist_connectionplayer.setSelected(false);
                tv_rankinglist_lucre_player.setSelected(true);
                serchType = 5;
                refreshDate = "";
                page = 0;
                type = 0;
                incomeUser();
			/*mScrollView.post(new Runnable() {
				public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
                break;
            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    protected void onPause() {
        //DownLoadManager.getManager().stopAllTask();
        MobclickAgent.onPageEnd("RankingListActivity");
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
        MobclickAgent.onPageStart("RankingListActivity");
        super.onResume();
        if (num == 0) {
            num = 1;
			/*mScrollView.post(new Runnable() {
				public void run() {
					mScrollView.fullScroll(ScrollView.FOCUS_UP);
				}
			});*/
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

    /**
     * 单机榜
     */
    public void getNoNetGame() {
        new GetNoNetGameTask(mContext, "", page + "", new Back() {

            @SuppressWarnings("unchecked")
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetNoNetGame) {
                    GetNoNetGame mGetNoNetGame = (GetNoNetGame) object;
                    if (mGetNoNetGame.data != null) {
                        refreshDate = mGetNoNetGame.data.refreshDate;
                        rankinglist_listView_game.setVisibility(View.VISIBLE);
                        rankinglist_listView_player.setVisibility(View.GONE);
                        if (mGetNoNetGame.data.list != null) {
                            if (type == 0) {// 刷新
                                if (mGetNoNetGame.data.list.size() >= 0) {
                                    //setNoNetGamedata(mGetNoNetGame);
                                    mList = mGetNoNetGame.data.list;
                                    page++;
                                }
                            } else {
                                if (mGetNoNetGame.data.list.size() >= 0) {
                                    mList.addAll(mGetNoNetGame.data.list);
                                    page++;
                                }
                                if (mGetNoNetGame.data.list.size() < 10) {
                                    ToastUtil.showToast(mContext, "已经到底了");
                                }
                            }
                            setHeight(mList.size(), rankinglist_listView_game);
                            mRankingListGameAdapter.setmList(mList);
                        }
                    }
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                }

                if (mList.size() == 0) {
                    if (page == 1) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    refresh_view.hideStateTextView(true);
                    refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {
                    refresh_view.hideStateTextView(false);
                    refresh_view.setBackgroundLoadingRelative(R.color.gray);
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
                if (type == 0) {// 刷新
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                // mRelative_RankingList1.setVisibility(View.GONE);
                //mRelative_RankingList2.setVisibility(View.GONE);
                rankinglist_listView_game.setVisibility(View.GONE);
                rankinglist_listView_player.setVisibility(View.GONE);

            }
        }).start();
    }

    /**
     * 热门榜
     */
    public void setHot() {
        new GetHotGameTask(mContext, refreshDate, page + "", new Back() {

            @SuppressWarnings("unchecked")
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetHotGame) {
                    GetHotGame mGetNoNetGame = (GetHotGame) object;
                    if (mGetNoNetGame.data != null) {
                        refreshDate = mGetNoNetGame.data.refreshDate;

                        if (mGetNoNetGame.data.list != null) {
                            rankinglist_listView_player.setVisibility(View.GONE);
                            rankinglist_listView_game.setVisibility(View.VISIBLE);
                            if (type == 0) {// 刷新
                                if (mGetNoNetGame.data.list.size() >= 0) {
                                    //setHotData(mGetNoNetGame);
                                    mList = mGetNoNetGame.data.list;
                                    page++;
                                }
                            } else {
                                if (mGetNoNetGame.data.list.size() >= 0) {
                                    mList.addAll(mGetNoNetGame.data.list);
                                    page++;
                                }
                                if (mGetNoNetGame.data.list.size() < 10) {
                                    ToastUtil.showToast(mContext, "已经到底了");
                                }
                            }
                            setHeight(mList.size(), rankinglist_listView_game);
                            mRankingListGameAdapter.setmList(mList);
                        }

                    }
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                }

                if (mList.size() == 0) {
                    if (type == 1) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    refresh_view.hideStateTextView(true);
                    refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);
                } else {
                    refresh_view.hideStateTextView(false);
                    refresh_view.setBackgroundLoadingRelative(R.color.gray);
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
                if (type == 0) {// 刷新
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                //mRelative_RankingList1.setVisibility(View.GONE);
                //mRelative_RankingList2.setVisibility(View.GONE);
                rankinglist_listView_game.setVisibility(View.GONE);
                rankinglist_listView_player.setVisibility(View.GONE);

            }
        }).start();
    }

    /**
     * 网游榜
     */
    public void getNetGame() {
        new GetNetGameTask(mContext, refreshDate, page + "", new Back() {

            @SuppressWarnings("unchecked")
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetNetGame) {
                    GetNetGame mGetNoNetGame = (GetNetGame) object;
                    if (mGetNoNetGame.data != null) {
                        refreshDate = mGetNoNetGame.data.refreshDate;
                        if (mGetNoNetGame.data.list != null) {
                            rankinglist_listView_player.setVisibility(View.GONE);
                            rankinglist_listView_game.setVisibility(View.VISIBLE);
                            if (type == 0) {// 刷新
                                if (mGetNoNetGame.data.list.size() >= 0) {
                                    //setNetData(mGetNoNetGame);
                                    mList = mGetNoNetGame.data.list;
                                    page++;
                                }
                            } else {
                                if (mGetNoNetGame.data.list.size() >= 0) {
                                    mList.addAll(mGetNoNetGame.data.list);
                                    page++;
                                }
                                if (mGetNoNetGame.data.list.size() < 10) {
                                    ToastUtil.showToast(mContext, "已经到底了");
                                }
                            }
                            setHeight(mList.size(), rankinglist_listView_game);
                            mRankingListGameAdapter.setmList(mList);
                        }
                    }
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                if (mList.size() == 0) {
                    if (type == 1) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    refresh_view.hideStateTextView(true);
                    refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {
                    refresh_view.hideStateTextView(false);
                    refresh_view.setBackgroundLoadingRelative(R.color.gray);
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {

                refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
                if (type == 0) {// 刷新
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                //mRelative_RankingList1.setVisibility(View.GONE);
                //mRelative_RankingList2.setVisibility(View.GONE);
                rankinglist_listView_game.setVisibility(View.GONE);
                rankinglist_listView_player.setVisibility(View.GONE);

            }
        }).start();
    }


    /**
     * 明星榜
     */
    public void starUser() {
        new StarUserTask(mContext, refreshDate, page + "", new Back() {

            @SuppressWarnings("unchecked")
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof StarUser) {
                    final StarUser mStarUser = (StarUser) object;
                    if (mStarUser.data != null) {
                        refreshDate = mStarUser.data.refreshDate;
                        if (mStarUser.data.list != null) {
                            rankinglist_listView_player.setVisibility(View.VISIBLE);
                            rankinglist_listView_game.setVisibility(View.GONE);
                            if (type == 0) {// 刷新
                                if (mStarUser.data.list.size() >= 0) {
                                    //setStarUserData(mStarUser);
                                    data = mStarUser.data.list;
                                    page++;
                                }
                            } else {
                                if (mStarUser.data.list.size() >= 0) {
                                    data.addAll(mStarUser.data.list);
                                    page++;
                                }
                                if (mStarUser.data.list.size() < 10) {
                                    ToastUtil.showToast(mContext, "已经到底了");
                                }
                            }
                            setHeight(data.size(), rankinglist_listView_player);
                            mRankingListPlayerAdapter.setData(data, 0);
                        }
                    }
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                if (data.size() == 0) {
                    if (page == 1) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    refresh_view.hideStateTextView(true);
                    refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {
                    refresh_view.hideStateTextView(false);
                    refresh_view.setBackgroundLoadingRelative(R.color.gray);
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
                if (type == 0) {// 刷新
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                //mRelative_RankingList1.setVisibility(View.GONE);
                //mRelative_RankingList2.setVisibility(View.GONE);
                rankinglist_listView_game.setVisibility(View.GONE);
                rankinglist_listView_player.setVisibility(View.GONE);

            }
        }).start();
    }

    /**
     * 人脉榜
     */
    public void connectUser() {
        new ConnectUserTask(mContext, refreshDate, page + "", new Back() {

            @SuppressWarnings("unchecked")
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof ConnectUser) {
                    final ConnectUser mStarUser = (ConnectUser) object;
                    if (mStarUser.data != null) {
                        refreshDate = mStarUser.data.refreshDate;
                        if (mStarUser.data.list != null) {
                            rankinglist_listView_player.setVisibility(View.VISIBLE);
                            rankinglist_listView_game.setVisibility(View.GONE);
                            if (type == 0) {// 刷新
                                if (mStarUser.data.list.size() >= 0) {
                                    //setConnectUserData(mStarUser);
                                    data = mStarUser.data.list;
                                    page++;
                                }
                            } else {
                                if (mStarUser.data.list.size() >= 0) {
                                    data.addAll(mStarUser.data.list);
                                    page++;
                                }
                                if (mStarUser.data.list.size() < 10) {
                                    ToastUtil.showToast(mContext, "已经到底了");
                                }
                            }
                            setHeight(data.size(), rankinglist_listView_player);
                            mRankingListPlayerAdapter.setData(data, 1);
                        }
                    }
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                if (data.size() == 0) {
                    if (page == 1) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    refresh_view.hideStateTextView(true);
                    refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {
                    refresh_view.hideStateTextView(false);
                    refresh_view.setBackgroundLoadingRelative(R.color.gray);
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
                if (type == 0) {// 刷新
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                //mRelative_RankingList1.setVisibility(View.GONE);
                //mRelative_RankingList2.setVisibility(View.GONE);
                rankinglist_listView_game.setVisibility(View.GONE);
                rankinglist_listView_player.setVisibility(View.GONE);

            }
        }).start();
    }


    /**
     * 收益榜
     */
    public void incomeUser() {
        new IncomeUserTask(mContext, refreshDate, page + "", new Back() {

            @SuppressWarnings("unchecked")
            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof IncomeUser) {
                    final IncomeUser mStarUser = (IncomeUser) object;
                    if (mStarUser.data != null) {
                        refreshDate = mStarUser.data.refreshDate;
                        if (mStarUser.data.list != null) {
                            rankinglist_listView_game.setVisibility(View.GONE);
                            rankinglist_listView_player.setVisibility(View.VISIBLE);
                            if (type == 0) {// 刷新
                                if (mStarUser.data.list.size() >= 0) {
                                    //setIncomeUserData(mStarUser);
                                    data = mStarUser.data.list;
                                    page++;
                                }
                            } else {
                                if (mStarUser.data.list.size() >= 0) {
                                    data.addAll(mStarUser.data.list);
                                    page++;
                                }
                                if (mStarUser.data.list.size() < 10) {
                                    ToastUtil.showToast(mContext, "已经到底了");
                                }
                            }
                            setHeight(data.size(), rankinglist_listView_player);
                            mRankingListPlayerAdapter.setData(data, 2);
                        }
                    }
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                if (data.size() == 0) {
                    if (page == 1) {
                        ToastUtil.showToast(mContext, "暂无数据");
                    }
                    refresh_view.hideStateTextView(true);
                    refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);//gray
                } else {
                    refresh_view.hideStateTextView(false);
                    refresh_view.setBackgroundLoadingRelative(R.color.gray);
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
                if (type == 0) {// 刷新
                    ToastUtil.showToast(mContext, msg);
                } else if (type == 1) {// 加载
                    ToastUtil.showToast(mContext, "获取列表失败");
                }
                //mRelative_RankingList1.setVisibility(View.GONE);
                //mRelative_RankingList2.setVisibility(View.GONE);
                rankinglist_listView_game.setVisibility(View.GONE);
                rankinglist_listView_player.setVisibility(View.GONE);

            }
        }).start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // Unregister
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(final DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (final DownLoadModel md : mRankingListGameAdapter.getmList()) {
                    Log.e("lbb", "--------onEventMainThread71-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mRankingListGameAdapter.getmList().set(mRankingListGameAdapter.getmList().indexOf(md), event);
                        mRankingListGameAdapter.notifyDataSetChanged();                            }

                        break;
                    }
                }
            }
        }

    /*public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (model != null && model.getmAppContent() != null && model.getmAppContent().gameId != null
                    && model.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {
                model = event;
                setData(model, model.getInfo());
            }
        }
    }*/
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        type = 0;
        page = 0;
        refreshDate = "";
        getRefresh();

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        type = 1;
        getRefresh();
    }

    private void getRefresh() {
        initData();
    }

    public void setHeight(int size, ListView mListView) {
        if (size > 0) {
            LayoutParams params = mListView.getLayoutParams();
            params.height = dip2px(mContext, 85 * size);
            mListView.setLayoutParams(params);
        } else {
            LayoutParams params = mListView.getLayoutParams();
            params.height = dip2px(mContext, 80);
            mListView.setLayoutParams(params);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}