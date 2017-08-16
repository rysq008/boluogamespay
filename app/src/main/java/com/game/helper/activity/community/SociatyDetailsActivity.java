package com.game.helper.activity.community;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.adapter.community.EveryonePlayingAdapter;
import com.game.helper.adapter.community.SociatyCircleAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetDynamicInfoTask;
import com.game.helper.net.task.GetGuildByIdTask;
import com.game.helper.net.task.GetGuildGameListTask;
import com.game.helper.net.task.GetMainPageInfoTask;
import com.game.helper.net.task.GetNoticeListTask;
import com.game.helper.net.task.GetSignListTask;
import com.game.helper.net.task.JoinOrExitGuildTask;
import com.game.helper.net.task.JudgeSignTask;
import com.game.helper.net.task.SaveSignTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetDynamic_1Info;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.Dynamic_1Info;
import com.game.helper.sdk.model.returns.GetGuildById;
import com.game.helper.sdk.model.returns.GetGuildById.GetGuildByIdData;
import com.game.helper.sdk.model.returns.GetGuildGameList;
import com.game.helper.sdk.model.returns.GetGuildGameList.GuildGame;
import com.game.helper.sdk.model.returns.GetMainPageInfo;
import com.game.helper.sdk.model.returns.GetNoticeList;
import com.game.helper.sdk.model.returns.GetNoticeList.GetNoticeListData;
import com.game.helper.sdk.model.returns.GetSignList;
import com.game.helper.sdk.model.returns.GetSignList.GetSignListData;
import com.game.helper.sdk.model.returns.JudgeSign;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.TimeUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.game.helper.view.dialog.ShareDialog;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.ListViewForScrollView;
import com.game.helper.view.widget.MyScrollviewGridView;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

/**
 * @Description 公会详情
 * @Path com.game.helper.activity.community.SociatyDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月25日 上午11:25:22
 * @Company
 */
public class SociatyDetailsActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.iv_photo)
    XCRoundImageViewByXfermode iv_photo;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.tv_sign_in)
    TextView tv_sign_in;
    @BindView(R.id.lv)
    ListViewForScrollView lv;
    @BindView(R.id.tv_lastnew)
    TextView tv_title_lastnew;
    @BindView(R.id.tv_time_lastnew)
    TextView tv_time_lastnew;
    @BindView(R.id.tv_content_lastnew)
    TextView tv_content_lastnew;
    @BindView(R.id.mLinear_lastnew_detail)
    LinearLayout mLinear_lastnew_detail;
    @BindView(R.id.everyoneplaying_gridview)
    MyScrollviewGridView everyoneplaying_gridview;
    @BindView(R.id.lv_GuildCircle)
    ListViewForScrollView lv_GuildCircle;
    @BindView(R.id.tv_goto_sociaty)
    TextView tv_goto_sociaty;
    private SociatyCircleAdapter mSociatyCircleAdapter;
    private List<Dynamic_1Info> list = new ArrayList<Dynamic_1Info>();
    private EveryonePlayingAdapter mEveryonePlayingAdapter;
    public List<GuildGame> gameList = new ArrayList<GuildGame>();

    private List<GetSignListData> datas = new ArrayList<GetSignListData>();//水平列表 展示头像
    private ArrayList<String> arrayList = new ArrayList<String>();
    private LinearLayout header_ll;
    private View headerView;
    private ArrayAdapter adapter;
    private PopupWindow popupWindow;
    private ShareDialog shareDialog;

    private String guildId;
    private LoginData user;
    private String guild_userId;
    GetNoticeListData mGetNoticeListData;

    protected LayoutInflater mInflater;
    public Comparator comp1 = new Comparator() {
        public int compare(Object o1, Object o2) {
            GetNoticeListData p1 = (GetNoticeListData) o1;
            GetNoticeListData p2 = (GetNoticeListData) o2;
            int a = 0;
            a = TimeUtil.parserTime(TimeUtil.TIME_FORMAT_FULL, p1.createTimeString, p2.createTimeString);
            if (a != 2) {
                if (a == 1) {
                    return -1;
                } else if (a == 1) {
                    return -1;
                } else {
                    return a;
                }
            }
            return 0;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_sociaty_details);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initView() {
        mInflater = LayoutInflater.from(SociatyDetailsActivity.this);
        topTitle.setText("");
        topLeftBack.setVisibility(View.VISIBLE);
        top_liner_right.setVisibility(View.VISIBLE);
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable._shiqu_09));

        tv_sign_in.setEnabled(false);
        tv_sign_in.setText("");
        tv_goto_sociaty.setText("已加入公会");
        tv_goto_sociaty.setEnabled(false);
        tv_goto_sociaty.setVisibility(View.GONE);

        headerView = mInflater.inflate(
                R.layout.item_listview_community_sociaty_details_header, null);
        header_ll = (LinearLayout) headerView.findViewById(R.id.header_ll);

        adapter = new ArrayAdapter(this, R.layout.item_listview_home_game,
                R.id.textView1, arrayList);
        lv.setAdapter(adapter);

        mSociatyCircleAdapter = new SociatyCircleAdapter(mContext, list);
        lv_GuildCircle.setAdapter(mSociatyCircleAdapter);

        mEveryonePlayingAdapter = new EveryonePlayingAdapter(mContext, gameList);
        everyoneplaying_gridview.setAdapter(mEveryonePlayingAdapter);
        everyoneplaying_gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("gameId", mEveryonePlayingAdapter.getGameList().get(position).gameId);
                startActivity(GameDetailActivity.class, bundle);
            }
        });
    }


    public void setHeadSign() {
        lv.setAdapter(null);
        lv.removeHeaderView(headerView);
        header_ll.removeAllViews();
        for (int i = 0; i < datas.size(); i++) {
            final int a = i;
            View coupon_home_ad_item = mInflater.inflate(
                    R.layout.item_listview_community_sociaty_details, null);
            final CircleImageView icon = (CircleImageView) coupon_home_ad_item
                    .findViewById(R.id.coupon_ad_iv);//
            final String href = "" + datas.get(i).fileAskPath + datas.get(i).icon;
            if (!TextUtils.isEmpty(datas.get(i).icon)) {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load(href)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.pic_moren)//加载失败时显示的图片
                        //.crossFade()
                        .into(icon);
            } else {
                icon.setImageDrawable(getResources().getDrawable(R.drawable.pic_moren));
            }
            if (!TextUtils.isEmpty(href)) {
                coupon_home_ad_item.setOnClickListener(new OnClickListener() {//

                    @Override
                    public void onClick(View v) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("guildId", datas.get(a).guildId);
                        bundle1.putString("userId", user.userId);
                        startActivity(SociatySignInActivity.class, bundle1);
                    }
                });

            }
            header_ll.addView(coupon_home_ad_item);

        }
        lv.addHeaderView(headerView);//
        lv.setAdapter(adapter);
    }

    String fileAskPath, icon, declareContent, abstractContent;

    public void setDatasForGuild(GetGuildByIdData data) {
        guild_userId = data.userId;
        iv_photo.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
        iv_photo.setRoundBorderRadius(23);

        fileAskPath = data.fileAskPath;
        icon = data.icon;
        declareContent = data.declareContent;
        abstractContent = data.abstractContent;

        Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                .load("" + data.fileAskPath + data.icon)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.centerCrop()// 长的一边撑满
                //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                .error(R.drawable.picture_defeated)//加载失败时显示的图片
                //.crossFade()
                .into(iv_photo);
        tv_msg.setText(data.declareContent);//宣言
        tv_count.setText(data.total);
        tv_id.setText(data.guildId);
        topTitle.setText(data.name);
        name = data.name;
    }

    String name;

    @Override
    protected void initData() {

        user = DBManager.getInstance(mContext).getUserMessage();
        guildId = getIntent().getStringExtra("guildId");
        if (!TextUtils.isEmpty(guildId) && !guildId.equals("0") && user != null && !TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0") && guildId.equals(user.guildId)) {

            tv_goto_sociaty.setEnabled(false);
            tv_goto_sociaty.setText("已加入公会");
            tv_goto_sociaty.setVisibility(View.GONE);
        } else {
            tv_goto_sociaty.setEnabled(true);
            tv_goto_sociaty.setText("加入公会");
            tv_goto_sociaty.setVisibility(View.VISIBLE);
        }
        //大家都在玩
        new GetGuildGameListTask(mContext, guildId, new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGuildGameList) {

                    GetGuildGameList mGetGuildGameList = (GetGuildGameList) object;
                    if (mGetGuildGameList.data != null) {
                        if (mGetGuildGameList.data.gameList != null && mGetGuildGameList.data.gameList.size() >= 0) {
                            gameList.clear();
                            if (mGetGuildGameList.data.gameList.size() > 8) {
                                for (int i = 0; i < 8; i++) {
                                    gameList.add(mGetGuildGameList.data.gameList.get(i));
                                }
                            } else {
                                gameList.addAll(mGetGuildGameList.data.gameList);
                            }

                            mEveryonePlayingAdapter.setGameList(gameList);

                        }

                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
        //动态圈
        new GetDynamicInfoTask(mContext, false, guildId, "0", new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetDynamic_1Info) {
                    GetDynamic_1Info mGetDynamic_1Info = (GetDynamic_1Info) object;
                    if (mGetDynamic_1Info.data != null && mGetDynamic_1Info.data.list != null) {
                        if (mGetDynamic_1Info.data.list.size() >= 0) {
                            list.clear();
                            if (mGetDynamic_1Info.data.list.size() > 2) {
                                list.add(mGetDynamic_1Info.data.list.get(0));
                                list.add(mGetDynamic_1Info.data.list.get(1));
                            } else {
                                list.addAll(mGetDynamic_1Info.data.list);
                            }
                            mSociatyCircleAdapter.setList(list);
                        }
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
        //获取工会详情
        new GetGuildByIdTask(mContext, guildId, new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGuildById) {
                    GetGuildById mGetGuildById = (GetGuildById) object;
                    if (mGetGuildById.data != null) {
                        setDatasForGuild(mGetGuildById.data);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetGuildById + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
                if (TextUtils.isEmpty(topTitle.getText().toString())) {
                    topTitle.setText("公会详情");
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGuildById + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetGuildById.class, json);
                    if (mObject != null && mObject instanceof GetGuildById) {
                        GetGuildById mGetGuildById = (GetGuildById) mObject;
                        if (mGetGuildById.data != null) {
                            setDatasForGuild(mGetGuildById.data);
                        }
                    }
                }
                if (TextUtils.isEmpty(topTitle.getText().toString())) {
                    topTitle.setText("公会详情");
                }

            }
        }).start();
        //获取工会公告
        new GetNoticeListTask(mContext, false, guildId, new Back() {

            @Override
            public void success(Object object, String msg) {

                if (object != null && object instanceof GetNoticeList) {
                    GetNoticeList mGetNoticeList = (GetNoticeList) object;
                    if (mGetNoticeList.data != null) {
                        if (mGetNoticeList.data.size() > 0) {
                            Collections.sort(mGetNoticeList.data, comp1);
                            tv_title_lastnew.setText(mGetNoticeList.data.get(0).noticeName);
                            tv_time_lastnew.setText(mGetNoticeList.data.get(0).createTimeString);
                            tv_content_lastnew.setText(mGetNoticeList.data.get(0).content);
                            mGetNoticeListData = mGetNoticeList.data.get(0);
                            mLinear_lastnew_detail.setVisibility(View.VISIBLE);
                        }
                        SharedPreUtil.putStringValue(mContext, ACTION_GetNoticeList + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetNoticeList + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetNoticeList.class, json);
                    if (mObject != null && mObject instanceof GetNoticeList) {
                        GetNoticeList mGetNoticeList = (GetNoticeList) mObject;
                        if (mGetNoticeList != null && mGetNoticeList.data != null) {
                            if (mGetNoticeList.data.size() > 0) {
                                Collections.sort(mGetNoticeList.data, comp1);
                                tv_title_lastnew.setText(mGetNoticeList.data.get(0).noticeName);
                                tv_time_lastnew.setText(mGetNoticeList.data.get(0).createTimeString);
                                tv_content_lastnew.setText(mGetNoticeList.data.get(0).content);
                                mGetNoticeListData = mGetNoticeList.data.get(0);
                                mLinear_lastnew_detail.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }
        }).start();

        //查询是否已签到
        new JudgeSignTask(mContext, false, user.userId, guildId, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_XXXX_XX_XX), new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof JudgeSign) {
                    JudgeSign mJudgeSign = (JudgeSign) object;
                    if (mJudgeSign.data != null) {
                        if (!TextUtils.isEmpty(mJudgeSign.data.isSign)) {
                            if (mJudgeSign.data.isSign.equals("Y")) {
                                tv_sign_in.setEnabled(false);
                                tv_sign_in.setText("已签到");
                            } else {
                                tv_sign_in.setText("签到");
                                tv_sign_in.setEnabled(true);
                            }
                        }
                        SharedPreUtil.putStringValue(mContext, ACTION_JudgeSign + "_" + user.userId + "_" + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_JudgeSign + "_" + user.userId + "_" + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(JudgeSign.class, json);
                    if (mObject != null && mObject instanceof JudgeSign) {
                        JudgeSign mJudgeSign = (JudgeSign) mObject;
                        if (mJudgeSign != null && mJudgeSign.data != null) {
                            if (!TextUtils.isEmpty(mJudgeSign.data.isSign)) {
                                if (mJudgeSign.data.isSign.equals("Y")) {
                                    tv_sign_in.setEnabled(false);
                                    tv_sign_in.setText("已签到");
                                } else {
                                    tv_sign_in.setText("签到");
                                    tv_sign_in.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            }
        }).start();
        //获取工会签到头像列表（默认展示6个）
        new GetSignListTask(mContext, false, guildId, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_XXXX_XX_XX), new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetSignList) {
                    GetSignList mGetSignList = (GetSignList) object;
                    if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                        datas.clear();
                        datas.addAll(mGetSignList.data);
                        setHeadSign();
                        SharedPreUtil.putStringValue(mContext, ACTION_GetSignList + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetSignList + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetSignList.class, json);
                    if (mObject != null && mObject instanceof GetSignList) {
                        GetSignList mGetSignList = (GetSignList) mObject;
                        if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {

                            datas.clear();
                            datas.addAll(mGetSignList.data);
                            setHeadSign();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_liner_right, R.id.mLinear_comm_sociaty1
            , R.id.mLinear_comm_sociaty2, R.id.mLinear_comm_sociaty3
            , R.id.mLinear_comm_sociaty4, R.id.relat_lastnew
            , R.id.mLinear_lastnew_detail, R.id.relat_GuildCircle
            , R.id.tv_goto_sociaty, R.id.tv_sign_in, R.id.mLineareveryoneplaying})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                //底部弹出选择项
                showPopupWindow();
                break;
            case R.id.mLinear_comm_sociaty1:
                //公告
                Bundle bundle = new Bundle();
                bundle.putString("guildId", guildId);
                bundle.putString("guild_userId", guild_userId);

                startActivity(SociatyAnnoucementActivity.class, bundle);
                break;
            case R.id.mLinear_comm_sociaty2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("guildId", guildId);
                startActivity(SociatyInformationActivity.class, bundle2);
                break;
            case R.id.mLinear_comm_sociaty3:
                Bundle budle = new Bundle();
                budle.putInt("currIndex", 1);
                budle.putString("guildId", guildId);
                startActivity(DynamicActivity.class, budle);
                break;
            case R.id.mLinear_comm_sociaty4:
                Bundle bundle3 = new Bundle();
                bundle3.putString("guild_userId", guild_userId);
                bundle3.putString("guildId", guildId);
                bundle3.putString("nickName", name);
                startActivity(SociatyMembersActivity.class, bundle3);
                break;
            case R.id.relat_lastnew:
                //最新公告-更多
                Bundle bundle1 = new Bundle();
                bundle1.putString("guildId", guildId);
                startActivity(SociatyAnnoucementActivity.class, bundle1);
                break;
            case R.id.mLinear_lastnew_detail:
                if (mGetNoticeListData != null) {
                    Bundle bu = new Bundle();
                    bu.putString("noticeId", mGetNoticeListData.noticeId);
                    startActivity(AnnouncementDetailsActivity.class, bu);
                } else {
                    ToastUtil.showToast(mContext, "暂无最新公告");
                }
                break;
            case R.id.relat_GuildCircle:
                Bundle budle1 = new Bundle();
                budle1.putInt("currIndex", 1);
                budle1.putString("guildId", guildId);
                startActivity(DynamicActivity.class, budle1);
                break;
            case R.id.tv_goto_sociaty:
                if (tv_goto_sociaty.isEnabled()) {
                    tv_goto_sociaty.setEnabled(false);
                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                    Resources res = mContext.getResources();
                    dialog.setTitle("提醒", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    dialog.setContent1("是否加入该公会？每个用户只能加入一个公会哦~"
                            , Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    dialog.setLeftButtonText("取消");
                    dialog.setRightButtonText("确定");
                    if (dialog != null && !dialog.isShowing()) {
                        dialog.show();
                    }
                    dialog.setOnClickLeftListener(new onClickLeftListener() {
                        @Override
                        public void onClickLeft() {
                            dialog.dismiss();
                            tv_goto_sociaty.setEnabled(true);
                        }
                    });
                    dialog.setOnClickRightListener(new onClickRightListener() {

                        @Override
                        public void onClickRight() {
                            dialog.dismiss();
                            //加入工会
                            user = DBManager.getInstance(mContext).getUserMessage();
                            new JoinOrExitGuildTask(mContext, user.userId, guildId, "0", new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    ToastUtil.showToast(mContext, "已加入工会 ");
                                    tv_goto_sociaty.setEnabled(false);
                                    tv_goto_sociaty.setText("");
                                    tv_goto_sociaty.setVisibility(View.GONE);
                                    user.jsonData = null;
                                    user.guildId = guildId;
                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                    DBManager.getInstance(mContext).insert(user);
                                    user = DBManager.getInstance(mContext).getUserMessage();
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, "加入工会失败 " + msg);
                                    tv_goto_sociaty.setEnabled(true);
                                }
                            }).start();
                        }
                    });
                }
                break;

            case R.id.tv_sign_in:
                //签到
                if (tv_sign_in.isEnabled()) {
                    tv_sign_in.setEnabled(false);
                    if (!TextUtils.isEmpty(guildId) && !guildId.equals("0") && user != null && !TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0") && guildId.equals(user.guildId)) {//已加入公会
                        user = DBManager.getInstance(mContext).getUserMessage();
                        new SaveSignTask(mContext, user.userId, guildId, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                tv_sign_in.setEnabled(false);
                                tv_sign_in.setText("已签到");
                                final MyAlertDailog dialog1 = new MyAlertDailog(
                                        mContext);
                                Resources res1 = mContext.getResources();
                                dialog1.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                dialog1.setContent1("签到成功!"
                                        , Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                dialog1.setSingle("确定");
                                if (dialog1 != null && !dialog1.isShowing()) {
                                    dialog1.show();
                                }
                                dialog1.setOnClickSingleListener(new onClickSingleListener() {

                                    @Override
                                    public void onClickSingle() {
                                        dialog1.dismiss();
                                        //刷新工会签到头像列表
                                        new GetSignListTask(mContext, false, guildId, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_XXXX_XX_XX), new Back() {

                                            @Override
                                            public void success(Object object, String msg) {
                                                // TODO Auto-generated method stub
                                                if (object != null && object instanceof GetSignList) {
                                                    GetSignList mGetSignList = (GetSignList) object;
                                                    if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                                                        datas.clear();
                                                        datas.addAll(mGetSignList.data);
                                                        setHeadSign();
                                                        SharedPreUtil.putStringValue(mContext, ACTION_GetSignList + guildId, new JsonBuild().setModel(object).getJson1());
                                                    }
                                                }
                                            }

                                            @Override
                                            public void fail(String status, String msg, Object object) {
                                                // TODO Auto-generated method stub
                                                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetSignList + guildId, "");
                                                if (!TextUtils.isEmpty(json)) {
                                                    Object mObject = new JsonBuild().getData(GetSignList.class, json);
                                                    if (mObject != null && mObject instanceof GetSignList) {
                                                        GetSignList mGetSignList = (GetSignList) mObject;
                                                        if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {

                                                            datas.clear();
                                                            datas.addAll(mGetSignList.data);
                                                            setHeadSign();
                                                        }
                                                    }
                                                }
                                            }
                                        }).start();

                                    }
                                });
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                tv_sign_in.setEnabled(true);
                                ToastUtil.showToast(mContext, "签到失败 " + msg);
                            }
                        }).start();
                    } else {
                        tv_sign_in.setEnabled(true);
                        ToastUtil.showToast(mContext, "请先加入公会后再签到");
                    }
                }


                break;
            case R.id.btn_take_photo:
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
                            sp.setTitle("公会:" + name + " Id:" + guildId + "邀你一起玩战天下 ");
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGuild?guildId=" + guildId); // 标题的超链接
                            sp.setText("公会宣言：" + declareContent + "," + abstractContent);
                            sp.setImageUrl("" + fileAskPath + icon);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(SociatyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("公会:" + name + " Id:" + guildId + "邀你一起玩战天下 ");
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGuild?guildId=" + guildId); // 标题的超链接
                            sp.setText("公会宣言：" + declareContent + "," + abstractContent);
                            sp.setImageUrl("" + fileAskPath + icon);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toGuild?guildId=" + guildId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(SociatyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("公会:" + name + " Id:" + guildId + "邀你一起玩战天下 " + "公会宣言：" + declareContent + "," + abstractContent + Config.getInstance().IP + "/helper/app/toGuild?guildId=" + guildId);
                            sp.setImageUrl("" + fileAskPath + icon);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(SociatyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("公会:" + name + " Id:" + guildId + "邀你一起玩战天下 ");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("公会宣言：" + declareContent + "," + abstractContent);
                            sp.setImageUrl("" + fileAskPath + icon);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGuild?guildId=" + guildId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(SociatyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("公会:" + name + " Id:" + guildId + "邀你一起玩战天下 " + "公会宣言：" + declareContent + "," + abstractContent);
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("公会宣言：" + declareContent + "," + abstractContent);//设置了不会显示,可选参数
                            sp.setImageUrl("" + fileAskPath + icon);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGuild?guildId=" + guildId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(SociatyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(SociatyDetailsActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_pick_photo:
                user = DBManager.getInstance(mContext).getUserMessage();
                if (!TextUtils.isEmpty(guild_userId) && user != null && !TextUtils.isEmpty(user.userId) && guild_userId.equals(user.userId)) {//会长->解散公会
                    final MyAlertDailog dialog3 = new MyAlertDailog(mContext);
                    Resources res3 = mContext.getResources();
                    dialog3.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    dialog3.setContent1("解散的公会将不能恢复，确定要解散吗？"
                            , Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    dialog3.setLeftButtonText("取消");
                    dialog3.setRightButtonText("确定");
                    if (dialog3 != null && !dialog3.isShowing()) {
                        dialog3.show();
                    }
                    dialog3.setOnClickLeftListener(new onClickLeftListener() {
                        @Override
                        public void onClickLeft() {
                            dialog3.dismiss();

                        }
                    });
                    dialog3.setOnClickRightListener(new onClickRightListener() {

                        @Override
                        public void onClickRight() {
                            dialog3.dismiss();
                            //解散工会
                            LoginData user = DBManager.getInstance(mContext).getUserMessage();
                            new JoinOrExitGuildTask(mContext, user.userId, guildId, "1", new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    // TODO Auto-generated method stub
                                    ToastUtil.showToast(mContext, "您已解散该公会");
                                    if (popupWindow != null && popupWindow.isShowing()) {
                                        popupWindow.dismiss();
                                    }
                                    //
                                    SharedPreUtil.putStringValue(mContext, ACTION_GetSignList + guildId, "");
                                    //刷新个人信息（含我的公会字段）
                                    final LoginData user = DBManager.getInstance(mContext).getUserMessage();
                                    new GetMainPageInfoTask(mContext, user.userId, new Back() {

                                        @Override
                                        public void success(Object object, String msg) {

                                            if (object != null && object instanceof GetMainPageInfo) {
                                                GetMainPageInfo mQueryPtb = (GetMainPageInfo) object;
                                                if (mQueryPtb.data != null) {
                                                    user.jsonData = null;
                                                    user.guildId = mQueryPtb.data.guildId;
                                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                                    DBManager.getInstance(mContext).insert(user);
                                                    finish1();
                                                }
                                            }


                                        }

                                        @Override
                                        public void fail(String status, String msg, Object object) {
                                            user.guildId = "";
                                            user.jsonData = null;
                                            user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                            DBManager.getInstance(mContext).insert(user);
                                            finish1();
                                        }
                                    }).start();


                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, msg);

                                }
                            }).start();

                        }
                    });
                } else if (!TextUtils.isEmpty(guildId) && !guildId.equals("0") && user != null && !TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0") && guildId.equals(user.guildId)) {
                    final MyAlertDailog dialog3 = new MyAlertDailog(mContext);
                    Resources res3 = mContext.getResources();
                    dialog3.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    dialog3.setContent1("确定退出公会吗？"
                            , Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    dialog3.setLeftButtonText("取消");
                    dialog3.setRightButtonText("确定");
                    if (dialog3 != null && !dialog3.isShowing()) {
                        dialog3.show();
                    }
                    dialog3.setOnClickLeftListener(new onClickLeftListener() {
                        @Override
                        public void onClickLeft() {
                            dialog3.dismiss();

                        }
                    });
                    dialog3.setOnClickRightListener(new onClickRightListener() {

                        @Override
                        public void onClickRight() {
                            dialog3.dismiss();

                            //退出工会
                            LoginData user = DBManager.getInstance(mContext).getUserMessage();
                            new JoinOrExitGuildTask(mContext, user.userId, guildId, "1", new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    //
                                    SharedPreUtil.putStringValue(mContext, ACTION_GetSignList + guildId, "");
                                    initData();
                                    ToastUtil.showToast(mContext, "您已退出公会");
                                    if (popupWindow != null && popupWindow.isShowing()) {
                                        popupWindow.dismiss();
                                    }
                                    //刷新个人信息（含我的公会字段）
                                    LoginData user = DBManager.getInstance(mContext).getUserMessage();
                                    user.guildId = "";
                                    user.jsonData = null;
                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                    DBManager.getInstance(mContext).insert(user);
                                    //finish1();
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, msg);

                                }
                            }).start();

                        }
                    });
                } else {
                    ToastUtil.showToast(mContext, "你还没加入公会");
                }


                break;
            case R.id.btn_cancel:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.mLineareveryoneplaying:
                //大家都在玩的游戏
                Bundle budles = new Bundle();
                budles.putString("guildId", guildId);
                startActivity(EveryonePlayingActivity.class, budles);
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void showPopupWindow() {

        View view = (LinearLayout) LayoutInflater.from(SociatyDetailsActivity.this)
                .inflate(R.layout.popmenu, null);

        Button bt_clear = (Button) view.findViewById(R.id.btn_take_photo);
        Button bt_exit = (Button) view.findViewById(R.id.btn_pick_photo);
        user = DBManager.getInstance(mContext).getUserMessage();
        if (!TextUtils.isEmpty(guild_userId) && user != null && !TextUtils.isEmpty(user.userId) && guild_userId.equals(user.userId)) {//会长->解散公会
            bt_exit.setText("解散公会");
        }
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        bt_clear.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        if (popupWindow == null) {
            int width = view.getWidth();
            int height = view.getHeight();
            popupWindow = new PopupWindow(SociatyDetailsActivity.this);
			/* WindowManager.LayoutParams lp = getWindow().getAttributes();  
	        lp.alpha =0.5f; //0.0-1.0  
	        getWindow().setAttributes(lp);*/
            // 设置半透明灰色
            ColorDrawable dw = new ColorDrawable(0x7F000000);
            popupWindow.setBackgroundDrawable(dw);
            //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popr_bg_shape));
            //setBackgroundDrawableResource(android.R.color.transparent);
            //popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
            popupWindow.setTouchable(true); // 设置PopupWindow可触摸
            popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

            popupWindow.setContentView(view);

            popupWindow.setWidth(LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LayoutParams.MATCH_PARENT);

            popupWindow.setAnimationStyle(R.style.popuStyle);   //设置 popupWindow 动画样式
        }

        popupWindow.showAtLocation(topTitle, Gravity.BOTTOM, 0, 0);

        popupWindow.update();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                finish1();
            }
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
    int i = 0;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("SociatyDetailsActivity");
        super.onResume();
        if (i == 0) {
            i = 1;
        } else {
            initData();
        }
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("SociatyDetailsActivity");
        super.onPause();
    }


}
