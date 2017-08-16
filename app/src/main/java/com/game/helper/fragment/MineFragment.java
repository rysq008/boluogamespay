package com.game.helper.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.community.FollowPeopleActivity;
import com.game.helper.activity.community.FollowerActivity;
import com.game.helper.activity.community.GiftsReceivedActivity;
import com.game.helper.activity.home.DownloadManagementActivity;
import com.game.helper.activity.home.MineGameActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.activity.mine.MineFeedActivity;
import com.game.helper.activity.mine.MineGameOrdersActivity;
import com.game.helper.activity.mine.MineGiftsActivity;
import com.game.helper.activity.mine.MineInvitedFriendsActivity;
import com.game.helper.activity.mine.MinePlatformCurrencyActivity;
import com.game.helper.activity.mine.MineProceedsActivity;
import com.game.helper.activity.mine.MineSetingActivity;
import com.game.helper.activity.mine.MineSystemMsgActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.model.PushModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetMainPageInfoTask;
import com.game.helper.net.task.QueryPtbTask;
import com.game.helper.sdk.model.returns.GetMainPageInfo;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryPtb;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.Util;
import com.game.helper.view.widget.CircleImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
/**
 * @Description 我的
 * @Path com.game.helper.fragment.MineFragment.java
 * @Author lbb
 * @Date 2016年8月18日 下午12:40:25
 * @Company
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_grade_iv)
    ImageView mineGradeIv;
    /* @BindView(R.id.mine_mineid_tv)
     TextView mineMineidTv;*/
    @BindView(R.id.mine_seting_tv)
    ImageView mineSetingTv;
    @BindView(R.id.mine_nikename_tv)
    TextView mineNikenameTv;
    @BindView(R.id.mine_top_message_iv)
    ImageView mineTopMessageIv;
    @BindView(R.id.mine_account_manage_ll)
    LinearLayout mineAccountManageLl;
    @BindView(R.id.game_account_ll)
    LinearLayout gameAccountLl;
    @BindView(R.id.mine_f_attention)
    LinearLayout mineFAttention;
    @BindView(R.id.mine_f_fans)
    LinearLayout mineFFans;
    @BindView(R.id.mine_f_gift)
    LinearLayout mineFGift;
    //private TextView topRight;
    //private TextView topTitle;
    private LinearLayout top_left_layout;
    private ImageView topLeftBack;
    private RelativeLayout mRelativeLayout;
    //private ImageView top_iv_right;
    //private RelativeLayout top_liner_right;

    private CircleImageView imageView_pic;//头像
    // private TextView tv_userName;//昵称
    //private TextView tv_userNum;//账号
    //private ImageView mine_toiv;//进入个人资料

    /* private LinearLayout mLayout1;//关注
     private LinearLayout mLayout2;//粉丝
     private LinearLayout mLayout3;//礼物*/
    private TextView weddingsNum;//关注
    private TextView collect_Num;//粉丝
    private TextView gift_Num;//礼物

    private LinearLayout tv_money;//我的金币
    private LinearLayout tv_earnings;//我的收益
    private LinearLayout tv_gameorder;//游戏订单
    //private LinearLayout tv_Discount;//我的折扣号
    private LinearLayout tv_gift;//我的礼包
    //private LinearLayout tv_collcet;//我的收藏
    //private LinearLayout tv_record_conversion;//兑换记录

    private LinearLayout mine_share;//邀请好友
    //private LinearLayout mine_seting;//设置

    //private LinearLayout mine_moneyLinearLayout;//我的账号
    private TextView tv_moneyNum;
    //private View isDownload;

    private LinearLayout mine_feedback;//兑换记录
    private boolean isnoread;

    public MineFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MineFragment(BaseApplication application, Activity activity,
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
            mView = inflater.inflate(R.layout.fragment_mine, container, false);
            ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);
        return mView;
    }

    Drawable mDrawable;

    @Override
    protected void initViews() {
        mDrawable = getActivity().getResources().getDrawable(R.drawable.pic_moren);
        mine_feedback = (LinearLayout) findViewById(R.id.mine_feedback);
        //isDownload=(View) findViewById(R.id.isDownload1);

        //topRight = (TextView) findViewById(R.id.top_right);
        /// topTitle = (TextView) findViewById(R.id.top_title);
        top_left_layout = (LinearLayout) findViewById(R.id.top_left_layout);
        //topTitle.setText("");
        topLeftBack = (ImageView) findViewById(R.id.topLeftBack);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mRelativeLayout);

        //top_iv_right = (ImageView) findViewById(R.id.top_iv_right);

        //top_iv_right.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.wode_29));
        //top_liner_right = (RelativeLayout) findViewById(R.id.top_liner_right);
        //top_liner_right.setVisibility(View.VISIBLE);

        imageView_pic = (CircleImageView) findViewById(R.id.imageView_pic);
        //tv_userName = (TextView) findViewById(R.id.tv_userName);
        // tv_userNum = (TextView) findViewById(R.id.tv_userNum);
        //mine_toiv = (ImageView) findViewById(R.id.mine_toiv);

       /* mLayout1 = (LinearLayout) findViewById(R.id.mLayout1);
        mLayout2 = (LinearLayout) findViewById(R.id.mLayout2);
        mLayout3 = (LinearLayout) findViewById(R.id.mLayout3);*/
        weddingsNum = (TextView) findViewById(R.id.weddingsNum);
        collect_Num = (TextView) findViewById(R.id.collect_Num);
        gift_Num = (TextView) findViewById(R.id.gift_Num);

        tv_moneyNum = (TextView) findViewById(R.id.tv_moneyNum);
        tv_money = (LinearLayout) findViewById(R.id.tv_money);
        tv_gift = (LinearLayout) findViewById(R.id.tv_gift);
        //tv_collcet = (LinearLayout) findViewById(R.id.tv_collcet);

        tv_earnings = (LinearLayout) findViewById(R.id.tv_earnings);//我的收益
        tv_gameorder = (LinearLayout) findViewById(R.id.tv_gameorder);//游戏订单
        //tv_Discount=(LinearLayout) findViewById(R.id.tv_Discount);//我的折扣号
        // tv_record_conversion = (LinearLayout) findViewById(R.id.tv_record_conversion);//兑换记录

        mine_share = (LinearLayout) findViewById(R.id.mine_share);
        //CustomProgressBar customProgressBar = new CustomProgressBar(mContext);
        //(CustomProgressBar) findViewById(R.id.item_progress_SeekBar);
        //mine_seting=(LinearLayout) findViewById(R.id.mine_seting);
        //mine_moneyLinearLayout = (LinearLayout) findViewById(R.id.mine_moneyLinearLayout);
    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub
        //topRight.setOnClickListener(this);
        //top_liner_right.setOnClickListener(this);

        //imageView_pic.setOnClickListener(this);

       /* mLayout1.setOnClickListener(this);
        mLayout2.setOnClickListener(this);
        mLayout3.setOnClickListener(this);*/

        tv_money.setOnClickListener(this);
        tv_gift.setOnClickListener(this);
        //tv_collcet.setOnClickListener(this);

        mine_share.setOnClickListener(this);
        //mine_seting.setOnClickListener(this);

        tv_earnings.setOnClickListener(this);
        tv_gameorder.setOnClickListener(this);
        //tv_Discount.setOnClickListener(this);
        //tv_record_conversion.setOnClickListener(this);
        //mine_moneyLinearLayout.setOnClickListener(this);
        mine_feedback.setOnClickListener(this);
    }

    LoginData user;

    @Override
    protected void init() {
        receiver = new BaseBroadcast(getActivity()) {

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
        getActivity().registerReceiver(receiver, filter);

        user = DBManager.getInstance(getActivity()).getUserMessage();
        setNickName(user);
        setPic(user);
        setWeddingsNum(user);
        setCollect_Num(user);
        setGift_Num(user);
        setTv_moneyNum(user);
        setNickName(user);
    }

    public void setRead() {
        int count1 = 0;
        List<PushModel> list = DBManager.getInstance(getActivity()).getPushAllMessage();
        for (PushModel push : list) {
            if (push.isRead == 0) {
                count1++;
                isnoread = true;
            }
        }

        if (isnoread){
            mineTopMessageIv.setImageDrawable(getResources().getDrawable(R.drawable.home_message));
        }else{
            mineTopMessageIv.setImageDrawable(getResources().getDrawable(R.drawable.home_nomessage));
        }

        //有无下载内容
        /*if(count1>0){
            isDownload.setVisibility(View.VISIBLE);
		}else{
			isDownload.setVisibility(View.INVISIBLE);
		}*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.mLayout1:
                //关注

                Bundle bundle1 = new Bundle();
                bundle1.putString("userId", user.userId);
                ((BaseActivity) getActivity()).startActivity(FollowPeopleActivity.class, bundle1);
                break;
            case R.id.mLayout2:
                //粉丝,跳转社区
                Bundle bundle2 = new Bundle();
                bundle2.putString("userId", user.userId);
                ((BaseActivity) getActivity()).startActivity(FollowerActivity.class, bundle2);
                break;
            case R.id.mLayout3:
                //礼物,跳转社区
                Bundle bundle3 = new Bundle();
                bundle3.putString("userId", user.userId);
                ((BaseActivity) getActivity()).startActivity(GiftsReceivedActivity.class, bundle3);
                break;*/
            case R.id.tv_money:
                //我的金币
                ((BaseActivity) getActivity()).startActivity(MinePlatformCurrencyActivity.class);
                break;
            case R.id.tv_earnings:
                //我的收益
                ((BaseActivity) getActivity()).startActivity(MineProceedsActivity.class);
                break;
            case R.id.tv_gameorder:
                //游戏订单
                ((BaseActivity) getActivity()).startActivity(MineGameOrdersActivity.class);
                break;
        /*case R.id.tv_Discount:
            //折扣号
			((BaseActivity)getActivity()).startActivity(MineCollarDiscountNumberActivity.class);
			break;*/
            case R.id.tv_gift:
                //我的礼包
                ((BaseActivity) getActivity()).startActivity(MineGiftsActivity.class);
                break;
           /* case R.id.tv_collcet:
                //我的收藏
                ((BaseActivity) getActivity()).startActivity(MineCollectActivity.class);
                break;
            case R.id.tv_record_conversion:
                //兑换记录
                ((BaseActivity) getActivity()).startActivity(MallExchangeRecordActivity.class);
                break;*/
            case R.id.mine_share:
                //邀请的好友
                ((BaseActivity) getActivity()).startActivity(MineInvitedFriendsActivity.class);

                break;
        /*case R.id.mine_seting:
            //设置
			((BaseActivity)getActivity()).startActivity(MineSetingActivity.class);
			break;*/
            case R.id.mine_feedback:
                //我的反馈
                ((BaseActivity) getActivity()).startActivity(MineFeedActivity.class);
                break;
            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(mContext);
        MobclickAgent.onPageEnd("MineFragment");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(mContext);
        MobclickAgent.onPageStart("MineFragment");
        user = DBManager.getInstance(getActivity()).getUserMessage();
        setRead();
        setNickName(user);
        setPic(user);
        setWeddingsNum(user);
        setCollect_Num(user);
        setGift_Num(user);
        setTv_moneyNum(user);
        //setNumber(user);
        new GetMainPageInfoTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetMainPageInfo) {
                    GetMainPageInfo mQueryPtb = (GetMainPageInfo) object;
                    if (mQueryPtb.data != null) {
                        user.jsonData = null;
                        user.status = mQueryPtb.data.status;
                        user.guildId = mQueryPtb.data.guildId;
                        user.field1 = mQueryPtb.data.field1;
                        user.ptb = mQueryPtb.data.ptb;
                        user.nickName = mQueryPtb.data.nickName;
                        user.account = mQueryPtb.data.account;
                        user.field5 = mQueryPtb.data.userId;
                        //user.field7=mQueryPtb.data.field7;

                        user.icon = mQueryPtb.data.icon;
                        user.iconThumb = mQueryPtb.data.iconThumb;
                        user.fansTotal = mQueryPtb.data.fansTotal;
                        user.field3 = mQueryPtb.data.field3;
                        user.foucsTotal = mQueryPtb.data.foucsTotal;
                        user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                        DBManager.getInstance(mContext).insert(user);
                        user = DBManager.getInstance(getActivity()).getUserMessage();
                        setWeddingsNum(user);
                        setCollect_Num(user);
                        setGift_Num(user);
                        setPic(user);
                        setTv_moneyNum(user);
                        setNickName(user);
                        //setNumber(user);
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
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
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

            }
        }).start();

    }

    public void setPic(LoginData user) {

        if (user != null) {
            if (!TextUtils.isEmpty(user.icon) && !TextUtils.isEmpty(user.fileAskPath)) {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load(user.fileAskPath + user.icon)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        .placeholder(R.drawable.pic_moren)//加载中显示的图片
                        .error(R.drawable.pic_moren)//加载失败时显示的图片
                        //.crossFade()
                        .into(imageView_pic);
            } else {
                try {
                    imageView_pic.setImageDrawable(mDrawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
/*
    public void setNumber(LoginData user) {

        if (user != null) {
            if (!TextUtils.isEmpty(user.account)) {
                mineMineidTv.setText("G9账号：" + user.account);

               *//* if (!TextUtils.isEmpty(user.field7)) {
                    mineMineidTv.setText( "G9账号" + user.account);
                } else {
                    mineMineidTv.setText("ID：" + user.account);
                }*//*
            }
        }
    }*/

    public void setWeddingsNum(LoginData user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.foucsTotal)) {
                weddingsNum.setText("" + user.foucsTotal);
            }
        }
    }

    public void setCollect_Num(LoginData user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.fansTotal)) {
                collect_Num.setText("" + user.fansTotal);
            }
        }
    }

    public void setGift_Num(LoginData user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.field3)) {
                gift_Num.setText("" + user.field3);
            }
        }
    }

    public void setTv_moneyNum(LoginData user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.ptb)) {
                tv_moneyNum.setText("" + user.ptb);
            }
        }
    }

    public void setNickName(LoginData user) {
        if (user != null) {
            if (!TextUtils.isEmpty(user.nickName)) {
                mineNikenameTv.setText(Util.setLongStringPoint(user.nickName));
            } else {
                mineNikenameTv.setText("");
            }

            //vip等级
            if (!TextUtils.isEmpty(user.field7)) {
                String vipstring = user.field7;
                switch (vipstring) {
                    case "VIP1":
                        mineGradeIv.setBackgroundResource(R.drawable.vip_one);
                        break;
                    case "VIP2":
                        mineGradeIv.setBackgroundResource(R.drawable.vip_two);
                        break;
                    case "VIP3":
                        mineGradeIv.setBackgroundResource(R.drawable.vip_three);
                        break;
                    case "VIP4":
                        mineGradeIv.setBackgroundResource(R.drawable.vip_four);
                        break;
                    case "VIP5":
                        mineGradeIv.setBackgroundResource(R.drawable.vip_five);
                        break;
                    case "黑钻":
                        mineGradeIv.setBackgroundResource(R.drawable.vip_black);
                        break;
                }
            }
        }
    }

    @OnClick({R.id.mine_seting_tv, R.id.mine_top_message_iv, R.id.mine_account_manage_ll,
            R.id.game_account_ll, R.id.imageView_pic,R.id.mine_f_attention,
            R.id.mine_f_fans, R.id.mine_f_gift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_seting_tv:
                ((BaseActivity) getActivity()).startActivity(MineSetingActivity.class);
                break;
            case R.id.mine_top_message_iv:
                //系统消息
                ((BaseActivity) getActivity()).startActivity(MineSystemMsgActivity.class);
                break;
            case R.id.imageView_pic:
            case R.id.mine_account_manage_ll:
                //个人信息和头像设置
                ((BaseActivity) getActivity()).startActivity(MineDataEditingActivity.class);
                break;
            case R.id.game_account_ll:
                //我的游戏
                //((BaseActivity) getActivity()).startActivity(MineGameActivity.class);
                ((BaseActivity) getActivity()).startActivity(DownloadManagementActivity.class);
                break;
            case R.id.mine_f_attention:
                //关注
                Bundle bundle1 = new Bundle();
                bundle1.putString("userId", user.userId);
                ((BaseActivity) getActivity()).startActivity(FollowPeopleActivity.class, bundle1);
                break;
            case R.id.mine_f_fans:
                //粉丝,跳转社区
                Bundle bundle2 = new Bundle();
                bundle2.putString("userId", user.userId);
                ((BaseActivity) getActivity()).startActivity(FollowerActivity.class, bundle2);
                break;
            case R.id.mine_f_gift:
                //礼物,跳转社区
                Bundle bundle3 = new Bundle();
                bundle3.putString("userId", user.userId);
                ((BaseActivity) getActivity()).startActivity(GiftsReceivedActivity.class, bundle3);
                break;
        }
    }
}
