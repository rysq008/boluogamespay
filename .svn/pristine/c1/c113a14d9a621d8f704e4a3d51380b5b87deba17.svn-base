package com.game.helper.fragment.mine;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.mine.WithdrawCashAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.MyWithdrawTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.MyWithdraw;
import com.game.helper.sdk.model.returns.MyWithdraw.Withdraw;
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
 * @Description
 * @Path com.game.helper.fragment.mine.WithdrawCashFragment.java
 * @Author lbb
 * @Date 2016年11月25日 上午9:23:23
 * @Company 
 */
public class WithdrawCashFragment extends BaseFragment{
	private ListView listView;
	private WithdrawCashAdapter mWithdrawCashAdapter;
	private List<Withdraw> mList=new ArrayList<Withdraw>();
	public WithdrawCashFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WithdrawCashFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_mine_withdraw_cash, container,false);
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
		mWithdrawCashAdapter=new WithdrawCashAdapter(mContext, mList);
		listView.setAdapter(mWithdrawCashAdapter);
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
		new MyWithdrawTask(mContext, user.userId, 
				new Back() {

			@Override
			public void success(Object object, String msg) {
				if( object!=null &&object instanceof MyWithdraw){
					MyWithdraw mGetTradeList=(MyWithdraw) object;
					if(mGetTradeList.data!=null){
						mList.clear();
						mList.addAll(mGetTradeList.data);
						mWithdrawCashAdapter.setmList(mList);
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
		MobclickAgent.onPageEnd("WithdrawCashFragment"); 
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("WithdrawCashFragment");
	}
}
