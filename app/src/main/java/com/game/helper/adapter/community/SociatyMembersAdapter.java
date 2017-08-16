package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;

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
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.community.SociatyMembersAdapter.java
 * @Author lbb
 * @Date 2016年8月25日 下午3:36:58
 * @Company 
 */
public class SociatyMembersAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	List<GetGuildUserData> data=new ArrayList<GetGuildUserData>();
	public SociatyMembersAdapter(Context mContext,List<GetGuildUserData> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	public List<GetGuildUserData> getData() {
		return data;
	}

	public void setData(List<GetGuildUserData> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
		holder.setData(data.get(position));
		holder.cb_clause.setVisibility(View.GONE);
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_icon) CircleImageView iv_icon;
		public @BindView(R.id.tv_name) TextView tv_name;
		
		public @BindView(R.id.cb_clause) CheckBox cb_clause;
		
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