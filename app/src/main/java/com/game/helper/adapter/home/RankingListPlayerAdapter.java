package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.StarUser.Star;
import com.game.helper.util.Util;
import com.game.helper.view.widget.CircleImageView;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description 人物排行榜
 * @Path com.game.helper.adapter.home.RankingListPlayerAdapter.java
 * @Author lbb
 * @Date 2016年8月24日 下午4:03:46
 * @Company 
 */
public class RankingListPlayerAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	public List<Star> data=new ArrayList<Star>();
	public RankingListPlayerAdapter(Context mContext,List<Star> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	
	public List<Star> getData() {
		return data;
	}

	int tag=0;
	public void setData(List<Star> data,int tag) {
		this.tag = tag;
		this.data = data;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_home_rankinglist_player, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Star mMineCollect=(Star) getItem(position);

		if (position==0) {
			holder.rankinglist_stace_player_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_one));
		}else if (position==1){
			holder.rankinglist_stace_player_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_two));
		}else if (position==2){
			holder.rankinglist_stace_player_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_three));
		}else {
			holder.tvNumNo.setText("" + (position + 1));
		}
		holder.imageView_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId",mMineCollect.userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
				
			}
		});
		
		holder.setData(mMineCollect);
		
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.tvNumNo) TextView tvNumNo;
		public @BindView(R.id.imageView_pic) CircleImageView imageView_pic;
		//public @BindView(R.id.iv_item) ImageView iv_item;
		
		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.tv_msg) TextView tv_msg;
		
		public @BindView(R.id.tv_note_msg) TextView tv_note_msg;
		public @BindView(R.id.tv_note_num) TextView tv_note_num;
		//public @BindView(R.id.tv_note_msg1) TextView tv_note_msg1;

		@BindView(R.id.rankinglist_stace_player_iv)
		ImageView rankinglist_stace_player_iv;

		@BindView(R.id.ranking_player_line_vip_iv)
		ImageView ranking_player_line_vip_iv;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		@SuppressWarnings("unchecked")
		public void setData(Star mMineCollect){
			if(mMineCollect!=null){
				
				if(!TextUtils.isEmpty(mMineCollect.icon)){
					ViewTarget viewTarget = new ViewTarget<CircleImageView, GlideDrawable>( imageView_pic ) {  
						@Override  
						public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {  
							this.view.setImageDrawable( resource.getCurrent() );  
						}  
					};  
					Glide  
					.with( BaseApplication.mInstance.context.getApplicationContext() ) // safer!  
					.load( ""+mMineCollect.fileAskPath+mMineCollect.icon )  
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					.into( viewTarget );
				}else{
					imageView_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}

				tv_item.setText(Util.setLongStringPoint(mMineCollect.nickName));
				/*
				if (mMineCollect.nickName.length()>5){
					tv_item.setText(mMineCollect.nickName.substring(0,5)+"...");
				}else{
					tv_item.setText(mMineCollect.nickName);
				}*/
				tv_msg.setText(mMineCollect.field2);
				
				if(tag==0){
					tv_note_msg.setText("礼物收入：");
					//tv_note_msg1.setText("");
					tv_note_num.setText(mMineCollect.field9);
					
				}else if(tag==1){
					tv_note_msg.setText("邀请(人)");
					//tv_note_msg1.setText("人");
					tv_note_num.setText(mMineCollect.field9);
				}else if(tag==2){
					tv_note_msg.setText("收益（金币）");
					//tv_note_msg1.setText("金币");
					tv_note_num.setText(mMineCollect.field9);
				}

				String vipstring=mMineCollect.field7;
				switch (vipstring){
					case "VIP1":
						ranking_player_line_vip_iv.setBackgroundResource(R.drawable.vip_one);
						break;
					case "VIP2":
						ranking_player_line_vip_iv.setBackgroundResource(R.drawable.vip_two);
						break;
					case "VIP3":
						ranking_player_line_vip_iv.setBackgroundResource(R.drawable.vip_three);
						break;
					case "VIP4":
						ranking_player_line_vip_iv.setBackgroundResource(R.drawable.vip_four);
						break;
					case "VIP5":
						ranking_player_line_vip_iv.setBackgroundResource(R.drawable.vip_five);
						break;
					case "黑钻":
						ranking_player_line_vip_iv.setBackgroundResource(R.drawable.vip_black);
						break;


				}
			}
		}
	}
	

}