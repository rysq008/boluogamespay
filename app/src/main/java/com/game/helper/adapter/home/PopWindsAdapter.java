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
public class PopWindsAdapter extends BaseAdapter{

	private List<Contact> mList=new ArrayList<Contact>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public PopWindsAdapter(Context mContext,List<Contact> mList) {
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
			convertView = mInflater.inflate(R.layout.item_pop_winds, null);
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
					if(mContact.type.equals("1")){
						if(position==0){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf1));
						}else if(position==1){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf2));
						}else if(position==2){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf3));
						}else if(position==3){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf4));
						}else if(position==4){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf5));
						}else if(position==5){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf6));
						}else if(position==6){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf7));
						}else if(position==7){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf8));
						}else if(position==8){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf9));
						}else if(position==9){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf10));
						}else if(position==10){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf11));
						}else if(position==11){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf12));
						}else if(position==12){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf13));
						}else if(position==13){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf14));
						}else if(position==14){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf15));
						}else if(position==15){
							tv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qqkf16));
						}
						tv2.setText(mContact.contactName);
					}/*else if(mContact.type.equals("1")){
						tv1.setText("QQ客服:"+mContact.contactWay);
					}else if(mContact.type.equals("2")){
						tv1.setText("QQ群:"+mContact.contactWay);
					}*/
				}
				
				
			}
		}
	}

}
