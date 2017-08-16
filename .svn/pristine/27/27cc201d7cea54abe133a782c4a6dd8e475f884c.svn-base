package com.game.helper.activity.mall;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.game.helper.activity.home.RechargeActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAddressListTask;
import com.game.helper.net.task.GetGoodListTask;
import com.game.helper.net.task.GetLotteryTask;
import com.game.helper.net.task.SaveExchangeTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.model.returns.GetAddressList.Address;
import com.game.helper.sdk.model.returns.GetGoodList;
import com.game.helper.sdk.model.returns.GetGoodList.Good;
import com.game.helper.sdk.model.returns.GetLottery;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailogEd1;
import com.game.helper.view.dialog.ShareDialog;
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
 * @Description 商品详情
 * @Path com.game.helper.activity.mall.MallProductDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月20日 下午12:40:09
 * @Company
 */
public class MallProductDetailsActivity extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.tv_conts)
    TextView tv_conts;
    @BindView(R.id.iv_mImageView)
    ImageView iv_mImageView;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.priceNow)
    TextView priceNow;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tv_Exchange)
    TextView tv_Exchange;
    public
    @BindView(R.id.webview)
    WebView webview;
    String  addressId;//
    Good good;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mall_productdetails_server_call_ll)
    LinearLayout mallProductdetailsServerCallLl;

    private List<Address> mList = new ArrayList<Address>();
    LoginData user;
    private ShareDialog shareDialog;
    private String goodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mall_productdetails);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        // 初始化ShareSDK
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

        topTitle.setText("商品详情");
        topLeftBack.setVisibility(View.VISIBLE);
        top_liner_right.setVisibility(View.VISIBLE);
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.share));
    }

    String goodName, fileAskPath, img, sprice, ptb;

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        good = (Good) getIntent().getSerializableExtra("good");
        goodId = good.goodId;

        if (good != null) {
            goodName = good.goodName;
            ptb = good.ptb;
            sprice = good.price;
            fileAskPath = good.fileAskPath;
            img = good.img;

            try {
                tv_conts.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                webview.loadUrl(Config.getInstance().IP + "/helper/app/toWapPage?type=goods&id=" + good.goodId);//222

            } catch (Exception e) {
                e.printStackTrace();
                webview.setVisibility(View.GONE);
                tv_conts.setVisibility(View.VISIBLE);
                tv_conts.setText(good.content);
            }

            tv_msg.setText(good.goodName);
            priceNow.setText(good.ptb + "积分");
            if (!TextUtils.isEmpty(good.price)) {
                price.setText(good.price + "元");
            } else {
                price.setText("");
            }

            if (TextUtils.isEmpty(good.amount) || good.amount.equals("0")) {
                tv_Exchange.setText("缺货");
                tv_Exchange.setEnabled(false);
            } else {
                tv_Exchange.setEnabled(true);
                tv_Exchange.setText("立即" + good.goodType);
            }

            if (!TextUtils.isEmpty(good.status) && good.status.equals("正常")) {

            }
            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                    .load("" + good.fileAskPath + good.img)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.centerCrop()// 长的一边撑满
                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                    .error(R.drawable.picture_defeated)//加载失败时显示的图片
                    //.crossFade()
                    .into(iv_mImageView);
        }



        //if (!TextUtils.isEmpty(goodId)) {
            //String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGoodList, "");
            //if (!TextUtils.isEmpty(json)) {
                //Object mObject = new JsonBuild().getData(GetGoodList.class, json);
                //if (mObject != null && mObject instanceof GetGoodList) {
                    //GetGoodList mData = (GetGoodList) mObject;
                    //if (mData != null && mData.data != null && mData.data.size() >= 0) {
                        //for (Good good : mData.data) {
                            //if (!TextUtils.isEmpty(good.goodId) && good.goodId.equals(goodId)) {
                                //this.good = good;

                              //  break;
                           // }
                       // }
                   // }
               // }

          //  }
        //}
        /*new GetGoodListTask(mContext, false, new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGoodList) {
                    GetGoodList mlist = (GetGoodList) object;
                    if (mlist.data != null && mlist.data.size() >= 0) {
                        for (Good good : mlist.data) {
                            if (!TextUtils.isEmpty(good.goodId) && good.goodId.equals(goodId)) {
                                MallProductDetailsActivity.this.good = good;
                                if (good != null) {
                                    goodName = good.goodName;
                                    ptb = good.ptb;
                                    sprice = good.price;
                                    fileAskPath = good.fileAskPath;
                                    img = good.img;

                                    try {
                                        tv_conts.setVisibility(View.GONE);
                                        webview.setVisibility(View.VISIBLE);
                                        webview.loadUrl(Config.getInstance().IP + "/helper/app/toWapPage?type=goods&id=" + goodId);//222

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        webview.setVisibility(View.GONE);
                                        tv_conts.setVisibility(View.VISIBLE);
                                        tv_conts.setText(good.content);
                                    }

                                    tv_msg.setText(good.goodName);
                                    priceNow.setText(good.ptb + "金币");
                                    price.setText(good.price + "元");
                                    if (TextUtils.isEmpty(good.amount) || good.amount.equals("0")) {
                                        tv_Exchange.setText("缺货");
                                        tv_Exchange.setEnabled(false);
                                    } else {
                                        tv_Exchange.setEnabled(true);
                                        tv_Exchange.setText("立即" + good.goodType);
                                    }
                                    if (!TextUtils.isEmpty(good.status) && good.status.equals("正常")) {

                                    }
                                    Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                            .load("" + good.fileAskPath + good.img)
                                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                            //.centerCrop()// 长的一边撑满
                                            //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                            .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                            //.crossFade()
                                            .into(iv_mImageView);
                                }
                                break;
                            }
                        }
                        SharedPreUtil.putStringValue(mContext, ACTION_GetGoodList, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                if (!TextUtils.isEmpty(goodId)) {
                    String json = SharedPreUtil.getStringValue(mContext, ACTION_GetGoodList, "");
                    if (!TextUtils.isEmpty(json)) {
                        Object mObject = new JsonBuild().getData(GetGoodList.class, json);
                        if (mObject != null && mObject instanceof GetGoodList) {
                            GetGoodList mData = (GetGoodList) mObject;
                            if (mData != null && mData.data != null && mData.data.size() >= 0) {
                                for (Good good : mData.data) {
                                    if (!TextUtils.isEmpty(good.goodId) && good.goodId.equals(goodId)) {
                                        MallProductDetailsActivity.this.good = good;
                                        if (good != null) {
                                            goodName = good.goodName;
                                            ptb = good.ptb;
                                            sprice = good.price;
                                            fileAskPath = good.fileAskPath;
                                            img = good.img;

                                            tv_msg.setText(good.goodName);
                                            priceNow.setText(good.ptb + "金币");
                                            price.setText(good.price + "元");
                                            if (TextUtils.isEmpty(good.amount) || good.amount.equals("0")) {
                                                tv_Exchange.setText("缺货中");
                                                tv_Exchange.setEnabled(false);
                                            } else {
                                                tv_Exchange.setEnabled(true);
                                                tv_Exchange.setText("立即" + good.goodType);
                                            }
                                            if (!TextUtils.isEmpty(good.status) && good.status.equals("正常")) {

                                            }
                                            Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                                    .load("" + good.fileAskPath + good.img)
                                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                                    //.centerCrop()// 长的一边撑满
                                                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                                    .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                                    //.crossFade()
                                                    .into(iv_mImageView);
                                        }
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }).start();*/


        new GetAddressListTask(mContext, false, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetAddressList) {
                    GetAddressList address = (GetAddressList) object;
                    if (address.data != null && address.data.size() >= 0) {
                        mList.clear();
                        mList.addAll(address.data);
                        /*for(Address mAddress:address.data){
                            if(!TextUtils.isEmpty(mAddress.isDefault)&&mAddress.isDefault.equals("Y")){
								addressId=mAddress.addressId;
								break;
							}
						}*/
                        SharedPreUtil.putStringValue(mContext, ACTION_GetAddressList, new JsonBuild().setModel(object).getJson1());
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {


            }
        }).start();

        price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_Exchange, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
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
                                            int arg2, long arg3) {//goodName,fileAskPath,img,sprice,ptb;
                        HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                        if (item.get("ItemText").equals("QQ好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("商品:" + goodName);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGood?goodId=" + goodId); // 标题的超链接
                            sp.setText("原价:" + sprice + "，现在G9商城只要:" + ptb + "，快来购买吧");
                            sp.setImageUrl("" + fileAskPath + img);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(MallProductDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("商品:" + goodName);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toGood?goodId=" + goodId); // 标题的超链接
                            sp.setText("原价:" + sprice + "，现在G9商城只要:" + ptb + "，快来购买吧");
                            sp.setImageUrl("" + fileAskPath + img);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toGood?goodId=" + goodId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(MallProductDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("商品:" + goodName + "，原价:" + sprice + "，现在G9商城只要:" + ptb + "，快来购买吧 " + Config.getInstance().IP + "/helper/app/toGood?goodId=" + goodId);
                            sp.setImageUrl("" + fileAskPath + img);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(MallProductDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("商品:" + goodName);
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("原价:" + sprice + "，现在G9商城只要:" + ptb + "，快来购买吧");
                            sp.setImageUrl("" + fileAskPath + img);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGood?goodId=" + goodId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(MallProductDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("商品:" + goodName + "，原价:" + sprice + "，现在G9商城只要:" + ptb + "，快来购买吧");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("原价:" + sprice + "，现在G9商城只要:" + ptb + "，快来购买吧");//设置了不会显示,可选参数
                            sp.setImageUrl("" + fileAskPath + img);//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toGood?goodId=" + goodId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(MallProductDetailsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(MallProductDetailsActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                break;
            case R.id.tv_Exchange:
                setexChage();
                break;
            default:
                super.onClick(v);
                break;
        }

    }

    int i = 0;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MallProductDetailsActivity");
        super.onResume();
        if (i == 0) {
            i = 1;
        } else {
            new GetAddressListTask(mContext, false, user.userId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof GetAddressList) {
                        GetAddressList address = (GetAddressList) object;
                        if (address.data != null && address.data.size() >= 0) {
                            mList.clear();
                            mList.addAll(address.data);

                            SharedPreUtil.putStringValue(mContext, ACTION_GetAddressList, new JsonBuild().setModel(object).getJson1());
                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {


                }
            }).start();
        }
    }

    MyAlertDailog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == 1100) {
            if (data != null) {
                addressId = data.getStringExtra("addressId");
                if (dialog != null && dialog.isShowing()) {
                    dialog.show();

                }
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == 1200) {
            if (data != null) {
                addressId = data.getStringExtra("addressId");
            }
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

    public void setDialog() {
        final MyAlertDailog dialog = new MyAlertDailog(
                mContext);
        Resources res = mContext.getResources();
        dialog.setTitle("余额不足", Gravity.CENTER_VERTICAL | Gravity.LEFT);
        dialog.setContent1("当前余额不足，充值后才能继续" + good.goodType + "。是否去充值？"
                , Gravity.CENTER_VERTICAL | Gravity.LEFT);
        dialog.setLeftButtonText("取消");
        dialog.setRightButtonText("确定");
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        dialog.setOnClickLeftListener(new MyAlertDailog.onClickLeftListener() {
            @Override
            public void onClickLeft() {
                dialog.dismiss();

            }
        });
        dialog.setOnClickRightListener(new MyAlertDailog.onClickRightListener() {

            @Override
            public void onClickRight() {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putInt("currIndex", 1);
                startActivity(RechargeActivity.class, bundle);
            }
        });
    }

    public void setexChage() {
        if (good != null) {
            if (TextUtils.isEmpty(good.amount) || good.amount.equals("0")) {
                tv_Exchange.setText("缺货中");
                tv_Exchange.setEnabled(false);
            } else {
                tv_Exchange.setEnabled(true);
                tv_Exchange.setText("立即" + good.goodType);
            }
        }
        if (tv_Exchange.isEnabled()) {
            tv_Exchange.setEnabled(false);

            if (user != null) {
                if (!TextUtils.isEmpty(user.ptb)) {
                    if (good != null && !TextUtils.isEmpty(good.ptb)) {
                        if (Double.parseDouble(user.ptb) >= Double.parseDouble(good.ptb)) {
                            if (!TextUtils.isEmpty(good.goodType)) {
                                if (good.goodType.equals("兑换")) {
                                    if (!TextUtils.isEmpty(addressId)) {
                                        final MyAlertDailogEd1 dialog = new MyAlertDailogEd1(mContext);
                                        dialog.setTitle("备注信息");
                                        dialog.setLeftButtonText("取消");
                                        dialog.setRightButtonText("确认兑换");
                                        if (dialog != null && !dialog.isShowing()) {
                                            dialog.show();
                                        }
                                        dialog.setOnClickLeftListener(new MyAlertDailogEd1.onClickLeftListener() {
                                            @Override
                                            public void onClickLeft() {
                                                dialog.dismiss();
                                                tv_Exchange.setEnabled(true);
                                            }
                                        });
                                        dialog.setOnClickRightListener(new MyAlertDailogEd1.onClickRightListener() {

                                            @Override
                                            public void onClickRight() {
                                                String txet = dialog.getEdText();
                                                dialog.dismiss();
                                                new SaveExchangeTask(mContext, "兑换中", user.userId, goodId, addressId, txet, new Back() {

                                                    @Override
                                                    public void success(Object object, String msg) {
                                                        addressId = null;
                                                        //刷新个人信息（含金币）

                                                        ToastUtil.showToast(mContext, msg);
                                                        user = DBManager.getInstance(mContext).getUserMessage();//刷新
                                                        tv_Exchange.setEnabled(true);
                                                        dialog.dismiss();
                                                       /* //刷新商品数量
                                                        new GetGoodListTask(mContext, false, new Back() {

                                                            @Override
                                                            public void success(Object object, String msg) {
                                                                if (object != null && object instanceof GetGoodList) {
                                                                    GetGoodList mlist = (GetGoodList) object;
                                                                    if (mlist.data != null && mlist.data.size() >= 0) {
                                                                        for (Good good : mlist.data) {
                                                                            if (!TextUtils.isEmpty(good.goodId) && good.goodId.equals(goodId)) {
                                                                                MallProductDetailsActivity.this.good = good;
                                                                                if (good != null) {
                                                                                    goodName = good.goodName;
                                                                                    ptb = good.ptb;
                                                                                    sprice = good.price;
                                                                                    fileAskPath = good.fileAskPath;
                                                                                    img = good.img;

                                                                                    tv_msg.setText(good.goodName);
                                                                                    priceNow.setText(good.ptb + "金币");
                                                                                    price.setText(good.price + "元");
                                                                                    if (TextUtils.isEmpty(good.amount) || good.amount.equals("0")) {
                                                                                        tv_Exchange.setText("缺货");
                                                                                        tv_Exchange.setEnabled(false);
                                                                                    } else {
                                                                                        tv_Exchange.setEnabled(true);
                                                                                        tv_Exchange.setText("立即" + good.goodType);
                                                                                    }
                                                                                    if (!TextUtils.isEmpty(good.status) && good.status.equals("正常")) {

                                                                                    }
                                                                                }
                                                                                break;
                                                                            }
                                                                        }
                                                                        SharedPreUtil.putStringValue(mContext, ACTION_GetGoodList, new JsonBuild().setModel(object).getJson1());
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void fail(String status, String msg, Object object) {

                                                            }
                                                        }).start();*/


                                                    }

                                                    @Override
                                                    public void fail(String status, String msg, Object object) {
                                                        // TODO Auto-generated method stub
                                                        tv_Exchange.setEnabled(true);
                                                        ToastUtil.showToast(mContext, msg);
                                                    }
                                                }).start();
                                            }
                                        });

                                    } else {
                                        tv_Exchange.setEnabled(true);

                                        if (mList.size() > 0) {
                                            startActivityForResult1(SeclectAddressActivity.class, null, 1100);
                                        } else {
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("key", 0);
                                            startActivityForResult1(MallAddressManagementActivity.class, bundle, 1200);
                                        }


                                    }
                                } else {

                                    new GetLotteryTask(mContext, "抽奖中", user.userId, good.goodId, new Back() {

                                        @Override
                                        public void success(Object object, String msg) {

                                            //刷新个人金币信息

                                            //
                                            if (object != null && object instanceof GetLottery) {
                                                GetLottery mGetLottery = (GetLottery) object;
                                                if (mGetLottery.data != null) {
                                                    good = mGetLottery.data;
                                                    tv_msg.setText(good.goodName);
                                                    priceNow.setText(good.ptb + "金币");
                                                    price.setText(good.price + "元");
                                                    good.goodType = "兑换";
                                                    if (TextUtils.isEmpty(good.amount) || good.amount.equals("0")) {
                                                        tv_Exchange.setText("缺货中");
                                                        tv_Exchange.setEnabled(false);
                                                    } else {
                                                        tv_Exchange.setEnabled(true);
                                                        tv_Exchange.setText("立即" + good.goodType);
                                                    }
                                                    if (!TextUtils.isEmpty(good.status) && good.status.equals("正常")) {

                                                    }
                                                    Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                                            .load("" + good.fileAskPath + good.img)
                                                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                                            //.centerCrop()// 长的一边撑满
                                                            //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                                            .error(R.drawable.picture_defeated)//加载失败时显示的图片
                                                            //.crossFade()
                                                            .into(iv_mImageView);
                                                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                                    dialog.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                                    dialog.setContent1("恭喜您抽中" + mGetLottery.data.goodName + "，兑换后管理员将安排奖品发放。"
                                                            , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                                    dialog.setLeftButtonText("取消");
                                                    dialog.setRightButtonText("立即兑换");
                                                    if (dialog != null && !dialog.isShowing()) {
                                                        dialog.show();
                                                    }
                                                    dialog.setOnClickLeftListener(new MyAlertDailog.onClickLeftListener() {
                                                        @Override
                                                        public void onClickLeft() {
                                                            dialog.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                        }
                                                    });
                                                    dialog.setOnClickRightListener(new MyAlertDailog.onClickRightListener() {

                                                        @Override
                                                        public void onClickRight() {
                                                            dialog.dismiss();
                                                            tv_Exchange.setEnabled(true);

                                                            user = DBManager.getInstance(mContext).getUserMessage();
                                                            setexChage();
                                                        }
                                                    });


                                                }
                                            }


                                        }

                                        @Override
                                        public void fail(String status, String msg, Object object) {

                                            if (TextUtils.isEmpty(status)) {
                                                ToastUtil.showToast(mContext, msg);
                                                tv_Exchange.setEnabled(true);
                                            } else {
                                                if (status.equals("110")) {//未中奖
                                                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                                    dialog.setContent(msg + "！");
                                                    dialog.setLeftButtonText("取消");
                                                    dialog.setRightButtonText("再来一次");
                                                    if (dialog != null && !dialog.isShowing()) {
                                                        dialog.show();
                                                    }
                                                    dialog.setOnClickLeftListener(new MyAlertDailog.onClickLeftListener() {
                                                        @Override
                                                        public void onClickLeft() {
                                                            dialog.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                        }
                                                    });
                                                    dialog.setOnClickRightListener(new MyAlertDailog.onClickRightListener() {

                                                        @Override
                                                        public void onClickRight() {
                                                            dialog.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                            user = DBManager.getInstance(mContext).getUserMessage();
                                                            setexChage();
                                                        }
                                                    });
                                                } else if (status.equals("confirm")) {
                                                    //免费次数用完
                                                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                                    dialog.setTitle("温馨提示", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                                    dialog.setContent1("" + msg, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                                    dialog.setLeftButtonText("取消");
                                                    dialog.setRightButtonText("再来一次");
                                                    if (dialog != null && !dialog.isShowing()) {
                                                        dialog.show();
                                                    }
                                                    dialog.setOnClickLeftListener(new MyAlertDailog.onClickLeftListener() {
                                                        @Override
                                                        public void onClickLeft() {
                                                            dialog.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                        }
                                                    });
                                                    dialog.setOnClickRightListener(new MyAlertDailog.onClickRightListener() {

                                                        @Override
                                                        public void onClickRight() {
                                                            dialog.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                            user = DBManager.getInstance(mContext).getUserMessage();
                                                            setexChage();
                                                        }
                                                    });

                                                } else if (status.equals("yebz")) {
                                                    //金币不足
                                                    final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                                                    dialog1.setTitle("余额不足", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                                    dialog1.setContent1(msg + ""
                                                            , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                                    dialog1.setLeftButtonText("取消");
                                                    dialog1.setRightButtonText("充值");
                                                    if (dialog1 != null && !dialog1.isShowing()) {
                                                        dialog1.show();
                                                    }
                                                    dialog1.setOnClickLeftListener(new MyAlertDailog.onClickLeftListener() {
                                                        @Override
                                                        public void onClickLeft() {
                                                            dialog1.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                        }
                                                    });
                                                    dialog1.setOnClickRightListener(new MyAlertDailog.onClickRightListener() {

                                                        @Override
                                                        public void onClickRight() {
                                                            dialog1.dismiss();
                                                            tv_Exchange.setEnabled(true);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt("currIndex", 1);
                                                            startActivity(RechargeActivity.class, bundle);
                                                        }
                                                    });
                                                }
                                            }

                                        }
                                    }).start();

                                }
                            } else {
                                tv_Exchange.setEnabled(true);
                                ToastUtil.showToast(mContext, "商品类型不存在，无法继续操作");
                            }

                        } else {
                            tv_Exchange.setEnabled(true);
                            setDialog();

                        }
                    } else {
                        tv_Exchange.setEnabled(true);
                        ToastUtil.showToast(mContext, "商品不存在");
                    }
                } else {
                    tv_Exchange.setEnabled(true);
                    setDialog();
                }
            } else {
                tv_Exchange.setEnabled(true);
                ToastUtil.showToast(mContext, "用户不存在");
            }
        }
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
        MobclickAgent.onPageEnd("MallProductDetailsActivity");
        super.onPause();
    }

    @OnClick({R.id.mall_productdetails_server_call_ll, R.id.tv_Exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mall_productdetails_server_call_ll:
                Util.showPopupWindow(this,iv_mImageView);
                break;
        }
    }
}
