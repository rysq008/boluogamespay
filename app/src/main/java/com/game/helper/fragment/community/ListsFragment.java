package com.game.helper.fragment.community;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.adapter.community.ListsAdapter;
import com.game.helper.adapter.community.SquareDynamicAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetRankTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.GetAdList;
import com.game.helper.sdk.model.returns.GetRank;
import com.game.helper.sdk.model.returns.GetRank.GetRankData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
@SuppressLint("ValidFragment")

/**
 * @Description 榜单
 * @Path com.game.helper.fragment.community.ListFragment.java
 * @Author lbb
 * @Date 2016年8月26日 下午2:50:04
 * @Company 
 */
public class ListsFragment extends BaseFragment{

	RelativeLayout re_second;
	CircleImageView imageView_pic_second;
	TextView tv_title_second;
	TextView tv_type_second;

	RelativeLayout re_first;
	CircleImageView imageView_pic_first;
	TextView tv_title_first;
	TextView tv_type_first;


	RelativeLayout re_third;
	CircleImageView imageView_pic_third;
	TextView tv_title_third;
	TextView tv_type_third;

	private ListViewForScrollView listView;
	private ListsAdapter mDetailAdapter;
	private List<GetRankData> mList=new ArrayList<GetRankData>();

	String userId;
	String userId1;
	String userId2;
	String userId3;
	
	public ListsFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListsFragment(BaseApplication application, Activity activity, Context context) {
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
			mView = inflater.inflate(R.layout.fragment_community_lists, container,false);
			initViews();
			initEvents();
			init();
		}
		FragmentCache(mView);
		return mView;
	}
	@Override
	protected void initViews() {

		re_second=(RelativeLayout) findViewById(R.id.re_second);
		imageView_pic_second=(CircleImageView) findViewById(R.id.imageView_pic_second);
		tv_title_second=(TextView) findViewById(R.id.tv_title_second);
		tv_type_second=(TextView) findViewById(R.id.tv_type_second);

		re_first=(RelativeLayout) findViewById(R.id.re_first);
		imageView_pic_first=(CircleImageView) findViewById(R.id.imageView_pic_first);
		tv_title_first=(TextView) findViewById(R.id.tv_title_first);
		tv_type_first=(TextView) findViewById(R.id.tv_type_first);


		re_third=(RelativeLayout) findViewById(R.id.re_third);
		imageView_pic_third=(CircleImageView) findViewById(R.id.imageView_pic_third);
		tv_title_third=(TextView) findViewById(R.id.tv_title_third);
		tv_type_third=(TextView) findViewById(R.id.tv_type_third);



		listView=(ListViewForScrollView) findViewById(R.id.listView);
		mDetailAdapter=new ListsAdapter(getActivity(),mList);
		listView.setAdapter(mDetailAdapter);
	}

	@Override
	protected void initEvents() {
		imageView_pic_second.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", userId2);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle); 
			}
		});
		imageView_pic_first.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", userId1);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle); 
			}
		});

		imageView_pic_third.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId",userId3);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle); 
			}
		});
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ListsFragment");
		//LoginData user=DBManager.getInstance(getActivity()).getUserMessage();
		userId=(String)getArguments().get("userId");
		new GetRankTask(getActivity(), true,userId, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetRank){
					GetRank mGetRank=(GetRank) object;
					if(mGetRank.data!=null){
						if(mGetRank.data.size()>=0){
							SharedPreUtil.putStringValue(getActivity(), ACTION_GetRank+userId, new JsonBuild().setModel(object).getJson1());
						}
						setDatas(mGetRank.data);
					}
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				String json=SharedPreUtil.getStringValue(getActivity(), ACTION_GetRank+userId,"");
				if (!TextUtils.isEmpty(json)) {
					Object mObject =new JsonBuild().getData(GetRank.class, json);
					if(mObject !=null&& mObject instanceof GetRank){
						GetRank mData=(GetRank) mObject;
						if(mData!=null &&mData.data!=null){
							setDatas(mData.data);
						}
					}
				}

			}
		}).start();
	}


	public void setDatas(List<GetRankData> data){
		if(data.size()>0){
			mList.clear();
			if(data.size()>=3){
				for(int i=0;i<data.size();i++){
					if(i>2){
						mList.add(data.get(i));
					}
				}
				re_second.setVisibility(View.VISIBLE);
				re_first.setVisibility(View.VISIBLE);
				re_third.setVisibility(View.VISIBLE);
				
				userId1=data.get(0).userId;
				tv_title_first.setText(data.get(0).nickName);
				tv_type_first.setText(data.get(0).totalSum);

				if(!TextUtils.isEmpty(data.get(0).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(0).fileAskPath+data.get(0).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_first);
				}else{
					imageView_pic_first.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}

				userId2=data.get(1).userId;
				tv_title_second.setText(data.get(1).nickName);
				tv_type_second.setText(data.get(1).totalSum);
				if(!TextUtils.isEmpty(data.get(1).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(1).fileAskPath+data.get(1).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_second);
				}else{
					imageView_pic_second.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}


				userId3=data.get(2).userId;
				tv_title_third.setText(data.get(2).nickName);
				tv_type_third.setText(data.get(2).totalSum);
				if(!TextUtils.isEmpty(data.get(2).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(2).fileAskPath+data.get(2).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_third);
				}else{
					imageView_pic_third.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}


			}/*else if(data.size()==3){
				re_second.setVisibility(View.VISIBLE);
				re_first.setVisibility(View.VISIBLE);
				re_third.setVisibility(View.VISIBLE);

				tv_title_first.setText(data.get(0).nickName);
				tv_type_first.setText(data.get(0).totalSum);
				if(!TextUtils.isEmpty(data.get(0).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(0).fileAskPath+data.get(0).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_first);
				}else{
					imageView_pic_first.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}


				tv_title_second.setText(data.get(1).nickName);
				tv_type_second.setText(data.get(1).totalSum);
				if(!TextUtils.isEmpty(data.get(1).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(1).fileAskPath+data.get(1).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_second);
				}else{
					imageView_pic_second.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}



				tv_title_second.setText(data.get(2).nickName);
				tv_type_second.setText(data.get(2).totalSum);

				if(!TextUtils.isEmpty(data.get(2).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(2).fileAskPath+data.get(2).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_third);
				}else{
					imageView_pic_third.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}



			}*/else if(data.size()==2){
				re_second.setVisibility(View.VISIBLE);
				re_first.setVisibility(View.VISIBLE);
				re_third.setVisibility(View.INVISIBLE);

				userId1=data.get(0).userId;
				tv_title_first.setText(data.get(0).nickName);
				tv_type_first.setText(data.get(0).totalSum);
				if(!TextUtils.isEmpty(data.get(0).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(0).fileAskPath+data.get(0).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_first);
				}else{
					imageView_pic_first.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}


				userId2=data.get(1).userId;
				tv_title_second.setText(data.get(1).nickName);
				tv_type_second.setText(data.get(1).totalSum);
				if(!TextUtils.isEmpty(data.get(1).icon)){

					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(1).fileAskPath+data.get(1).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_second);
				}else{
					imageView_pic_second.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}



			}else if(data.size()==1){
				re_second.setVisibility(View.INVISIBLE);
				re_first.setVisibility(View.VISIBLE);
				re_third.setVisibility(View.INVISIBLE);

				userId1=data.get(0).userId;
				tv_title_first.setText(data.get(0).nickName);
				tv_type_first.setText(data.get(0).totalSum);
				if(!TextUtils.isEmpty(data.get(0).icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+data.get(0).fileAskPath+data.get(0).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic_first);
				}else{
					imageView_pic_first.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pic_moren));
				}


			}
			mDetailAdapter.setmList(mList);
		}else{
			re_second.setVisibility(View.INVISIBLE);
			re_first.setVisibility(View.INVISIBLE);
			re_third.setVisibility(View.INVISIBLE);
			mList.clear();
			mDetailAdapter.setmList(mList);
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("ListsFragment"); 
	}


}

