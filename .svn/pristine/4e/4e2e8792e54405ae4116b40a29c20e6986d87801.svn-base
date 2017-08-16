package com.game.helper.adapter.mine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.MyWithdraw.Withdraw;
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
 * @Path com.game.helper.adapter.mine.WithdrawCashAdapter.java
 * @Author lbb
 * @Date 2016年11月25日 上午9:26:07
 * @Company 
 */
public class WithdrawCashAdapter extends BaseAdapter{

	private List<Withdraw> mList=new ArrayList<Withdraw>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public WithdrawCashAdapter(Context mContext,List<Withdraw> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<Withdraw> getmList() {
		return mList;
	}

	public void setmList(List<Withdraw> mList) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_mine_zhichu, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Withdraw mMineCollect=(Withdraw) getItem(position);
		holder.setData(mMineCollect);
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.time) TextView time;
		public @BindView(R.id.type) TextView type;
		public @BindView(R.id.money) TextView money;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Withdraw mMineCollect){
			if(mMineCollect!=null){
				
				if(!TextUtils.isEmpty(mMineCollect.dealTimeString)){
					time.setText(mMineCollect.dealTimeString);
				}else{
					time.setText(mMineCollect.createTimeString);
				}
				type.setText(mMineCollect.status);
				BigDecimal bd = new BigDecimal(mMineCollect.ptb);
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
				money.setText(""+bd.toString());
				
			}
		}
	}
}