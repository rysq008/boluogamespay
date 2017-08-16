package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.QueryHotWord.HotWord;

public class HotGameAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	private List<HotWord> hotWords=new ArrayList<HotWord>();
	
	public HotGameAdapter(Context mContext,List<HotWord> hotWords) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.hotWords=hotWords;
	}

	public List<HotWord> getHotWords() {
		return hotWords;
	}

	public void setHotWords(List<HotWord> hotWords) {
		this.hotWords = hotWords;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotWords.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hotWords.get(position);
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
			convertView = mInflater.inflate(R.layout.item_gridview_home_hot_search_game, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		HotWord mMineCollect=(HotWord) getItem(position);
		holder.setData(mMineCollect);
		
		return convertView;
	}

	class ViewHolder {
		
		public @BindView(R.id.tv_game) TextView tv_game;
		

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(HotWord mHotWord){
			tv_game.setText(mHotWord.hotWord);
		}
	}
	

}
