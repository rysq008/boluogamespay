package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.QueryGameCut.GameCut;
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
 * @Description
 * @Path com.game.helper.fragment.home.StrategyAdapter.java
 * @Author lbb
 * @Date 2016年8月23日 下午2:19:23
 * @Company 
 */
public class StrategyAdapter extends BaseAdapter {

	private List<GameCut> mList=new ArrayList<GameCut>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public StrategyAdapter(Context mContext,List<GameCut> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
		
	}

	public List<GameCut> getmList() {
		return mList;
	}

	public void setmList(List<GameCut> mList) {
		
		this.mList = mList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();//mList.size()
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_home_strategy, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GameCut mGameCut=(GameCut) getItem(position);
		holder.setData(mGameCut);
		holder.iv_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", mGameCut.userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
				
			}
		});
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) CircleImageView iv_item;
		
		public @BindView(R.id.logo)  ImageView logo;
		public @BindView(R.id.tv_item) TextView tv_item;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(final GameCut mGameCut){
			if(mGameCut!=null){
				
				if(!TextUtils.isEmpty(mGameCut.userIcon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mGameCut.fileAskPath+mGameCut.userIcon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(iv_item);
				}else{
					iv_item.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				
				
				tv_item.setText(mGameCut.title);
				tv_msg.setText(mGameCut.content);
				if(!TextUtils.isEmpty(mGameCut.orderby)&&mGameCut.orderby.equals("0")){
					logo.setVisibility(View.VISIBLE);
				}else{
					logo.setVisibility(View.GONE);
				}
			}
		}
	}
	

}
