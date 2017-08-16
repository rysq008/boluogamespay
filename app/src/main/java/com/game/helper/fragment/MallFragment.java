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
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.community.ShakingActivity;
import com.game.helper.activity.home.GameGiftsActivity;
import com.game.helper.activity.mall.MallExchangeRecordActivity;
import com.game.helper.activity.mall.MallProductDetailsActivity;
import com.game.helper.activity.mine.AddressManagementActivity;
import com.game.helper.adapter.mall.ImagePagerAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter.MsetV;
import com.game.helper.adapter.mall.MallGridAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAdListTask;
import com.game.helper.net.task.QueryPtbTask;
import com.game.helper.sdk.model.returns.GetAdList;
import com.game.helper.sdk.model.returns.GetGoodList;
import com.game.helper.sdk.model.returns.GetGoodList.Good;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryPtb;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

@SuppressLint("ValidFragment")
/**
 * @Description 商城
 * @Path com.game.helper.fragment.MallFragment.java
 * @Author lbb
 * @Date 2016年8月18日 下午12:40:09
 * @Company
 */
public class MallFragment extends BaseFragment implements MsetV {

    @BindView(R.id.mall_title_ll)
    LinearLayout mallTitleLl;
    @BindView(R.id.mall_line_one)
    LinearLayout mallLineOne;
    @BindView(R.id.mall_cursor_iv)
    ImageView mallCursorIv;
    @BindView(R.id.mall_tv_guid1)
    TextView mallTvGuid1;
    @BindView(R.id.mall_tv_guid2)
    TextView mallTvGuid2;
    @BindView(R.id.mall_tv_guid3)
    TextView mallTvGuid3;
    @BindView(R.id.mall_tv_guid4)
    TextView mallTvGuid4;
    @BindView(R.id.scrollview_second_sv)
    ScrollView scrollviewSecondSv;
    @BindView(R.id.mScrollView_all)
    ScrollView mScrollViewAll;
    @BindView(R.id.scrollview_third_sv)
    ScrollView scrollviewThirdSv;
    @BindView(R.id.mall_line_fourth)
    LinearLayout mallLineFourth;
    @BindView(R.id.scrollview_fourth_sv)
    ScrollView scrollviewFourthSv;
    @BindView(R.id.mall_address_ll)
    LinearLayout mallAddressLl;

    @BindView(R.id.mall_gift_iv)
    ImageView mallGiftIv;
    @BindView(R.id.mall_shake_iv)
    ImageView mallShakeIv;
    @BindView(R.id.gridview_two)
    MyScrollviewGridView gridviewTwo;
    @BindView(R.id.gridview_three)
    MyScrollviewGridView gridviewThree;
    @BindView(R.id.gridview_four)
    MyScrollviewGridView gridviewFour;
    private TextView topRight;
    private TextView topTitle;
    private LinearLayout top_left_layout;
    private ImageView topLeftBack;
    private RelativeLayout mRelativeLayout;

    ScrollView mScrollViewfirst_page;
    private AutoScrollViewPager viewPager; // 显示轮播图

    private MyScrollviewGridView gridview;
    private MallGridAdapter mMallGridAdapter;

    private LinearLayout imgTipsLayout; // 显示小圆点
    private List<ImageView> imageViewTips = new ArrayList<ImageView>();  // 装载小圆点

    //private List<Good> mGoodList = new ArrayList<Good>();

    private TextView tv_ptb;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private int tabLineLength;
    private int first_x;
    private int first_y;
    private int second_x;
    private int second_y;
    private int third_x;
    private int third_y;
    private int fourth_x;
    private int fourth_y;
    private LinearLayout.LayoutParams lp;
    private ArrayList<Good> typeone = new ArrayList<Good>();
    private ArrayList<Good> typetwo = new ArrayList<Good>();
    private ArrayList<Good> typeThree = new ArrayList<Good>();
    private ArrayList<Good> typeFour = new ArrayList<Good>();
    private MallGridAdapter mMallGridAdapter_two;
    private MallGridAdapter mMallGridAdapter_three;
    private MallGridAdapter mMallGridAdapter_four;
    private List<AppContent> adLists;

    public MallFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MallFragment(BaseApplication application, Activity activity,
                        Context context) {
        super(application, activity, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mApplication = (BaseApplication) getActivity().getApplication();
        mApplication.context = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_mall, container, false);
            ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);
        return mView;
    }

    @Override
    protected void initViews() {
        topRight = (TextView) findViewById(R.id.top_right);
        topRight.setText("兑换记录");
        topRight.setVisibility(View.VISIBLE);
        topTitle = (TextView) findViewById(R.id.top_title);
        top_left_layout = (LinearLayout) findViewById(R.id.top_left_layout);
        topTitle.setText("积分商城");
        topLeftBack = (ImageView) findViewById(R.id.topLeftBack);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);

        mScrollViewfirst_page = (ScrollView) findViewById(R.id.mScrollView_firstpage);

        // 获取UI控件
        viewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        imgTipsLayout = (LinearLayout) findViewById(R.id.imgTipsLayout);

        //分类一
        gridview = (MyScrollviewGridView) findViewById(R.id.gridview);
        mMallGridAdapter = new MallGridAdapter(getActivity(), typeone);
        gridview.setAdapter(mMallGridAdapter);

        //分类二
        mMallGridAdapter_two = new MallGridAdapter(getActivity(), typetwo);
        gridviewTwo.setAdapter(mMallGridAdapter_two);

        //分类三
        mMallGridAdapter_three = new MallGridAdapter(getActivity(), typeThree);
        gridviewThree.setAdapter(mMallGridAdapter_three);

        //分类四
        mMallGridAdapter_four = new MallGridAdapter(getActivity(), typeFour);
        gridviewFour.setAdapter(mMallGridAdapter_four);


        tv_ptb = (TextView) findViewById(R.id.tv_ptb);


        mallTvGuid1.setOnClickListener(new txListener(0));
        mallTvGuid2.setOnClickListener(new txListener(1));
        mallTvGuid3.setOnClickListener(new txListener(2));
        mallTvGuid4.setOnClickListener(new txListener(3));
        initTextBar();
        setGoodList();
    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub
        topRight.setOnClickListener(this);

        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("good", mMallGridAdapter.getmGoodList().get(arg2));
                ((BaseActivity) getActivity()).startActivity(MallProductDetailsActivity.class, bundle);
            }
        });

        gridviewTwo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("good", mMallGridAdapter_two.getmGoodList().get(arg2));
                ((BaseActivity) getActivity()).startActivity(MallProductDetailsActivity.class, bundle);
            }
        });

        gridviewThree.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("good", mMallGridAdapter_three.getmGoodList().get(arg2));
                ((BaseActivity) getActivity()).startActivity(MallProductDetailsActivity.class, bundle);
            }
        });

        gridviewFour.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("good", mMallGridAdapter_four.getmGoodList().get(arg2));
                ((BaseActivity) getActivity()).startActivity(MallProductDetailsActivity.class, bundle);
            }
        });
    }


    @Override
    protected void init() {
        receiver = new BaseBroadcast(getActivity()) {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_GetAdList3.equals(action)) {
                    setTV();
                    Log.e("lbb", "---------3---3------");

                } else if (ACTION_GetAdList_Fail3.equals(action)) {
                    setTV();
                } else if (ACTION_GetGoodList.equals(action)) {
                    setGoodList();
                } else if (ACTION_GetGoodList_Fail.equals(action)) {
                    setGoodList();
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_GetAdList3);
        filter.addAction(ACTION_GetAdList_Fail3);
        filter.addAction(ACTION_GetGoodList);
        filter.addAction(ACTION_GetGoodList_Fail);

        getActivity().registerReceiver(receiver, filter);


        mImagePagerAdapter = new ImagePagerAdapter(getActivity(), adLists, 3);
        mImagePagerAdapter.setLiser(this);
        mImagePagerAdapter.setInfiniteLoop(true);
        viewPager.setAdapter(mImagePagerAdapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_right:
                ((BaseActivity) getActivity()).startActivity(MallExchangeRecordActivity.class);
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private ImagePagerAdapter mImagePagerAdapter;

    public void setTV() {
        mImagePagerAdapter.setImageIdList(BaseApplication.mInstance.imageIdList3);
        initImageViewTips();
        ref();
    }


    @OnClick({R.id.mall_address_ll, R.id.mall_gift_iv, R.id.mall_shake_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mall_address_ll:
                //getActivity().startActivity(AddressManagementActivity.class);
                ((BaseActivity) getActivity()).startActivity(AddressManagementActivity.class);
                break;
            case R.id.mall_gift_iv:
                ((BaseActivity) getActivity()).startActivity(GameGiftsActivity.class);
                break;
            case R.id.mall_shake_iv:
                ((BaseActivity) getActivity()).startActivity(ShakingActivity.class);
                break;
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
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(mContext);
        MobclickAgent.onPageStart("MallFragment");
        /*if(mImagePagerAdapter.getImageIdList().size()==0){
            setTV();
		}*/
        String json = SharedPreUtil.getStringValue(getActivity(), ACTION_GetAdList3, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetAdList.class, json);
            if (mObject != null && mObject instanceof GetAdList) {
                GetAdList mData = (GetAdList) mObject;
                if (mData != null && mData.data != null && mData.data.size() >= 0) {
                    BaseApplication.mInstance.imageIdList3 = mData.data;
                    setTV();
                }
            }
        }

        //轮播图
        new GetAdListTask(getActivity(), "2", new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetAdList) {
                    GetAdList list = (GetAdList) object;
                    Log.e("lbb", "---------3-------0-2-");
                    if (list != null && list.data != null && list.data.size() >= 0) {
                        BaseApplication.mInstance.imageIdList3 = list.data;
                        adLists = list.data;
                        try {
                            setTV();
                            BaseApplication.mInstance.context.sendBroadcast(new Intent(ACTION_GetAdList3));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //getActivity().sendBroadcast(new Intent(ACTION_GetAdList3));
                        SharedPreUtil.putStringValue(getActivity(), ACTION_GetAdList3, new JsonBuild().setModel(object).getJson1());
                        Log.e("lbb", "---------3-----1-2---");
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub
                //getActivity().sendBroadcast(new Intent(ACTION_GetAdList_Fail3));
                /*try {
                    BaseApplication.mInstance.context.sendBroadcast(new Intent(ACTION_GetAdList_Fail3));
				} catch (Exception e) {
					e.printStackTrace();
				}*/
            }
        }).start();
        setGoodList();
        user = DBManager.getInstance(getActivity()).getUserMessage();
        setTv_moneyNum(user);
        new QueryPtbTask(getActivity(), user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryPtb) {
                    QueryPtb mQueryPtb = (QueryPtb) object;
                    if (mQueryPtb.data != null) {
                        user.jsonData = null;
                        user.ptb = mQueryPtb.data;
                        user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                        DBManager.getInstance(mContext).insert(user);
                        user = DBManager.getInstance(getActivity()).getUserMessage();
                        setTv_moneyNum(user);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();

        ViewChange();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void ViewChange() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                first_x = mallLineOne.getLeft();
                first_y = mallLineOne.getTop();

                second_x = scrollviewSecondSv.getLeft();
                second_y = scrollviewSecondSv.getTop() - 120;

                third_x = scrollviewThirdSv.getLeft();
                third_y = scrollviewThirdSv.getTop() - 120;

                fourth_x = scrollviewFourthSv.getLeft();
                fourth_y = scrollviewFourthSv.getTop() - 120;
            }
        });
        mScrollViewAll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 430) {
                    mallTitleLl.setVisibility(View.VISIBLE);
                } else {
                    mallTitleLl.setVisibility(View.GONE);
                }

                if (scrollY < second_y) {
                    checkFirst();
                } else if (scrollY >= second_y && scrollY < third_y) {
                    checkSecond();
                } else if (scrollY >= third_y && scrollY < fourth_y) {
                    checkThird();
                } else if (scrollY > fourth_y) {
                    checkFourth();
                }
            }
        });
    }


    LoginData user;

    /**
     * 填充数据
     */
    public void setGoodList() {
        String json = SharedPreUtil.getStringValue(getActivity(), ACTION_GetGoodList, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetGoodList.class, json);
            if (mObject != null && mObject instanceof GetGoodList) {
                GetGoodList mData = (GetGoodList) mObject;
                List<GetGoodList.TypeList> typeLists = mData.data;
                //GetGoodList.TypeList typeList=typeLists.get(0);
                typeone = typeLists.get(0).typeOne;
                typetwo = typeLists.get(1).typeTwo;
                typeThree = typeLists.get(2).typeThree;
                typeFour = typeLists.get(3).typeFour;

                if (typeone != null && typeone != null && typeone.size() >= 0) {
                    mMallGridAdapter.setmGoodList(typeone);
                }

                if (typetwo != null && typetwo != null && typetwo.size() >= 0) {
                    mMallGridAdapter_two.setmGoodList(typetwo);
                }

                if (typeThree != null && typeThree != null && typeThree.size() >= 0) {
                    mMallGridAdapter_three.setmGoodList(typeThree);
                }

                if (typeFour != null && typeFour != null && typeFour.size() >= 0) {
                    mMallGridAdapter_four.setmGoodList(typeFour);
                }
            }
        }
    }

    public void setTv_moneyNum(LoginData user) {

        if (user != null) {
            if (!TextUtils.isEmpty(user.ptb)) {
                tv_ptb.setText("" + user.ptb);
            } else {
                tv_ptb.setText("0");
            }
        } else {
            tv_ptb.setText("0");
        }
    }


    /*
     * 初始化图片的位移像素
     */
    public void initTextBar() {

        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 宽度
        tabLineLength = metrics.widthPixels / 4;
        lp = (LinearLayout.LayoutParams) mallCursorIv.getLayoutParams();
        lp.width = tabLineLength;
        mallCursorIv.setLayoutParams(lp);

        mallTvGuid1.setSelected(true);
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {

            if (index == 0) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //mScrollView.fullScroll(ScrollView.FOCUS_UP);

                        mScrollViewAll.scrollTo(first_x, first_y);
                    }
                });
                checkFirst();
            } else if (index == 1) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //scrollviewNext.fullScroll(ScrollView.FOCUS_UP);

                        mScrollViewAll.scrollTo(second_x, second_y);
                    }
                });
                checkSecond();
            } else if (index == 2) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //scrollviewNext.fullScroll(ScrollView.FOCUS_UP);

                        mScrollViewAll.scrollTo(third_x, third_y);
                    }
                });
                checkThird();
            } else if (index == 3) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //mScrollViewAll.fullScroll(ScrollView.FOCUS_DOWN);
                        mScrollViewAll.scrollTo(fourth_x, fourth_y);
                    }
                });
                checkFourth();
            }
        }
    }

    private void checkFourth() {
        lp.leftMargin = tabLineLength * 3;
        mallCursorIv.setLayoutParams(lp);
        mallTvGuid1.setSelected(false);
        mallTvGuid2.setSelected(false);
        mallTvGuid3.setSelected(false);
        mallTvGuid4.setSelected(true);
    }

    private void checkThird() {
        lp.leftMargin = tabLineLength * 2;
        mallCursorIv.setLayoutParams(lp);
        mallTvGuid1.setSelected(false);
        mallTvGuid2.setSelected(false);
        mallTvGuid3.setSelected(true);
        mallTvGuid4.setSelected(false);
    }


    private void checkSecond() {
        lp.leftMargin = tabLineLength;
        mallCursorIv.setLayoutParams(lp);
        mallTvGuid1.setSelected(false);
        mallTvGuid2.setSelected(true);
        mallTvGuid3.setSelected(false);
        mallTvGuid4.setSelected(false);
    }

    private void checkFirst() {
        lp.leftMargin = 0;
        mallCursorIv.setLayoutParams(lp);
        mallTvGuid1.setSelected(true);
        mallTvGuid2.setSelected(false);
        mallTvGuid3.setSelected(false);
        mallTvGuid4.setSelected(false);
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


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
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
                // TODO Auto-generated method stub
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

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(mContext);
        MobclickAgent.onPageEnd("MallFragment");
    }

}
