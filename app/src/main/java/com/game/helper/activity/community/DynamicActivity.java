package com.game.helper.activity.community;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.fragment.community.ConcernedPeopleDynamicFragment;
import com.game.helper.fragment.community.SociatyCircleDynamicFragment;
import com.game.helper.fragment.community.SquareDynamicFragment;
import com.game.helper.sdk.model.returns.LoginData;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 广场/公会圈/关注的人-动态列表
 * @Path com.game.helper.activity.community.DynamicActivity.java
 * @Author lbb
 * @Date 2016年8月26日 上午9:33:04
 * @Company
 */
public class DynamicActivity extends BaseActivity {

    @BindView(R.id.tv_guid1)
    TextView view1;
    @BindView(R.id.tv_guid2)
    TextView view2;
    @BindView(R.id.tv_guid3)
    TextView view3;
    @BindView(R.id.cursor)
    ImageView barText;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private ArrayList<BaseFragment> fragmentList;

    LoginData user;
    private int currIndex = 0;//当前页卡编号
    private String guildId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_dynamic);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("动态");
        topLeftBack.setVisibility(View.VISIBLE);


    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        guildId = getIntent().getStringExtra("guildId");
        int isHome = getIntent().getIntExtra("isHome", 0);
        if (isHome == 0) {
            if (!TextUtils.isEmpty(guildId) && !guildId.equals("0") && user != null && !TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0") && guildId.equals(user.guildId)) {//自己已加入的公会
                /*topRight.setText("发布动态");
            topRight.setVisibility(View.VISIBLE);*/
                top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable._shiqu_17));
                top_liner_right.setVisibility(View.VISIBLE);
            }
        } else {
            top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable._shiqu_17));
            top_liner_right.setVisibility(View.VISIBLE);
        }

        view1.setSelected(true);
        view2.setSelected(false);
        view3.setSelected(false);

        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
        currIndex = getIntent().getIntExtra("currIndex", 0);
        initTextBar();
        initViewPager();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                Bundle budle1 = new Bundle();
                budle1.putString("guildId", guildId);
                startActivity(ReleaseDynamicActivity.class, budle1);
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
        // 1/2屏幕宽度
        int tabLineLength = metrics.widthPixels / 3;
        LayoutParams lp = (LayoutParams) barText.getLayoutParams();
        lp.width = tabLineLength;
        barText.setLayoutParams(lp);

    }

    /*
     * 初始化ViewPager
     */
    public void initViewPager() {
        mPager.setOffscreenPageLimit(3);
        fragmentList = new ArrayList<BaseFragment>();

        Bundle bundle0 = new Bundle();
        bundle0.putInt("currIndex", currIndex);
        SquareDynamicFragment oFragment = new SquareDynamicFragment(mApplication, this, this);
        oFragment.setArguments(bundle0);
        fragmentList.add(oFragment);

        SociatyCircleDynamicFragment eFragment = new SociatyCircleDynamicFragment(mApplication, this, this);
        Bundle bundle = new Bundle();
        bundle.putInt("currIndex", currIndex);
        bundle.putString("guildId", guildId);
        eFragment.setArguments(bundle);
        fragmentList.add(eFragment);

        ConcernedPeopleDynamicFragment gFragment = new ConcernedPeopleDynamicFragment(mApplication, this, this);
        gFragment.setArguments(bundle0);
        fragmentList.add(gFragment);

        //给ViewPager设置适配器
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mPager.setAdapter(mMyFragmentPagerAdapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            LayoutParams ll = (LayoutParams) barText
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
            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
                view3.setSelected(false);
            } else if (i == 3) {
                view1.setSelected(false);
                view2.setSelected(false);
                view3.setSelected(true);
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
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("DynamicActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("DynamicActivity");
        super.onResume();
    }
}
