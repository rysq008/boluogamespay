package com.game.helper.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.fragment.home.RechargeGameFragment;
import com.game.helper.fragment.home.RechargePlatformCurrencyFragment;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryPtbTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryPtb;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.Util;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 快捷充值
 * @Path com.game.helper.activity.home.RechargeActivity.java
 * @Author lbb
 * @Date 2016年8月23日 下午6:56:05
 * @Company
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.recharge_title_ll)
    LinearLayout rechargeTitleLl;
    @BindView(R.id.cursor_ll)
    LinearLayout cursorLl;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private ArrayList<BaseFragment> fragmentList;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.tv_guid1)
    TextView view1;
    @BindView(R.id.tv_guid2)
    TextView view2;
    @BindView(R.id.cursor)
    ImageView barText;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private int currIndex = 0;//当前页卡编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_recharge);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("快速充值");
        topLeftBack.setVisibility(View.VISIBLE);
        //topRight.setText("帮助");
        topLinerRight.setVisibility(View.VISIBLE);
        topIvRight.setVisibility(View.VISIBLE);
        topIvRight.setImageDrawable(getResources().getDrawable(R.drawable.shouye_05));

        view1.setSelected(true);
        view2.setSelected(false);
        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        currIndex = getIntent().getIntExtra("currIndex", 0);
        initTextBar();
        initViewPager();
    }

    LoginData user;

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new QueryPtbTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryPtb) {
                    QueryPtb mQueryPtb = (QueryPtb) object;
                    if (mQueryPtb.data != null) {
                        user.jsonData = null;
                        user.ptb = mQueryPtb.data;
                        user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                        DBManager.getInstance(mContext).insert(user);
                        //	user=DBManager.getInstance(mContext).getUserMessage();

                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                startActivity(RechargeExplainActivity.class);
                break;
            default:
                super.onClick(v);
                break;
        }
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

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/4屏幕宽度
        int tabLineLength = metrics.widthPixels / 4;
        LayoutParams lp = (LayoutParams) barText.getLayoutParams();
        lp.width = tabLineLength;
        barText.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp_title_ll = (RelativeLayout.LayoutParams) rechargeTitleLl.getLayoutParams();
        lp_title_ll.width = metrics.widthPixels / 2;
        rechargeTitleLl.setLayoutParams(lp_title_ll);

        LinearLayout.LayoutParams lp_cursorLl = (LinearLayout.LayoutParams) cursorLl.getLayoutParams();
        lp_cursorLl.width = metrics.widthPixels / 2;
        cursorLl.setLayoutParams(lp_cursorLl);


    }

    /*
     * 初始化ViewPager
     */
    public void initViewPager() {
        mPager.setOffscreenPageLimit(2);
        fragmentList = new ArrayList<BaseFragment>();


        //游戏
        RechargeGameFragment oFragment = new RechargeGameFragment(mApplication, this, this);
        Bundle bundle = new Bundle();
        bundle.putInt("KEY", 0);
        oFragment.setArguments(bundle);
        fragmentList.add(oFragment);

        //金币
        RechargePlatformCurrencyFragment eFragment = new RechargePlatformCurrencyFragment(mApplication, this, this);
        fragmentList.add(eFragment);

        //给ViewPager设置适配器
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mPager.setAdapter(mMyFragmentPagerAdapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            LayoutParams ll = (LayoutParams) barText.getLayoutParams();

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
            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
            }
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (Util.popupWindow != null && Util.popupWindow.isShowing()) {
                Util.popupWindow.dismiss();
            }
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("RechargeActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("RechargeActivity");
        super.onResume();
    }
}
