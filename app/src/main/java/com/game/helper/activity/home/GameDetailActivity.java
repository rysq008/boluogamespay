package com.game.helper.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.WebActivity;
import com.game.helper.adapter.home.SpecialgameAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.fragment.home.CommentFragment;
import com.game.helper.fragment.home.GameDetailFragment;
import com.game.helper.fragment.home.GiftsFragment;
import com.game.helper.fragment.home.RechargeFragment;
import com.game.helper.fragment.home.StrategyFragment;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.BrowseTask;
import com.game.helper.net.task.GetGameByIdTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetGameById;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.Util;
import com.game.helper.view.dialog.ShareDialog;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.EventBus;

/**
 * @Description 游戏详情
 * @Path com.game.helper.activity.home.GameDetailActivity.java
 * @Author lbb
 * @Date 2016年8月23日 上午10:31:12
 * @Company
 */
public class GameDetailActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.iv_item)
    XCRoundImageViewByXfermode iv_item;
    @BindView(R.id.tv_item)
    TextView tv_item;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_datasize)
    TextView tv_datasize;
    /*
        @BindView(R.id.mLinearload)
        LinearLayout mLinearload;*/
    @BindView(R.id.tv_fromdownload)
    TextView tv_fromdownload;
    /*@BindView(R.id.tv_first)
    TextView tv_first;
    @BindView(R.id.tv_gift)
    TextView tv_gift;*/
    /*@BindView(R.id.tv_msg)
    TextView tv_msg;*/
    @BindView(R.id.mLinear_dz)
    LinearLayout mLinear_dz;
    @BindView(R.id.tv_dz)
    TextView tv_dz;
    /* @BindView(R.id.tv_first3)
     TextView tv_first3;*/
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.game_detail_tv_download)
    TextView gameDetailTvDownload;
    @BindView(R.id.game_detail_lv)
    ListView gameDetailLv;
    @BindView(R.id.game_detail_sczk_tv)
    TextView gameDetailSczkTv;
    @BindView(R.id.game_detail_xczk_tv)
    TextView gameDetailXczkTv;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.game_detail_discount_rl)
    RelativeLayout gameDetailDiscountRl;

    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private ArrayList<BaseFragment> fragmentList;
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.tv_guid1)
    TextView view1;
    @BindView(R.id.tv_guid2)
    TextView view2;
    @BindView(R.id.tv_guid3)
    TextView view3;
    /* @BindView(R.id.tv_guid4)
     TextView view4;*/
    @BindView(R.id.tv_guid5)
    TextView view5;
    @BindView(R.id.cursor)
    ImageView barText;
    private int currIndex = 0;//当前页卡编号
    String gameId;
    String detail = null, fileSize = null;
    AppContent mAppContent;
    int num = 2;
    int tag = 0;//0代表view3显示，1代表view4显示，2代表view5显示
    //，3代表view3/view4显示，4代表view3/view5显示，5代表view4/view5显示,6代表view3/view4/view5显示

    private Handler handlers = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
                default:
                    break;
            }
        }

    };
    public AppContent appContent;
    private SpecialgameAdapter mSpecialgameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_game_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initView() {
        gameId = getIntent().getStringExtra("gameId");
        List<AppContent> mList = new ArrayList<>();
        appContent = getIntent().getParcelableExtra("appcontent");
        mList.add(appContent);
        topTitle.setText("游戏详情");
        topLeftBack.setVisibility(View.VISIBLE);

        topLinerRight.setVisibility(View.VISIBLE);
        topIvRight.setVisibility(View.VISIBLE);
        topIvRight.setImageDrawable(getResources().getDrawable(R.drawable.share));
        /*topRight.setText("分享");
        topRight.setVisibility(View.VISIBLE);
*/
        if (mList.size() >= 1) {
            mSpecialgameAdapter = new SpecialgameAdapter(this, mList, 3, R.id.home_fragment_pagelist_newgame_lv);
            gameDetailLv.setAdapter(mSpecialgameAdapter);
            gameDetailLv.setEnabled(false);
        }
    }

    LoginData user;

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        num = 5;
        tag = 6;
        getGameData();
    }

    public void getGameData() {
        gameDetailXczkTv.setText(appContent.xczk);
        gameDetailSczkTv.setText(appContent.sczk);

        new GetGameByIdTask(mContext, gameId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetGameById) {
                    GetGameById mGetGameById = (GetGameById) object;
                    if (mGetGameById.data != null) {
                        mAppContent = mGetGameById.data;
                        Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                .load("" + mGetGameById.data.fileAskPath + mGetGameById.data.logo)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                //.centerCrop()// 长的一边撑满
                                //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                //.crossFade()
                                .into(iv_item);
                        tv_item.setText(mGetGameById.data.gameName);
                        tv_type.setText(mGetGameById.data.kindName);
                        if (!TextUtils.isEmpty(mAppContent.fileSize)) {
                            tv_datasize.setText("" + mAppContent.fileSize + "M");
                            fileSize = mGetGameById.data.fileSize;
                        } else {
                            tv_datasize.setText("0.0" + "M");
                            fileSize = "0.0";
                        }

                        tv_fromdownload.setText(mGetGameById.data.platName);
                        // mLinearload.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(mGetGameById.data.sczk) && Double.parseDouble(mGetGameById.data.sczk) > 0) {
                            //tv_first.setText("首充");
                            tv_dz.setText("" + mGetGameById.data.sczk);
                            gameDetailSczkTv.setVisibility(View.VISIBLE);
                        } else if (!TextUtils.isEmpty(mGetGameById.data.xczk) && Double.parseDouble(mGetGameById.data.xczk) > 0) {
                            //tv_first.setText("续充");
                            gameDetailXczkTv.setText("" + mGetGameById.data.xczk);
                            mLinear_dz.setVisibility(View.VISIBLE);
                        } else {
                            mLinear_dz.setVisibility(View.GONE);
                        }

                        detail = mGetGameById.data.detail;
                        LoginData user = DBManager.getInstance(mContext).getUserMessage();
                        new BrowseTask(mContext, user.userId, gameId, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                // TODO Auto-generated method stub

                            }
                        }).start();
                    }
                }
                view1.setSelected(true);
                view2.setSelected(false);

                view3.setSelected(false);
                //view4.setSelected(false);
                view5.setSelected(false);
                //0代表view3显示，1代表view4显示，2代表view5显示
                //，3代表view3/view4显示，4代表view3/view5显示，5代表view4/view5显示,6代表view3/view4/view5显示
                if (num == 2) {
                    view3.setVisibility(View.GONE);
                    // view4.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                } else if (num == 3) {
                    if (tag == 0) {
                        view3.setVisibility(View.VISIBLE);
                        //view4.setVisibility(View.GONE);
                        view5.setVisibility(View.GONE);
                    } else if (tag == 1) {
                        view3.setVisibility(View.GONE);
                        //view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.GONE);
                    } else if (tag == 2) {
                        view3.setVisibility(View.GONE);
                        //view4.setVisibility(View.GONE);
                        view5.setVisibility(View.VISIBLE);
                    }

                } else if (num == 4) {
                    if (tag == 3) {
                        view3.setVisibility(View.VISIBLE);
                        //view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.GONE);
                    } else if (tag == 4) {
                        view3.setVisibility(View.VISIBLE);
                        //view4.setVisibility(View.GONE);
                        view5.setVisibility(View.VISIBLE);
                    } else if (tag == 5) {
                        view3.setVisibility(View.GONE);
                        //view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.VISIBLE);
                    }
                } else if (num == 5) {
                    view3.setVisibility(View.VISIBLE);
                    //view4.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);
                }

                if (num == 2) {
                    view1.setOnClickListener(new txListener(0));
                    view2.setOnClickListener(new txListener(1));
                    //view3.setOnClickListener(new txListener(2));
                    //view4.setOnClickListener(new txListener(2));
                    //view5.setOnClickListener(new txListener(3));
                } else if (num == 3) {
                    if (tag == 0) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        //view5.setOnClickListener(new txListener(3));
                    } else if (tag == 1) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        //view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        //view5.setOnClickListener(new txListener(2));
                    } else if (tag == 2) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        //view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        view5.setOnClickListener(new txListener(2));
                    }

                } else if (num == 4) {
                    if (tag == 3) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(3));
                        //view5.setOnClickListener(new txListener(3));
                    } else if (tag == 4) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        view5.setOnClickListener(new txListener(3));
                    } else if (tag == 5) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        //view3.setOnClickListener(new txListener(2));
                        // view4.setOnClickListener(new txListener(2));
                        view5.setOnClickListener(new txListener(3));
                    }

                } else if (num == 5) {
                    view1.setOnClickListener(new txListener(0));
                    view2.setOnClickListener(new txListener(1));
                    view3.setOnClickListener(new txListener(2));
                    //view4.setOnClickListener(new txListener(3));
                    view5.setOnClickListener(new txListener(3));
                }

                currIndex = getIntent().getIntExtra("currIndex", 0);
                initTextBar();
                initViewPager();


            }

            @Override
            public void fail(String status, String msg, Object object) {
                view1.setSelected(true);
                view2.setSelected(false);
                view3.setSelected(false);
                //view4.setSelected(false);
                view5.setSelected(false);
                //0代表view3显示，1代表view4显示，2代表view5显示
                //，3代表view3/view4显示，4代表view3/view5显示，5代表view4/view5显示,6代表view3/view4/view5显示
                if (num == 2) {
                    view3.setVisibility(View.GONE);
                    // view4.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                } else if (num == 3) {
                    if (tag == 0) {
                        view3.setVisibility(View.VISIBLE);
                        // view4.setVisibility(View.GONE);
                        view5.setVisibility(View.GONE);
                    } else if (tag == 1) {
                        view3.setVisibility(View.GONE);
                        //view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.GONE);
                    } else if (tag == 2) {
                        view3.setVisibility(View.GONE);
                        // view4.setVisibility(View.GONE);
                        view5.setVisibility(View.VISIBLE);
                    }

                } else if (num == 4) {
                    if (tag == 3) {
                        view3.setVisibility(View.VISIBLE);
                        // view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.GONE);
                    } else if (tag == 4) {
                        view3.setVisibility(View.VISIBLE);
                        //view4.setVisibility(View.GONE);
                        view5.setVisibility(View.VISIBLE);
                    } else if (tag == 5) {
                        view3.setVisibility(View.GONE);
                        //view4.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.VISIBLE);
                    }
                } else if (num == 5) {
                    view3.setVisibility(View.VISIBLE);
                    //view4.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);
                }

                if (num == 2) {
                    view1.setOnClickListener(new txListener(0));
                    view2.setOnClickListener(new txListener(1));
                    //view3.setOnClickListener(new txListener(2));
                    //view4.setOnClickListener(new txListener(2));
                    //view5.setOnClickListener(new txListener(3));
                } else if (num == 3) {
                    if (tag == 0) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        //view5.setOnClickListener(new txListener(3));
                    } else if (tag == 1) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        //view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        //view5.setOnClickListener(new txListener(2));
                    } else if (tag == 2) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        //view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        view5.setOnClickListener(new txListener(2));
                    }

                } else if (num == 4) {
                    if (tag == 3) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(3));
                        //view5.setOnClickListener(new txListener(3));
                    } else if (tag == 4) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        view3.setOnClickListener(new txListener(2));
                        //view4.setOnClickListener(new txListener(2));
                        view5.setOnClickListener(new txListener(3));
                    } else if (tag == 5) {
                        view1.setOnClickListener(new txListener(0));
                        view2.setOnClickListener(new txListener(1));
                        //view3.setOnClickListener(new txListener(2));
                        // view4.setOnClickListener(new txListener(2));
                        view5.setOnClickListener(new txListener(3));
                    }

                } else if (num == 5) {
                    view1.setOnClickListener(new txListener(0));
                    view2.setOnClickListener(new txListener(1));
                    view3.setOnClickListener(new txListener(2));
                    //view4.setOnClickListener(new txListener(3));
                    view5.setOnClickListener(new txListener(3));
                }
                currIndex = getIntent().getIntExtra("currIndex", 0);
                initTextBar();
                initViewPager();

            }
        }).start();
    }

    private ShareDialog shareDialog;

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_iv_right, R.id.game_detail_discount_rl})
//,R.id.top_liner_right
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_iv_right:
                shareDialog = new ShareDialog(this);
                shareDialog.setCancelButtonOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();

                    }
                });
                shareDialog.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                        if (item.get("ItemText").equals("QQ好友")) {
                            ShareParams sp = new ShareParams();
                            if (mAppContent != null) {
                                sp.setTitle(mAppContent.gameName);
                                sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGameDetail?gameId=" + gameId); // 标题的超链接
                                sp.setText(mAppContent.intro);
                                sp.setImageUrl("" + mAppContent.fileAskPath + mAppContent.logo);//分享网络图片

                                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                                qq.setPlatformActionListener(GameDetailActivity.this); // 设置分享事件回调
                                // 执行分享
                                qq.share(sp);
                            } else {
                                Toast.makeText(GameDetailActivity.this, "暂无游戏详情", Toast.LENGTH_LONG).show();
                            }
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle(mAppContent.gameName);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGameDetail?gameId=" + gameId); // 标题的超链接
                            sp.setText(mAppContent.intro);
                            sp.setImageUrl("" + mAppContent.fileAskPath + mAppContent.logo);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toGameDetail?gameId=" + gameId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(GameDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText(mAppContent.gameName + "，" + mAppContent.intro + Config.getInstance().IP + "/helper/app/toGameDetail?gameId=" + gameId);
                            sp.setImageUrl("" + mAppContent.fileAskPath + mAppContent.logo);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(GameDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle(mAppContent.gameName);
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText(mAppContent.intro);
                            sp.setImageUrl("" + mAppContent.fileAskPath + mAppContent.logo);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGameDetail?gameId=" + gameId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(GameDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle(mAppContent.gameName + "," + mAppContent.intro);
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText(mAppContent.intro);//设置了不会显示,可选参数
                            sp.setImageUrl("" + mAppContent.fileAskPath + mAppContent.logo);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGameDetail?gameId=" + gameId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(GameDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(GameDetailActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                break;

            case R.id.game_detail_discount_rl:
                Bundle bundleweb = new Bundle();
                bundleweb.putString("Url", "http://ghelper.h5h5h5.cn/helper/app/toBannerDisCount");
                bundleweb.putString("TITLE", "自动折扣步骤");
                ((BaseActivity) mContext).startActivity(WebActivity.class, bundleweb);
                break;
            default:
                super.onClick(v);
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
        // 宽度
        int tabLineLength = metrics.widthPixels / 4;
        LayoutParams lp = (LayoutParams) barText.getLayoutParams();
        lp.width = tabLineLength;
        barText.setLayoutParams(lp);

    }

    /*
     * 初始化ViewPager
     */
    public void initViewPager() {
        mPager.setOffscreenPageLimit(num);
        fragmentList = new ArrayList<BaseFragment>();
        Bundle args = new Bundle();
        args.putString("gameId", gameId);
        args.putString("detail", detail);
        args.putString("fileSize", fileSize);
        if (mAppContent != null) {
            args.putString("fileAskPath", mAppContent.fileAskPath);
            args.putString("logo", mAppContent.logo);
            args.putString("logoThumb", mAppContent.logoThumb);
        }
        args.putParcelable("appContent", mAppContent);

        //游戏充值
        RechargeFragment eFragment = new RechargeFragment(mApplication, this, this);
        eFragment.setArguments(args);
        fragmentList.add(eFragment);

        //游戏详情
        GameDetailFragment oFragment = new GameDetailFragment(mApplication, this, this);
        oFragment.setArguments(args);
        fragmentList.add(oFragment);
        if (num == 3) {
            if (tag == 0) {
                GiftsFragment gFragment = new GiftsFragment(mApplication, this, this);
                gFragment.setArguments(args);
                fragmentList.add(gFragment);
            } else if (tag == 1) {
                StrategyFragment sFragment = new StrategyFragment(mApplication, this, this);
                sFragment.setArguments(args);
                //fragmentList.add(sFragment);
            }
            if (tag == 2) {
                CommentFragment cFragment = new CommentFragment(mApplication, this, this);
                cFragment.setArguments(args);
                fragmentList.add(cFragment);
            }
        } else if (num == 4) {
            if (tag == 3) {
                GiftsFragment gFragment = new GiftsFragment(mApplication, this, this);
                gFragment.setArguments(args);
                fragmentList.add(gFragment);

                StrategyFragment sFragment = new StrategyFragment(mApplication, this, this);
                sFragment.setArguments(args);
                //fragmentList.add(sFragment);
            } else if (tag == 4) {
                GiftsFragment gFragment = new GiftsFragment(mApplication, this, this);
                gFragment.setArguments(args);
                fragmentList.add(gFragment);

                CommentFragment cFragment = new CommentFragment(mApplication, this, this);
                cFragment.setArguments(args);
                fragmentList.add(cFragment);
            } else if (tag == 5) {
                StrategyFragment sFragment = new StrategyFragment(mApplication, this, this);
                sFragment.setArguments(args);
                //fragmentList.add(sFragment);

                CommentFragment cFragment = new CommentFragment(mApplication, this, this);
                cFragment.setArguments(args);
                fragmentList.add(cFragment);
            }
        } else if (num == 5) {

            GiftsFragment gFragment = new GiftsFragment(mApplication, this, this);
            gFragment.setArguments(args);
            fragmentList.add(gFragment);

            StrategyFragment sFragment = new StrategyFragment(mApplication, this, this);
            sFragment.setArguments(args);
            //fragmentList.add(sFragment);

            CommentFragment cFragment = new CommentFragment(mApplication, this, this);
            cFragment.setArguments(args);
            fragmentList.add(cFragment);
        }


        //给ViewPager设置适配器
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mPager.setAdapter(mMyFragmentPagerAdapter);
        if (num == 2) {
            mPager.setOnPageChangeListener(new MyOnPageChangeListener2());//页面变化时的监听器
        } else if (num == 3) {
            mPager.setOnPageChangeListener(new MyOnPageChangeListener0());//页面变化时的监听器
        } else if (num == 4) {
            mPager.setOnPageChangeListener(new MyOnPageChangeListener1());//页面变化时的监听器
        } else if (num == 5) {
            mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        }
        if (currIndex >= num) {
            mPager.setCurrentItem(0);//设置当前显示标签页为第一页
        } else {
            mPager.setCurrentItem(currIndex);//设置当前显示标签页为第一页
        }

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
                //view4.setSelected(false);
                view5.setSelected(false);
            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
                view3.setSelected(false);
                //view4.setSelected(false);
                view5.setSelected(false);
            } else if (i == 3) {
                view1.setSelected(false);
                view2.setSelected(false);
                view3.setSelected(true);
                //view4.setSelected(false);
                view5.setSelected(false);
            } else if (i == 4) {
                view1.setSelected(false);
                view2.setSelected(false);
                view3.setSelected(false);
                //view4.setSelected(true);
                view5.setSelected(true);
            } else if (i == 5) {
                view1.setSelected(false);
                view2.setSelected(false);
                view3.setSelected(false);
                // view4.setSelected(false);
                view5.setSelected(true);
            }
        }
    }

    public class MyOnPageChangeListener2 implements OnPageChangeListener {

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
                //view3.setSelected(false);
                //view4.setSelected(false);
                //view5.setSelected(false);
            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
                //view3.setSelected(false);
                //view4.setSelected(false);
                //view5.setSelected(false);
            }
        }
    }

    public class MyOnPageChangeListener1 implements OnPageChangeListener {

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
                //0代表view3显示，1代表view4显示，2代表view5显示
                //，3代表view3/view4显示，4代表view3/view5显示，5代表view4/view5显示,6代表view3/view4/view5显示
                view1.setSelected(true);
                view2.setSelected(false);
                //view3.setSelected(false);
                if (tag == 3) {
                    view3.setSelected(false);
                    //view4.setSelected(false);
                } else if (tag == 4) {
                    view3.setSelected(false);
                    view5.setSelected(false);
                } else if (tag == 5) {
                    //view4.setSelected(false);
                    view5.setSelected(false);
                }

            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
                //view3.setSelected(false);
                if (tag == 3) {
                    view3.setSelected(false);
                    // view4.setSelected(false);
                } else if (tag == 4) {
                    view3.setSelected(false);
                    view5.setSelected(false);
                } else if (tag == 5) {
                    // view4.setSelected(false);
                    view5.setSelected(false);
                }
            } else if (i == 3) {
                view1.setSelected(false);
                view2.setSelected(false);
                //view3.setSelected(true);
                if (tag == 3) {
                    view3.setSelected(true);
                    //view4.setSelected(false);
                } else if (tag == 4) {
                    view3.setSelected(true);
                    view5.setSelected(false);
                } else if (tag == 5) {
                    // view4.setSelected(true);
                    view5.setSelected(false);
                }
            } else if (i == 4) {
                view1.setSelected(false);
                view2.setSelected(false);
                //view3.setSelected(false);
                if (tag == 3) {
                    view3.setSelected(false);
                    // view4.setSelected(true);
                } else if (tag == 4) {
                    view3.setSelected(false);
                    view5.setSelected(true);
                } else if (tag == 5) {
                    // view4.setSelected(false);
                    view5.setSelected(true);
                }
            }/*else if(i==5){
                view1.setSelected(false);
				view2.setSelected(false);
				view3.setSelected(false);
				view4.setSelected(false);
				view5.setSelected(true);
			}*/
        }
    }

    public class MyOnPageChangeListener0 implements OnPageChangeListener {

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
                //view3.setSelected(false);
                if (tag == 0) {
                    view3.setSelected(false);
                } else if (tag == 1) {
                    // view4.setSelected(false);
                } else if (tag == 2) {
                    view5.setSelected(false);
                }

                //view5.setSelected(false);
            } else if (i == 2) {
                view1.setSelected(false);
                view2.setSelected(true);
                if (tag == 0) {
                    view3.setSelected(false);
                } else if (tag == 1) {
                    //view4.setSelected(false);
                } else if (tag == 2) {
                    view5.setSelected(false);
                }
                //view3.setSelected(false);
                //view4.setSelected(false);
                //view5.setSelected(false);
            } else if (i == 3) {
                view1.setSelected(false);
                view2.setSelected(false);
                if (tag == 0) {
                    view3.setSelected(true);
                } else if (tag == 1) {
                    // view4.setSelected(true);
                } else if (tag == 2) {
                    view5.setSelected(true);
                }
                //view3.setSelected(true);
                //view4.setSelected(true);
                //view5.setSelected(false);
            }/*else if(i==4){
                view1.setSelected(false);
				view2.setSelected(false);
				//view3.setSelected(false);
				view4.setSelected(false);
				view5.setSelected(true);
			}else if(i==5){
				view1.setSelected(false);
				view2.setSelected(false);
				view3.setSelected(false);
				view4.setSelected(false);
				view5.setSelected(true);
			}*/
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
    public void onCancel(Platform arg0, int arg1) {//回调的地方是子线程，进行UI操作要用handle处理
        handler.sendEmptyMessage(2);

    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {//回调的地方是子线程，进行UI操作要用handle处理
        if (arg0.getName().equals(QQ.NAME)) {// 判断成功的平台是不是QQ
            handler.sendEmptyMessage(1);
        } else if (arg0.getName().equals(QZone.NAME)) {// 判断成功的平台是不是QQ空间
            handler.sendEmptyMessage(4);
        } else if (arg0.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪
            handler.sendEmptyMessage(5);
        } else if (arg0.getName().equals(Wechat.NAME)) {// 判断成功的平台是不是微信
            handler.sendEmptyMessage(6);
        } else if (arg0.getName().equals(WechatMoments.NAME)) {// 判断成功的平台是不是微信朋友圈
            handler.sendEmptyMessage(7);
        }

    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {//回调的地方是子线程，进行UI操作要用handle处理
        arg2.printStackTrace();
        Message msg = new Message();
        msg.what = 3;
        msg.obj = arg2.getMessage();
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(), "新浪微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(getApplicationContext(), "微信好友分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    Toast.makeText(getApplicationContext(), "微信朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "分享失败", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("GameDetailActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GameDetailActivity");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(DownLoadModel event) {
        if (event != null && isForeground(this)) {
            if (BaseApplication.mInstance.isRecommendBoutiqueAdapter != 0) {

                for (DownLoadModel md : mSpecialgameAdapter.getData()) {
                    Log.e("lbb", "--------onEventMainThread71-------");
                    if (md.getmAppContent() != null && md.getmAppContent().gameId != null
                            && md.getmAppContent().gameId.equals(event.getmAppContent().gameId)) {

//                        mSpecialgameAdapter.getData().set(mSpecialgameAdapter.getData().indexOf(md), event);
                        mSpecialgameAdapter.upDataDownloadModel(mSpecialgameAdapter.getData().indexOf(md),event);
                        break;
                    }
                }
            }
        }
    }

}
