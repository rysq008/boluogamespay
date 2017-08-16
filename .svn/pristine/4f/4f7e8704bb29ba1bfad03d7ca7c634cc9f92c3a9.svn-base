package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.R;
import com.game.helper.adapter.home.CommentAdapter.ViewHolder;
import com.game.helper.model.home.RecommendBoutique;

import android.content.Context;
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
 * @Path com.game.helper.adapter.home.RechargeAdapter.java
 * @Author lbb
 * @Date 2016年8月23日 下午4:16:19
 * @Company 
 */
public class RechargeAdapter extends BaseAdapter {

	private List<RecommendBoutique> mList=new ArrayList<RecommendBoutique>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public RechargeAdapter(Context mContext,List<RecommendBoutique> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
		
	}

	public List<RecommendBoutique> getmList() {
		return mList;
	}

	public void setmList(List<RecommendBoutique> mList) {
		
		this.mList = mList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;//mList.size()
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
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
			convertView = mInflater.inflate(R.layout.item_listview_home_recharge, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final RecommendBoutique mMineCollect=(RecommendBoutique) getItem(position);
		holder.setData(mMineCollect);
		
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.tv_title) TextView tv_title;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;
		public @BindView(R.id.tv_num) TextView tv_num;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(RecommendBoutique mMineCollect){
			if(mMineCollect!=null){
			}
		}
	}
	

}