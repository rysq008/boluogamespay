package com.game.helper.fragment.home;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.home.GiftsDetailActivity;
import com.game.helper.adapter.home.GameGiftsAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.home.Gift;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.ListGiftTask;
import com.game.helper.sdk.model.returns.ListGift;
import com.game.helper.sdk.model.returns.LoginData;
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
import android.widget.ListView;
@SuppressLint("ValidFragment")
/**
 * @Description   游戏详情-礼包
 * @Path com.game.helper.fragment.home.GiftsFragment.java
 * @Author lbb
 * @Date 2016年8月23日 上午10:52:55
 * @Company 
 */
public class GiftsFragment extends BaseFragment{
	LinearLayout mLinear_NULL;
	private ListView gamegifts_listView;
	private GameGiftsAdapter mGameGiftsAdapter;
	private List<Gift> mList=new ArrayList<Gift>();
	public String fileAskPath;
	public String logo;
	public String logoThumb;
	public GiftsFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GiftsFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_home_gifts, container,false);
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
		
		gamegifts_listView=(ListView) findViewById(R.id.gamegifts_listView);
		mGameGiftsAdapter=new GameGiftsAdapter(getActivity(), mList);
		gamegifts_listView.setAdapter(mGameGiftsAdapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		gamegifts_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle=new Bundle();
				bundle.putString("giftId", mGameGiftsAdapter.getmList().get(arg2).giftId);
				((BaseActivity)getActivity()).startActivity(GiftsDetailActivity.class,bundle);
			}
		});
	}
	LoginData user;
	String gameId;
	@Override
	protected void init() {
		gameId=getArguments().getString("gameId", "");
		user=DBManager.getInstance(getActivity()).getUserMessage();
		fileAskPath=getArguments().getString("fileAskPath", "");
		logo=getArguments().getString("logo", "");
		logoThumb=getArguments().getString("logoThumb", "");
		new ListGiftTask(getActivity(),false, user.userId, gameId, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof ListGift){
					ListGift mListGift=(ListGift) object;
					if(mListGift.data!=null&&mListGift.data.size()>=0){
						if(mListGift.data.size()==0){
                        	mLinear_NULL.setVisibility(View.VISIBLE);
                        	gamegifts_listView.setVisibility(View.GONE);
						}else{
							mLinear_NULL.setVisibility(View.GONE);
							gamegifts_listView.setVisibility(View.VISIBLE);
						}
						mList.clear();
						mList.addAll(mListGift.data);
						for( Gift mGift:mList){
							mGift.fileAskPath=fileAskPath;
							mGift.logo=logo;
							mGift.logoThumb=logoThumb;
						}
						mGameGiftsAdapter.setmList(mList);
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
		// TODO Auto-generated method stub
		super.onClick(v);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("GiftsFragment"); 
	}
  int i=0;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("GiftsFragment");
		if(i==0){
			i=1;
		}else{
			new ListGiftTask(getActivity(),true, user.userId, gameId, new Back() {

				@Override
				public void success(Object object, String msg) {
					if(object!=null && object instanceof ListGift){
						ListGift mListGift=(ListGift) object;
						if(mListGift.data!=null&&mListGift.data.size()>=0){
							if(mListGift.data.size()==0){
	                        	mLinear_NULL.setVisibility(View.VISIBLE);
	                        	gamegifts_listView.setVisibility(View.GONE);
							}else{
								mLinear_NULL.setVisibility(View.GONE);
								gamegifts_listView.setVisibility(View.VISIBLE);
							}
							mList.clear();
							mList.addAll(mListGift.data);
							for( Gift mGift:mList){
								mGift.fileAskPath=fileAskPath;
								mGift.logo=logo;
								mGift.logoThumb=logoThumb;
							}
							mGameGiftsAdapter.setmList(mList);
						}
					}

				}

				@Override
				public void fail(String status, String msg, Object object) {
					// TODO Auto-generated method stub

				}
			}).start();
		}
	}
}
