package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.GetSignList.GetSignListData;
import com.game.helper.view.widget.CircleImageView;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.community.SociatySignInAdapter.java
 * @Author lbb
 * @Date 2016年8月25日 上午11:16:37
 * @Company 
 */
public class SociatySignInAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<GetSignListData> datas=new ArrayList<GetSignListData>();
	public SociatySignInAdapter(Context mContext,List<GetSignListData> datas) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.datas=datas;
	}

	public List<GetSignListData> getDatas() {
		return datas;
	}

	public void setDatas(List<GetSignListData> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_community_sociaty_sign_in, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(datas.get(position));
		holder.iv_icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", datas.get(position).userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
				
			}
		});
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_icon) CircleImageView iv_icon;
		public @BindView(R.id.tv_name) TextView tv_name;
		
		public @BindView(R.id.tv_msg2) TextView tv_msg2;


		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GetSignListData mGetSignListData){
			if(mGetSignListData!=null){
				
				
				tv_name.setText(mGetSignListData.nickName);
				tv_msg2.setText(mGetSignListData.total);
				if(!TextUtils.isEmpty(mGetSignListData.icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mGetSignListData.fileAskPath+mGetSignListData.icon)
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