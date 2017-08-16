package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.game.helper.adapter.home.GameGiftsAdapter;
import com.game.helper.adapter.home.GameHistorySearchAdapter;
import com.game.helper.adapter.home.HotGameAdapter;
import com.game.helper.adapter.home.SpecialgameAdapter;
import com.game.helper.adapter.home.SpecialgameAdapter.MOnItemClickListener11;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.model.home.Gift;
import com.game.helper.model.home.SearchMsg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryGameByHotWordTask;
import com.game.helper.net.task.QueryHotWordTask;
import com.game.helper.net.task.SearchGiftTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryGameByHotWord;
import com.game.helper.sdk.model.returns.QueryHotWord;
import com.game.helper.sdk.model.returns.QueryHotWord.HotWord;
import com.game.helper.sdk.model.returns.SearchGift;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.game.helper.view.widget.ListViewForScrollView;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @Description 搜索->游戏
 * @Path com.game.helper.fragment.HomeFragment.java
 * @Author lbb
 * @Date 2016年8月18日 下午12:38:37
 * @Company
 */
public class SearchActivity extends BaseActivity implements MOnItemClickListener11, PullToRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.search_gridview)
    MyScrollviewGridView search_gridview;//热门搜索

    @BindView(R.id.search_ListView)
    ListViewForScrollView search_ListView;//历史搜索

    @BindView(R.id.cleanHistory)
    ImageView cleanHistory;//清空
    @BindView(R.id.searchGameResult_listView)
    ListViewForScrollView searchGameResult_listView;//搜索游戏的结果
    @BindView(R.id.searchGiftResult_listView)
    ListViewForScrollView searchGiftResult_listView;//搜索礼包的结果

    HotGameAdapter mHotGameAdapter;//热门搜索
    @BindView(R.id.select_refresh_view)
    PullToRefreshLayout selectRefreshView;
    private List<HotWord> hotWords = new ArrayList<HotWord>();

    GameHistorySearchAdapter mGameHistorySearchAdapter;//历史搜索
    protected List<SearchMsg> mSearchList = new ArrayList<SearchMsg>();//历史搜索

    SpecialgameAdapter mSpecialgameAdapter;//搜索游戏的结果
    private List<AppContent> mmSpecialgameAdapter1List = new ArrayList<AppContent>();//搜索游戏的结果


    private GameGiftsAdapter mGameGiftsAdapter;//搜索礼包的结果
    private int page = 0;//翻页
    private List<Gift> mList = new ArrayList<Gift>();//搜索礼包的结果

    LoginData user;

    @BindView(R.id.mLinear1)
    LinearLayout mLinear1;
    @BindView(R.id.mView)
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        // Register
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        /*topTitle.setText("搜索");
		topLeftBack.setVisibility(View.VISIBLE);*/

        selectRefreshView.setOnRefreshListener(this);

        mHotGameAdapter = new HotGameAdapter(mContext, hotWords);
        search_gridview.setAdapter(mHotGameAdapter);

        mGameHistorySearchAdapter = new GameHistorySearchAdapter(mContext, mSearchList);
        search_ListView.setAdapter(mGameHistorySearchAdapter);

        //游戏搜索列表
        mSpecialgameAdapter = new SpecialgameAdapter(mContext, mmSpecialgameAdapter1List, 1, R.id.searchGameResult_listView);
        mSpecialgameAdapter.setOnItemClickListener(this);
        searchGameResult_listView.setAdapter(mSpecialgameAdapter);


        mGameGiftsAdapter = new GameGiftsAdapter(mContext, mList);
        searchGiftResult_listView.setAdapter(mGameGiftsAdapter);
        search_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (mHotGameAdapter.getHotWords().size() > 0) {
                    ed_search.setText("" + mHotGameAdapter.getHotWords().get(arg2).hotWord);
                    ed_search.setSelection(ed_search.getText().length());
                }
				/*key=1;
				setViews();*/
            }
        });
        search_ListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ed_search.setText("" + mGameHistorySearchAdapter.getmList().get(arg2).msg);
                ed_search.setSelection(ed_search.getText().length());
				/*key=1;
				setViews();*/
            }
        });
		/*searchGameResult_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle=new Bundle();
				bundle.putInt("currIndex", 0);
				bundle.putString("gameId", mSpecialgameAdapter.getItem(arg2).getmAppContent().gameId);
				startActivity(GameDetailActivity.class,bundle);
			}
		});*/

        searchGiftResult_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String msg = ed_search.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    DBManager.getInstance(mContext).insert(user.userId, msg, type);
                }
                Bundle bundle = new Bundle();
                bundle.putString("giftId", mGameGiftsAdapter.getmList().get(arg2).giftId);
                startActivity(GiftsDetailActivity.class, bundle);
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
    }

    int key = 0;//0：显示历史搜索记录，1：显示搜索结果列表
    int type = 0;//0：代表搜索游戏，1：代表搜索礼包

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("KEY_RESULT", 0);

        user = DBManager.getInstance(mContext).getUserMessage();


        key = 0;
        setViews();

    }

    public void setViews() {
        if (1 == key) {
            mLinearLayout.setVisibility(View.GONE);
            if (type == 0) {
                searchGameResult_listView.setVisibility(View.VISIBLE);
                searchGiftResult_listView.setVisibility(View.GONE);
            } else if (type == 1) {
                searchGameResult_listView.setVisibility(View.GONE);
                selectRefreshView.setVisibility(View.GONE);
                searchGiftResult_listView.setVisibility(View.VISIBLE);
            }
            if (type == 1) {
                //搜索礼物
                new SearchGiftTask(mContext, ed_search.getText().toString(), new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof SearchGift) {
                            SearchGift mSearchGift = (SearchGift) object;
                            if (mSearchGift.data != null && mSearchGift.data.size() >= 0) {
                                mList.clear();
                                mList.addAll(mSearchGift.data);
                                mGameGiftsAdapter.setmList(mList);
                                if (mList.size() == 0) {
                                    ToastUtil.showToast(mContext, "没相关数据");
                                }
                            }
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        // TODO Auto-generated method stub

                    }
                }).start();
            } else {
                mmSpecialgameAdapter1List.clear();
                searchGame(0);

            }
        } else if (0 == key) {
            mSearchList.clear();
            List<SearchMsg> ls = DBManager.getInstance(mContext).getSearchMsg(user.userId, type);
            mSearchList.addAll(ls);
            mGameHistorySearchAdapter.setmList(mSearchList);

            mLinearLayout.setVisibility(View.VISIBLE);
            searchGameResult_listView.setVisibility(View.GONE);
            searchGiftResult_listView.setVisibility(View.GONE);
            if (type == 1) {//礼包
                mLinear1.setVisibility(View.GONE);
                mView.setVisibility(View.GONE);
                search_gridview.setVisibility(View.GONE);
            } else {
                mLinear1.setVisibility(View.VISIBLE);
                mView.setVisibility(View.VISIBLE);
                search_gridview.setVisibility(View.VISIBLE);
                new QueryHotWordTask(mContext, new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof QueryHotWord) {
                            QueryHotWord mQueryHotWord = (QueryHotWord) object;
                            if (mQueryHotWord.data != null) {
                                hotWords.clear();
                                if (mQueryHotWord.data.isDefault != null) {
                                    hotWords.add(mQueryHotWord.data.isDefault);
                                }
                                if (mQueryHotWord.data.hotWords != null) {
                                    hotWords.addAll(mQueryHotWord.data.hotWords);
                                }
                                mHotGameAdapter.setHotWords(hotWords);
                            }
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        // TODO Auto-generated method stub

                    }
                }).start();
            }


        }

    }

    private void searchGame(final int offset) {
        //搜索游戏
        new QueryGameByHotWordTask(mContext, ed_search.getText().toString(), null, offset + "", new Back() {

            @Override
            public void success(Object object, String msg) {
                selectRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (object != null && object instanceof QueryGameByHotWord) {
                    QueryGameByHotWord mQueryHotWord = (QueryGameByHotWord) object;
                    if (mQueryHotWord.data != null && mQueryHotWord.data.size() > 0) {
                        mmSpecialgameAdapter1List.addAll(mQueryHotWord.data);
                        //initStatus();
                        mSpecialgameAdapter.setmList(mmSpecialgameAdapter1List);
                        searchGameResult_listView.deferNotifyDataSetChanged();
                        Util.setHeight(mList.size(), searchGameResult_listView,SearchActivity.this );
                        page++;
                    }
                    if (mQueryHotWord.data != null && mQueryHotWord.data.size() < 9) {
                        ToastUtil.showToast(SearchActivity.this, "已经到底了");
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                selectRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                ToastUtil.showToast(SearchActivity.this, msg);
            }
        }).start();
    }

    /**
     * 初始化下载状态
     *//*
	private void initStatus() {
		List<AppContent> list = DBManager.getInstance(mContext.getApplicationContext()).getAll();
		for (AppContent appContent : list) {
			for (AppContent app : mmSpecialgameAdapter1List) {
				if(app.downloadPath.trim().equals(appContent.downloadPath.trim())) {
					if(appContent.status==AppContent.Status.DOWNLOADING){

						if(BaseApplication.mInstance.downloadorMap != null && BaseApplication.mInstance.downloadorMap.containsKey(appContent.downloadPath.trim())) {
							BaseApplication.mInstance.downloadorMap.get(appContent.downloadPath.trim()).download();
						}else{
							if(BaseApplication.mInstance.downloadorMap == null) {
								BaseApplication.mInstance.downloadorMap = new HashMap<String, Downloador>();
							}
							Downloador downloador = new Downloador(getActivity(), appContent);
							downloador.download();
							BaseApplication.mInstance.downloadorMap.put(appContent.downloadPath.trim(), downloador);
						}

						appContent.status=AppContent.Status.PAUSED;
						Downloador downloador = new Downloador(getActivity(), appContent);
						downloador.download();
						if(downloadorMap == null) {
							downloadorMap = new HashMap<String, Downloador>();
						}
						downloadorMap.put(app.downloadPath.trim(), downloador);
					}
					app.status=(appContent.status);
					app.downloadPercent=(appContent.downloadPercent);
					break;
				}
			}
		}
		//初始化本地已下载完成存在的
		for(AppContent app : mmSpecialgameAdapter1List){
			if(app.status==AppContent.Status.PENDING){
				if(MonitorSysReceiver.isInstall(mContext.getApplicationContext(), app.packageName, app.gameName)){//已安装
					app.status=AppContent.Status.AlreadyInstalled;
					app.downloadPercent=100;
					DBManager.getInstance(mContext).insertDownloadFile(app);
					DBManager.getInstance(mContext).delDownloadInfoByUrl(app.downloadPath);
				}else{
					//未安装
					if(!TextUtils.isEmpty(app.downloadPath)){
						File file = new File(FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + 
								app.gameName+ ".apk");
						if (file.exists()){
							if (!TextUtils.isEmpty(app.fileSize)&&(Double.parseDouble(app.fileSize)*1024*1024)== file.length()) {
								app.status=AppContent.Status.FINISHED;
								app.downloadPercent=100;
								DBManager.getInstance(mContext).insertDownloadFile(app);
								DBManager.getInstance(mContext).delDownloadInfoByUrl(app.downloadPath);
							}
						}
					}
				}
			}
		}
	}*/
    @Override
    @OnClick({R.id.top_left_layout1, R.id.cleanHistory, R.id.tv_search1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout1:
                finish1();
                break;
            case R.id.cleanHistory:
                //清空
                DBManager.getInstance(mContext).deletedAllSearch();

                mSearchList.clear();
                List<SearchMsg> ls = DBManager.getInstance(mContext).getSearchMsg(user.userId, type);
                mSearchList.addAll(ls);
                mGameHistorySearchAdapter.setmList(mSearchList);
                break;
            case R.id.tv_search1:
                //搜索
                String msg = ed_search.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    key = 1;
                    DBManager.getInstance(mContext).insert(user.userId, msg, type);
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
    protected void onDestroy() {
        // Unregister
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mSpecialgameAdapter.getData()) {
                    Log.e("lbb", "--------onEventMainThread62-------");
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
    protected void onPause() {
        MobclickAgent.onPageEnd("SearchActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SearchActivity");
        super.onResume();
    }

    @Override
    public void onItemClick() {
        // TODO Auto-generated method stub
        String msg = ed_search.getText().toString();
        if (!TextUtils.isEmpty(msg)) {
            DBManager.getInstance(mContext).insert(user.userId, msg, type);
        }
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        searchGame(page);
    }

}