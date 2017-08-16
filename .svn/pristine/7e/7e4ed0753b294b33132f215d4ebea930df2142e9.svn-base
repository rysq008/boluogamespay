package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetGuildUser.GetGuildUserData;
import com.game.helper.view.widget.CircleImageView;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Path com.game.helper.adapter.community.DeleteSociatyMembersAdapter.java
 * @Author lbb
 * @Date 2016年8月30日 下午12:23:46
 * @Company
 */
public class DeleteSociatyMembersAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<Map<String, Object>> mDatas= new ArrayList<Map<String, Object>>();
	public DeleteSociatyMembersAdapter(Context mContext,List<GetGuildUserData> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		initDatas(mList);

	}
	public List<Map<String, Object>> getmDatas() {
		return mDatas;
	}
	public void setmDatas(List<GetGuildUserData> list) {
		initDatas(list);
		notifyDataSetChanged();
	}
	private List<Map<String, Object>> initDatas(List<GetGuildUserData> lists){
		mDatas.clear();
		if(lists!=null && !lists.isEmpty()){
			Map<String, Object> map;
			for(GetGuildUserData mLineInfo:lists){
				map=new HashMap<String, Object>();
				map.put("GetGuildUserData", mLineInfo);
				map.put("sel", 0);
				mDatas.add(map);
			}
			mDatas.get(0).put("sel",0);
		}
		return mDatas;
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
			convertView = mInflater.inflate(R.layout.item_listview_community_sociaty_members, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> map= mDatas.get(position);
		final GetGuildUserData line =(GetGuildUserData) map.get("GetGuildUserData");
		final int sel= (Integer) map.get("sel");
		if(sel==1){
			holder.cb_clause.setChecked(true);
		}else{
			holder.cb_clause.setChecked(false);
		}
		holder.setData(line);
		return convertView;
	}

	class ViewHolder {
        @BindView(R.id.iv_icon)
        CircleImageView iv_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.cb_clause)
        CheckBox cb_clause;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GetGuildUserData mGetGuildUserData){
			if(mGetGuildUserData!=null){
				tv_name.setText(mGetGuildUserData.nickName);

				if(!TextUtils.isEmpty(mGetGuildUserData.icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mGetGuildUserData.fileAskPath+mGetGuildUserData.icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(iv_icon);
				}else{
					iv_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
			}
		}
	}


}
