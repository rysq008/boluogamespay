package com.game.helper.adapter.community;

import com.game.helper.R;
import com.game.helper.model.BaseViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityAdapter extends BaseAdapter {
	private Context mContext;
	public String[] img_text = { "公会基地", "资讯中心", "动态圈", "摇一摇"};
	public int[] imgs = { 
			R.drawable._shiqu_114, R.drawable._shiqu_116,
			R.drawable._shiqu_118, R.drawable._shiqu_120};
	
	
	public CommunityAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_gridview_communitys, parent, false);
		}
		//TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setBackgroundResource(imgs[position]);

		//tv.setText(img_text[position]);
		return convertView;
	}



}
