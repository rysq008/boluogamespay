package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.model.home.GameFrom;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.SortingTwoAdapter.java
 * @Author lbb
 * @Date 2016年8月23日 下午5:05:25
 * @Company 
 */
public class SortingTwoAdapter extends BaseAdapter{
	private Context context;
	protected LayoutInflater mInflater;
	protected List<GameFrom> mDatas = new ArrayList<GameFrom>();
	public SortingTwoAdapter(Context context,List<GameFrom> datas) {
		super();
		mInflater = LayoutInflater.from(context);
		this.context=context;
		this.mDatas=datas;
	}
	int choese=0;
	private int setpos;
	public List<GameFrom> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<GameFrom> mDatas) {
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
			convertView = mInflater.inflate(R.layout.item_listview_home_game_classify_sort, null);
			holder = new ViewHolder();
			holder.tv_sortingname=(TextView) convertView.findViewById(R.id.tv_sortingname);
			holder.iv_sel=(ImageView) convertView.findViewById(R.id.iv_sel);
			convertView.setTag(holder); 
		} else {
			holder = (ViewHolder) convertView.getTag();  
		}
		if (position == setpos) {
			holder.iv_sel.setImageDrawable(context.getResources().getDrawable(R.drawable.shouye_33));
			holder.tv_sortingname.setSelected(true);
		}else{
			holder.iv_sel.setImageDrawable(context.getResources().getDrawable(R.drawable.shouye_31));
			holder.tv_sortingname.setSelected(false);
		}
		GameFrom item = (GameFrom) getItem(position);
		holder.tv_sortingname.setText(item.platName);
		
		return convertView;
	}
	public void setpos(int pos){
		setpos = pos;
	}
	class ViewHolder {
		TextView tv_sortingname;
		ImageView iv_sel;
	}

}