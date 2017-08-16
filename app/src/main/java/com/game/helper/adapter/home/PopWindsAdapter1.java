package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetContactList.Contact;
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
 * @Path com.game.helper.adapter.home.PopWindsAdapter.java
 * @Author lbb
 * @Date 2016年9月26日 下午8:29:59
 * @Company 
 */
public class PopWindsAdapter1 extends BaseAdapter{

	private List<Contact> mList=new ArrayList<Contact>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public PopWindsAdapter1(Context mContext,List<Contact> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<Contact> getmList() {
		return mList;
	}

	public void setmList(List<Contact> mList) {
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
			convertView = mInflater.inflate(R.layout.item_pop_winds1, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Contact mContact=(Contact) getItem(position);
		holder.setData(mContact,position);
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.tv1) ImageView tv1;
		public @BindView(R.id.tv2) TextView tv2;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Contact mContact,int position){
			if(mContact!=null){
				if(!TextUtils.isEmpty(mContact.type)){
					/*if(mContact.type.equals("0")){
						tv2.setText("电话客服:"+mContact.contactWay);
					}else if(mContact.type.equals("1")){
						tv2.setText("QQ客服:"+mContact.contactWay);
					}else */if(mContact.type.equals("2")){
						tv2.setText(mContact.contactName);
					}
				}
				
				
			}
		}
	}

}
