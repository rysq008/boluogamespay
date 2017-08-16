package com.game.helper.activity.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class GameDetailActivity1 extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_left_layout)
    LinearLayout topLeftLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.isDownload1)
    View isDownload1;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.iv_item)
    XCRoundImageViewByXfermode ivItem;
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.tv_dz)
    TextView tvDz;
    @BindView(R.id.mLinear_dz)
    LinearLayout mLinearDz;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_datasize)
    TextView tvDatasize;
    @BindView(R.id.tv_fromdownload)
    TextView tvFromdownload;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_guid1)
    TextView tvGuid1;
    @BindView(R.id.tv_guid2)
    TextView tvGuid2;
    @BindView(R.id.tv_guid3)
    TextView tvGuid3;
    @BindView(R.id.tv_guid5)
    TextView tvGuid5;
    @BindView(R.id.cursor)
    ImageView cursor;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_game_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
