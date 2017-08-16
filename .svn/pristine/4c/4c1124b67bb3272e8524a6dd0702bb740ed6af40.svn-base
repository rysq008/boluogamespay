package com.game.helper;

import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.interfaces.comm.HttpComm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnClickListener, CommValues, HttpComm {
	protected Context mContext;
	protected FragmentActivity mActivity;
	// 内容区域的布局
	protected View mView;

	public BroadcastReceiver receiver;
	public BaseApplication mApplication;
	
	public BaseFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseFragment( BaseApplication application,Activity activity,Context context) {
		mApplication = application;
		mApplication.context=activity;
		mActivity = (FragmentActivity) activity;
		mContext = context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("BaseFragment", "-----onCreateView------");
		//mContext = getActivity();
		return mView;
	}

	
	protected View findViewById(int id) {
		return mView.findViewById(id);
	}

	protected abstract void initViews();

	protected abstract void initEvents();

	protected abstract void init();
	
	public void FragmentCache(View view) {
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
	}
	
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}

	public void startActivityForResult(Intent intent, int code) {
		super.startActivityForResult(intent, code);
		getActivity().overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDestroy() {
		if (receiver != null)
			getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	
}
