package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineProblemsAdapter.ViewHolder;
import com.game.helper.sdk.model.returns.GetfeedBack.FeedBack;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.MineFeedAdapter.java
 * @Author lbb
 * @Date 2016年11月24日 下午6:28:52
 * @Company 
 */
public class MineFeedAdapter  extends BaseAdapter{

	private List<Map<String, Object>> mList=new ArrayList<Map<String, Object>>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public MineFeedAdapter(Context mContext,List<FeedBack> list) {
		super();
		this.mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		initDatas(list);
	}
	
	

	public List<Map<String, Object>> getmList() {
		return mList;
	}

	public void setmLists(List<FeedBack> list) {
		initDatas(list);
		notifyDataSetChanged();
	}
	private List<Map<String, Object>> initDatas(List<FeedBack> orders){
		mList.clear();
		if(orders!=null && !orders.isEmpty()){
			Map<String, Object> map;
			for(FeedBack mLineInfo:orders){
				map=new HashMap<String, Object>();
				map.put("FeedBack", mLineInfo);
				map.put("sel", 0);
				mList.add(map);
			}
			mList.get(0).put("sel",0);
		}
		return mList;
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

	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_mine_problems, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map=(Map<String, Object>) getItem(position);
		final FeedBack mQuestion =(FeedBack) map.get("FeedBack");
		final int sel= (Integer) map.get("sel");
		holder.setData(mQuestion,position);
		if(sel==1){
			holder.msg.setVisibility(View.VISIBLE);
			holder.title.setTextColor(mContext.getResources().getColor(R.color.light_blue));
		}else{
			holder.msg.setVisibility(View.GONE);
			holder.title.setTextColor(mContext.getResources().getColor(R.color.gh_shenhui_color));
		}
		
		holder.top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(sel==1){
					for(int i=0;i<mList.size();i++){
						mList.get(i).put("sel", 0);
					}
					notifyDataSetChanged();
				}else{
					for(int i=0;i<mList.size();i++){
						mList.get(i).put("sel", 0);
						if(i==position){
							mList.get(i).put("sel", 1);
						}
					}
					notifyDataSetChanged();
				}
			}
		});
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.title) TextView title;
		public @BindView(R.id.msg) TextView msg;
		public @BindView(R.id.top) LinearLayout top;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(FeedBack mQuestion,int position){
			if(mQuestion!=null){
				title.setText((position+1)+"."+mQuestion.feedbackContent);
				if(!TextUtils.isEmpty(mQuestion.field1)){
					msg.setText("\n"+mQuestion.field1+"\n");
				}else{
					msg.setText("\n"+"等待回复中..."+"\n");
				}
				
			}
		}
		
	}
}
