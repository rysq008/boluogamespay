package com.game.helper.fragment.community;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.community.DetailsAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGiveListTask;
import com.game.helper.sdk.model.returns.GetGiveList;
import com.game.helper.sdk.model.returns.GetGiveList.GetGiveListData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
@SuppressLint("ValidFragment")

/**
 * @Description 明细
 * @Path com.game.helper.fragment.community.DetailFragment.java
 * @Author lbb
 * @Date 2016年8月26日 下午2:49:42
 * @Company 
 */
public class DetailsFragment extends BaseFragment{
	
	TextView tv_total;
	ListViewForScrollView listView;
	DetailsAdapter mDetailsAdapter;
	List<GetGiveListData> data=new ArrayList<GetGiveListData>();
	String userId;
	public DetailsFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailsFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_community_details, container,false);
			initViews();
			initEvents();
			init();
		}
		FragmentCache(mView);
		return mView;
	}
	@Override
	protected void initViews() {
		tv_total=(TextView) findViewById(R.id.tv_total);
		listView=(ListViewForScrollView) findViewById(R.id.listView);
		mDetailsAdapter=new DetailsAdapter(getActivity(),data);
		listView.setAdapter(mDetailsAdapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("DetailsFragment");
		userId=(String)getArguments().get("userId");
		new GetGiveListTask(getActivity(),true, userId, new Back() {
			
			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null&& object instanceof GetGiveList){
					GetGiveList mGetGiveList=(GetGiveList) object;
					if(mGetGiveList.data!=null){
						if(mGetGiveList.data.giftGiveList.size()>=0){
							data.clear();
							data.addAll(mGetGiveList.data.giftGiveList);
							mDetailsAdapter.setData(data);
						}
						if(!TextUtils.isEmpty(mGetGiveList.data.sumBenefit)){
							tv_total.setText(mGetGiveList.data.sumBenefit);
						}
						SharedPreUtil.putStringValue(getActivity(), ACTION_GetGiveList+userId, new JsonBuild().setModel(object).getJson1());
					}
				}
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				String json=SharedPreUtil.getStringValue(getActivity(), ACTION_GetGiveList+userId,"");
				if (!TextUtils.isEmpty(json)) {
					Object mObject =new JsonBuild().getData(GetGiveList.class, json);
					if(mObject !=null&& mObject instanceof GetGiveList){
						GetGiveList mData=(GetGiveList) mObject;
						if(mData!=null &&mData.data!=null){
							if(mData.data.giftGiveList.size()>=0){
								data.clear();
								data.addAll(mData.data.giftGiveList);
								mDetailsAdapter.setData(data);
							}
							if(!TextUtils.isEmpty(mData.data.sumBenefit)){
								tv_total.setText(mData.data.sumBenefit);
							}
						}
					}
				}
				
			}
		}).start();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("DetailsFragment"); 
	}

	
}

