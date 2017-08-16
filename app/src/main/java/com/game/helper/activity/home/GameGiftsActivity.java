package com.game.helper.activity.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.HomeGameGiftsAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter;
import com.game.helper.adapter.mall.ImagePagerAdapter.MsetV;
import com.game.helper.download.bean.AppContent;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAdListTask;
import com.game.helper.net.task.QueryGameGiftTask;
import com.game.helper.sdk.model.returns.GetAdList;
import com.game.helper.sdk.model.returns.QueryGameGift;
import com.game.helper.sdk.model.returns.QueryGameGift.GameGift;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * @Description 游戏礼包
 * @Path com.game.helper.activity.home.GameGiftsActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午4:27:34
 * @Company
 */
public class GameGiftsActivity extends BaseActivity implements MsetV, OnRefreshListener {

    @BindView(R.id.viewPager)
    AutoScrollViewPager viewPager; // 显示轮播图
    @BindView(R.id.imgTipsLayout)
    LinearLayout imgTipsLayout;  // 显示小圆点

    @BindView(R.id.gameGift_listView)
    ListView gameGift_listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.tv_set)
    TextView tv_set;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.gifts_refresh_view)
    PullToRefreshLayout giftsRefreshView;
    private List<GameGift> mList = new ArrayList<GameGift>();
    HomeGameGiftsAdapter mHomeGameGiftsAdapter;
    int page = 0;

    private ImagePagerAdapter mImagePagerAdapter;
    private List<ImageView> imageViewTips = new ArrayList<ImageView>(); // 装载小圆点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_game_gifts);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        giftsRefreshView.setOnRefreshListener(this);
        topTitle.setText("游戏礼包");
        topLeftBack.setVisibility(View.VISIBLE);
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.shouye_19));
        top_liner_right.setVisibility(View.VISIBLE);
        tv_set.setVisibility(View.VISIBLE);

        mImagePagerAdapter = new ImagePagerAdapter(mContext, new ArrayList<AppContent>(), 4);
        mImagePagerAdapter.setLiser(this);
        mImagePagerAdapter.setInfiniteLoop(true);
        viewPager.setAdapter(mImagePagerAdapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        mHomeGameGiftsAdapter = new HomeGameGiftsAdapter(mContext, mList);
        gameGift_listView.setAdapter(mHomeGameGiftsAdapter);
        gameGift_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();

                bundle.putString("gameId", mHomeGameGiftsAdapter.getmList().get(arg2).gameId);
                bundle.putString("gameName", mHomeGameGiftsAdapter.getmList().get(arg2).gameName);
                bundle.putString("fileAskPath", mHomeGameGiftsAdapter.getmList().get(arg2).fileAskPath);
                bundle.putString("logo", mHomeGameGiftsAdapter.getmList().get(arg2).logo);
                bundle.putString("logoThumb", mHomeGameGiftsAdapter.getmList().get(arg2).logoThumb);
                startActivity(GiftsActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        if (null != mList && mList.size() > 0) {
            mList.clear();
        }
        getGameGiftList(0);

    }

    private void getGameGiftList(int offset) {
        new QueryGameGiftTask(mContext, offset + "", new Back() {

            @Override
            public void success(Object object, String msg) {
                giftsRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (object != null && object instanceof QueryGameGift) {
                    QueryGameGift mQueryGameGift = (QueryGameGift) object;
                    if (mQueryGameGift.data != null && mQueryGameGift.data.size() >= 0) {
                        mList.addAll(mQueryGameGift.data);
                        page++;
                        if (mQueryGameGift.data.size() < 9) {
                            ToastUtil.showToast(mContext, "已经到底了");
                        }
                        Util.setHeight(mList.size(), gameGift_listView, GameGiftsActivity.this);
                        mHomeGameGiftsAdapter.setmList(mList);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                giftsRefreshView.refreshFinish(PullToRefreshLayout.FAIL);
                ToastUtil.showToast(GameGiftsActivity.this, msg);
            }
        }).start();
    }

    public void setTV() {
        mImagePagerAdapter.setImageIdList(BaseApplication.mInstance.imageIdList4);
        initImageViewTips();
        ref();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                //搜索礼包
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_RESULT", 1);
                startActivity(SearchActivity.class, bundle);

                break;
            default:
                super.onClick(v);
                break;
        }
    }

    /**
     * 初始化圆点
     */
    private void initImageViewTips() {

        imgTipsLayout.removeAllViews();
        imageViewTips = new ArrayList<ImageView>();

        for (int i = 0; i < ListUtils.getSize(mImagePagerAdapter.getImageIdList()); i++) {
            ImageView imageViewTip = new ImageView(mContext);
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
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getGameGiftList(page);
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
                            imageViewTips.get(i).setBackgroundResource(R.drawable.gh_cecolor_oval_shape_nor);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    int i = 0;

    @Override
    public void ref() {
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

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("GameGiftsActivity");
        super.onResume();
        /*if(mImagePagerAdapter.getImageIdList().size()==0){
            setTV();
		}*/
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetAdList4, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetAdList.class, json);
            if (mObject != null && mObject instanceof GetAdList) {
                GetAdList mData = (GetAdList) mObject;
                if (mData != null && mData.data != null && mData.data.size() >= 0) {
                    BaseApplication.mInstance.imageIdList4 = mData.data;
                    setTV();
                }
            }
        }
        new GetAdListTask(mContext, "3", new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetAdList) {
                    GetAdList list = (GetAdList) object;
                    if (list != null && list.data != null && list.data.size() >= 0) {
                        BaseApplication.mInstance.imageIdList4 = list.data;
                        setTV();
                        SharedPreUtil.putStringValue(mContext, ACTION_GetAdList4, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {

            }
        }).start();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("GameGiftsActivity");
        super.onPause();
    }


}
