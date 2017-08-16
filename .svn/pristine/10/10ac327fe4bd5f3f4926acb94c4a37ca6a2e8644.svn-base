package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetQuestionlist.Question;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.MineProblemsAdapter.java
 * @Author lbb
 * @Date 2016年8月19日 下午6:27:55
 * @Company 
 */
public class MineProblemsAdapter extends BaseAdapter{

	private List<Map<String, Object>> mList=new ArrayList<Map<String, Object>>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public MineProblemsAdapter(Context mContext,List<Question> list) {
		super();
		this.mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		initDatas(list);
	}
	
	

	public List<Map<String, Object>> getmList() {
		return mList;
	}

	public void setmLists(List<Question> list) {
		initDatas(list);
		notifyDataSetChanged();
	}
	private List<Map<String, Object>> initDatas(List<Question> orders){
		mList.clear();
		if(orders!=null && !orders.isEmpty()){
			Map<String, Object> map;
			for(Question mLineInfo:orders){
				map=new HashMap<String, Object>();
				map.put("Question", mLineInfo);
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
		final Question mQuestion =(Question) map.get("Question");
		final int sel= (Integer) map.get("sel");
		holder.setData(mQuestion);
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
		public void setData(Question mQuestion){
			if(mQuestion!=null){
				title.setText(mQuestion.orderBy+"."+mQuestion.questionName);
				msg.setText(mQuestion.context);
			}
		}
		
	}
}
