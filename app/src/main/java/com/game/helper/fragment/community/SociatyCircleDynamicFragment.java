package com.game.helper.fragment.community;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.community.DynamicDetailsActivity;
import com.game.helper.adapter.community.SquareDynamicAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetDynamicInfoTask;
import com.game.helper.sdk.model.returns.GetDynamic_1Info;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.Dynamic_1Info;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.ListViewForScrollView;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.BindView;
@SuppressLint("ValidFragment")

/**
 * @Description 公会圈动态
 * @Path com.game.helper.fragment.community.SociatyCircleFragment.java
 * @Author lbb
 * @Date 2016年8月26日 上午9:42:30
 * @Company 
 */
public class SociatyCircleDynamicFragment extends BaseFragment implements OnRefreshListener{
	PullToRefreshLayout refresh_view;
	
	LinearLayout mLinear_seting_comment;
	ListViewForScrollView listView;
	SquareDynamicAdapter mSquareDynamicAdapter;

	private List<Dynamic_1Info> list=new ArrayList<Dynamic_1Info>();
	private int page =0;//页码 没传默认为第1页
	private int type=0;//0：刷新，1：加载更多
	
	private int currIndex=0;//当前页卡编号
	private boolean isStart=false;
	public SociatyCircleDynamicFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SociatyCircleDynamicFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_community_square_dynamic, container,false);
			initViews();
			initEvents();
			init();
		}
		FragmentCache(mView);
		return mView;
	}
	@Override
	protected void initViews() {
		refresh_view=(PullToRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		
		mLinear_seting_comment=(LinearLayout) findViewById(R.id.mLinear_seting_comment);
		listView=(ListViewForScrollView) findViewById(R.id.listView);
		mSquareDynamicAdapter=new SquareDynamicAdapter(getActivity(),list);
		listView.setAdapter(mSquareDynamicAdapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putString("dynamicId", mSquareDynamicAdapter.getList().get(position).dynamicId);
				((BaseActivity)getActivity()).startActivity(DynamicDetailsActivity.class);
			}
		});
	}
	String guildId;
	@Override
	protected void init() {
		guildId=(String) getArguments().get("guildId");
		currIndex=getArguments().getInt("currIndex", 0);
		isStart=false;
		
		type=0;
		page =0;
		getRefresh();
	}
	private void getRefresh() {
		if(currIndex==1){
			isStart=true;
		}
		new  GetDynamicInfoTask(getActivity(), isStart,guildId, ""+page, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null&& object instanceof GetDynamic_1Info){
					GetDynamic_1Info  m=(GetDynamic_1Info) object;
					if(m.data!=null&&m.data.list!=null){
						if (type == 0) {// 刷新
							if(m.data.list.size()>=0){
								list.clear();
								list.addAll(m.data.list);
								page++;
							}
							mSquareDynamicAdapter.setList(list);
						}else{
							if(m.data.list.size()>=0){
								list.addAll(m.data.list);
								page++;
							}
							if(m.data.list.size()<10){
								ToastUtil.showToast(mContext,"已经到底了");
							}
							mSquareDynamicAdapter.setList(list);
						}
					}
					refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
				isStart=true;
				if(list.size()==0){
					if(currIndex==1){
						ToastUtil.showToast(mContext, "暂无数据");
					}
					currIndex=1;
					refresh_view.hideStateTextView(true);
					refresh_view.setBackgroundLoadingRelative(android.R.color.transparent);//gray
				}else{
					refresh_view.hideStateTextView(false);
					refresh_view.setBackgroundLoadingRelative(R.color.gray);
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				refresh_view.refreshFinish(PullToRefreshLayout.FAIL);
				if (type== 0) {// 刷新
					ToastUtil.showToast(mContext, msg);
				} else if (type== 1) {// 加载
					ToastUtil.showToast(mContext, "获取列表失败");
				}
				isStart=true;
			}
		}).start(); 
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}
	int i=0;

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SociatyCircleDynamicFragment");
		if(i==0){
			i=1;
		}else{
			currIndex=getArguments().getInt("currIndex", 0);
			isStart=false;
			
			type=0;
			page =0;
			getRefresh();
		}
	}
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

		type=0;
		page =0;
		getRefresh();
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		type=1;
		getRefresh();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SociatyCircleDynamicFragment"); 
	}
	
}
