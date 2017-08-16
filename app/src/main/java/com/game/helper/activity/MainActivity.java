package com.game.helper.activity;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.fragment.CommunityFragment;
import com.game.helper.fragment.HomeFragment;
import com.game.helper.fragment.MakeMoneyFragment;
import com.game.helper.fragment.MallFragment;
import com.game.helper.fragment.MineFragment;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.yuan.leopardkit.download.DownLoadManager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    public FragmentTabHost mTabHost;
    @SuppressWarnings("rawtypes")
    Class[] tabid = new Class[]{
            HomeFragment.class,
            CommunityFragment.class,
            MallFragment.class,
            MineFragment.class};
    String[] tabtitle = new String[]{"首页", "资讯", "商城", "我的"};
    int[] image_id = new int[]{
            R.drawable.selector_tab_home1,
            R.drawable.selector_tab_home2,
            R.drawable.selector_tab_home3,
            R.drawable.selector_tab_home4};
    private TextView mtitle;

    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMainActivity = true;
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //储存权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            //ToastUtil.showToast(MainActivity.this, "已有权限");
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //ToastUtil.showToast(MainActivity.this, "您允许储存权限");
            } else {
                ToastUtil.showToast(MainActivity.this, "为了更好的体验，请您打开储存的权限，否则会下载不了游戏");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);

    }

    @Override
    protected void initData() {
        for (int i = 0; i < tabid.length; i++) {
            mAddTab("" + i, tabtitle[i], i, tabid[i]);
        }
    }

    private void mAddTab(String tag, String title, int i, @SuppressWarnings("rawtypes") Class cls) {
        TabSpec tabSpec = mTabHost.newTabSpec(tag);
        View view = getLayoutInflater().inflate(R.layout.item_ic_tab, null);
        ImageView image = (ImageView) view.findViewById(R.id.image_tabic);
        mtitle = (TextView) view.findViewById(R.id.tv_tabtitle);
        image.setBackgroundResource(image_id[i]);
        mtitle.setText(title);
        mTabHost.addTab(tabSpec.setIndicator(view), cls, null);
    }

    @Override
    protected void onAppExit() {
        Log.e("MainActivity", "------onAppExit()--------");
        BaseApplication.mInstance.isonAppExit = true;
        BaseApplication.mInstance.onAppExit();
    }

    @Override
    protected void desotryItems() {
        Log.e("MainActivity", "------desotryItems()--------");
        super.desotryItems();
        DownLoadManager.getManager().pauseAllTask();
    }

    public void setTable(int tabnum) {
        mTabHost.setCurrentTab(tabnum);

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("MainActivity");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("MainActivity");
    }


}
