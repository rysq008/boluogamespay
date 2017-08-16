package com.game.helper.activity.home;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 充值-充值说明-选项
 * @Path com.game.helper.activity.home.RechargeExplainActivity.java
 * @Author lbb
 * @Date 2016年8月24日 上午10:26:01
 * @Company 
 */
public class RechargeExplainActivity extends BaseActivity{
	@BindView(R.id.relat_set_new_number)
	RelativeLayout relat_set_new_number;
	@BindView(R.id.relat_set_discount) RelativeLayout relat_set_discount;
	@BindView(R.id.relat_set_platform_currency) RelativeLayout relat_set_platform_currency;
	@BindView(R.id.topLeftBack)
	ImageView topLeftBack;
	@BindView(R.id.top_title)
	TextView topTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home_recharge_explain);
		ButterKnife.bind(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initView() {
		topTitle.setText("充值说明");
		topLeftBack.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initData() {
		
	}

	@Override
	@OnClick({R.id.top_left_layout,R.id.relat_set_new_number
		,R.id.relat_set_discount,R.id.relat_set_platform_currency})
	public void onClick(View v) {
		switch ( v.getId()) {
		case R.id.top_left_layout:
			finish1();
			break;
		case R.id.relat_set_new_number:
			Bundle bundle=new Bundle();
			bundle.putInt("KEY_NAMETYPE", 0);
			startActivity(RechargeExplainDetailsActivity.class,bundle);
			break;
		case R.id.relat_set_discount:
			Bundle bundle1=new Bundle();
			bundle1.putInt("KEY_NAMETYPE", 1);
			startActivity(RechargeExplainDetailsActivity.class,bundle1);
			break;
		case R.id.relat_set_platform_currency:
			Bundle bundle2=new Bundle();
			bundle2.putInt("KEY_NAMETYPE", 2);
			startActivity(RechargeExplainDetailsActivity.class,bundle2);
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
		MobclickAgent.onPageEnd("RechargeExplainActivity");
		super.onPause();
	}

	@Override
	protected void onResume() {
		MobclickAgent.onPageStart("RechargeExplainActivity");
		super.onResume();
	}
}
