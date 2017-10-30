package com.game.helper.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.LoginActivity;
import com.game.helper.activity.MainActivity;
import com.game.helper.activity.MakeMoneyActivity;
import com.game.helper.activity.WebActivity;
import com.game.helper.activity.home.GameClassifyActivity;
import com.game.helper.activity.home.GameThemeActivity;
import com.game.helper.activity.home.GameThemeDetailsActivity;
import com.game.helper.activity.home.RankingListActivity;
import com.game.helper.activity.home.RechargeActivity;
import com.game.helper.activity.home.SearchActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.activity.mine.MineSystemMsgActivity;
import com.game.helper.adapter.home.HomeGridAdapter;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.adapter.home.MyFragmentPagerAdapter;
import com.game.helper.adapter.home.SpecialgameAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter.MsetV;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.fragment.home.HomePageListHotGameFragment;
import com.game.helper.fragment.home.HomePageListNewGameFragment;
import com.game.helper.fragment.home.HomePageListSaleGameFragment;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.model.PushModel;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.CrollListTask;
import com.game.helper.net.task.GetAdListTask;
import com.game.helper.net.task.GetContactListTask;
import com.game.helper.net.task.QueryFirstOneTask;
import com.game.helper.net.task.QueryGameByModularTask;
import com.game.helper.net.task.QueryGameBykindAndTypeTask;
import com.game.helper.net.task.QueryHotWordTask;
import com.game.helper.sdk.model.returns.CrollList;
import com.game.helper.sdk.model.returns.GetAdList;
import com.game.helper.sdk.model.returns.GetContactList;
import com.game.helper.sdk.model.returns.GetContactList.Contact;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryFirstOne;
import com.game.helper.sdk.model.returns.QueryGameByModular;
import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.sdk.model.returns.QueryHotWord;
import com.game.helper.sdk.model.returns.QueryTheme.ThemeGame;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.game.helper.view.widget.BarrageView;
import com.game.helper.view.widget.CircleImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import de.greenrobot.event.EventBus;

@SuppressLint("ValidFragment")
/**
 * @Description 首页
 * @Path com.game.helper.fragment.HomeFragment.java
 * @Author lbb
 * @Date 2016年8月18日 下午12:38:37
 * @Company
 */
public class HomeFragment extends BaseFragment implements MsetV {
    @BindView(R.id.home_tv_guid1)
    TextView homeTvGuid1;
    @BindView(R.id.home_tv_guid2)
    TextView homeTvGuid2;
    @BindView(R.id.home_tv_guid3)
    TextView homeTvGuid3;
    @BindView(R.id.home_cursor)
    ImageView homeCursor;
    @BindView(R.id.home_showlist_viewPager)
    ViewPager homeShowlistViewPager;//*********************************************新游上架，热闹推荐，特价推广
    @BindView(R.id.home_pager_list_title_iv1)
    ImageView homePagerListTitleIv1;
    @BindView(R.id.home_pager_list_title_iv2)
    ImageView homePagerListTitleIv2;
    @BindView(R.id.home_pager_list_title_iv3)
    ImageView homePagerListTitleIv3;
    Unbinder unbinder1;
    @BindView(R.id.home_webgame_rv)
    RelativeLayout homeWebgameRv;
    @BindView(R.id.home_webgame_ListView)
    ListView homeWebgameListView;
    @BindView(R.id.home_discount_step_ll)
    LinearLayout homeDiscountStepLl;
    @BindView(R.id.home_ranking_ll)
    LinearLayout homeRankingLl;
    @BindView(R.id.home_dealback_ll)
    LinearLayout homeDealbackLl;
    @BindView(R.id.home_fastpay_ll)
    LinearLayout homeFastpayLl;
    @BindView(R.id.home_game_hall_rl)
    RelativeLayout homeGameHallRl;
    @BindView(R.id.home_imageView_pic)
    CircleImageView homeImageViewPic;
    @BindView(R.id.home_makemoney_rl)
    RelativeLayout homeMakemoneyRl;
    private ArrayList<BaseFragment> fragmentList;

    private LinearLayout LinearLayout_searcher;
    private ImageView tv_searcher;
    private HomeGridAdapter mHomeGridAdapter;
    private AutoScrollViewPager viewPager; // 显示轮播图
    private LinearLayout imgTipsLayout; // 显示小圆点
    private List<ImageView> imageViewTips = new ArrayList<ImageView>();// 装载小圆点
    private ArrayList<AppContent> mRecommendBoutiqueList = new ArrayList<AppContent>();

    //主题游戏
    RelativeLayout relat_ThemeGame;
    TextView tv_ThemeGamemore;
    ImageView iv_mThemeGame;
    TextView tv_mThemegame;

    //特价游戏
    RelativeLayout relat_Specialgame;
    ListView mSpecialgame_ListView;//*************************************************变态手游列表
    SpecialgameAdapter mSpecialgameAdapter1;
    private ArrayList<AppContent> mmSpecialgameAdapter1List = new ArrayList<AppContent>();

    @Override
    protected View findViewById(int id) {
        return super.findViewById(id);
    }

    private ArrayList<AppContent> mmSpecialgameAdapter2List = new ArrayList<AppContent>();
    private ArrayList<AppContent> webgamelist = new ArrayList<AppContent>();
    ImageView iv_call;
    TextView tv_ser;
    private int currIndex = 0;//当前页卡编号
    BarrageView mBarrageView;
    private ScrollView home_scrollview;
    private LinearLayout home_ll_title;

    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private LoginData user;
    private Drawable mDrawable;
    private MineGameAdapter mineGameAdapter;
    private boolean isnoread;
    private ImagePagerAdapter mImagePagerAdapter;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    int count = 0;
//                    List<DownloadInfo> downloadInfosList = DownLoadManager.getManager().getDownloadInfosList();
//                    for (DownloadInfo dm : downloadInfosList) {
//                        if (dm.getDownLoadTask() != null) {
//                            if (dm.getDownLoadTask().getDownloadInfo().getState() == DownLoadManager.STATE_DOWNLOADING) {
//                                count++;
//                                break;
//                            }
//                        }
//                    }
                    break;
                case 0x002:
                    /**
                     * 轮播图
                     */
                    String adjson = SharedPreUtil.getStringValue(getActivity(), ACTION_GetAdList1, "");
                    if (!TextUtils.isEmpty(adjson)) {
                        Object mObject = new JsonBuild().getData(GetAdList.class, adjson);
                        if (mObject != null && mObject instanceof GetAdList) {
                            GetAdList list = (GetAdList) mObject;
                            if (list != null && list.data != null && list.data.size() >= 0) {
                                BaseApplication.mInstance.imageIdList1 = list.data;
                                try {
                                    setTV();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        //首页轮播图请求
                        new GetAdListTask(getActivity(), "4", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof GetAdList) {
                                    GetAdList list = (GetAdList) object;
                                    if (list != null && list.data != null && list.data.size() >= 0) {
                                        BaseApplication.mInstance.imageIdList1 = list.data;
                                        try {
                                            setTV();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        SharedPreUtil.putStringValue(getActivity(), ACTION_GetAdList1, new JsonBuild().setModel(object).getJson1());
                                    }
                                }
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {

                            }
                        }).start();
                    }


                    /**
                     * 热词搜索
                     */
                    new QueryHotWordTask(mContext, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof QueryHotWord) {
                                QueryHotWord mQueryHotWord = (QueryHotWord) object;
                                if (mQueryHotWord.data != null) {
                                    if (mQueryHotWord.data.isDefault != null) {
                                        if (!TextUtils.isEmpty(mQueryHotWord.data.isDefault.isDefault)
                                                && mQueryHotWord.data.isDefault.isDefault.equals("Y")) {
                                            if (!TextUtils.isEmpty(mQueryHotWord.data.isDefault.hotWord)) {

                                                tv_ser.setText("大家都在搜‘" + mQueryHotWord.data.isDefault.hotWord + "'");
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            // TODO Auto-generated method stub

                        }
                    }).start();


                    /**
                     * 客服列表
                     */
                    String Contact_adjson = SharedPreUtil.getStringValue(getActivity(), ACTION_GetAdList1, "");
                    if (!TextUtils.isEmpty(Contact_adjson)) {
                        Object mObject = new JsonBuild().getData(GetContactList.class, Contact_adjson);
                        if (mObject != null && mObject instanceof GetContactList) {
                            GetContactList mGetContactList = (GetContactList) mObject;
                            if (mGetContactList.data != null) {
                                if (mGetContactList.data.size() >= 0) {
                                    data1.clear();
                                    data2.clear();
                                    data3.clear();
                                    for (Contact mContact : mGetContactList.data) {
                                        if (mContact != null && !TextUtils.isEmpty(mContact.type)) {
                                            if (mContact.type.equals("0")) {//电话客服
                                                data2.add(mContact);
                                            } else if (mContact.type.equals("1")) {//qq客服
                                                data1.add(mContact);
                                            } else if (mContact.type.equals("2")) {// qq群
                                                data3.add(mContact);
                                            }
                                        }
                                    }
                                    SharedPreUtil.putStringValue(getActivity(), ACTION_GetContactList, new JsonBuild().setModel(mObject).getJson1());
                                }
                            }
                        }
                    } else {
                        new GetContactListTask(getActivity(), new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof GetContactList) {
                                    GetContactList mGetContactList = (GetContactList) object;
                                    if (mGetContactList.data != null) {
                                        if (mGetContactList.data.size() >= 0) {
                                            data1.clear();
                                            data2.clear();
                                            data3.clear();
                                            for (Contact mContact : mGetContactList.data) {
                                                if (mContact != null && !TextUtils.isEmpty(mContact.type)) {
                                                    if (mContact.type.equals("0")) {//电话客服
                                                        data2.add(mContact);
                                                    } else if (mContact.type.equals("1")) {//qq客服
                                                        data1.add(mContact);
                                                    } else if (mContact.type.equals("2")) {// qq群
                                                        data3.add(mContact);
                                                    }
                                                }
                                            }
                                            SharedPreUtil.putStringValue(getActivity(), ACTION_GetContactList, new JsonBuild().setModel(object).getJson1());
                                        }
                                    }
                                }

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {


                            }
                        }).start();
                    }

                    /**
                     * 热门推荐
                     */
                    String json = SharedPreUtil.getStringValue(getActivity(), ACTION_QueryGameByModular_JP_Y, "");
                    if (!TextUtils.isEmpty(json)) {
                        if (!TextUtils.isEmpty(json)) {
                            Object mObject = new JsonBuild().getData(QueryGameByModular.class, json);
                            if (mObject != null && mObject instanceof QueryGameByModular) {
                                QueryGameByModular mData = (QueryGameByModular) mObject;
                                if (mData.data != null && mData.data.size() >= 0) {
                                    mRecommendBoutiqueList.clear();
                                    mRecommendBoutiqueList.addAll(mData.data);
                                }
                            }
                        }
                    } else {
                        new QueryGameByModularTask(getActivity(), MODE_JP, "Y", "0", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof QueryGameByModular) {
                                    QueryGameByModular mQueryGameByModular = (QueryGameByModular) object;
                                    if (mQueryGameByModular.data != null && mQueryGameByModular.data.size() >= 0) {
                                        mRecommendBoutiqueList.clear();
                                        mRecommendBoutiqueList.addAll(mQueryGameByModular.data);
                                        SharedPreUtil.putStringValue(getActivity(), ACTION_QueryGameByModular_JP_Y, new JsonBuild().setModel(object).getJson1());
                                    }
                                }

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                // TODO Auto-generated method stub

                            }
                        }).start();
                    }

                    /**
                     * 滚动广告
                     */
                    mBarrageView.reStart();
                    String json0002 = SharedPreUtil.getStringValue(getActivity(), ACTION_QueryCS, "");
                    if (!TextUtils.isEmpty(json0002)) {
                        if (!TextUtils.isEmpty(json0002)) {
                            Object mObject = new JsonBuild().getData(CrollList.class, json0002);
                            if (mObject != null && mObject instanceof CrollList) {
                                CrollList mCrollList = (CrollList) mObject;
                                if (mCrollList.data != null) {
                                    mBarrageView.reInit(mCrollList.data);
                                }

                            }
                        }
                    } else {
                        //获取滚动广告
                        new CrollListTask(mContext, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof CrollList) {
                                    CrollList mCrollList = (CrollList) object;
                                    if (mCrollList.data != null) {
                                        mBarrageView.reInit(mCrollList.data);
                                    }
                                }

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {

                            }
                        }).start();
                    }


                    /**
                     * 第一个主题游戏
                     */
                    String json2 = SharedPreUtil.getStringValue(getActivity(), ACTION_QueryFirstOne, "");
                    if (!TextUtils.isEmpty(json2)) {
                        Object mObject = new JsonBuild().getData(QueryFirstOne.class, json2);
                        if (mObject != null && mObject instanceof QueryFirstOne) {
                            QueryFirstOne mData = (QueryFirstOne) mObject;
                            if (mData.data != null && mData.data.size() >= 0) {
                                if (mData.data.size() > 0) {

                                    Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                            .load("" + mData.data.get(0).fileAskPath + mData.data.get(0).pic)
                                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                            //.fitCenter()// 长的一边撑满
                                            .placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                            .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                            //.crossFade()
                                            .into(iv_mThemeGame);
                                    tv_mThemegame.setText(mData.data.get(0).remark);
                                    mThemeGame = mData.data.get(0);
                                } else {
                                    mThemeGame = null;
                                }

                            }
                        }
                    } else {
                        new QueryFirstOneTask(getActivity(), new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                // TODO Auto-generated method stub
                                if (object != null && object instanceof QueryFirstOne) {
                                    QueryFirstOne mQueryFirstOne = (QueryFirstOne) object;
                                    if (mQueryFirstOne.data != null && mQueryFirstOne.data.size() >= 0) {
                                        if (mQueryFirstOne.data.size() > 0) {

                                            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                                    .load("" + mQueryFirstOne.data.get(0).fileAskPath + mQueryFirstOne.data.get(0).pic)
                                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                                    //.centerCrop()// 长的一边撑满
                                                    .placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                                    .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                                    //.crossFade()
                                                    .into(iv_mThemeGame);
                                            tv_mThemegame.setText(mQueryFirstOne.data.get(0).remark);
                                            mThemeGame = mQueryFirstOne.data.get(0);
                                        } else {
                                            mThemeGame = null;
                                        }
                                        SharedPreUtil.putStringValue(getActivity(), ACTION_QueryFirstOne, new JsonBuild().setModel(object).getJson1());

                                    }
                                }
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                // TODO Auto-generated method stub

                            }
                        }).start();
                    }

                    /**
                     * 变态手游
                     */
                    String json_bt = SharedPreUtil.getStringValue(getActivity(), ACTION_QueryGameByModular_BT_N, "");
                    if (!TextUtils.isEmpty(json_bt)) {
                        if (!TextUtils.isEmpty(json_bt)) {
                            Object mObject = new JsonBuild().getData(QueryGameByModular.class, json_bt);
                            if (mObject != null && mObject instanceof QueryGameByModular) {
                                QueryGameByModular mData = (QueryGameByModular) mObject;
                                if (mData.data != null && mData.data.size() >= 0) {
                                    mmSpecialgameAdapter1List.clear();
                                    mmSpecialgameAdapter1List.addAll(mData.data);
                                    //initStatus1();
                                    Util.setHeight(3, mSpecialgame_ListView, mContext);
                                    mSpecialgameAdapter1.setmList(mmSpecialgameAdapter1List);

                                }
                            }
                        }
                    } else {
                        new QueryGameBykindAndTypeTask(getActivity(), "4", "1", "field1", "0", new BaseBBXTask.Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof QueryGameBykindAndType) {
                                    QueryGameBykindAndType queryGameBykindAndType = (QueryGameBykindAndType) object;
                                    if (queryGameBykindAndType.data != null && queryGameBykindAndType.data.size() >= 0) {
                                        mmSpecialgameAdapter1List.clear();
                                        mmSpecialgameAdapter1List.addAll(queryGameBykindAndType.data);
                                        //initStatus1();
                                        Util.setHeight(3, mSpecialgame_ListView, mContext);
                                        mSpecialgameAdapter1.setmList(mmSpecialgameAdapter1List);
                                        SharedPreUtil.putStringValue(getActivity(), ACTION_QueryGameByModular_BT_N, new JsonBuild().setModel(object).getJson1());

                                    }
                                }

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                // TODO Auto-generated method stub

                            }
                        }).start();
                    }


                    /**
                     * 新游上架
                     */
                    String json_xy = SharedPreUtil.getStringValue(getActivity(), ACTION_QueryGameByModular_XY_Y, "");
                    if (!TextUtils.isEmpty(json_xy)) {
                        Object mObject = new JsonBuild().getData(QueryGameByModular.class, json_xy);
                        if (mObject != null && mObject instanceof QueryGameByModular) {
                            QueryGameByModular mData = (QueryGameByModular) mObject;
                            if (mData.data != null && mData.data.size() >= 0) {
                                mmSpecialgameAdapter2List.clear();
                                mmSpecialgameAdapter2List.addAll(mData.data);
                            }
                        }
                    } else {
                        new QueryGameByModularTask(getActivity(), MODE_XY, "Y", "0", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof QueryGameByModular) {
                                    QueryGameByModular mData = (QueryGameByModular) object;
                                    if (mData.data != null && mData.data.size() >= 0) {
                                        mmSpecialgameAdapter2List.clear();
                                        mmSpecialgameAdapter2List.addAll(mData.data);
                                    }
                                }
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(getActivity(), msg);
                            }
                        }).start();
                    }


                    /**
                     * 手机页游
                     */
                    String json_yy = SharedPreUtil.getStringValue(getActivity(), ACTION_QueryGameByModular_WY_N, "");
                    if (!TextUtils.isEmpty(json_yy)) {
                        Object mObject = new JsonBuild().getData(QueryGameByModular.class, json_yy);
                        if (mObject != null && mObject instanceof QueryGameByModular) {
                            QueryGameByModular mData = (QueryGameByModular) mObject;
                            if (mData.data != null && mData.data.size() >= 0) {
                                webgamelist.addAll(mData.data);
                                mineGameAdapter.setmList(webgamelist);
                                Util.setHeight(3, homeWebgameListView, mContext);
                            }
                        }
                    } else {
                        new QueryGameBykindAndTypeTask(getActivity(), "3", "", "field1", "0", new Back() {
                            @Override
                            public void success(Object object, String msg) {
                                if (object != null && object instanceof QueryGameBykindAndType) {
                                    QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                                    if (mData.data != null && mData.data.size() >= 0) {
                                        webgamelist.addAll(mData.data);
                                        mineGameAdapter.setmList(webgamelist);
                                        Util.setHeight(3, homeWebgameListView, mContext);
                                        //SharedPreUtil.putStringValue(getActivity(), ACTION_QueryGameByModular_XY_Y, new JsonBuild().setModel(object).getJson1());
                                    }
                                }
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(getActivity(), msg);
                            }
                        }).start();
                    }

                    initViewPager();

                    break;
            }
        }
    };

    public HomeFragment() {
    }

    public HomeFragment(BaseApplication application, Activity activity,
                        Context context) {
        super(application, activity, context);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mApplication = (BaseApplication) getActivity().getApplication();
        mApplication.context = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);

        user = DBManager.getInstance(getActivity()).getUserMessage();
        setPic(user);
        unbinder1 = ButterKnife.bind(this, mView);
        return mView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initViews() {
        mBarrageView = (BarrageView) findViewById(R.id.containerView);
        tv_ser = (TextView) findViewById(R.id.tv_ser);
        home_ll_title = (LinearLayout) findViewById(R.id.home_ll_title);

        LinearLayout_searcher = (LinearLayout) findViewById(R.id.LinearLayout_searcher);
        tv_searcher = (ImageView) findViewById(R.id.tv_searcher);

        mHomeGridAdapter = new HomeGridAdapter(getActivity());

        // 获取UI控件
        viewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        imgTipsLayout = (LinearLayout) findViewById(R.id.imgTipsLayout);
        home_scrollview = (ScrollView) findViewById(R.id.home_scrollview);
        home_scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {//往上滑，渐变
                    float f_alpha = 1 - getAlphaFloat(scrollY);
                    home_ll_title.setAlpha(f_alpha);
                } else {//往下滑出现
                    home_ll_title.setAlpha(1);
                }
            }
        });
        iv_call = (ImageView) findViewById(R.id.iv_call);


        //主题游戏
        relat_ThemeGame = (RelativeLayout) findViewById(R.id.relat_ThemeGame);
        tv_ThemeGamemore = (TextView) findViewById(R.id.tv_ThemeGamemore);
        iv_mThemeGame = (ImageView) findViewById(R.id.iv_mThemeGame);
        tv_mThemegame = (TextView) findViewById(R.id.tv_mThemegame);

        //特价游戏
        relat_Specialgame = (RelativeLayout) findViewById(R.id.relat_Specialgame);

        //变态手游
        mSpecialgame_ListView = (ListView) findViewById(R.id.mSpecialgame_ListView);
        mSpecialgameAdapter1 = new SpecialgameAdapter(getActivity(), mmSpecialgameAdapter1List, 1, R.id.mSpecialgame_ListView);
        mSpecialgame_ListView.setAdapter(mSpecialgameAdapter1);
        mineGameAdapter = new MineGameAdapter(getActivity(), webgamelist, 2, R.id.home_webgame_ListView);
        homeWebgameListView.setAdapter(mineGameAdapter);

        mImagePagerAdapter = new ImagePagerAdapter(getActivity(), new ArrayList<AppContent>(), 1);
        mImagePagerAdapter.setLiser(this);
        mImagePagerAdapter.setInfiniteLoop(true);
        viewPager.setAdapter(mImagePagerAdapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        homeTvGuid1.setSelected(true);
        homeTvGuid2.setSelected(false);
        homeTvGuid3.setSelected(false);
        homeTvGuid1.setOnClickListener(new txListener(0));
        homeTvGuid2.setOnClickListener(new txListener(1));
        homeTvGuid3.setOnClickListener(new txListener(2));

        initTextBar();
    }


    @OnClick({R.id.home_discount_step_ll, R.id.home_ranking_ll, R.id.home_dealback_ll, R.id.home_fastpay_ll, R.id.home_game_hall_rl, R.id.home_makemoney_rl})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.home_discount_step_ll://折扣步骤
                Bundle bundleweb = new Bundle();
                bundleweb.putString("Url", "http://ghelper.h5h5h5.cn/helper/app/toBannerDisCount");
                bundleweb.putString("TITLE", "折扣步骤");
                ((BaseActivity) mContext).startActivity(WebActivity.class, bundleweb);
                break;
            case R.id.home_ranking_ll:
                ((BaseActivity) getActivity()).startActivity(RankingListActivity.class, bundle);//排行榜
                break;
            case R.id.home_dealback_ll:
                ToastUtil.showToast(getActivity(), "功能暂未开放");
                break;
            case R.id.home_fastpay_ll:
                ((BaseActivity) getActivity()).startActivity(RechargeActivity.class, bundle);//快速充值
                break;

            //游戏大厅
            case R.id.home_game_hall_rl:
                bundle.putInt("KEY_filtrate", 3);
                ((BaseActivity) getActivity()).startActivity(GameClassifyActivity.class, bundle);
                //((BaseActivity) getActivity()).startActivity(GameAllActivity.class, bundle);
                break;
            case R.id.home_makemoney_rl:
                ((BaseActivity) getActivity()).startActivity(MakeMoneyActivity.class);//任务赚钱

                break;
        }
    }

    public class txListener implements OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            homeShowlistViewPager.setCurrentItem(index);
        }
    }

    @Override
    protected void initEvents() {
        iv_call.setOnClickListener(this);
        LinearLayout_searcher.setOnClickListener(this);
        tv_searcher.setOnClickListener(this);
        relat_ThemeGame.setOnClickListener(this);
        relat_Specialgame.setOnClickListener(this);
        iv_mThemeGame.setOnClickListener(this);
        homeWebgameRv.setOnClickListener(this);
        homeImageViewPic.setOnClickListener(this);
    }


    @Override
    protected void init() {
        receiver = new BaseBroadcast(getActivity()) {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_GetAdList1.equals(action)) {
                    setTV();

                } else if (ACTION_GetAdList_Fail1.equals(action)) {
                    setTV();
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_GetAdList1);
        filter.addAction(ACTION_GetAdList_Fail1);
        getActivity().registerReceiver(receiver, filter);

        /**
         * 设置数据
         */
        Message message = new Message();
        message.what = 0x002;
        handler.sendMessage(message);

    }


    /**
     * 初始化圆点
     */
    private void initImageViewTips() {
        imgTipsLayout.removeAllViews();
        imageViewTips = new ArrayList<ImageView>();

        for (int i = 0; i < ListUtils.getSize(mImagePagerAdapter.getImageIdList()); i++) {
            ImageView imageViewTip = new ImageView(getActivity());
            imageViewTip.setLayoutParams(new LayoutParams(6, 6)); // 设置圆点宽高
            imageViewTips.add(imageViewTip);
            if (i == 0) {
                imageViewTip
                        .setBackgroundResource(R.drawable.gh_maincolor_oval_shape);
            } else {
                imageViewTip
                        .setBackgroundResource(R.drawable.gh_cecolor_oval_shape_nor);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            imgTipsLayout.addView(imageViewTip, params);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            try {
                if (mImagePagerAdapter.getImageIdList().size() > 0) {
                    imageViewTips.get((position) % ListUtils.getSize(mImagePagerAdapter.getImageIdList())).setBackgroundResource(
                            R.drawable.gh_maincolor_oval_shape);
                    for (int i = 0; i < ListUtils.getSize(mImagePagerAdapter.getImageIdList()); i++) {
                        // 改变前一个位置小红点的背景
                        if (i != ((position) % ListUtils.getSize(mImagePagerAdapter.getImageIdList()))) {
                            imageViewTips.get(i).setBackgroundResource(
                                    R.drawable.gh_cecolor_oval_shape_nor);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.LinearLayout_searcher:
                ((BaseActivity) getActivity()).startActivity(SearchActivity.class);
                break;
            case R.id.tv_searcher:
//                if ((getActivity().getIntent().getIntExtra(KEY_FIRSTUSER, 0)) == 1) {//1:登录成功，0：登录失败
//                    //系统消息
                    ((BaseActivity) getActivity()).startActivity(MineSystemMsgActivity.class);
//                } else {
//                    ((BaseActivity) getActivity()).startActivity(LoginActivity.class);
//                }

                break;
            case R.id.relat_ThemeGame:
                ((BaseActivity) getActivity()).startActivity(GameThemeActivity.class);
                break;
            case R.id.relat_Specialgame:
                ((BaseActivity) getActivity()).startActivity(GameClassifyActivity.class);
                break;
            case R.id.iv_mThemeGame:
                if (mThemeGame != null) {
                    Bundle bundles = new Bundle();
                    bundles.putString("remark", mThemeGame.remark);
                    bundles.putString("themeId", mThemeGame.themeId);
                    bundles.putString("createTimeString", mThemeGame.createTimeString);
                    bundles.putString("themeName", mThemeGame.themeName);
                    bundles.putString("pic", mThemeGame.pic);
                    bundles.putString("picThumb", mThemeGame.picThumb);
                    bundles.putString("gameNum", mThemeGame.gameNum);
                    bundles.putString("fileAskPath", mThemeGame.fileAskPath);
                    ((BaseActivity) getActivity()).startActivity(GameThemeDetailsActivity.class, bundles);
                }
                break;
            case R.id.iv_call:
                Util.showPopupWindow(getActivity(), tv_ser);
                break;

            case R.id.home_webgame_rv:
                ((BaseActivity) getActivity()).startActivity(GameClassifyActivity.class);
                break;
            case R.id.home_imageView_pic:
//                if ((getActivity().getIntent().getIntExtra(KEY_FIRSTUSER, 0)) == 1) {//1:登录成功，0：登录失败
//                    MainActivity mainActivity = (MainActivity) getActivity();
//                    mainActivity.setTable(3);
//                } else {
//                    ((BaseActivity) getActivity()).startActivity(LoginActivity.class);
//                }
                //个人信息和头像设置
                ((BaseActivity) getActivity()).startActivity(MineDataEditingActivity.class);
                break;

            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    public void onDestroy() {
        //getActivity().unregisterReceiver(downloadStatusReceiver);
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
        try {
            if (imageViewTips != null) {
                /*释放资源*/
                for (int i = 0; i < imageViewTips.size(); i++) {
                    ImageView imageView = imageViewTips.get(i);
                    Drawable drawable = imageView.getDrawable();
                    if (drawable != null) {
                        // 解除drawable对view的引用
                        drawable.setCallback(null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    int i = 0;

    @Override
    public void ref() {
        // TODO Auto-generated method stub
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                if (mImagePagerAdapter.getImageIdList().size() > 0) {
                    if (i == 0) {
                        i = 1;
                        viewPager.setInterval(2000);
                        viewPager.startAutoScroll();
                    }

                }
            }
        });
    }

    private List<Contact> data1 = new ArrayList<Contact>();
    private List<Contact> data2 = new ArrayList<Contact>();
    private List<Contact> data3 = new ArrayList<Contact>();

    @Override
    public void onResume() {
//        new TTSThread().start();
        isStop = false;
        super.onResume();
        MobclickAgent.onResume(mContext);
        MobclickAgent.onPageStart("HomeFragment");


        setMessageImg();
    }

    ThemeGame mThemeGame = null;

    @Override
    public void onPause() {
        mBarrageView.stop();
        isStop = true;
        //DownLoadManager.getManager().stopAllTask();
        super.onPause();
        MobclickAgent.onPageEnd("HomeFragment");
        MobclickAgent.onPause(mContext);
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null && getUserVisibleHint() && event.needRefreshAdapter) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {
                Log.e("lbb", "--------onEventMainThread02-------");
                for (DownLoadModel md : mSpecialgameAdapter1.getData()) {
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {
                        mSpecialgameAdapter1.getData().set(mSpecialgameAdapter1.getData().indexOf(md), event);
                        mSpecialgameAdapter1.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }

    public static boolean isStop = true;

    public class TTSThread extends Thread {

        @Override
        public synchronized void run() {
            // TODO Auto-generated method stub
            try {
                while (!isStop) {
                    handler.sendEmptyMessage(1);
                    Thread.sleep(1000);

                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置三个viewpager
     */
    private void initViewPager() {
        homeShowlistViewPager.setOffscreenPageLimit(2);
        fragmentList = new ArrayList<BaseFragment>();

        //新游上架
        HomePageListNewGameFragment homePageListFragment = HomePageListNewGameFragment.newInstance(mmSpecialgameAdapter2List);
        fragmentList.add(homePageListFragment);

        //热门推荐
        HomePageListHotGameFragment homePageListHotGameFragment = HomePageListHotGameFragment.newInstance(mRecommendBoutiqueList);
        fragmentList.add(homePageListHotGameFragment);

        //特价推荐（变态手游）
        HomePageListSaleGameFragment homePageListSaleGameFragment = HomePageListSaleGameFragment.newInstance(mmSpecialgameAdapter1List);
        // new HomePageListSaleGameFragment(mApplication, getActivity(), mContext, mmSpecialgameAdapter1List);
        fragmentList.add(homePageListSaleGameFragment);

        //给ViewPager设置适配器
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        homeShowlistViewPager.setAdapter(mMyFragmentPagerAdapter);
        homeShowlistViewPager.setOnPageChangeListener(new MyListOnPageChangeListener());//页面变化时的监听器
        homeShowlistViewPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页

    }


    public void setTV() {
        mImagePagerAdapter.setImageIdList(BaseApplication.mInstance.imageIdList1);
        initImageViewTips();
        ref();

    }

    /**
     * 初始化图片的位移像素
     */
    public void initTextBar() {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/3屏幕宽度
        int tabLineLength = metrics.widthPixels / 3;
        LayoutParams lp = (LayoutParams) homeCursor.getLayoutParams();
        lp.width = tabLineLength;
        homeCursor.setLayoutParams(lp);
    }

    /**
     * 新游上架，热门推荐 特价推荐滑动监听
     */
    public class MyListOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) homeCursor.getLayoutParams();

            if (currIndex == arg0) {
                ll.leftMargin = (int) (currIndex * homeCursor.getWidth() + arg1 * homeCursor.getWidth());
            } else if (currIndex > arg0) {
                ll.leftMargin = (int) (currIndex * homeCursor.getWidth() - (1 - arg1) * homeCursor.getWidth());
            }
            homeCursor.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        /**
         * 设置滑动栏图标
         *
         * @param arg0
         */
        @Override
        public void onPageSelected(int arg0) {
            currIndex = arg0;
            int i = currIndex + 1;
            if (i == 1) {
                homeTvGuid1.setSelected(true);
                homeTvGuid2.setSelected(false);
                homeTvGuid3.setSelected(false);
            } else if (i == 2) {
                homeTvGuid1.setSelected(false);
                homeTvGuid2.setSelected(true);
                homeTvGuid3.setSelected(false);
            } else if (i == 3) {
                homeTvGuid1.setSelected(false);
                homeTvGuid2.setSelected(false);
                homeTvGuid3.setSelected(true);
            }

            if (i == 1) {
                homePagerListTitleIv1.setImageDrawable(getResources().getDrawable(R.drawable.home_new_icon));
                homePagerListTitleIv2.setImageDrawable(getResources().getDrawable(R.drawable.home_nohot_icon));
                homePagerListTitleIv3.setImageDrawable(getResources().getDrawable(R.drawable.home_nosale_icon));
            } else if (i == 2) {
                homePagerListTitleIv1.setImageDrawable(getResources().getDrawable(R.drawable.home_nonew_icon));
                homePagerListTitleIv2.setImageDrawable(getResources().getDrawable(R.drawable.home_hot_icon));
                homePagerListTitleIv3.setImageDrawable(getResources().getDrawable(R.drawable.home_nosale_icon));
            } else if (i == 3) {
                homePagerListTitleIv1.setImageDrawable(getResources().getDrawable(R.drawable.home_nonew_icon));
                homePagerListTitleIv2.setImageDrawable(getResources().getDrawable(R.drawable.home_nohot_icon));
                homePagerListTitleIv3.setImageDrawable(getResources().getDrawable(R.drawable.home_sale_icon));
            }
        }
    }

    /**
     * 设置消息提示图标
     */
    private void setMessageImg() {
        List<PushModel> list = DBManager.getInstance(getActivity()).getPushAllMessage();
        for (PushModel push : list) {
            if (push.isRead == 0) {//有未读消息
                isnoread = true;
            }
        }
        if (isnoread) {
            tv_searcher.setImageDrawable(getResources().getDrawable(R.drawable.home_message));
        } else {
            tv_searcher.setImageDrawable(getResources().getDrawable(R.drawable.home_nomessage));
        }
    }

    /**
     * 设置头像
     *
     * @param user
     */
    public void setPic(LoginData user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.icon) && !TextUtils.isEmpty(user.fileAskPath)) {
                Glide.with(mContext)
                        .load(user.fileAskPath + user.icon)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        .placeholder(R.drawable.pic_moren)//加载中显示的图片
                        .error(R.drawable.pic_moren)//加载失败时显示的图片
                        //.crossFade()
                        .into(homeImageViewPic);
            } else {
                try {
                    mDrawable = getActivity().getResources().getDrawable(R.drawable.pic_moren);
                    homeImageViewPic.setImageDrawable(mDrawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 获取渐变透明值
     *
     * @param dis
     * @return
     */
    public float getAlphaFloat(int dis) {
        int step = 3300;
        if (dis == 0) {
            return 0.0f;
        }
        if (dis < step) {
            return (float) (dis * (1.0 / step));
        } else {
            return 1.0f;
        }
    }
}
