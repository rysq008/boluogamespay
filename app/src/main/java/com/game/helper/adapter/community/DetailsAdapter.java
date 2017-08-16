package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.GetGiveList.GetGiveListData;
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
 * @Description 明细
 * @Path com.game.helper.adapter.community.DetailAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 下午5:30:29
 * @Company 
 */
public class DetailsAdapter  extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	private List<GetGiveListData> data=new ArrayList<GetGiveListData>();
	public DetailsAdapter(Context mContext,List<GetGiveListData> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	public List<GetGiveListData> getData() {
		return data;
	}

	public void setData(List<GetGiveListData> data) {
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
			convertView = mInflater.inflate(R.layout.item_listview_community_details, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GetGiveListData mMineCollect=(GetGiveListData) getItem(position);
		holder.setData(mMineCollect);
		holder.iv_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putString("userId", mMineCollect.operId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle); 
			}
		});
		return convertView;
	}

	class ViewHolder {
		
		
		public @BindView(R.id.iv_item) CircleImageView iv_item;
		
		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.tv_time) TextView tv_time;
		
		public @BindView(R.id.iv_gift) ImageView iv_gift;
		public @BindView(R.id.tv_giftnum) TextView tv_giftnum;
		public @BindView(R.id.tv_giftprice) TextView tv_giftprice;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GetGiveListData mGetGiveListData){
			if(mGetGiveListData!=null){
				if(!TextUtils.isEmpty(mGetGiveListData.icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mGetGiveListData.fileAskPath+mGetGiveListData.icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(iv_item);
				}else{
					iv_item.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mGetGiveListData.fileAskPath+mGetGiveListData.giftIcon)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(iv_gift);
				tv_item.setText(mGetGiveListData.nickName);
				tv_time.setText(mGetGiveListData.createTimeString);
				tv_giftnum.setText("x"+mGetGiveListData.field1);
				tv_giftprice.setText(mGetGiveListData.benefit);
				
			}
		}
	}
	

}
