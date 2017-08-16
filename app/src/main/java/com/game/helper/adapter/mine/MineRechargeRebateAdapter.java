package com.game.helper.adapter.mine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.GetTradeList.Trade;
import com.game.helper.view.widget.CircleImageView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description 充值返利
 * @Path com.game.helper.adapter.mine.MineRechargeRebateAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 下午7:03:35
 * @Company 
 */
public class MineRechargeRebateAdapter extends BaseAdapter{

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<Trade> mList=new ArrayList<Trade>();
	public MineRechargeRebateAdapter(Context mContext,List<Trade> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}


	public List<Trade> getmList() {
		return mList;
	}


	public void setmList(List<Trade> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_mine_recharge_rebate, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Trade mZhichuModel=(Trade) getItem(position);
		holder.setData(mZhichuModel);
		holder.iv_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", mZhichuModel.field3);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle); 
				
			}
		});
		return convertView;
	}
	class ViewHolder {
		
		
		public @BindView(R.id.iv_item) CircleImageView iv_item;
		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.tv_time) TextView tv_time;
		
		
		public @BindView(R.id.gameNmae) TextView gameNmae;
		public @BindView(R.id.money) TextView money;
		
		public @BindView(R.id.add) TextView add;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData( Trade mMineCollect){
			if(!TextUtils.isEmpty(mMineCollect.field6)){
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mMineCollect.fileAskPath+mMineCollect.field6)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.pic_moren)//加载失败时显示的图片
				//.crossFade()
				.into(iv_item);
			}else{
				iv_item.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
			}
			
			tv_item.setText(mMineCollect.field4);
			tv_time.setText(mMineCollect.createTimeString);
			
			
			gameNmae.setText(mMineCollect.field2);
		
			money.setText(mMineCollect.field5);
			
			BigDecimal bd = new BigDecimal(mMineCollect.ptb);
			bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			add.setText("+"+bd.toString());
		}
	}
}
