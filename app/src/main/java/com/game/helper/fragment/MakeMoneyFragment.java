package com.game.helper.fragment;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.home.InviteFriendsActivity;
import com.game.helper.activity.home.TaskCenterActivity;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
@SuppressLint("ValidFragment")
/**
 * @Description
 * @Path com.game.helper.fragment.MakeMoneyFragment.java
 * @Author lbb
 * @Date 2016年12月19日 上午9:35:10
 * @Company 
 */
public class MakeMoneyFragment extends BaseFragment {

	private TextView topRight;
	private TextView topTitle;
	private LinearLayout top_left_layout;
	private ImageView topLeftBack;
	private RelativeLayout mRelativeLayout;

	ImageView iv_Image1;
	ImageView iv_Image2;
	public MakeMoneyFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MakeMoneyFragment(BaseApplication application, Activity activity,
			Context context) {
		super(application, activity, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext=getActivity();
		mApplication=(BaseApplication) getActivity().getApplication();
		mApplication.context=getActivity();
		super.onCreateView(inflater, container, savedInstanceState);
		if(mView==null){
			mView = inflater.inflate(R.layout.fragment_make_money, container,false);
			initViews();
			initEvents();
			init();
		}
		FragmentCache(mView);
		return mView;
	}

	@Override
	protected void initViews() {
		topRight=(TextView) findViewById(R.id.top_right);
		topRight.setText("");
		topRight.setVisibility(View.GONE);
		topTitle=(TextView) findViewById(R.id.top_title);
		top_left_layout=(LinearLayout) findViewById(R.id.top_left_layout);
		topTitle.setText("赚钱");
		topLeftBack=(ImageView) findViewById(R.id.topLeftBack);
		mRelativeLayout=(RelativeLayout) findViewById(R.id.mRelativeLayout);
		
		iv_Image1=(ImageView) findViewById(R.id.iv_Image1);
		iv_Image2=(ImageView) findViewById(R.id.iv_Image2);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		iv_Image1.setOnClickListener(this);
		iv_Image2.setOnClickListener(this);
		
	}

	@Override
	protected void init() {
		receiver = 	new BaseBroadcast(getActivity()){

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				super.onReceive(context, intent);
				String action = intent.getAction();
				
			}

		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_GetAdList3);

		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_Image1:
			((BaseActivity)getActivity()).startActivity(InviteFriendsActivity.class);
			break;
		case R.id.iv_Image2:
			((BaseActivity)getActivity()).startActivity(TaskCenterActivity.class);
			break;
		default:
			super.onClick(v);
			break;
		}
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageEnd("MakeMoneyFragment"); 
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageStart("MakeMoneyFragment");
	}

	
}
