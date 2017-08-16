package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetMyGameOrder.GameOrder;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.MineGameOrdersAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 下午6:35:17
 * @Company 
 */
public class MineGameOrdersAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<GameOrder> data=new ArrayList<GameOrder>();
	public MineGameOrdersAdapter(Context mContext,List<GameOrder> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	public List<GameOrder> getData() {
		return data;
	}

	public void setData(List<GameOrder> data) {
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
			convertView = mInflater.inflate(R.layout.item_listview_mine_game_orders, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(data.get(position));
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_icon) XCRoundImageViewByXfermode iv_icon;
		public @BindView(R.id.tv_title) TextView tv_title;
		
		public @BindView(R.id.tv_orderid) TextView tv_orderid;
		
		//public @BindView(R.id.tv_num1) TextView tv_num1;
		//public @BindView(R.id.tv_num2) TextView tv_num2;
		public @BindView(R.id.tv_add) TextView tv_add;
		public @BindView(R.id.tv_time) TextView tv_time;
		
		//public @BindView(R.id.tv_num3) TextView tv_num3;
		//public @BindView(R.id.tv_num4) TextView tv_num4;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GameOrder mMineCollect){
			if(mMineCollect!=null){
				tv_orderid.setText(mMineCollect.orderNo);
				tv_title.setText(mMineCollect.gameName);
				//tv_num1.setText("充值金额：￥"+mMineCollect.money);
				//tv_num2.setText("实际支付：￥"+mMineCollect.realPay);
				/*if(TextUtils.isEmpty(mMineCollect.orderType)){
					tv_num3.setText("订单类型："+mMineCollect.orderType);
				}else if(mMineCollect.orderType.equals("sc")){
					tv_num3.setText("订单类型："+"首充");
				}else if(mMineCollect.orderType.equals("xc")){
					tv_num3.setText("订单类型："+"续充");
				}else{
					tv_num3.setText("订单类型："+mMineCollect.orderType);
				}*/
			
				/*if(TextUtils.isEmpty(mMineCollect.payWay)){
					tv_num4.setText("支付方式："+mMineCollect.payWay);
				}else if(mMineCollect.payWay.equals("ptb")){
					tv_num4.setText("支付方式："+"金币支付");
				}else if(mMineCollect.payWay.equals("alipay")){
					tv_num4.setText("支付方式："+"支付宝支付");
				}else if(mMineCollect.payWay.equals("weixin")){
					tv_num4.setText("支付方式："+"微信支付");
				}else{
					tv_num4.setText("支付方式："+mMineCollect.payWay);
				}*/
				
				if(!TextUtils.isEmpty(mMineCollect.dealStatus)&&mMineCollect.dealStatus.equals("Y")){
					//退款
					tv_add.setText("已退款");
					tv_time.setText(mMineCollect.field7);
				}else{
					if(!TextUtils.isEmpty(mMineCollect.status)&&mMineCollect.status.equals("2")){//0  未支付  1  已支付  2 已受理
						tv_add.setText("已到账");
						tv_time.setText(mMineCollect.payTimeString);
					}else if(!TextUtils.isEmpty(mMineCollect.status)&&mMineCollect.status.equals("1")){//0  未支付  1  已支付  2 已受理
						tv_add.setText("已支付");
						tv_time.setText(mMineCollect.payTimeString);
					}else{
						tv_add.setText("未支付");
						tv_time.setText(mMineCollect.createTimeString);
					}
				}
				
				
				iv_icon.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
				iv_icon.setRoundBorderRadius(23);
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mMineCollect.fileAskPath+mMineCollect.field1)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(iv_icon);
				
			}
		}
	}
	

}
