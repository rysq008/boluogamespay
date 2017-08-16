package com.game.helper.adapter.mine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetTradeList.Trade;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.ShouruAdapter.java
 * @Author lbb
 * @Date 2016年8月19日 下午5:26:52
 * @Company 
 */
public class ShouruAdapter  extends BaseAdapter{
	
	private List<Trade> mList=new ArrayList<Trade>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public ShouruAdapter(Context mContext,List<Trade> mList) {
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
		Trade mZhichuModel=(Trade) getItem(position);
		holder.setData(mZhichuModel);
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.time) TextView time;
		public @BindView(R.id.type) TextView type;
		public @BindView(R.id.money) TextView money;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Trade mMineCollect){
			if(mMineCollect!=null){
				time.setText(mMineCollect.createTimeString);
				type.setText(mMineCollect.tradeName);
				BigDecimal bd = new BigDecimal(mMineCollect.ptb);
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
				money.setText("+"+bd.toString());
			}
		}
		
	}
}
