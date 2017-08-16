package com.game.helper.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.R;
import com.game.helper.model.home.SearchMsg;

public class GameHistorySearchAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<SearchMsg> mList=new ArrayList<SearchMsg>();
	
	public GameHistorySearchAdapter(Context mContext,List<SearchMsg> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<SearchMsg> getmList() {
		return mList;
	}

	public void setmList(List<SearchMsg> mList) {
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
		
		return mList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_home_history_game_search, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_game.setText(mList.get(position).msg);
		
		return convertView;
	}

	class ViewHolder {
		
		public @BindView(R.id.tv_game) TextView tv_game;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		
	}
	

}
