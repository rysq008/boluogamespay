package com.game.helper.activity.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 首充-套餐信息
 * @Path com.game.helper.activity.home.RechargePackageDetailsActivity.java
 * @Author lbb
 * @Date 2016年8月23日 下午8:20:50
 * @Company
 */
public class RechargePackageDetailsActivity extends BaseActivity {

    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_iv_right)
    ImageView top_iv_right;
    @BindView(R.id.top_liner_right)
    RelativeLayout top_liner_right;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_recharge_package_details);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("充值");
        topLeftBack.setVisibility(View.VISIBLE);
        top_liner_right.setVisibility(View.VISIBLE);
        top_iv_right.setVisibility(View.VISIBLE);
        top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.shouye_05));
    }

    @Override
    protected void initData() {

    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_liner_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_liner_right:
                startActivity(RechargeExplainActivity.class);
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
        MobclickAgent.onPageEnd("RechargePackageDetailsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("RechargePackageDetailsActivity");
        super.onResume();
    }

}
