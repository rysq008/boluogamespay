package com.game.helper.activity.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.MineGameAdapter;
import com.game.helper.adapter.home.MyFragmentPagerAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.fragment.gameclassify.GameClassifyDiscountFragment;
import com.game.helper.fragment.gameclassify.WebGameFragment;
import com.game.helper.fragment.home.GameClassifyBtGameFragment;
import com.game.helper.fragment.home.HomePageListHotGameFragment;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.model.home.Kind;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @Description 游戏分类
 * @Path com.game.helper.activity.home.GameClassifyActivity.java
 * @Author lbb
 * @Date 2016年8月23日 下午4:21:22
 * @Company
 */
public class GameClassifyActivity extends BaseActivity {
    /* @BindView(R.id.listView_Classify)
     GridView listView_Classify;*/
    @BindView(R.id.classify_cursor_iv)
    ImageView classifyCursorIv;
    @BindView(R.id.game_classify_tv_guid1)
    TextView gameClassifyTvGuid1;
    /* @BindView(R.id.game_classify_tv_guid2)
     TextView gameClassifyTvGuid2;*/
    @BindView(R.id.game_classify_tv_guid3)
    TextView gameClassifyTvGuid3;
    @BindView(R.id.game_classify_tv_guid4)
    TextView gameClassifyTvGuid4;
    @BindView(R.id.game_classify_top_ll)
    RelativeLayout gameClassifyTopLl;
    @BindView(R.id.classify_showlist_viewPager)
    ViewPager classifyShowlistViewPager;
    @BindView(R.id.home_game_classify_title_ll)
    LinearLayout homeGameClassifyTitleLl;
    @BindView(R.id.game_classify_all_LinearLayout_searcher)
    LinearLayout gameClassifyAllLinearLayoutSearcher;
    @BindView(R.id.classify_topLeftBack)
    ImageView classifyTopLeftBack;
    MineGameAdapter mMineGameAdapter;

    private int currIndex = 0;//当前页卡编号
    ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_game_classify);
        ButterKnife.bind(this);
        // Register
        //EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        currIndex=getIntent().getIntExtra("page_num",0);
        initViewPager();

            if (currIndex==1){
                gameClassifyTvGuid1.setSelected(false);
                gameClassifyTvGuid3.setSelected(true);
                gameClassifyTvGuid4.setSelected(false);
            }else if (currIndex==2){
                gameClassifyTvGuid1.setSelected(false);
                gameClassifyTvGuid3.setSelected(false);
                gameClassifyTvGuid4.setSelected(true);
            }else if (currIndex==0){
                gameClassifyTvGuid1.setSelected(true);
                gameClassifyTvGuid3.setSelected(false);
                gameClassifyTvGuid4.setSelected(false);

            }

        gameClassifyTvGuid1.setOnClickListener(new txListener(0));
        //gameClassifyTvGuid2.setOnClickListener(new txListener(1));
        gameClassifyTvGuid3.setOnClickListener(new txListener(1));
        gameClassifyTvGuid4.setOnClickListener(new txListener(2));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        initTextBar();
        super.onWindowFocusChanged(hasFocus);
    }

    /*
           * 初始化图片的位移像素
           */
    public void initTextBar() {

        Display display = getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/3屏幕宽度
        int tabLineLength = homeGameClassifyTitleLl.getWidth() / 3;
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) classifyCursorIv.getLayoutParams();
        lp.width = tabLineLength;
        classifyCursorIv.setLayoutParams(lp);

    }


    private void initViewPager() {
        classifyShowlistViewPager.setOffscreenPageLimit(1);//页面预加载

        //折扣专区
        GameClassifyDiscountFragment gameClassifyDiscountFragment = GameClassifyDiscountFragment.newInstance();
        fragmentList.add(gameClassifyDiscountFragment);

        //变态手游
        GameClassifyBtGameFragment gameClassifyBtGameFragment = GameClassifyBtGameFragment.newInstance();
        fragmentList.add(gameClassifyBtGameFragment);

        //手机页游
        WebGameFragment webGameFragment = WebGameFragment.newInstance();
        //new HomePageListSaleGameFragment(mApplication, this, mContext, mList);
        fragmentList.add(webGameFragment);

       /* //特价推荐
        HomePageListSaleGameFragment homePageListSaleGameFragment1 = HomePageListSaleGameFragment.newInstance(mList);
        // new HomePageListSaleGameFragment(mApplication, this, mContext, mList);
        fragmentList.add(homePageListSaleGameFragment1);
*/

        //给ViewPager设置适配器
        MyFragmentPagerAdapter mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        classifyShowlistViewPager.setAdapter(mMyFragmentPagerAdapter);
        classifyShowlistViewPager.setOnPageChangeListener(new MyListOnPageChangeListener());//页面变化时的监听器
        classifyShowlistViewPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页

    }

    public class MyListOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) classifyCursorIv.getLayoutParams();

            if (currIndex == arg0) {
                ll.leftMargin = (int) (currIndex * classifyCursorIv.getWidth() + arg1 * classifyCursorIv.getWidth());
            } else if (currIndex > arg0) {
                ll.leftMargin = (int) (currIndex * classifyCursorIv.getWidth() - (1 - arg1) * classifyCursorIv.getWidth());
            }
            classifyCursorIv.setLayoutParams(ll);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int arg0) {
            currIndex = arg0;
            int i = currIndex + 1;
            if (i == 1) {
                gameClassifyTvGuid1.setSelected(true);
                // gameClassifyTvGuid2.setSelected(false);
                gameClassifyTvGuid3.setSelected(false);
                gameClassifyTvGuid4.setSelected(false);
            } else if (i == 2) {
                gameClassifyTvGuid1.setSelected(false);
                // gameClassifyTvGuid2.setSelected(true);
                gameClassifyTvGuid3.setSelected(true);
                gameClassifyTvGuid4.setSelected(false);
            } else if (i == 3) {
                gameClassifyTvGuid1.setSelected(false);
                //gameClassifyTvGuid2.setSelected(false);
                gameClassifyTvGuid3.setSelected(false);
                gameClassifyTvGuid4.setSelected(true);

            }/* else if (i == 4) {
                gameClassifyTvGuid1.setSelected(false);
                //gameClassifyTvGuid2.setSelected(false);
                gameClassifyTvGuid3.setSelected(false);
                gameClassifyTvGuid4.setSelected(true);

            }*/


        }
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            classifyShowlistViewPager.setCurrentItem(index);
        }
    }


    String typeId = "3";
    String kindId = "";//全部分类
    String orderby = "field1";//默认排序

    @Override
    protected void initData() {

        /*//获取网游，单机游戏
        new QueryGameBykindAndTypeTask(mContext, typeId, kindId, orderby, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryGameBykindAndType) {
                    QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
                    if (mData.data != null && mData.data.size() >= 0) {
                        mList.clear();
                        mList.addAll(mData.data);
                        // initStatus1();
                        mMineGameAdapter.setmList(mList);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();*/

    }

    @Override
    @OnClick({ /*R.id.ll_all, R.id.ll_sorting, R.id.ll_sortings
            , */R.id.game_classify_all_LinearLayout_searcher,R.id.classify_topLeftBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_classify_all_LinearLayout_searcher:
                startActivity(SearchActivity.class);
                break;

            case R.id.classify_topLeftBack:
                finish();
                break;
           /* case R.id.ll_all:
                //排序 全部
                TitleSorting(ALL);
                mSortingOneAdapter.setmDatas(types);
                lv_sortingone.setVisibility(View.VISIBLE);
                lv_sortingtwo.setVisibility(View.GONE);
                break;

            case R.id.ll_sorting:
                //排序 智能
                TitleSorting(SORTING);
                lv_sortingtwo.setVisibility(View.VISIBLE);
                lv_sortingone.setVisibility(View.GONE);
                mSortingTwoAdapter.setmDatas(mSortingEntity);
                break;
            case R.id.ll_sortings:
                //关闭排序
                TitleSorting(NOXING);
                msortings.setVisibility(View.GONE);
                break;*/
            default:
                super.onClick(v);
                break;
        }
    }

    /*//排序listview1的点击事件
    class OneOnItemClick implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
            if (xuanzhong == ALL) {
                Kind mKind = (Kind) mSortingOneAdapter.getmDatas().get(arg2);
                setTitlename(xuanzhong, mKind.kindName);
                msortings.setVisibility(View.GONE);
                xuanzhong = NOXING;
                TitleSorting(NOXING);
                lv_sortingtwo.setVisibility(View.GONE);
                mSortingOneAdapter.setpos(arg2);
                kindId = mKind.kindId;
                initData();
            }
        }
    }

    //排序listview2的点击事件
    class TwoOnItemClick implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (xuanzhong == SORTING) {
                SortingEntity mCgametype = (SortingEntity) mSortingTwoAdapter.getmDatas().get(arg2);
                setTitlename(xuanzhong, mCgametype.orderbyName);
                msortings.setVisibility(View.GONE);
                xuanzhong = NOXING;
                TitleSorting(NOXING);
                lv_sortingone.setVisibility(View.GONE);
                mSortingTwoAdapter.setpos(arg2);
                orderby = mCgametype.orderby;
                initData();
            }

        }
    }*/

    /*//标题选中的效果显示//排序listview的显示隐藏
    public void TitleSorting(int ts) {

		*//*mSortingTwoAdapter1.setpos(10000);
        mSortingTwoAdapter2.setpos(10000);*//*
        msortings.setVisibility(View.VISIBLE);


        for (int i = 0; i < tv_ids.length; i++) {
            if (xuanzhong != ts && i == ts) {
                tv_ids[i].setSelected(true);
                final ImageView view = iv_ids[i];
                Animation mclockwise = AnimationUtils.loadAnimation(this, R.anim.rotating_clockwise);
                iv_ids[i].startAnimation(mclockwise);
                mclockwise.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setSelected(true);
                    }
                });
            } else if (xuanzhong == i) {
                tv_ids[i].setSelected(false);
                final ImageView view = iv_ids[i];
                Animation mclockwise = AnimationUtils.loadAnimation(this, R.anim.rotating_clockwise);
                iv_ids[i].startAnimation(mclockwise);
                mclockwise.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setSelected(false);
                    }
                });
            } else {
                iv_ids[i].setSelected(false);
                tv_ids[i].setSelected(false);
            }
        }
        if (xuanzhong == ts) {
            msortings.setVisibility(View.GONE);
            ts = NOXING;
        }
        xuanzhong = ts;
    }
*/
    /*//还原选中效果
    public void TitleSortingOff() {
        msortings.setVisibility(View.GONE);
        for (int i = 0; i < tv_ids.length; i++) {
            iv_ids[i].setSelected(false);
            tv_ids[i].setSelected(false);
        }
    }

    //设置选中的名字
    public void setTitlename(int i, String name) {
        if (tv_ids.length > i) {
            tv_ids[i].setText(name);
        }
    }*/

    int i = 0;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("GameClassifyActivity");
        super.onResume();
        /*if(i==0){
            i=1;
		}else{
			initData();
		}*/
    }

    @Override
    public void onPause() {
        //关闭排序
        //TitleSortingOff();
        //xuanzhong = NOXING;
        //DownLoadManager.getManager().stopAllTask();
        MobclickAgent.onPageEnd("GameClassifyActivity");
        super.onPause();
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
    public void onDestroy() {
        //unregisterReceiver(downloadStatusReceiver);
        // Unregister
        //EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onEventMainThread(DownLoadModel event) {
        if (event != null) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mMineGameAdapter.getmList()) {
                    Log.e("lbb", "--------onEventMainThread42-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

                        mMineGameAdapter.getmList().set(mMineGameAdapter.getmList().indexOf(md), event);
                        mMineGameAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    }


}
