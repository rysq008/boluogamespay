package com.game.helper.activity.community;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.StrategyDetailsCommentsAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.AddDZTask;
import com.game.helper.net.task.AddPLTask;
import com.game.helper.net.task.AddSCTask;
import com.game.helper.net.task.GetContenteOtherDetailTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetContenteOtherDetail;
import com.game.helper.sdk.model.returns.GetContenteOtherDetail.Comment;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.ShareDialog;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.ListViewForScrollView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
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
 * @Description 资讯详情
 * @Path com.game.helper.activity.community.ConsultingDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月25日 下午5:05:35
 * @Company
 */
public class ConsultingDetailsActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.imageView_pic)
    CircleImageView imageView_pic;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.tv_collect)
    TextView tv_collect;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_de_img)
    ImageView iv_de_img;
    @BindView(R.id.iv_de_img1)
    ImageView iv_de_img1;
    @BindView(R.id.tv_commentTotal)
    TextView tv_commentTotal;

    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.tv_setcomment)
    ImageView tv_setcomment;

    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.iv_zan)
    ImageView iv_zan;

    @BindView(R.id.mScrollView)
    ScrollView mScrollView;
    @BindView(R.id.comments_listView)
    ListViewForScrollView comments_listView;

    @BindView(R.id.msg100)
    TextView msg100;
    public
    @BindView(R.id.webview)
    WebView webview;

    protected List<Comment> commentList = new ArrayList<Comment>();
    StrategyDetailsCommentsAdapter mStrategyDetailsCommentsAdapter;
    ShareDialog shareDialog;
    int isStart = 0;
    String contentId;
    LoginData user;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    /* @BindView(R.id.top_right)
     TextView topRight;*/
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_consulting_details);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initView() {
        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        webview.setInitialScale(25);


        msg100.setText("首次分享成功后，即可获得" + BaseApplication.mInstance.shareNum + "金币\n每位好友首次阅读，您都将获得" + (BaseApplication.mInstance.shareNum / 2) + "金币");
        user = DBManager.getInstance(mContext).getUserMessage();

        topTitle.setText("资讯详情");
        topLeftBack.setVisibility(View.VISIBLE);

        topLinerRight.setVisibility(View.VISIBLE);
        topIvRight.setImageDrawable(getResources().getDrawable(R.drawable.share));
        /*topRight.setText("分享");
        topRight.setVisibility(View.VISIBLE);*/

        mStrategyDetailsCommentsAdapter = new StrategyDetailsCommentsAdapter(mContext, commentList);
        comments_listView.setAdapter(mStrategyDetailsCommentsAdapter);
    }


    String titles, fileAskPath, contentImage, contentText, contentAbstract;

    @Override
    protected void initData() {
        contentId = getIntent().getStringExtra("contentId");
        tv_title.setText(getIntent().getStringExtra("contentTitle"));
        titles = getIntent().getStringExtra("contentTitle");
        tv_msg.setText(getIntent().getStringExtra("createTimeString"));
        tv_zan.setText("（" + getIntent().getStringExtra("dzNum") + "）");
        tv_commentTotal.setText("全部评论(" + getIntent().getStringExtra("plNum") + ")");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("contentText"))) {

            contentText = getIntent().getStringExtra("contentText");
            //tv_content.setText(getIntent().getStringExtra("contentText"));
        } else {
            contentAbstract = getIntent().getStringExtra("contentAbstract");
            //tv_content.setText(getIntent().getStringExtra("contentAbstract"));
        }
        tv_collect.setText("（" + getIntent().getStringExtra("scNum") + "）");
        fileAskPath = getIntent().getStringExtra("fileAskPath");
        contentImage = getIntent().getStringExtra("contentImage");
        Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                .load("" + fileAskPath + contentImage)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.centerCrop()// 长的一边撑满
                //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                .error(R.drawable.picture_defeated)//加载失败时显示的图片
                //.crossFade()
                .into(iv_de_img);

        //获取资讯详情
        new GetContenteOtherDetailTask(mContext, contentId, new Back() {

            @Override
            public void success(Object object, String msg) {
                //ACTION_GetContenteOtherDetail
                if (object != null && object instanceof GetContenteOtherDetail) {
                    GetContenteOtherDetail mDetail = (GetContenteOtherDetail) object;
                    if (mDetail.data != null) {
                        if (mDetail.data.tinfoContent != null) {
                            titles = mDetail.data.tinfoContent.contentTitle;
                            //默认图片，无图片或没加载完显示此图片
                            //Drawable defaultDrawables = getResources().getDrawable(R.drawable.preview_card_pic_loading);
                            //调用
                            //Spanned sps = Html.fromHtml(mDetail.data.tinfoContent.contentAbstract, new HtmlImageGetter(tv_content, "/g9esun_msg", defaultDrawables), null);
                            contentAbstract = mDetail.data.tinfoContent.contentAbstract;

                            if (!TextUtils.isEmpty(contentAbstract)) {
                                tv_title.setText(mDetail.data.tinfoContent.contentTitle + contentAbstract);
                            }

                            tv_msg.setText(mDetail.data.tinfoContent.createTimeString);
                            tv_zan.setText("（" + mDetail.data.tinfoContent.dzNum + "）");
                            tv_commentTotal.setText("全部评论(" + mDetail.data.tinfoContent.plNum + ")");
                            if (!TextUtils.isEmpty(mDetail.data.tinfoContent.contentText)) {
                                //默认图片，无图片或没加载完显示此图片
                                Drawable defaultDrawable = getResources().getDrawable(R.drawable.preview_card_pic_loading);
                                //调用
                                Spanned sp = Html.fromHtml(mDetail.data.tinfoContent.contentText);//, new HtmlImageGetter(tv_content, "/g9esun_msg", defaultDrawable), null

                                //tv_content.setText(sp);
                                contentText = sp.toString();
                                //webView.loadData(mDetail.data.tinfoContent.contentText,"text/html", "UTF-8");
                                webview.loadUrl(Config.getInstance().IP + "/helper/app/toWapPage?type=content&id=" + contentId);//222

                                //	tv_content.setText(Html.fromHtml(mDetail.data.tinfoContent.contentText, imgGetter, null));
                                //	contentText=Html.fromHtml(mDetail.data.tinfoContent.contentText, imgGetter, null).toString();
                            } else {

                                //webView.loadData(mDetail.data.tinfoContent.contentAbstract,"text/html", "UTF-8");
                                //tv_content.setText(Html.fromHtml(mDetail.data.tinfoContent.contentAbstract, imgGetter, null));
                                //contentAbstract=Html.fromHtml(mDetail.data.tinfoContent.contentAbstract, imgGetter, null).toString();
                            }


                            tv_collect.setText("（" + mDetail.data.tinfoContent.scNum + "）");
                            fileAskPath = mDetail.data.tinfoContent.fileAskPath;
                            contentImage = mDetail.data.tinfoContent.contentImage;
                            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                    .load("" + mDetail.data.tinfoContent.fileAskPath + mDetail.data.tinfoContent.contentImage)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    //.centerCrop()// 长的一边撑满
                                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                    .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                    //.crossFade()
                                    .into(iv_de_img);

                        }
                        if (mDetail.data.commentList != null && mDetail.data.commentList.size() >= 0) {
                            commentList.clear();
                            commentList.addAll(mDetail.data.commentList);
                            mStrategyDetailsCommentsAdapter.setCommentList(commentList);
                        }
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {


            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_iv_right, R.id.iv_collect, R.id.tv_collect, R.id.iv_zan, R.id.tv_zan, R.id.tv_setcomment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_collect:
            case R.id.iv_collect:
                if (iv_collect.isEnabled() && tv_collect.isEnabled()) {
                    iv_collect.setEnabled(false);
                    tv_collect.setEnabled(false);
                    new AddSCTask(mContext, contentId, user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {

                            //获取资讯详情
                            new GetContenteOtherDetailTask(mContext, contentId, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    //ACTION_GetContenteOtherDetail
                                    if (object != null && object instanceof GetContenteOtherDetail) {
                                        GetContenteOtherDetail mDetail = (GetContenteOtherDetail) object;
                                        if (mDetail.data != null) {
                                            if (mDetail.data.tinfoContent != null) {
                                                tv_zan.setText("（" + mDetail.data.tinfoContent.dzNum + "）");
                                                tv_collect.setText("（" + mDetail.data.tinfoContent.scNum + "）");
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {


                                }
                            }).start();
                            iv_collect.setEnabled(true);
                            tv_collect.setEnabled(true);
                            //iv_collect.setSelected(true);
                            ToastUtil.showToast(mContext,msg);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            ToastUtil.showToast(mContext, msg);
                            iv_collect.setEnabled(true);
                            tv_collect.setEnabled(true);
                        }
                    }).start();
                }
                break;
            case R.id.tv_zan:
            case R.id.iv_zan:
                if (iv_zan.isEnabled() && tv_zan.isEnabled()) {
                    iv_zan.setEnabled(false);
                    tv_zan.setEnabled(false);
                    new AddDZTask(mContext, contentId, user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            //获取资讯详情
                            ToastUtil.showToast(mContext, msg);
                            new GetContenteOtherDetailTask(mContext, contentId, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    //ACTION_GetContenteOtherDetail
                                    if (object != null && object instanceof GetContenteOtherDetail) {
                                        GetContenteOtherDetail mDetail = (GetContenteOtherDetail) object;
                                        if (mDetail.data != null) {
                                            if (mDetail.data.tinfoContent != null) {
                                                tv_zan.setText("（" + mDetail.data.tinfoContent.dzNum + "）");
                                                tv_collect.setText("（" + mDetail.data.tinfoContent.scNum + "）");
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {


                                }
                            }).start();
                            iv_zan.setEnabled(true);
                            tv_zan.setEnabled(true);
                            //iv_zan.setSelected(true);
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
            case R.id.tv_setcomment:
                if (tv_setcomment.isEnabled()) {
                    tv_setcomment.setEnabled(false);
                    String plContent = et_comment.getText().toString();
                    if (TextUtils.isEmpty(plContent)) {
                        ToastUtil.showToast(mContext, "说点什么吧");
                        tv_setcomment.setEnabled(true);
                    } else {
                        new AddPLTask(mContext, contentId, user.userId, plContent, new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                //刷新评论列表...
                                new GetContenteOtherDetailTask(mContext, contentId, new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        if (object != null && object instanceof GetContenteOtherDetail) {
                                            GetContenteOtherDetail mDetail = (GetContenteOtherDetail) object;
                                            if (mDetail.data != null) {
                                                if (mDetail.data.commentList != null && mDetail.data.commentList.size() >= 0) {
                                                    commentList.clear();
                                                    commentList.addAll(mDetail.data.commentList);
                                                    mStrategyDetailsCommentsAdapter.setCommentList(commentList);
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {


                                    }
                                }).start();

                                ToastUtil.showToast(mContext, "评论成功");
                                tv_setcomment.setEnabled(true);
                                et_comment.setText("");
                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                ToastUtil.showToast(mContext, "评论失败");
                                tv_setcomment.setEnabled(true);
                            }
                        }).start();
                    }
                }

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
                            if (!TextUtils.isEmpty(contentAbstract)) {
                                sp.setTitle(titles + contentAbstract);
                            } else {
                                sp.setTitle(titles);
                            }
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId); // 标题的超链接
                            sp.setText(contentText);
                            sp.setImageUrl("" + fileAskPath + contentImage);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(ConsultingDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            if (!TextUtils.isEmpty(contentAbstract)) {
                                sp.setTitle(titles + contentAbstract);
                            } else {
                                sp.setTitle(titles);
                            }
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId); // 标题的超链接
                            sp.setText(contentText);
                            sp.setImageUrl("" + fileAskPath + contentImage);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(ConsultingDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            if (!TextUtils.isEmpty(contentAbstract)) {
                                sp.setText(titles + " " + contentAbstract + " " + contentText + Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId);
                            } else {
                                sp.setText(titles + ":" + contentText + Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId);
                            }

                            sp.setImageUrl("" + fileAskPath + contentImage);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(ConsultingDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            if (!TextUtils.isEmpty(contentAbstract)) {
                                sp.setTitle(titles + contentAbstract);
                            } else {
                                sp.setTitle(titles);
                            }
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText(contentText);

                            sp.setImageUrl("" + fileAskPath + contentImage);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(ConsultingDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();

                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            if (!TextUtils.isEmpty(contentText)) {
                                sp.setText(contentText);//设置了不会显示,可选参数
                                if (!TextUtils.isEmpty(contentAbstract)) {
                                    sp.setTitle(titles + " " + contentAbstract + " " + contentText);
                                } else {
                                    sp.setTitle(titles + ":" + contentText);
                                }
                            } else if (!TextUtils.isEmpty(contentAbstract)) {
                                sp.setText(contentAbstract);//设置了不会显示,可选参数
                                sp.setTitle(titles + " " + contentAbstract);
                            } else {
                                sp.setTitle(titles);
                            }
                            sp.setImageUrl("" + fileAskPath + contentImage);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toContent?contentId=" + contentId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(ConsultingDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(ConsultingDetailsActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                break;
            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("ConsultingCenterActivity");
        super.onResume();
        if (isStart == 0) {
            isStart = 1;
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
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
                /*new InsertShareTask(ConsultingDetailsActivity.this, contentId, user.userId, new Back() {

					@Override
					public void success(Object object, String msg) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						// TODO Auto-generated method stub
						
					}
				}).start();*/
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                /*new InsertShareTask(ConsultingDetailsActivity.this, contentId, user.userId, new Back() {
					
					@Override
					public void success(Object object, String msg) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						// TODO Auto-generated method stub
						
					}
				}).start();*/
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
               /* new InsertShareTask(ConsultingDetailsActivity.this, contentId, user.userId, new Back() {
					
					@Override
					public void success(Object object, String msg) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						// TODO Auto-generated method stub
						
					}
				}).start();*/
                    Toast.makeText(getApplicationContext(), "新浪微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 6:
               /* new InsertShareTask(ConsultingDetailsActivity.this, contentId, user.userId, new Back() {
					
					@Override
					public void success(Object object, String msg) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						// TODO Auto-generated method stub
						
					}
				}).start();*/
                    Toast.makeText(getApplicationContext(), "微信好友分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
               /* new InsertShareTask(ConsultingDetailsActivity.this, contentId, user.userId, new Back() {
					
					@Override
					public void success(Object object, String msg) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						// TODO Auto-generated method stub
						
					}
				}).start();*/
                    Toast.makeText(getApplicationContext(), "微信朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "分享失败", Toast.LENGTH_LONG).show();
                    break;
                case 100:
                    iv_de_img1.setVisibility(View.GONE);
                    break;
                case 101:
                    iv_de_img1.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("ConsultingCenterActivity");
        super.onPause();
    }


}

