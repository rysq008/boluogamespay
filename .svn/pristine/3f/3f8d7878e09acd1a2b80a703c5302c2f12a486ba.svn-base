package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.QueryGameGift.GameGift;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.HomeGameGiftsAdapter.java
 * @Author lbb
 * @Date 2016年8月24日 下午5:00:36
 * @Company 
 */
public class HomeGameGiftsAdapter extends BaseAdapter {

	private List<GameGift> mList=new ArrayList<GameGift>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public HomeGameGiftsAdapter(Context mContext,List<GameGift> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
		
	}

	public List<GameGift> getmList() {
		return mList;
	}

	public void setmList(List<GameGift> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_home_game_gifts, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GameGift mGift=(GameGift) getItem(position);
		holder.setData(mGift);
		
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) XCRoundImageViewByXfermode iv_item;
		public @BindView(R.id.tv_item) TextView tv_item;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;
		public @BindView(R.id.tv_msg2) TextView tv_msg2;

		public @BindView(R.id.tv_download) TextView tv_download;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GameGift mGift){
			if(mGift!=null){
				iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
				iv_item.setRoundBorderRadius(23);
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mGift.fileAskPath+mGift.logo)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.picture_defeated)//加载失败时显示的图片
				//.crossFade()
				.into(iv_item);
				tv_item.setText(mGift.gameName);
				if(!TextUtils.isEmpty(mGift.giftKindNum)){
					tv_msg.setText("礼包种类："+mGift.giftKindNum+"种");	
				}else{
					tv_msg.setText("礼包种类："+0+"种");
				}
				if(!TextUtils.isEmpty(mGift.lastGift)){
					tv_msg2.setText("最新礼包："+mGift.lastGift);
					tv_msg2.setVisibility(View.VISIBLE);
				}else{
					tv_msg2.setVisibility(View.INVISIBLE);
				}
				
			}
		}
	}
	

}
