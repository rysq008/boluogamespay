package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetCard.Card;
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
 * @Path com.game.helper.adapter.home.FirstRechargeableCardAdapter.java
 * @Author lbb
 * @Date 2016年10月21日 下午7:53:18
 * @Company 
 */
public class FirstRechargeableCardAdapter extends BaseAdapter{

	private List<Card> mList=new ArrayList<Card>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public FirstRechargeableCardAdapter(Context mContext,List<Card> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<Card> getmList() {
		return mList;
	}

	public void setmList(List<Card> mList) {
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
			convertView = mInflater.inflate(R.layout.listview_firstrechargeablecard, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Card mInfoAct=(Card) getItem(position);
		holder.setData(mInfoAct);
		return convertView;
	}
	class ViewHolder {
		
		public @BindView(R.id.tv_gets) TextView tv_gets;
		public @BindView(R.id.cardName) TextView cardName;
		public @BindView(R.id.cardTime) TextView cardTime;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Card mCard){
			if(mCard!=null){
				tv_gets.setText(mCard.money);
				cardName.setText(mCard.cardName);
				cardTime.setVisibility(View.INVISIBLE);
				cardTime.setText("有效期至"+mCard.useTimeString);
				
			}
		}
	}

}

