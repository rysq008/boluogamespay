package com.game.helper.fragment.mine;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.mine.ZhichuAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.mine.ZhichuModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetTradeListTask;
import com.game.helper.sdk.model.returns.GetTradeList;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.GetTradeList.Trade;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
@SuppressLint("ValidFragment")
/**
 * @Description  支出明细
 * @Path com.game.helper.fragment.ZhichuFragment.java
 * @Author lbb
 * @Date 2016年8月19日 上午10:34:22
 * @Company 
 */
public class ZhichuFragment extends BaseFragment{
	private ListView listView;
	private ZhichuAdapter mZhichuAdapter;
	private List<Trade> mList=new ArrayList<Trade>();
	public ZhichuFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ZhichuFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_mine_zhichu, container,false);
			initViews();
			initEvents();
			init();
		}
		FragmentCache(mView);
		return mView;
	}
	@Override
	protected void initViews() {
		listView=(ListView) findViewById(R.id.listView);
		mZhichuAdapter=new ZhichuAdapter(mContext, mList);
		listView.setAdapter(mZhichuAdapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		LoginData user=DBManager.getInstance(mContext).getUserMessage();
		//支出
		new GetTradeListTask(mContext, user.userId, "0",null,
				new Back() {

			@Override
			public void success(Object object, String msg) {
				if( object!=null &&object instanceof GetTradeList){
					GetTradeList mGetTradeList=(GetTradeList) object;
					if(mGetTradeList.data!=null){
						mList.clear();
						mList.addAll(mGetTradeList.data);
						mZhichuAdapter.setmList(mList);
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
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("ZhichuFragment"); 
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("ZhichuFragment");
	}
}
