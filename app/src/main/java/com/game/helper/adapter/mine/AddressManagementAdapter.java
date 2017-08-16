package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineCollectAdapter.ViewHolder;
import com.game.helper.sdk.model.returns.GetAddressList.Address;

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
 * @Path com.game.helper.adapter.mine.AddressManagementAdapter.java
 * @Author lbb
 * @Date 2016年9月27日 上午11:50:48
 * @Company 
 */
public class AddressManagementAdapter  extends BaseAdapter{

	private List<Address> mList=new ArrayList<Address>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public AddressManagementAdapter(Context mContext,List<Address> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<Address> getmList() {
		return mList;
	}

	public void setmList(List<Address> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_mine_address_management, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Address mAddress=(Address) getItem(position);
		holder.setData(mAddress);
		return convertView;
		
	}
	class ViewHolder {
		public @BindView(R.id.tv_name) TextView tv_name;
		public @BindView(R.id.tv_phone) TextView tv_phone;
		public @BindView(R.id.tv_address) TextView tv_address;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Address mAddress){
			if(mAddress!=null){
				tv_name.setText(mAddress.realName);
				tv_phone.setText(mAddress.phone);
				String cityName="";
				if(!TextUtils.isEmpty(mAddress.cityName)){
					cityName=mAddress.cityName;
				}
				String areaName="";
				if(!TextUtils.isEmpty(mAddress.areaName)){
					areaName=mAddress.areaName;
				}
				String address="";
				if(!TextUtils.isEmpty(mAddress.address)){
					address=mAddress.address;
				}
				tv_address.setText(cityName+areaName+" "+address);
			}
		}
		
	}
}