package com.game.helper.fragment.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.adapter.home.CommentAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.home.RecommendBoutique;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryGamePlTask;
import com.game.helper.net.task.SaveGamePlTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryGamePl;
import com.game.helper.sdk.model.returns.QueryGamePl.GamePl;
import com.game.helper.util.TimeUtil;
import com.game.helper.util.ToastUtil;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
@SuppressLint("ValidFragment")
/**
 * @Description  游戏详情-评论
 * @Path com.game.helper.fragment.home.CommentFragment.java
 * @Author lbb
 * @Date 2016年8月23日 上午10:54:11
 * @Company 
 */
public class CommentFragment extends BaseFragment{
	LinearLayout mLinear_NULL;
	EditText et_comment;
	ImageView tv_setcomment;
	ListViewForScrollView comment_listView ;
	CommentAdapter mCommentAdapter;
	private List<GamePl> mList=new ArrayList<GamePl>();

	Comparator  comp1 = new Comparator() { 
		public int compare(Object o1, Object o2) { 
			GamePl p1 = (GamePl) o1; 
			GamePl p2 = (GamePl) o2; 
			int a=0;
			a=TimeUtil.parserTime(TimeUtil.TIME_FORMAT_FULL, p1.createTimeString, p2.createTimeString);
			if (a!=2) {
				if(a==1){
					return -1; 
				}else if(a==-1){
					return 1; 
				}else {
					return a; 
				}
			}
			return 0; 
		} 
	}; 
	public CommentFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_home_comment, container,false);
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
		et_comment=(EditText) findViewById(R.id.et_comment);
		tv_setcomment=(ImageView) findViewById(R.id.tv_setcomment);

		comment_listView=(ListViewForScrollView) findViewById(R.id.comment_listView);
		mCommentAdapter=new CommentAdapter(getActivity(), mList);
		comment_listView.setAdapter(mCommentAdapter);
	}

	@Override
	protected void initEvents() {
		tv_setcomment.setOnClickListener(this);

	}
	LoginData user;
	String gameId;
	@Override
	protected void init() {
		gameId=getArguments().getString("gameId", "");
		user=DBManager.getInstance(mContext).getUserMessage();

		setComments();
	}
	public void setComments(){
		new QueryGamePlTask(getActivity(), gameId, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof QueryGamePl){
					QueryGamePl mQueryGamePl=(QueryGamePl) object;
					if(mQueryGamePl.data!=null&& mQueryGamePl.data.size()>=0){
                        if(mQueryGamePl.data.size()==0){
                        	mLinear_NULL.setVisibility(View.VISIBLE);
                        	comment_listView.setVisibility(View.GONE);
						}else{
							mLinear_NULL.setVisibility(View.GONE);
                        	comment_listView.setVisibility(View.VISIBLE);
						}
						mList.clear();
						mList.addAll(mQueryGamePl.data);
						Collections.sort(mList, comp1);
						mCommentAdapter.setmList(mList);
						
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
		case R.id.tv_setcomment:
			if(tv_setcomment.isEnabled()){
				tv_setcomment.setEnabled(false);
				String content=et_comment.getText().toString();
				if(TextUtils.isEmpty(content)){
					tv_setcomment.setEnabled(true);
					ToastUtil.showToast(getActivity(), "说些什么呗...");
				}else{
					new SaveGamePlTask(getActivity(), gameId, user.userId, content, new Back() {

						@Override
						public void success(Object object, String msg) {
							// TODO Auto-generated method stub
							ToastUtil.showToast(getActivity(), "评论成功");
							tv_setcomment.setEnabled(true);
							setComments();
						}

						@Override
						public void fail(String status, String msg, Object object) {
							ToastUtil.showToast(getActivity(), msg);
							tv_setcomment.setEnabled(true);
						}
					}).start();
				}
			}
			break;
		default:
			super.onClick(v);
			break;
		}
	}
	int num=0;
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("CommentFragment");
		if(num==0){
			num=1;
		}else{
			setComments();
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("CommentFragment"); 
	}

	
}