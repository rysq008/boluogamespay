package com.game.helper.adapter.mall;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.BindView;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetExchangelist.Exchange;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MallExchangeRecordAdapter extends BaseAdapter {

	private List<Exchange> mList=new ArrayList<Exchange>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public MallExchangeRecordAdapter(Context mContext,List<Exchange> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<Exchange> getmList() {
		return mList;
	}

	public void setmList(List<Exchange> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_mall_exchange_record, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Exchange mMineCollect=(Exchange) getItem(position);
		holder.setData(mMineCollect);
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.detail) TextView detail;
		public @BindView(R.id.money) TextView money;
		public @BindView(R.id.state) TextView state;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Exchange mMineCollect){
			if(mMineCollect!=null){
				detail.setText(mMineCollect.goodName);
				money.setText("-"+mMineCollect.ptb);
				if(!TextUtils.isEmpty(mMineCollect.status)&&mMineCollect.status.equals("Y")){
					state.setText("已受理");
				}else {
					state.setText("未受理");
				}
			}
		}
	}

}
