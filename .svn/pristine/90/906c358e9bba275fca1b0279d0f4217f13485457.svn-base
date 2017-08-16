package com.game.helper.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.home.InviteFriendsActivity;
import com.game.helper.activity.home.TaskCenterActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MakeMoneyActivity extends BaseActivity {

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
    @BindView(R.id.iv_Image1)
    ImageView ivImage1;
    @BindView(R.id.iv_Image2)
    ImageView ivImage2;
    @BindView(R.id.mScrollView)
    ScrollView mScrollView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_make_money);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        topLeftBack.setVisibility(View.VISIBLE);
        topTitle.setText("任务赚钱");

    }


    @OnClick({R.id.iv_Image1, R.id.iv_Image2,R.id.topLeftBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_Image1:
                startActivity(InviteFriendsActivity.class);

                break;
            case R.id.iv_Image2:
                startActivity(TaskCenterActivity.class);

                break;

            case R.id.topLeftBack:
                finish1();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
        MobclickAgent.onPageEnd("MakeMoneyFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPause(mContext);
        MobclickAgent.onPageStart("MakeMoneyFragment");
    }

}
