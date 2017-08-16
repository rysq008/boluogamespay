package com.game.helper.fragment.home;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.home.ReleaseStrategyActivity;
import com.game.helper.activity.home.StrategyDetailsActivity;
import com.game.helper.adapter.home.StrategyAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryGameCutTask;
import com.game.helper.sdk.model.returns.QueryGameCut;
import com.game.helper.sdk.model.returns.QueryGameCut.GameCut;
import com.game.helper.view.widget.CustomScrollView;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
@SuppressLint("ValidFragment")
/**
 * @Description  游戏详情-攻略
 * @Path com.game.helper.fragment.home.StrategyFragment.java
 * @Author lbb
 * @Date 2016年8月23日 上午10:53:24
 * @Company 
 */
public class StrategyFragment extends BaseFragment{
	CustomScrollView mScrollView;
	int num=0;
	LinearLayout mLinear_NULL;
	ListViewForScrollView strategy_listView;
	StrategyAdapter mStrategyAdapter;
	private List<GameCut> mList=new ArrayList<GameCut>();

	ImageView btn_setStrategy;

	public StrategyFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StrategyFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_home_strategy, container,false);
			initViews();
			initEvents();
			init();
		}
		FragmentCache(mView);


		return mView;
	}


	@Override
	protected void initViews() {
		mLinear_NULL=(LinearLayout) findViewById(R.id.mLinear_NULL);
		
		btn_setStrategy=(ImageView) findViewById(R.id.btn_setStrategy);
		mScrollView=(CustomScrollView) findViewById(R.id.mScrollView);
		strategy_listView=(ListViewForScrollView) findViewById(R.id.strategy_listView);
		mStrategyAdapter=new StrategyAdapter(getActivity(), mList);
		strategy_listView.setAdapter(mStrategyAdapter);
		strategy_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle=new Bundle();
				bundle.putString("cutId", mStrategyAdapter.getmList().get(arg2).cutId);
				((BaseActivity)getActivity()).startActivity(StrategyDetailsActivity.class,bundle);
			}
		});

	}

	@Override
	protected void initEvents() {
		btn_setStrategy.setOnClickListener(this);


	}
	String gameId;
	@Override
	protected void init() {
		gameId=getArguments().getString("gameId", "");
		setQueryGameCut();

	}
	public void setQueryGameCut(){
		new QueryGameCutTask(getActivity(), gameId, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof QueryGameCut){
					QueryGameCut mQueryGameCut=(QueryGameCut) object;
					if(mQueryGameCut.data!=null && mQueryGameCut.data.size()>=0){
						  if(mQueryGameCut.data.size()==0){
	                        	mLinear_NULL.setVisibility(View.VISIBLE);
	                        	strategy_listView.setVisibility(View.GONE);
							}else{
								mLinear_NULL.setVisibility(View.GONE);
								strategy_listView.setVisibility(View.VISIBLE);
							}
						
						mList.clear();
						mList.addAll(mQueryGameCut.data);
						mStrategyAdapter.setmList(mList);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setStrategy:
			Bundle bundle=new Bundle();
			bundle.putString("gameId", gameId);
			((BaseActivity)getActivity()).startActivity(ReleaseStrategyActivity.class,bundle);
			break;
		default:
			super.onClick(v);
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("StrategyFragment");
		if(num==0){
			num=1;
			mScrollView.post(new Runnable() { 
				public void run() { 
					mScrollView.fullScroll(ScrollView.FOCUS_UP); 
				} 
			});
		}else{
			setQueryGameCut();
		}


	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("StrategyFragment"); 
	}


	
}