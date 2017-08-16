package com.game.helper.activity.home;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.home.Gift;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGiftDetailTask;
import com.game.helper.net.task.GetGiftTask;
import com.game.helper.net.task.InsertShareTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetGift;
import com.game.helper.sdk.model.returns.GetGiftDetail;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.game.helper.view.dialog.ShareDialog;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

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
 * @Description 礼包详情-全民突击礼包
 * @Path com.game.helper.activity.home.GiftsDetailActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午5:55:44
 * @Company
 */
public class GiftsDetailActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.tv_gets)
    TextView tv_gets;
    @BindView(R.id.iv_item)
    XCRoundImageViewByXfermode iv_item;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_users)
    TextView tv_users;
    @BindView(R.id.tv_startTimeTOEndTime)
    TextView tv_startTimeTOEndTime;

    String giftId;
    Gift mGift;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_gifts_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initView() {
        giftId = getIntent().getStringExtra("giftId");
        topTitle.setText("");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("分享");
        topRight.setVisibility(View.VISIBLE);
    }

    LoginData user;

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new GetGiftDetailTask(mContext, giftId, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetGiftDetail) {
                    GetGiftDetail mGetGiftDetail = (GetGiftDetail) object;
                    if (mGetGiftDetail.data != null) {
                        mGift = mGetGiftDetail.data;
                        setData();
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();

    }

    protected void setData() {
        if (mGift != null) {
            if (!TextUtils.isEmpty(mGift.giftName)) {
                topTitle.setText(mGift.giftName);
            } else {
                topTitle.setText("礼包");
            }

            iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
            iv_item.setRoundBorderRadius(23);
            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                    .load("" + mGift.fileAskPath + mGift.field2)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.centerCrop()// 长的一边撑满
                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                    .error(R.drawable.picture_defeated)//加载失败时显示的图片
                    //.crossFade()
                    .into(iv_item);

            if (!TextUtils.isEmpty(mGift.amount)) {
                tv_msg.setText("剩余：" + mGift.amount);
            } else {
                tv_msg.setText("剩余：0");
            }

            tv_content.setText(mGift.content);
            tv_users.setText(mGift.useWay);
            tv_startTimeTOEndTime.setText(mGift.leftTimeString + "-" + mGift.rightTimeString);
            if (!TextUtils.isEmpty(mGift.field1)) {//表示用户是否已领取（非空则表示已领取）,
                tv_gets.setText("已领取");
                tv_gets.setEnabled(false);
            } else {
                tv_gets.setText("领取");
                tv_gets.setEnabled(true);
            }

        } else {
            tv_gets.setText("领取");
            tv_gets.setEnabled(true);
        }
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_gets, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
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
                            sp.setTitle("快来领取:" + mGift.giftName + "吧！");
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGiftDetail?giftId=" + giftId); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");
                            sp.setImageUrl("" + mGift.fileAskPath + mGift.field2);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(GiftsDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + mGift.giftName + "吧！");
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGiftDetail?giftId=" + giftId); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");
                            sp.setImageUrl("" + mGift.fileAskPath + mGift.field2);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toGiftDetail?giftId=" + giftId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(GiftsDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("快来领取:" + mGift.giftName + "吧！" + "立即下载G9游戏领取吧 ，还有更多福利等你拿。" + Config.getInstance().IP + "/helper/app/toGiftDetail?giftId=" + giftId);
                            sp.setImageUrl("" + mGift.fileAskPath + mGift.field2);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(GiftsDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + mGift.giftName + "吧！");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");
                            sp.setImageUrl("" + mGift.fileAskPath + mGift.field2);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGiftDetail?giftId=" + giftId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(GiftsDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + mGift.giftName + "吧！" + "立即下载G9游戏搜索游戏Id：" + mGift.gameId + "即可领取 ");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");//设置了不会显示,可选参数
                            sp.setImageUrl("" + mGift.fileAskPath + mGift.field2);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGiftDetail?giftId=" + giftId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(GiftsDetailActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(GiftsDetailActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                break;
            case R.id.tv_gets:
                if (mGift != null) {
                    final MyAlertDailog dialog = new MyAlertDailog(
                            mContext);
                    Resources res = mContext.getResources();
                    dialog.setTitle("确认领取", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    dialog.setContent1("您将免费《" + mGift.giftName + "》新手礼包，确定要领取吗？"
                            , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    dialog.setLeftButtonText("取消");
                    dialog.setRightButtonText("确定");
                    if (dialog != null && !dialog.isShowing()) {
                        dialog.show();
                    }
                    dialog.setOnClickLeftListener(new onClickLeftListener() {
                        @Override
                        public void onClickLeft() {
                            dialog.dismiss();

                        }
                    });
                    dialog.setOnClickRightListener(new onClickRightListener() {

                        @Override
                        public void onClickRight() {
                            dialog.dismiss();
                            user = DBManager.getInstance(mContext).getUserMessage();
                            new GetGiftTask(mContext, user.userId, mGift.gameId, mGift.giftId, new Back() {

                                @Override
                                public void success(Object object, String msg) {

                                    if (object != null && object instanceof GetGift) {
                                        final GetGift mGetGift = (GetGift) object;
                                        if (mGetGift.data != null) {
                                            tv_gets.setText("已领取");
                                            tv_gets.setEnabled(false);
                                            tv_msg.setText("剩余：" + mGetGift.data.amount);
                                            mGift.amount = mGetGift.data.amount;
                                            mGift.field1 = user.userId;


                                            final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                            dialog.setTitle(mGetGift.data.title);
                                            dialog.setContent1(mGetGift.data.content + "\n\n", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                            dialog.setContentT(mGetGift.data.getCode + "\n", R.color.gh_shenhui_color);
                                            dialog.setSingle("复制卡号");
                                            if (dialog != null && !dialog.isShowing()) {
                                                dialog.show();
                                            }
                                            dialog.setOnClickSingleListener(new onClickSingleListener() {

                                                @Override
                                                public void onClickSingle() {
                                                    dialog.dismiss();
                                                    if (SystemUtil.getSDKVersionNumber() >= 11) {
                                                        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                                        clipboardManager.setText(mGetGift.data.getCode);
                                                    } else {
                                                        // 得到剪贴板管理器
                                                        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mGetGift.data.getCode));
                                                    }
                                                    ToastUtil.showToast(mContext, "成功复制到剪切板");
                                                }
                                            });
                                        } else {
                                            ToastUtil.showToast(mContext, msg);
                                        }
                                    } else {
                                        ToastUtil.showToast(mContext, msg);
                                    }


                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, msg);

                                }
                            }).start();


                        }
                    });

                } else {
                    ToastUtil.showToast(mContext, "礼包不存在");
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
    protected void onPause() {
        MobclickAgent.onPageEnd("GiftsDetailActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GiftsDetailActivity");
        super.onResume();
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
                    new InsertShareTask(GiftsDetailActivity.this, "libao", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    new InsertShareTask(GiftsDetailActivity.this, "libao", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    new InsertShareTask(GiftsDetailActivity.this, "libao", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "新浪微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    new InsertShareTask(GiftsDetailActivity.this, "libao", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "微信好友分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    new InsertShareTask(GiftsDetailActivity.this, "libao", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


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
}
