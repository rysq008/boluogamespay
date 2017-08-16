package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.GetRank.GetRankData;
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
 * @Path com.game.helper.adapter.community.DetailAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 下午3:01:30
 * @Company 
 */
public class ListsAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	private List<GetRankData> mList=new ArrayList<GetRankData>();
	public ListsAdapter(Context mContext,List<GetRankData> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<GetRankData> getmList() {
		return mList;
	}

	public void setmList(List<GetRankData> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
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
			convertView = mInflater.inflate(R.layout.item_listview_community_lists, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GetRankData mGetRankData=(GetRankData) getItem(position);
		holder.setData(mGetRankData,position);
		holder.imageView_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", mGetRankData.userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle); 
			}
		});
		return convertView;
	}

	class ViewHolder {
		
		
		public @BindView(R.id.imageView_pic) CircleImageView imageView_pic;
		public @BindView(R.id.iv_item) TextView iv_item;//排行号数
		
		public @BindView(R.id.tv_item) TextView tv_item;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;
		
		
		public @BindView(R.id.tv_note_msg) TextView tv_note_msg;
		
		public @BindView(R.id.tv_note_num) TextView tv_note_num;
		

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GetRankData mGetRankData , int position){
			if(mGetRankData!=null){
				iv_item.setText(""+(position+4));
				tv_item.setText(mGetRankData.nickName);
				tv_note_num.setText(mGetRankData.totalSum);
				
				if(!TextUtils.isEmpty(mGetRankData.icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mGetRankData.fileAskPath+mGetRankData.icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic);
				}else{
					imageView_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
			}
		}
	}
	

}
