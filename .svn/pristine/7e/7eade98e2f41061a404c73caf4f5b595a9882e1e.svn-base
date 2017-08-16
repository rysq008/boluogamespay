package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.model.home.Cgametype;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.SortingThridAdapter.java
 * @Author lbb
 * @Date 2016年10月10日 上午11:30:03
 * @Company 
 */
public class SortingThridAdapter extends BaseAdapter{
	private Context context;
	protected LayoutInflater mInflater;
	protected List<Cgametype> mDatas = new ArrayList<Cgametype>();
	public SortingThridAdapter(Context context,List<Cgametype> datas) {
		super();
		mInflater = LayoutInflater.from(context);
		this.context=context;
		this.mDatas=datas;
	}
	public List<Cgametype> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<Cgametype> mDatas) {
		this.mDatas = mDatas;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_home_game_classify, null);
			holder = new ViewHolder();
			holder.tv_H5=(TextView) convertView.findViewById(R.id.tv_H5);
			convertView.setTag(holder); 
		} else {
			holder = (ViewHolder) convertView.getTag();  
		}
		Cgametype item = (Cgametype) getItem(position);
		if (item.isSel == 1) {
			holder.tv_H5.setSelected(false);
		}else{
			holder.tv_H5.setSelected(true);
		}
		holder.tv_H5.setText(item.typeName);
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tv_H5;
	}

}