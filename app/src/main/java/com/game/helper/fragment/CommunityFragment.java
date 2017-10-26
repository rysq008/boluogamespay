package com.game.helper.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.community.ConsultingCenterActivity;
import com.game.helper.adapter.community.CommunityAdapter;
import com.game.helper.adapter.home.MyFragmentPagerAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter.MsetV;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.fragment.community.AmuseFragment;
import com.game.helper.fragment.community.ConsultingActivityFragment;
import com.game.helper.fragment.community.ConsultingFragment;
import com.game.helper.fragment.community.SplendidFragment;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAdListTask;
import com.game.helper.net.task.GetContentTypeListTask;
import com.game.helper.sdk.model.returns.GetAdList;
import com.game.helper.sdk.model.returns.GetContentTypeList;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

@SuppressLint("ValidFragment")
/**
 * @Description 社区
 * @Path com.game.helper.fragment.CommunityFragment.java
 * @Author lbb
 * @Date 2016年8月18日 下午12:39:42
 * @Company
 */
public class CommunityFragment extends BaseFragment implements MsetV {

    @BindView(R.id.top_right)
    TextView topRight;

    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.tv_guid1)
    TextView view1;
    @BindView(R.id.tv_guid2)
    TextView view2;
    @BindView(R.id.tv_guid3)
    TextView view3;
    @BindView(R.id.tv_guid4)
    TextView view4;
    @BindView(R.id.cursor)
    ImageView barText;
    @BindView(R.id.viewPager)
    ViewPager mPager;

    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private ArrayList<BaseFragment> fragmentList;
    private int currIndex = 0;//当前页卡编号
    private String typeId1 = "1";
    private String typeId2 = "2";
    private String typeId3 = "3";
    private String typeId4 = "4";


    public CommunityFragment() {
        super();

    }

    public CommunityFragment(BaseApplication application, Activity activity,
                             Context context) {
        super(application, activity, context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mApplication = (BaseApplication) getActivity().getApplication();
        mApplication.context = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_community, container, false);
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
        topTitle.setText("资讯中心");
    }

    @Override
    protected void initEvents() {


        new GetContentTypeListTask(mContext, new Back() {

            @Override
            public void success(Object object, String msg) {
                //
                if (object != null && object instanceof GetContentTypeList) {
                    GetContentTypeList mGetContentTypeList = (GetContentTypeList) object;
                    if (mGetContentTypeList.data != null && mGetContentTypeList.data.size() >= 0) {
                        for (int i = 0; i < mGetContentTypeList.data.size(); i++) {
                            if (i == 0) {
                                view1.setText(mGetContentTypeList.data.get(i).typeName);
                                typeId1 = mGetContentTypeList.data.get(i).typeId;
                            } else if (i == 1) {
                                view2.setText(mGetContentTypeList.data.get(i).typeName);
                                typeId2 = mGetContentTypeList.data.get(i).typeId;
                            } else if (i == 2) {
                                view3.setText(mGetContentTypeList.data.get(i).typeName);
                                typeId3 = mGetContentTypeList.data.get(i).typeId;
                            } else if (i == 3) {
                                view4.setText(mGetContentTypeList.data.get(i).typeName);
                                typeId4 = mGetContentTypeList.data.get(i).typeId;
                            }
                        }

                        SharedPreUtil.putStringValue(mContext, ACTION_GetContentTypeList, new JsonBuild().setModel(object).getJson1());

                    }
                }
                view1.setSelected(true);
                view2.setSelected(false);
                view3.setSelected(false);
                view4.setSelected(false);

                view1.setOnClickListener(new txListener(0));
                view2.setOnClickListener(new txListener(1));
                view3.setOnClickListener(new txListener(2));
                view4.setOnClickListener(new txListener(3));
                if(null != getActivity() && !getActivity().isFinishing() && null != getActivity().getIntent()){
                    currIndex = getActivity().getIntent().getIntExtra("currIndex", 0);
                }
                initTextBar();
                initViewPager();
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetContentTypeList, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetContentTypeList.class, json);
                    if (mObject != null && mObject instanceof GetContentTypeList) {
                        GetContentTypeList mData = (GetContentTypeList) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            for (int i = 0; i < mData.data.size(); i++) {
                                if (i == 0) {
                                    view1.setText(mData.data.get(i).typeName);
                                    typeId1 = mData.data.get(i).typeId;
                                } else if (i == 1) {
                                    view2.setText(mData.data.get(i).typeName);
                                    typeId2 = mData.data.get(i).typeId;
                                } else if (i == 2) {
                                    view3.setText(mData.data.get(i).typeName);
                                    typeId3 = mData.data.get(i).typeId;
                                } else if (i == 3) {
                                    view4.setText(mData.data.get(i).typeName);
                                    typeId4 = mData.data.get(i).typeId;
                                }
                            }
                        }
                    }
                }
                view1.setSelected(true);
                view2.setSelected(false);
                view3.setSelected(false);
                view4.setSelected(false);

                view1.setOnClickListener(new txListener(0));
                view2.setOnClickListener(new txListener(1));
                view3.setOnClickListener(new txListener(2));
                view4.setOnClickListener(new txListener(3));
                currIndex = getActivity().getIntent().getIntExtra("currIndex", 0);
                initTextBar();
                initViewPager();
            }
        }).start();
    }

    @Override
    protected void init() {

    }

    @Override
    public void ref() {

    }


    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
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
        // 1/2屏幕宽度
        int tabLineLength = metrics.widthPixels / 4;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) barText.getLayoutParams();
        lp.width = tabLineLength;
        barText.setLayoutParams(lp);

    }

    /*
     * 初始化ViewPager
     */
    public void initViewPager() {
        mPager.setOffscreenPageLimit(4);
        fragmentList = new ArrayList<BaseFragment>();
        //活动
        ConsultingActivityFragment oFragment = new ConsultingActivityFragment(mApplication, getActivity(), getActivity());
        Bundle budle = new Bundle();
        budle.putString("typeId", typeId1);
        budle.putInt("currIndex", currIndex);
        oFragment.setArguments(budle);
        fragmentList.add(oFragment);
        //独家原创
        ConsultingFragment eFragment = new ConsultingFragment(mApplication,getActivity(), getActivity());
        Bundle budle2 = new Bundle();
        budle2.putString("typeId", typeId2);
        budle2.putInt("currIndex", currIndex);
        eFragment.setArguments(budle2);
        fragmentList.add(eFragment);

        AmuseFragment gFragment = new AmuseFragment(mApplication,getActivity(), getActivity());
        Bundle budle3 = new Bundle();
        budle3.putString("typeId", typeId3);
        budle3.putInt("currIndex", currIndex);
        gFragment.setArguments(budle3);
        fragmentList.add(gFragment);

        SplendidFragment sFragment = new SplendidFragment(mApplication,getActivity(), getActivity());
        Bundle budle4 = new Bundle();
        budle4.putString("typeId", typeId4);
        budle4.putInt("currIndex", currIndex);
        sFragment.setArguments(budle4);
        fragmentList.add(sFragment);

        //给ViewPager设置适配器
       // mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
          mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        mPager.setAdapter(mMyFragmentPagerAdapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) barText
                    .getLayoutParams();

            if (currIndex == arg0) {
                ll.leftMargin = (int) (currIndex * barText.getWidth() + arg1
                        * barText.getWidth());
            } else if (currIndex > arg0) {
                ll.leftMargin = (int) (currIndex * barText.getWidth() - (1 - arg1) * barText.getWidth());
            }
            barText.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int arg0) {
            currIndex = arg0;
            int i = currIndex + 1;
            if (i == 1) {
                view1.setSelected(true);
                view2.setSelected(false);
                view3.setSelected(false);
                view4.setSelected(false);
            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
                view3.setSelected(false);
                view4.setSelected(false);
            } else if (i == 3) {
                view1.setSelected(false);
                view2.setSelected(false);
                view3.setSelected(true);
                view4.setSelected(false);
            } else if (i == 4) {
                view1.setSelected(false);
                view2.setSelected(false);
                view3.setSelected(false);
                view4.setSelected(true);
            }
        }
    }

    /*public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        ArrayList<BaseFragment> list;

        public MyFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<BaseFragment> list) {
            super(fragmentManager);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public ArrayList<BaseFragment> getList() {
            return list;
        }

        public void setList(ArrayList<BaseFragment> list) {
            this.list = list;
        }

        @Override
        public BaseFragment getItem(int position) {

            return list.get(position % list.size());
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseFragment f = (BaseFragment) super.instantiateItem(container, position);
            return f;
        }
    }
*/
    /*
    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("CommunityFragment");
        super.onResume();
    }*/

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd("CommunityFragment");
        super.onPause();
    }

    @Override
    public void onResume() {
        MobclickAgent.onPageEnd("CommunityFragment");
        super.onResume();
    }
}
