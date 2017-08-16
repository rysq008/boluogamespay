package com.game.helper.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.fragment.mine.DynamicMsgFragment;
import com.game.helper.fragment.mine.RechargeMsgFragment;
import com.game.helper.fragment.mine.SystemMsgFragment;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.model.PushModel;
import com.game.helper.view.widget.WaterDrop;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 系统消息
 * @Path com.game.helper.activity.mine.MineSystemMsgActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午1:51:46
 * @Company
 */
public class MineSystemMsgActivity extends BaseActivity {

    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private ArrayList<BaseFragment> fragmentList;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.tv_guid1)
    TextView view1;
    @BindView(R.id.tv_guid2)
    TextView view2;
    @BindView(R.id.tv_guid3)
    TextView view3;
    @BindView(R.id.cursor)
    ImageView barText;

    private int currIndex;//当前页卡编号

    @BindView(R.id.msg_unread_count1)
    WaterDrop msg_unread_count1;
    @BindView(R.id.msg_unread_count2)
    WaterDrop msg_unread_count2;
    @BindView(R.id.msg_unread_count3)
    WaterDrop msg_unread_count3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_system_msg);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的消息");
        topLeftBack.setVisibility(View.VISIBLE);
        view1.setSelected(true);
        view2.setSelected(false);
        view3.setSelected(false);
        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
        msg_unread_count1.setVisibility(View.INVISIBLE);
        msg_unread_count2.setVisibility(View.INVISIBLE);
        msg_unread_count3.setVisibility(View.INVISIBLE);
        msg_unread_count1.setText("0");
        msg_unread_count1.setTextSize(10);
        msg_unread_count2.setText("0");
        msg_unread_count2.setTextSize(10);
        msg_unread_count3.setText("0");
        msg_unread_count3.setTextSize(10);

        currIndex = getIntent().getIntExtra("currIndex", 0);
        setRead();

        initTextBar();
        initViewPager();
    }

    @Override
    protected void initData() {
        receiver = new BaseBroadcast(this) {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_GET_INFO.equals(action)) {
                    setRead();
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_GET_INFO);
        registerReceiver(receiver, filter);
    }

    @Override
    @OnClick({R.id.top_left_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    public void setRead() {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        List<PushModel> list = DBManager.getInstance(mContext).getPushAllMessage();
        for (PushModel push : list) {
            if (TextUtils.isEmpty(push.tradetype)) {
                if (push.isRead == 0) {
                    count1++;
                }

            } else if (!TextUtils.isEmpty(push.tradetype) && (push.tradetype.equals("6") || push.tradetype.equals("7") || push.tradetype.equals("8"))) {
                if (push.isRead == 0) {
                    count3++;
                }
            } else if (!TextUtils.isEmpty(push.tradetype)) {
                if (push.isRead == 0) {
                    count2++;
                }
            }
        }
        if (count1 > 0) {
            msg_unread_count1.setText("" + count1);
            msg_unread_count1.setVisibility(View.VISIBLE);
        } else {
            msg_unread_count1.setText("0");
            msg_unread_count1.setVisibility(View.INVISIBLE);
        }
        if (count2 > 0) {
            msg_unread_count2.setText("" + count2);
            msg_unread_count2.setVisibility(View.VISIBLE);
        } else {
            msg_unread_count2.setText("0");
            msg_unread_count2.setVisibility(View.INVISIBLE);
        }
        if (count3 > 0) {
            msg_unread_count3.setText("" + count3);
            msg_unread_count3.setVisibility(View.VISIBLE);
        } else {
            msg_unread_count3.setText("0");
            msg_unread_count3.setVisibility(View.INVISIBLE);
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
        // 1/3屏幕宽度
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

        SystemMsgFragment eFragment = new SystemMsgFragment(mApplication, this, this);
        fragmentList.add(eFragment);
        DynamicMsgFragment gFragment = new DynamicMsgFragment(mApplication, this, this);
        fragmentList.add(gFragment);
        RechargeMsgFragment sFragment = new RechargeMsgFragment(mApplication, this, this);
        fragmentList.add(sFragment);
        //给ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        mPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            // 取得该控件的实例
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
            // TODO Auto-generated method stub

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
        MobclickAgent.onPageEnd("MineSystemMsgActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineSystemMsgActivity");
        super.onResume();
    }
}
