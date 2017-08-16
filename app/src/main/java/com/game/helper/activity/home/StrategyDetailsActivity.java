package com.game.helper.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.adapter.home.StrategyCommentsAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGameCutByIdTask;
import com.game.helper.net.task.InsertShareTask;
import com.game.helper.net.task.QueryGameCutPlTask;
import com.game.helper.net.task.SaveGameCutOptypeTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetGameCutById;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryGameCut.GameCut;
import com.game.helper.sdk.model.returns.QueryGameCutPl;
import com.game.helper.sdk.model.returns.QueryGameCutPl.GameCutPl;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.ShareDialog;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.ListViewForScrollView;
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

/**
 * @Description 攻略详情
 * @Path com.game.helper.activity.home.StrategyDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午7:48:29
 * @Company
 */
public class StrategyDetailsActivity extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.imageView_pic)
    CircleImageView imageView_pic;
    @BindView(R.id.tv_item)
    TextView tv_item;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_cai)
    TextView tv_cai;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_sc)
    TextView tv_sc;
    @BindView(R.id.tv_caontent)
    TextView tv_caontent;
    @BindView(R.id.tv_pl)
    TextView tv_pl;
    @BindView(R.id.iv_img1)
    ImageView iv_img1;
    @BindView(R.id.iv_img2)
    ImageView iv_img2;
    @BindView(R.id.iv_img3)
    ImageView iv_img3;
    @BindView(R.id.iv_img4)
    ImageView iv_img4;
    @BindView(R.id.iv_img5)
    ImageView iv_img5;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.tv_setcomment)
    ImageView tv_setcomment;
    @BindView(R.id.msg100)
    TextView msg100;
    @BindView(R.id.iv_cai)
    ImageView iv_cai;
    @BindView(R.id.iv_zan)
    ImageView iv_zan;
    @BindView(R.id.iv_sc)
    ImageView iv_sc;

    @BindView(R.id.comments_listView)
    ListViewForScrollView comments_listView;
    StrategyCommentsAdapter mStrategyDetailsCommentsAdapter;
    protected List<GameCutPl> commentList = new ArrayList<GameCutPl>();
    LoginData user;

    public String cutId;
    GameCut mGameCut;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_left_layout)
    LinearLayout topLeftLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.isDownload1)
    View isDownload1;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_strategy_details);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initView() {
        msg100.setText("首次分享成功后，即可获得" + BaseApplication.mInstance.shareNum + "金币\n每位好友首次阅读，您都将获得" + (BaseApplication.mInstance.shareNum / 2) + "金币");
        topTitle.setText("攻略详情");
        topTitle.setEnabled(true);
        topTitle.setFocusable(true);
        topTitle.setFocusableInTouchMode(true);
        topTitle.requestFocus();
        topLeftBack.setVisibility(View.VISIBLE);
        top_liner_right.setVisibility(View.VISIBLE);
        top_iv_right.setVisibility(View.VISIBLE);
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.shouye_09));
        mStrategyDetailsCommentsAdapter = new StrategyCommentsAdapter(mContext, commentList);
        comments_listView.setAdapter(mStrategyDetailsCommentsAdapter);
    }

    String title;
    String userName;
    String content;
    String imgUrl;

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        cutId = getIntent().getStringExtra("cutId");
        new GetGameCutByIdTask(mContext, true, cutId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetGameCutById) {
                    GetGameCutById mGetGameCutById = (GetGameCutById) object;
                    if (mGetGameCutById.data != null) {
                        mGameCut = mGetGameCutById.data;
                        if (mGameCut != null) {
                            tv_title.setText(mGameCut.title);
                            title = mGameCut.title;
                            userName = mGameCut.userName;
                            content = mGameCut.content;
                            if (!TextUtils.isEmpty(mGameCut.img1)) {
                                imgUrl = "" + mGameCut.fileAskPath + mGameCut.img1;
                            } else if (!TextUtils.isEmpty(mGameCut.img2)) {
                                imgUrl = "" + mGameCut.fileAskPath + mGameCut.img2;
                            } else if (!TextUtils.isEmpty(mGameCut.img3)) {
                                imgUrl = "" + mGameCut.fileAskPath + mGameCut.img3;
                            } else if (!TextUtils.isEmpty(mGameCut.img4)) {
                                imgUrl = "" + mGameCut.fileAskPath + mGameCut.img4;
                            } else if (!TextUtils.isEmpty(mGameCut.img5)) {
                                imgUrl = "" + mGameCut.fileAskPath + mGameCut.img5;
                            } else {
                                imgUrl = "" + mGameCut.fileAskPath + mGameCut.userIcon;
                            }

                            if (!TextUtils.isEmpty(mGameCut.userIcon)) {

                                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                        .load("" + mGameCut.fileAskPath + mGameCut.userIcon)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        //.centerCrop()// 长的一边撑满
                                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                        .error(R.drawable.pic_moren)//加载失败时显示的图片
                                        //.crossFade()
                                        .into(imageView_pic);
                            } else {
                                imageView_pic.setImageDrawable(getResources().getDrawable(R.drawable.pic_moren));
                            }

                            imageView_pic.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("userId", mGameCut.userId);
                                    ((BaseActivity) mContext).startActivity(PersonalHomepageActivity.class, bundle);

                                }
                            });

                            tv_item.setText(mGameCut.userName);
                            tv_item.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("userId", mGameCut.userId);
                                    ((BaseActivity) mContext).startActivity(PersonalHomepageActivity.class, bundle);

                                }
                            });
                            tv_msg.setText(mGameCut.createTimeString);
                            tv_cai.setText("(" + mGameCut.cjNum + ")");
                            tv_zan.setText("(" + mGameCut.dzNum + ")");
                            tv_sc.setText("(" + mGameCut.scNum + ")");
                            tv_caontent.setText(mGameCut.content);
                            tv_pl.setText("全部评论(" + mGameCut.plNum + ")");
                            if (!TextUtils.isEmpty(mGameCut.img1)) {
                                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                        .load("" + mGameCut.fileAskPath + mGameCut.img1)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        //.centerCrop()// 长的一边撑满
                                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                        //.crossFade()
                                        .into(iv_img1);
                                iv_img1.setVisibility(View.VISIBLE);
                            }
                            if (!TextUtils.isEmpty(mGameCut.img2)) {
                                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                        .load("" + mGameCut.fileAskPath + mGameCut.img2)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        //.centerCrop()// 长的一边撑满
                                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                        //.crossFade()
                                        .into(iv_img2);
                                iv_img2.setVisibility(View.VISIBLE);
                            }
                            if (!TextUtils.isEmpty(mGameCut.img3)) {
                                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                        .load("" + mGameCut.fileAskPath + mGameCut.img3)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        //.centerCrop()// 长的一边撑满
                                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                        //.crossFade()
                                        .into(iv_img3);
                                iv_img3.setVisibility(View.VISIBLE);
                            }
                            if (!TextUtils.isEmpty(mGameCut.img4)) {
                                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                        .load("" + mGameCut.fileAskPath + mGameCut.img4)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        //.centerCrop()// 长的一边撑满
                                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                        //.crossFade()
                                        .into(iv_img4);
                                iv_img4.setVisibility(View.VISIBLE);
                            }
                            if (!TextUtils.isEmpty(mGameCut.img5)) {
                                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                        .load("" + mGameCut.fileAskPath + mGameCut.img5)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        //.centerCrop()// 长的一边撑满
                                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                        //.crossFade()
                                        .into(iv_img5);
                                iv_img5.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();

        getComemetData();
    }

    public void GetGameCutById(final int tag) {
        new GetGameCutByIdTask(mContext, false, cutId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetGameCutById) {
                    GetGameCutById mGetGameCutById = (GetGameCutById) object;
                    if (mGetGameCutById.data != null) {
                        mGameCut = mGetGameCutById.data;
                        if (mGameCut != null) {
                            tv_cai.setText("(" + mGameCut.cjNum + ")");
                            tv_zan.setText("(" + mGameCut.dzNum + ")");
                            tv_sc.setText("(" + mGameCut.scNum + ")");
                            if (tag == 1) {
                                tv_pl.setText("全部评论(" + mGameCut.plNum + ")");
                            }


                        }
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    public void getComemetData() {
        //获取攻略评论
        new QueryGameCutPlTask(mContext, cutId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryGameCutPl) {
                    QueryGameCutPl mQueryGameCutPl = (QueryGameCutPl) object;
                    if (mQueryGameCutPl.data != null && mQueryGameCutPl.data.size() >= 0) {
                        commentList.clear();
                        commentList.addAll(mQueryGameCutPl.data);
                        mStrategyDetailsCommentsAdapter.setCommentList(commentList);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    private ShareDialog shareDialog;

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_setcomment, R.id.iv_cai, R.id.tv_cai
            , R.id.iv_zan, R.id.tv_zan, R.id.iv_sc, R.id.tv_sc, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                //分享
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
                            sp.setTitle(title);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId); // 标题的超链接
                            if (!TextUtils.isEmpty(content)) {
                                sp.setText(content);
                            } else {
                                sp.setText(userName + "--发布攻略");
                            }
                            sp.setImageUrl("" + imgUrl);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(StrategyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle(title);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId); // 标题的超链接
                            if (!TextUtils.isEmpty(content)) {
                                sp.setText(content);
                            } else {
                                sp.setText(userName + "--发布攻略");
                            }
                            sp.setImageUrl("" + imgUrl);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(StrategyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            if (!TextUtils.isEmpty(content)) {
                                sp.setText(content + Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId);
                            } else {
                                sp.setText(userName + "--发布攻略 " + Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId);
                            }

                            sp.setImageUrl("" + imgUrl);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(StrategyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle(title);
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            if (!TextUtils.isEmpty(content)) {
                                sp.setText(content);
                            } else {
                                sp.setText(userName + userName + "--发布攻略");
                            }
                            sp.setImageUrl("" + imgUrl);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(StrategyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();

                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            if (!TextUtils.isEmpty(content)) {
                                sp.setText(content);//设置了不会显示,可选参数
                                sp.setTitle(title + ":" + content);
                            } else if (!TextUtils.isEmpty(userName)) {
                                sp.setText(userName);//设置了不会显示,可选参数
                                sp.setTitle(title + ":" + userName + "--发布攻略");
                            } else {
                                sp.setTitle(title);
                            }
                            sp.setImageUrl("" + imgUrl);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toCutDetail?cutId=" + cutId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(StrategyDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            //Toast.makeText(StrategyDetailsActivity.this,"您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });


                break;
            case R.id.iv_cai:
            case R.id.tv_cai:
                //踩
                if (iv_cai.isEnabled() && tv_cai.isEnabled()) {
                    iv_cai.setEnabled(false);
                    tv_cai.setEnabled(false);
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "cj", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            iv_cai.setEnabled(true);
                            tv_cai.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            iv_cai.setEnabled(true);
                            tv_cai.setEnabled(true);
                            ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                }
                break;
            case R.id.iv_zan:
            case R.id.tv_zan:
                //赞
                if (iv_zan.isEnabled() && tv_zan.isEnabled()) {
                    iv_zan.setEnabled(false);
                    tv_zan.setEnabled(false);
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "dz", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            iv_zan.setEnabled(true);
                            tv_zan.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            iv_zan.setEnabled(true);
                            tv_zan.setEnabled(true);
                            ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                }
                break;
            case R.id.iv_sc:
            case R.id.tv_sc:
                //收藏
                if (iv_sc.isEnabled() && tv_sc.isEnabled()) {
                    iv_sc.setEnabled(false);
                    tv_sc.setEnabled(false);
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "sc", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            iv_sc.setEnabled(true);
                            tv_sc.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            iv_sc.setEnabled(true);
                            tv_sc.setEnabled(true);
                            ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                }
                break;
            case R.id.tv_setcomment:
                //评论
                if (tv_setcomment.isEnabled()) {
                    tv_setcomment.setEnabled(false);
                    String content = et_comment.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        tv_setcomment.setEnabled(true);
                        ToastUtil.showToast(mContext, "说些什么呗...");
                    } else {
                        new SaveGameCutOptypeTask(mContext, user.userId, cutId, "pl", content, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                tv_setcomment.setEnabled(true);
                                getComemetData();
                                GetGameCutById(1);
                                ToastUtil.showToast(mContext, "评论成功");
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                tv_setcomment.setEnabled(true);
                                ToastUtil.showToast(mContext, "评论失败");
                            }
                        }).start();
                    }
                }
                break;
            default:
                super.onClick(v);
                break;
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
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "fs", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);
                        }
                    }).start();

                    new InsertShareTask(StrategyDetailsActivity.this, "share", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);

                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "fs", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                    new InsertShareTask(StrategyDetailsActivity.this, "share", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);

                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "fs", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);
                        }
                    }).start();

                    new InsertShareTask(StrategyDetailsActivity.this, "share", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);

                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "新浪微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "fs", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);
                        }
                    }).start();

                    new InsertShareTask(StrategyDetailsActivity.this, "share", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);

                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "微信好友分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    new SaveGameCutOptypeTask(mContext, user.userId, cutId, "fs", null, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                    new InsertShareTask(StrategyDetailsActivity.this, "share", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            topRight.setEnabled(true);
                            GetGameCutById(0);

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            topRight.setEnabled(true);
                            //ToastUtil.showToast(mContext, msg);

                        }
                    }).start();
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
        MobclickAgent.onPageEnd("StrategyDetailsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("StrategyDetailsActivity");
        super.onResume();
    }

}