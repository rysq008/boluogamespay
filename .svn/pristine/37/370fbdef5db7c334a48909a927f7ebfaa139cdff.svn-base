package com.game.helper.activity.community;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.DetailsAdapter;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.task.GetGiveListTask;
import com.game.helper.sdk.model.returns.GetGiveList;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;
import com.game.helper.sdk.model.returns.GetGiveList.GetGiveListData;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 收到的礼物
 * @Path com.game.helper.activity.community.GiftsReceivedActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午2:40:33
 * @Company
 */
public class GiftsReceivedActivity extends BaseActivity {
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_left_layout)
    LinearLayout topLeftLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.isDownload1)
    View isDownload1;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.listView)
    ListViewForScrollView listView;
    DetailsAdapter mDetailsAdapter;
    List<GetGiveListData> data=new ArrayList<>();
    String userId;
/*
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.tv_guid1)
    TextView view1;
    @BindView(R.id.tv_guid2)
    TextView view2;
    @BindView(R.id.cursor)
    ImageView barText;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private ArrayList<BaseFragment> fragmentList;
    private int currIndex = 0;//当前页卡编号*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_gifts_received);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("收到的礼物");
        topLeftBack.setVisibility(View.VISIBLE);

        mDetailsAdapter=new DetailsAdapter(this,data);
        listView.setAdapter(mDetailsAdapter);
       /* view1.setSelected(true);
        view2.setSelected(false);
        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        currIndex = getIntent().getIntExtra("currIndex", 0);
        initTextBar();
        initViewPager();*/
    }

    @Override
    protected void initData() {
        userId=getIntent().getStringExtra("userId");
        new GetGiveListTask(this,true, userId, new BaseBBXTask.Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if(object!=null&& object instanceof GetGiveList){
                    GetGiveList mGetGiveList=(GetGiveList) object;
                    if(mGetGiveList.data!=null){
                        if(mGetGiveList.data.giftGiveList.size()>=0){
                            data.clear();
                            data.addAll(mGetGiveList.data.giftGiveList);
                            mDetailsAdapter.setData(data);
                        }
                        if(!TextUtils.isEmpty(mGetGiveList.data.sumBenefit)){
                            tv_total.setText(mGetGiveList.data.sumBenefit);
                        }
                        SharedPreUtil.putStringValue(GiftsReceivedActivity.this, ACTION_GetGiveList+userId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json=SharedPreUtil.getStringValue(GiftsReceivedActivity.this, ACTION_GetGiveList+userId,"");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject =new JsonBuild().getData(GetGiveList.class, json);
                    if(mObject !=null&& mObject instanceof GetGiveList){
                        GetGiveList mData=(GetGiveList) mObject;
                        if(mData!=null &&mData.data!=null){
                            if(mData.data.giftGiveList.size()>=0){
                                data.clear();
                                data.addAll(mData.data.giftGiveList);
                                mDetailsAdapter.setData(data);
                            }
                            if(!TextUtils.isEmpty(mData.data.sumBenefit)){
                                tv_total.setText(mData.data.sumBenefit);
                            }
                        }
                    }
                }

            }
        }).start();
    }

 @Override
    @OnClick({R.id.topLeftBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topLeftBack:
                finish1();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    /*   public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    *//*
     * 初始化图片的位移像素
     *//*
    public void initTextBar() {

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/2屏幕宽度
        int tabLineLength = metrics.widthPixels / 2;
        LayoutParams lp = (LayoutParams) barText.getLayoutParams();
        lp.width = tabLineLength;
        barText.setLayoutParams(lp);

    }

    *//*
     * 初始化ViewPager
     *//*
    public void initViewPager() {
        mPager.setOffscreenPageLimit(2);
        fragmentList = new ArrayList<BaseFragment>();

        Bundle bundle = new Bundle();
        bundle.putString("userId", getIntent().getStringExtra("userId"));
        //排行
        ListsFragment oFragment = new ListsFragment(mApplication, this, this);
        oFragment.setArguments(bundle);
        fragmentList.add(oFragment);
        //明细
        DetailsFragment eFragment = new DetailsFragment(mApplication, this, this);
        eFragment.setArguments(bundle);
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
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("GiftsReceivedActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GiftsReceivedActivity");
        super.onResume();
    }


}
