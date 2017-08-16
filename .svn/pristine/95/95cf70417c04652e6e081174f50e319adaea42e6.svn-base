package com.game.helper.activity.community;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetBasicTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.GetBasic;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.community.ShakeJPSMActivity.java
 * @Author lbb
 * @Date 2016年11月21日 下午5:11:12
 * @Company
 */
public class ShakeJPSMActivity extends BaseActivity {

    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shake_jpsm);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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

        topTitle.setText("奖品内容说明");
        topLeftBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

        new GetBasicTask(mContext, true, "award", new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetBasic) {
                    GetBasic mGetBasic = (GetBasic) object;
                    if (mGetBasic.data != null) {

                        //默认图片，无图片或没加载完显示此图片
                        Drawable defaultDrawable = getResources().getDrawable(R.drawable.preview_card_pic_loading);
                        //调用
                        //Spanned sp = Html.fromHtml(mGetBasic.data.context, new URLImageParser(textView), null);//, "/g9esun_msg", defaultDrawable

                        //textView.setText(sp);
                        webview.loadUrl(Config.getInstance().IP + "/helper/app/toWapPage?type=basic&id=" + 7);

                        //textView.setText(""+Html.fromHtml(mGetBasic.data.context));
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
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
        MobclickAgent.onPageEnd("ShakeJPSMActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("ShakeJPSMActivity");
        super.onResume();
    }
}